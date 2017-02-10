package com.frc.loader;

import com.frc.core.common.ConfigKeys;
import com.frc.core.common.DefaultValues;
import com.frc.utility.FRCLogger;
import com.frc.utility.FRCProperties;

/**
 * @author songbin
 * @version 创建时间：2016年8月23日
 * 
 */
public class PropertiesLoader {

    private static FRCProperties properties = null;
    private static PropertiesLoader instance = null;

    private PropertiesLoader() {
    }

    public static synchronized PropertiesLoader getIntance() {
	if (null == instance) {
	    properties = new FRCProperties();
	    boolean ret = properties
		    .loadConfig(DefaultValues.PATH_PROPERTIES_DEFAULT);
	    if (!ret) {
		FRCLogger.getInstance().warn(-100,
			"PropertiesLoader.getIntance",
			"load properties failed", null);
		System.exit(0);
	    }
	    instance = new PropertiesLoader();
	}
	return instance;
    }

    public String getZookerHosts() {
	return properties.getString(ConfigKeys.ZOOKKEEPER_HOST, "");
    }

    public String getZookerRoot() {
	return properties.getString(ConfigKeys.ZOOKEEPER_ROOT, "/frc");
    }

    /**
     * get the default value of the expire of the redis's lock
     * 
     * @return expired time of lock. unit is millisecond
     * */
    public int getRedisLockExpire() {
	return properties.getInt(ConfigKeys.REDIS_KEY_LOCK_EXPIRE, 10);
    }

    /**
     * @return max wait time for lock. unit is millisecond
     * */
    public int getRedisLockMaxWait() {
	return properties.getInt(ConfigKeys.REDIS_WAITKEY_MAX_TIME, 100);
    }

    public int getThriftListenPort() {
	return properties.getInt(ConfigKeys.THRIF_LISTEN_PORT, 80);
    }

    public int getThriftWorkerMode() {
	return properties.getInt(ConfigKeys.THRIFT_WORK_MODE, 1);
    }

    public int getThriftWorkerThreadNum() {
	return properties.getInt(ConfigKeys.THRIFT_WORKER_TREAD_NUM, 200);
    }

    public int getThriftListenThreadNum() {
	return properties.getInt(ConfigKeys.THRIFT_LISTEN_TREAD_NUM, 5);
    }

    public int getRedisMaxTotal() {
	return properties.getInt(ConfigKeys.REDIS_POOL_MAX_TOTAL, 50);
    }

    public int getRedisMaxIdle() {
	return properties.getInt(ConfigKeys.REDIS_POOL_MAX_IDLE, 10);
    }

    public long getRedisMaxWait() {
	return properties.getInt(ConfigKeys.REDIS_POOL_MAX_WAIT, 2000);
    }

    public boolean getRedisTestBorrow() {
	return 1 == properties.getInt(ConfigKeys.REDIS_TEST_BORROW, 1) ? true
		: false;
    }

    public boolean getRedisTestReturn() {
	return 1 == properties.getInt(ConfigKeys.REDIS_TEST_RETURN, 1) ? true
		: false;
    }

    public int getRedisVNode() {
	return properties.getInt(ConfigKeys.REDIS_VNODE_NUM, 20);
    }
    
    public int getClusterMigrateStartTime(){
	int time = properties.getInt(ConfigKeys.CLUSTER_MIGRATE_START_TIME, 3);
	if(time > 7) time = 3;
	return time;
    }
    
    public boolean getIsDev(){
	int dev = properties.getInt(ConfigKeys.SYSTEM_IS_DEV, 0);
	return 1 == dev ? true : false;
    }
}
