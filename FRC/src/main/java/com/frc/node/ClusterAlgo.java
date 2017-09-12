package com.frc.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;

import redis.clients.util.MurmurHash;

import com.frc.core.redis.RedisClient;
import com.frc.loader.PropertiesLoader;
import com.frc.redis.domain.RedisInfo;
import com.frc.redis.domain.RedisState;
import com.frc.utility.FRCLogger;
import com.frc.utility.JSONProvider;

/**
 * @author songbin
 * @version 2016年8月26日
 */
public class ClusterAlgo {
    /**
     * save the map between redis and vnode, this will be invoked when user
     * remove a redis from cluster
     */
    private static HashMap<String, List<Long>>  mapRedisVnodeList = new HashMap<String, List<Long>>();
    /** save the map between hash of vnode and hash of name of redis's instance */
    private static TreeMap<Long, Long>          vNodes            = new TreeMap<Long, Long>();
    /** map between hash of redis's name and redis client instance */
    private static SortedMap<Long, RedisClient> mapRedis          = new TreeMap<Long, RedisClient>();

    private static MurmurHash                   HASH_ALGO         = new MurmurHash();

    private static Lock                         lock              = new ReentrantLock();

    private static String getClassName() {
        return "ClusterAlgo";
    }

    public static void addRedis(long logIndex, RedisClient redisClient) {

        String flag = getClassName() + ".addRedis";
        lock.lock();
        try {
            _addRedis(logIndex, redisClient);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "", e);
        } finally {
            lock.unlock();
        }

    }

    public static void updateRedis(long logIndex, RedisInfo redisInfo) {

        if (null == redisInfo || StringUtils.isBlank(redisInfo.getRedis_name())) {
            return;
        }

        String flag = getClassName() + ".updateRedis";
        lock.lock();
        try {
            RedisClient client = new RedisClient();
            client.init(logIndex, redisInfo);

            RedisClient rmClient = new RedisClient();
            rmClient.setRedis_name(redisInfo.getRedis_name());
            removeRedis(logIndex, rmClient);

            addRedis(logIndex, client);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "", e);
        } finally {
            lock.unlock();
        }

    }

    public static void removeRedis(long logIndex, RedisClient redisClient) {
        String flag = getClassName() + ".removeRedis";
        lock.lock();
        try {
            _removeRedis(logIndex, redisClient);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * get the next redis intance according to the key
     * 
     * @return redisClient RedisClient
     * */
    public static RedisClient getNextRedis(long logIndex, String key) {
        String flag = getClassName() + ".RedisClient";
        lock.lock();
        try {
            return _getNextRedisByHash(logIndex, HASH_ALGO.hash(key));
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
            return null;
        } finally {
            lock.unlock();
        }

    }

    /**
     * get the current redis intance according to the key
     * 
     * @return redisClient RedisClient
     * */
    public static RedisClient getCurrentRedis(long logIndex, String key) {
        String flag = getClassName() + ".getCurrentRedis";
        lock.lock();
        try {

            if (mapRedis.isEmpty()) {
                return null;
            }
            if (1 == mapRedis.size()) {
                return mapRedis.get(mapRedis.firstKey());
            }

            Long key_hash = HASH_ALGO.hash(key);
            Long target_vnode_hash = null;
            Long target_redis_hash = null;

            SortedMap<Long, Long> tailMap = vNodes.tailMap(key_hash);

            if (null == tailMap) {
                tailMap = vNodes;
            }

            target_vnode_hash = tailMap.firstKey();
            target_redis_hash = tailMap.get(target_vnode_hash);
            return mapRedis.get(target_redis_hash);

        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
            return null;
        } finally {
            lock.unlock();
        }

    }

    /**return the redis instance which key exists on*/
    public static RedisClient migrateDB(long logIndex, String key, RedisClient curr) {
        String flag = getClassName() + ".migrateDB";

        lock.lock();
        try {
            if (curr.getRedis_state() == RedisState.STATE_NORMAL)
                return curr;

            RedisClient next = _getNextRedisByHash(logIndex, HASH_ALGO.hash(key));//getNextRedis(logIndex, key);
            if (null == next) {
                FRCLogger.getInstance().error(logIndex, flag, "had del all of redis", null);
                return curr;
            }

            RedisClient target_cli = curr;

            switch (curr.getRedis_state()) {
                case RedisState.STATE_ADD:
                    if (!curr.exists(logIndex, key)) {
                        next.migrate(logIndex, key, curr);
                    }
                    target_cli = curr;
                    break;
                case RedisState.STATE_DEL:
                    if (!next.exists(logIndex, key)) {
                        curr.migrate(logIndex, key, next);
                    }
                    target_cli = next;
                    break;
            }
            return target_cli;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "key:{} source:{}  dest:{}", e, key,
                JSONProvider.toJSONString(logIndex, curr));
            return null;
        } finally {
            lock.unlock();
        }

    }

    /**get list of next redis of all of this redis's vnode 
     * */
    public static List<Long> getInstanceNext(long logIndex, String redis_name) {
        String flag = getClassName() + ".getInstanceNext";

        lock.lock();
        try {
            if (mapRedis.isEmpty() || 1 == mapRedis.size()) {
                return null;
            }

            List<Long> listVnodes = mapRedisVnodeList.get(redis_name);
            if (null == listVnodes || listVnodes.isEmpty())
                return null;

            List<Long> listNextRedis = new ArrayList<Long>();
            for (Long vnode_hash : listVnodes) {
                RedisClient client = _getNextRedisByHash(logIndex, vnode_hash);
                if (null == client)
                    continue;
                listNextRedis.add(HASH_ALGO.hash(client.getRedis_name()));
            }

            return listNextRedis;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "", e);
            return null;
        } finally {
            lock.unlock();
        }

    }

    private static void _removeRedis(long logIndex, RedisClient redisClient) {
        if (null == redisClient || StringUtils.isBlank(redisClient.getRedis_name())) {
            return;
        }
        String flag = getClassName() + ".removeRedis";
        try {
            String redis_name = redisClient.getRedis_name();
            Long hash_redis_name = HASH_ALGO.hash(redis_name);
            List<Long> listVnodes = mapRedisVnodeList.get(redis_name);
            if (null == listVnodes || listVnodes.isEmpty()) {
                FRCLogger.getInstance().info(logIndex, flag, "not found redis:{}", null,
                    redis_name);
                return;
            }
            for (Long hash_vnode : listVnodes) {
                vNodes.remove(hash_vnode);
            }
            mapRedisVnodeList.remove(redis_name);
            mapRedis.remove(hash_redis_name);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "", e);
        }

    }

    private static void _addRedis(long logIndex, RedisClient redisClient) {
        if (null == redisClient || StringUtils.isBlank(redisClient.getRedis_name())) {
            return;
        }

        List<Long> listVnodes = new ArrayList<Long>();
        String redis_name = redisClient.getRedis_name();
        Long hash_redis_name = HASH_ALGO.hash(redis_name);
        int vnode = PropertiesLoader.getIntance().getRedisVNode();

        for (int index = 0; index < vnode; index++) {
            String vname = redis_name + "_" + index;
            long key = HASH_ALGO.hash(vname);
            vNodes.put(key, hash_redis_name);
            listVnodes.add(key);
        }

        mapRedisVnodeList.put(redis_name, listVnodes);
        mapRedis.put(hash_redis_name, redisClient);
    }

    private static RedisClient _getNextRedisByHash(long logIndex, long key_hash) {
        String flag = getClassName() + ".getNextRedisByHash";

        try {
            if (mapRedis.isEmpty()) {
                return null;
            }
            if (1 == mapRedis.size()) {
                return mapRedis.get(mapRedis.firstKey());
            }

            SortedMap<Long, Long> tailMap = vNodes.tailMap(key_hash);
            Long target_vnode_hash = null;
            Long target_redis_hash = null;
            if (null == tailMap) {
                tailMap = vNodes;
            }

            target_vnode_hash = tailMap.firstKey();
            target_redis_hash = tailMap.get(target_vnode_hash);
            Long next_redis_hash = null;
            for (Long map_key : tailMap.keySet()) {
                next_redis_hash = tailMap.get(map_key);
                if (!target_redis_hash.equals(next_redis_hash)) {
                    break;
                }
            }

            if (null == next_redis_hash && tailMap != vNodes) {
                for (Long map_key : vNodes.keySet()) {
                    next_redis_hash = vNodes.get(map_key);
                    if (!target_redis_hash.equals(next_redis_hash)) {
                        break;
                    }
                }
            }
            return mapRedis.get(next_redis_hash);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key_hash);
            return null;
        } finally {

        }

    }

    public static final RedisClient redisInfo2Client(long logIndex, RedisInfo redisInfo) {
        if (null == redisInfo)
            return null;
        RedisClient client = new RedisClient();
        client.setRedis_host(redisInfo.getRedis_host());
        client.setRedis_name(redisInfo.getRedis_name());
        client.setRedis_passwd(redisInfo.getRedis_passwd());
        client.setRedis_port(redisInfo.getRedis_port());
        client.setRedis_state(redisInfo.getRedis_state());
        return client;
    }

    public static final RedisInfo redisClient2Info(long logIndex, RedisClient client) {
        if (null == client)
            return null;
        RedisInfo info = new RedisInfo();
        info.setRedis_host(client.getRedis_host());
        info.setRedis_name(client.getRedis_name());
        info.setRedis_passwd(client.getRedis_passwd());
        info.setRedis_port(client.getRedis_port());
        info.setRedis_state(client.getRedis_state());
        return info;
    }

    public static HashMap<String, List<Long>> getMapRedisVnodeList() {
        return mapRedisVnodeList;
    }

    public static TreeMap<Long, Long> getvNodes() {
        return vNodes;
    }

    public static SortedMap<Long, RedisClient> getMapRedis() {
        return mapRedis;
    }

}
