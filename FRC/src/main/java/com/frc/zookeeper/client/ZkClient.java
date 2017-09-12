package com.frc.zookeeper.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;

import com.frc.core.common.DefaultValues;
import com.frc.loader.PropertiesLoader;
import com.frc.node.ClusterNodeManager;
import com.frc.redis.domain.RedisInfo;
import com.frc.redis.domain.RedisState;
import com.frc.utility.FRCLogger;
import com.frc.utility.JSONProvider;

/**
 * @author songbin
 */
public class ZkClient {
    // private Map<String, JSONObject> rdMap = null;

    private static CuratorFramework client   = null;
    // private NodesManagerCenter nmc = NodesManagerCenter.getInstance();
    // private Map<String, String> mapNodes = null;
    private static String           ZK_ROOT  = PropertiesLoader.getIntance().getZookerRoot();

    private int                     rNodeNum = 0;

    private ClusterNodeManager      cnm      = new ClusterNodeManager();

    private String getClassName() {
        return "ZkClient";
    }

    /**
     * get data from the path
     * 
     * @author songbin
     */
    @SuppressWarnings("unused")
    private String readPath(long logIndex, String path) {
        String flag = getClassName() + ".readPath";
        String strVal = null;
        try {

            strVal = new String(ZkClient.client.getData().forPath(path));
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "path:{}  exception", e, path);
            return null;
        }

