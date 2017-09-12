package com.frc.core.common;

/** 
 * @author  songbin 
 * @version 创建时间：2016年8月19日  
 *  
 */
public class ConfigKeys {
    /**default of the expired time of redis lock, unit is millisecond */
    public static final String REDIS_KEY_LOCK_EXPIRE      = "kredis.key.lock.expire";
    /**the max wait time when the redis key is locked, uint is millisecond*/
    public static final String REDIS_WAITKEY_MAX_TIME     = "redis.waitkey.maxtime";
    public static final String REDIS_POOL_MAX_TOTAL       = "redis.pool.maxTotal";
    public static final String REDIS_POOL_MAX_IDLE        = "redis.pool.maxIdle";
    public static final String REDIS_POOL_MAX_WAIT        = "redis.pool.maxWaitMillis";
    public static final String REDIS_TEST_BORROW          = "redis.testOnBorrow";
    public static final String REDIS_TEST_RETURN          = "redis.testOnReturn";

    public static final String REDIS_VNODE_NUM            = "redis.vnode.num";

    public static final String ZOOKKEEPER_HOST            = "zookeeper.hosts";
    public static final String ZOOKEEPER_ROOT             = "zookeeper.root";

    /**thrift server configure starts*/
    public static final String THRIF_LISTEN_PORT          = "thrift.listen.port";
    public static final String THRIFT_WORK_MODE           = "thrift.worker.mode";
    public static final String THRIFT_LISTEN_TREAD_NUM    = "thrift.listen.thread.num";
    public static final String THRIFT_WORKER_TREAD_NUM    = "thrift.worker.thread.num";
    /**thrift server configure ends*/

    public static final String CLUSTER_MIGRATE_START_TIME = "migrate.start.time";
    public static final String SYSTEM_IS_DEV              = "frc.dev";
}
