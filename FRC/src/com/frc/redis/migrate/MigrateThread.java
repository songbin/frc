package com.frc.redis.migrate;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.frc.core.common.DefaultValues;
import com.frc.core.redis.RedisClient;
import com.frc.loader.PropertiesLoader;
import com.frc.node.ClusterAlgo;
import com.frc.redis.domain.RedisInfo;
import com.frc.redis.domain.RedisState;
import com.frc.utility.FRCLogger;
import com.frc.utility.JSONProvider;
import com.frc.zookeeper.client.ZkClient;

/**
 * @author songbin
 * @version 2016年8月30日
 */
public class MigrateThread implements Runnable {

    private String getClassName() {
	return "MigrateThread";
    }

    private final long logIndex = -100;
    /** one hour */
    private long sleep_time = 1000 * 60 * 60;
    private final String KEY_LOCK = DefaultValues.MIGRATE_LOCK_KEY;
    private final int lock_expired = 60 * 60 * 5;
    private ZkClient zkClient = null;
    private final int MIGRATE_DURA = 3;

    @Override
    public void run() {
	String flag = this.getClassName() + ".run";
	try {
	    zkClient = new ZkClient();
	    int migrate_time = PropertiesLoader.getIntance().getClusterMigrateStartTime();
	    int migrate_end_time = migrate_time + MIGRATE_DURA;
	    boolean isDev = PropertiesLoader.getIntance().getIsDev();
	    if(isDev){
		sleep_time = 1000*30;
	    }
	    while (true) {
		if (!isDev) {
		    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		    if (hour < migrate_time || hour > migrate_end_time) {
			Thread.sleep(sleep_time);
			continue;
		    }
		}

		this.work();
	    }

	} catch (Exception e) {
	    FRCLogger.getInstance().warn(-100, flag, "exception", e);
	}
    }

    private final boolean updateZKRedis(RedisClient client) {
	String flag = this.getClassName() + ".updateZKRedis";
	RedisInfo redisInfo = new RedisInfo();
	redisInfo.setRedis_host(client.getRedis_host());
	redisInfo.setRedis_passwd(client.getRedis_passwd());
	redisInfo.setRedis_port(client.getRedis_port());
	redisInfo.setRedis_state(client.getRedis_state());
	String path = client.getRedis_name();
	zkClient.updatePath(logIndex, path,
		JSONProvider.toJSONString(logIndex, redisInfo));
	FRCLogger.getInstance().info(logIndex, flag, "path:{}  data:{}", null,
		path, JSONProvider.toJSONString(logIndex, redisInfo));
	return true;
    }

    private final boolean delZKRedis(RedisClient client) {
	String flag = this.getClassName() + ".delZKRedis";
	String path = client.getRedis_name();
	zkClient.delPath(logIndex, path);
	FRCLogger.getInstance().info(logIndex, flag, "path:{}  data:{}", null,
		JSONProvider.toJSONString(logIndex, client));
	return true;
    }

    private void migrateRedis(RedisClient redisClient) {
	String flag = this.getClassName() + ".migrateRedis";
	try {
	    if (null == redisClient)
		return;

	    switch (redisClient.getRedis_state()) {
	    case RedisState.STATE_ADD:
		this.migrateAdd(redisClient);
		redisClient.setRedis_state(RedisState.STATE_NORMAL);
		RedisInfo redisInfo = ClusterAlgo.redisClient2Info(logIndex,
			redisClient);
		ClusterAlgo.updateRedis(logIndex, redisInfo);
		this.updateZKRedis(redisClient);
		break;
	    case RedisState.STATE_DEL:
		this.migrateDel(redisClient);
		redisClient.setRedis_state(RedisState.STATE_DESTROY);
		ClusterAlgo.removeRedis(logIndex, redisClient);
		this.delZKRedis(redisClient);
		break;
	    }

	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "{}", e,
		    JSONProvider.toJSONString(logIndex, redisClient));
	}
	return;
    }

    private final Set<String> listKeys(RedisClient client) {
	String flag = this.getClassName() + ".listKeys";
	try {
	    return client.keys(logIndex, "*");
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "client:", null,
		    JSONProvider.toJSONString(logIndex, client));
	    return null;
	}
    }

    /** current redis is added state */
    private void migrateAdd(RedisClient curr_client) {
	String flag = this.getClassName() + ".migrateAdd";
	try {
	    List<Long> nextList = ClusterAlgo.getInstanceNext(logIndex,
		    curr_client.getRedis_name());
	    if (null == nextList || nextList.isEmpty())
		return;
	    for (Long next_hash : nextList) {

		RedisClient target_client = ClusterAlgo.getMapRedis().get(
			next_hash);
		if (null == target_client) {
		    FRCLogger.getInstance().warn(logIndex, flag,
			    "redis_hash: {}", null, next_hash);
		    continue;
		}

		Set<String> keys = this.listKeys(target_client);
		if (null == keys)
		    continue;

		for (String key : keys) {
		    RedisClient revert_cli = ClusterAlgo.getCurrentRedis(
			    logIndex, key);
		    if (null == revert_cli)
			continue;
		    if (revert_cli.getRedis_name().equals(
			    curr_client.getRedis_name())) {
			if (KEY_LOCK.equals(key))
			    continue;
			String ret = target_client.migrate(logIndex, key,
				curr_client);
			FRCLogger.getInstance().info(
				logIndex,
				flag,
				"ret:{}  source:{}  dest:{}",
				null,
				ret,
				JSONProvider.toJSONString(logIndex,
					target_client),
				JSONProvider
					.toJSONString(logIndex, curr_client));
		    }
		}
	    }
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	}

    }

    /** current redis is del state */
    private void migrateDel(RedisClient curr_client) {
	String flag = this.getClassName() + ".migrateDel";
	try {
	    Set<String> keys = this.listKeys(curr_client);
	    if (null == keys) {
		return;
	    }

	    for (String key : keys) {
		RedisClient next_cli = ClusterAlgo.getNextRedis(logIndex, key);
		if (null == next_cli)
		    continue;
		if (KEY_LOCK.equals(key))
		    continue;

		String ret = curr_client.migrate(logIndex, key, next_cli);
		FRCLogger.getInstance().info(logIndex, flag,
			"ret:{}  source:{}  dest:{}", null, ret,
			JSONProvider.toJSONString(logIndex, curr_client),
			JSONProvider.toJSONString(logIndex, next_cli));
	    }
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	}

    }

    private boolean getLock(RedisClient client) {
	long ret = client.setnx(logIndex, this.KEY_LOCK, "ok", lock_expired);
	return 1 == ret ? true : false;
    }

    private void work() {
	String flag = this.getClassName() + ".work";
	try {
	    SortedMap<Long, RedisClient> map = ClusterAlgo.getMapRedis();
	    SortedMap<Long, RedisClient> iteratorMap = new TreeMap<Long, RedisClient>(map);
	    RedisClient client = null;
	    if(null == iteratorMap || iteratorMap.isEmpty()) return;
	    for (Long key : iteratorMap.keySet()) {
		client = iteratorMap.get(key);
		if(null == client) continue;
		switch (client.getRedis_state()) {
		case RedisState.STATE_DEL:
		case RedisState.STATE_ADD:
		    // case RedisState.STATE_DESTROY:
		    if (!this.getLock(client)) {
			FRCLogger.getInstance().info(logIndex, flag,
				"{} has been another locked", null,
				client.getRedis_name());
			continue;
		    }

		    this.migrateRedis(client);
		    client.del(logIndex, this.KEY_LOCK);
		    break;
		}
	    }

	    Thread.sleep(this.sleep_time);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "", e);
	}

    }

}