        return strVal;
    }

    /**
     * update data for the path
     * 
     * @author songbin
     */
    public boolean updatePath(long logIndex, String path, String data) {
        String flag = getClassName() + ".updatePath";
        try {
            String connString = PropertiesLoader.getIntance().getZookerHosts();
            CuratorFramework zkclient = CuratorFrameworkFactory.builder().connectString(connString)
                .retryPolicy(new RetryNTimes(5, 1000)).connectionTimeoutMs(1000).build();
            zkclient.start();

            zkclient.setData().forPath(path, data.getBytes());
            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "path:{}  exception", e, path);
            return false;
        }
    }

    /**
     * delete the path by recursion
     * 
     * @author songbin
     * @param String
     *            path the path will be delete by recursion
     */
    public boolean delPath(long logIndex, String path) {
        String flag = getClassName() + ".delPath";
        if (StringUtils.isBlank(path)) {
            return false;
        }

        String connString = PropertiesLoader.getIntance().getZookerHosts();
        CuratorFramework zkclient = CuratorFrameworkFactory.builder().connectString(connString)
            .retryPolicy(new RetryNTimes(5, 1000)).connectionTimeoutMs(1000).build();
        zkclient.start();

        try {
            zkclient.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "delete path:{}  exception", e, path);
            return false;
        } finally {
            zkclient.close();
        }

        return true;
    }

    /**
     * fetch redis info from zookeeper cluster
     * **/
    private boolean initZookeeper(long logIndex) {
        String flag = getClassName() + ".init";
        String connString = PropertiesLoader.getIntance().getZookerHosts();

        // mapNodes = new HashMap<String, String>();

        client = CuratorFrameworkFactory.builder().connectString(connString)
            .retryPolicy(new RetryNTimes(5, 1000)).connectionTimeoutMs(1000).build();
        if (null == client) {
            FRCLogger.getInstance().warn(logIndex, flag, "create zookeeper client fail!", null);
            return false;
        }

        client.start();

        if (!this.startWatch(logIndex, client)) {
            FRCLogger.getInstance().warn(logIndex, flag, "start watch fail!", null);
            client.close();
            return false;
        }
        return true;
    }

    /**
     * initialize the zookeeper client
     * 
     * @author songbin
     * @param logIndex
     * 
     */
    public boolean start(long logIndex) {
        return initZookeeper(logIndex);
    }

    private boolean startWatch(long logIndex, CuratorFramework zkclient) {
        String flag = getClassName() + ".startWatch";
        TreeCache cache = new TreeCache(zkclient, ZK_ROOT);
        try {

            cache.start();
            this.addListener(logIndex, cache);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "ZkWatchRunnable thread run fail!", e);
            return false;
        }
        return true;
    }

    private int getState(int stateVal) {
        int state = RedisState.STATE_UNKOWN;
        /* 0:init,1:noraml,2:del,3:add,4:destroy */
        switch (stateVal) {
            case RedisState.STATE_INIT:
            case RedisState.STATE_NORMAL:
            case RedisState.STATE_DEL:
            case RedisState.STATE_ADD:
            case RedisState.STATE_DESTROY:
                state = stateVal;
                break;
            default:
                return RedisState.STATE_UNKOWN;
        }
        return state;
    }

    /**
     * zookeeper listen the node add event ret: 0: failed ,1:the node is root
     * node，2：success
     */
    private int nodeAdd(long logIndex, CuratorFramework client, TreeCacheEvent event) {
        String flag = getClassName() + ".nodeAdd";

        String strNode = null;
        String strVal = null;
        String strPath = null;
        try {
            strPath = event.getData().getPath();
            if (strPath.equals(ZK_ROOT)) {
                FRCLogger.getInstance().info(logIndex, flag, "NODE_ADDED ROOT dir!", null);
                return DefaultValues.ZK_ADD_ROOT;
            }

            strNode = ZKPaths.getNodeFromPath(strPath);
            strVal = new String(client.getData().forPath(strPath));

            FRCLogger.getInstance().info(logIndex, flag, "Node:{}\n value:{}", null, strNode,
                strVal);

            RedisInfo redisInfo = (RedisInfo) JSONProvider.parseObject(logIndex, strVal,
                RedisInfo.class);
            if (null == redisInfo) {
                FRCLogger.getInstance().warn(logIndex, flag, ":{}", null, strVal);
                this.delPath(logIndex, strPath);
                return DefaultValues.ZK_ADD_FAIL;
            }
            redisInfo.setRedis_name(strPath);
            cnm.nodeAdd(logIndex, redisInfo);

        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag,
                "nodeAdd event :path:{}->[{}] add fail,exception detail: {}", e, strVal);
            return DefaultValues.ZK_ADD_FAIL;
        }
        return DefaultValues.ZK_ADD_SUC;
    }

    /**
     * zookeeper listen the node update event
     */
    private boolean updateNode(long logIndex, TreeCacheEvent event) {
        String flag = this.getClassName() + ".updateNode";
        try {
            String strPath = event.getData().getPath();
            if (strPath.equals(ZK_ROOT)) {
                FRCLogger.getInstance().info(logIndex, flag, "NODE_ADDED ROOT dir!", null);
                return true;
            }
            strPath = event.getData().getPath();
            String strNode = ZKPaths.getNodeFromPath(strPath);
            String strVal = new String(client.getData().forPath(strPath));

            FRCLogger.getInstance().info(logIndex, flag, "Node:{}\n value:{}", null, strNode,
                strVal);

            RedisInfo redisInfo = getRedisInfo(logIndex, event);
            if (null == redisInfo)
                return false;

            redisInfo.setRedis_name(strPath);

            int state = this.getState(redisInfo.getRedis_state());
            if (RedisState.STATE_UNKOWN == state) {
                FRCLogger.getInstance().warn(logIndex, flag, "redis workstate is wrong! data:{}",
                    null, JSONProvider.toJSONString(logIndex, redisInfo));
                return false;
            }
            cnm.nodeUpdate(logIndex, redisInfo);
            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
            return false;
        }

    }

    private boolean removeNode(long logIndex, TreeCacheEvent event) {
        String flag = this.getClassName() + ".removeNode";
        try {
            String strPath = event.getData().getPath();
            if (strPath.equals(ZK_ROOT)) {
                FRCLogger.getInstance().info(logIndex, flag, "NODE_ADDED ROOT dir!", null);
                return true;
            }

            String strNode = ZKPaths.getNodeFromPath(strPath);
            String strVal = new String(client.getData().forPath(strPath));

            FRCLogger.getInstance().info(logIndex, flag, "Node:{}\n value:{}", null, strNode,
                strVal);

            RedisInfo redisInfo = getRedisInfo(logIndex, event);
            if (null == redisInfo)
                return false;

            cnm.nodeRemove(logIndex, redisInfo);
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
            return false;
        }

        return true;
    }

    /**
     * listen the zookeeper event
     * 
     * @param logIndex
     * @param cache
     */
    private void addListener(final long logIndex, TreeCache cache) {
        final String flag = this.getClassName() + ".addListener";
        TreeCacheListener plis = new TreeCacheListener() {
            public void childEvent(CuratorFramework client, TreeCacheEvent event) {
                switch (event.getType()) {
                    case NODE_ADDED: {
                        int resAdd = nodeAdd(logIndex, client, event);
                        if (resAdd == DefaultValues.ZK_ADD_SUC) {
                            rNodeNum++;
                        }

                        if (resAdd != DefaultValues.ZK_ADD_ROOT && rNodeNum == 0) {
                            FRCLogger.getInstance().warn(logIndex, flag, "No redis exist!", null);
                        }

                        break;
                    }

                    case NODE_UPDATED: {
                        updateNode(logIndex, event);
                        break;
                    }

                    case NODE_REMOVED: {
                        //removeNode(logIndex, event);
                        break;
                    }
                    default:
                        break;
                }
            }

        };

        cache.getListenable().addListener(plis);
    }

    /**
     * get data from zookeeper node
     * 
     * @author songbin
     * @param event
     *            TreeCacheEvent zookeeper event
     * @return RedisInfo
     */
    private RedisInfo getRedisInfo(long logIndex, TreeCacheEvent event) {
        String flag = getClassName() + ".getRedisClient";

        try {
            String redisStr = new String(
                ZkClient.client.getData().forPath(event.getData().getPath()));
            // jsonRedis = Utils.str2Json(logIndex, redisStr);
            RedisInfo redisCli = (RedisInfo) JSONProvider.parseObject(logIndex, redisStr,
                RedisInfo.class);
            return redisCli;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag, "event fail", e);
            return null;
        }
    }

    /**
     * get the zookeeper data of node of the event
     * 
     * @param logIndex
     * @param event
     *            zookeeper event
     * @return
     */
    @SuppressWarnings("unused")
    private String getRedisName(long logIndex, TreeCacheEvent event) {
        String flag = getClassName() + ".getRedisName";
        try {
            return ZKPaths.getNodeFromPath(event.getData().getPath());
        } catch (Exception e) {
            FRCLogger.getInstance().warn(logIndex, flag,
                "zookeeper getRedisName fail exception detail: {}", e);
            return null;
        }
    }

}
