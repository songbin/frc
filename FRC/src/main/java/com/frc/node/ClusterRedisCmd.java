package com.frc.node;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.frc.core.common.ReturnValues;
import com.frc.core.redis.RedisClient;
import com.frc.thrift.datatype.ResBool;
import com.frc.thrift.datatype.ResDouble;
import com.frc.thrift.datatype.ResListStr;
import com.frc.thrift.datatype.ResLong;
import com.frc.thrift.datatype.ResMapStrStr;
import com.frc.thrift.datatype.ResSetStr;
import com.frc.thrift.datatype.ResStr;
import com.frc.utility.FRCLogger;

/**
 * @author songbin
 * @version 2016年8月29日
 */
public class ClusterRedisCmd {
    private String getClassName() {
	return "ClusterRedisCmd";
    }

    public ResBool set(long logIndex, String key, String val) {
	String flag = this.getClassName() + ".set";
	ResBool res = new ResBool();
	try {
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance()
			.warn(logIndex, flag,
				"cannot locate the redis key:{} val:{}", null,
				key, val);
		res.res = ReturnValues.THRIFT_CODE_FAIL;
		res.value = false;
		return res;
	    }

	    boolean bret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    bret = target_redis.set(logIndex, key, val);
	    res.res = ReturnValues.THRIFT_CODE_SUCC;
	    res.value = bret;

	    return res;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} val:{}", e,
		    key, val);
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    return res;
	}
    }

    public ResStr get(long logIndex, String key) {
	String flag = this.getClassName() + ".get";
	try {
	    ResStr res = new ResStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    String value = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    value = target_redis.get(logIndex, key);
	    if (null == value)
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = value;

	    return res;
	} catch (Exception e) {
	    ResStr res = new ResStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResLong incr(long logIndex, String key) {
	String flag = this.getClassName() + ".incr";
	try {
	    ResLong res = new ResLong();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = -1;
		return res;
	    }
	    Long value = -1L;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    value = target_redis.incr(logIndex, key);
	    if (null == value)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = value;

	    return res;
	} catch (Exception e) {
	    ResLong res = new ResLong();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = -1;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResBool del(long logIndex, String key) {
	String flag = this.getClassName() + ".del";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.del(logIndex, key);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;

	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResStr hget(long logIndex, String key, String field) {
	String flag = this.getClassName() + ".hget";
	try {
	    ResStr res = new ResStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    String ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hget(logIndex, key, field);
	    if (StringUtils.isBlank(ret))
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;

	    return res;
	} catch (Exception e) {
	    ResStr res = new ResStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResSetStr hVals(long logIndex, String key) {
	String flag = this.getClassName() + ".hVals";
	try {
	    ResSetStr res = new ResSetStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    Set<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hVals(logIndex, key);
	    if (CollectionUtils.isEmpty(ret))
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;

	    return res;
	} catch (Exception e) {
	    ResSetStr res = new ResSetStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResBool hset(long logIndex, String key, String field, String value) {
	String flag = this.getClassName() + ".hset";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hset(logIndex, key, field, value);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;

	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResBool hexists(long logIndex, String key, String field) {
	String flag = this.getClassName() + ".hexists";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hexists(logIndex, key, field);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;

	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    // hmset
    public ResBool hmset(long logIndex, String key, Map<String, String> hash) {
	String flag = this.getClassName() + ".hmset";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hmset(logIndex, key, hash);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResMapStrStr hgetAll(long logIndex, String key) {
	String flag = this.getClassName() + ".hgetAll";
	try {
	    ResMapStrStr res = new ResMapStrStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    Map<String, String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hgetAll(logIndex, key);
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResMapStrStr res = new ResMapStrStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} ", e, key);
	    return res;
	}
    }

    public ResListStr hmget(long logIndex, String key, List<String> fields) {
	String flag = this.getClassName() + ".hgetAll";
	try {
	    ResListStr res = new ResListStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    List<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis
		    .hmget(logIndex, key, (String[]) fields.toArray());
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResListStr res = new ResListStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} fields:{} ",
		    e, key, fields);
	    return res;
	}
    }

    public ResBool hdel(long logIndex, String key, String field) {
	String flag = this.getClassName() + ".hdel";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.hdel(logIndex, key, field);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{} fields:{} ",
		    e, key, field);
	    return res;
	}
    }

    public ResBool zadd(long logIndex, String key,
	    Map<String, Double> scoreMembers) {
	String flag = this.getClassName() + ".zadd";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zadd(logIndex, key, scoreMembers);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} fields:{} scoreMembers:{} ", e, key, scoreMembers);
	    return res;
	}
    }

    public ResBool zadd(long logIndex, String key, double score, String member) {
	String flag = this.getClassName() + ".zadd";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zadd(logIndex, key, score, member);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} fields:{} score:{} member:{} ", e, key, score,
		    member);
	    return res;
	}
    }

    public ResBool zrem(long logIndex, String key, String[] members) {
	String flag = this.getClassName() + ".zadd";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrem(logIndex, key, members);
	    if (!ret)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}   member:{} ", e, key, members);
	    return res;
	}

    }

    public ResSetStr zrange(long logIndex, String key, long start, long end) {
	String flag = this.getClassName() + ".zrange";
	try {
	    ResSetStr res = new ResSetStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    Set<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrange(logIndex, key, start, end);
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResSetStr res = new ResSetStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} end:{} ", e, key, start, end);
	    return res;
	}
    }

    public ResSetStr zrevrange(long logIndex, String key, long start, long end) {
	String flag = this.getClassName() + ".zrange";
	try {
	    ResSetStr res = new ResSetStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    Set<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrevrange(logIndex, key, start, end);
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResSetStr res = new ResSetStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} end:{} ", e, key, start, end);
	    return res;
	}
    }
    
    public ResSetStr zrevrangeByScore(long logIndex, String key, long start, long end) {
	String flag = this.getClassName() + ".zrange";
	try {
	    ResSetStr res = new ResSetStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    Set<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrevrangeByScore(logIndex, key, start, end);
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResSetStr res = new ResSetStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} end:{} ", e, key, start, end);
	    return res;
	}
    }
    
    public ResDouble zscore(long logIndex, String key, String member) {
	String flag = this.getClassName() + ".zscore";
	try {
	    ResDouble res = new ResDouble();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = 0.0;
		return res;
	    }
	    Double ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zscore(logIndex, key, member);
	    if (null == ret )
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResDouble res = new ResDouble();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = 0.0;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} member:{} ", e, key, member);
	    return res;
	}
    } 
    
    public ResLong zrevrank(long logIndex, String key, String member) {
	String flag = this.getClassName() + ".zrevrank";
	try {
	    ResLong res = new ResLong();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = 0L;
		return res;
	    }
	    Long ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrevrank(logIndex, key, member);
	    if (null == ret )
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResLong res = new ResLong();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = 0L;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} member:{} ", e, key, member);
	    return res;
	}
    } 
 
    public ResLong zrank(long logIndex, String key, String member) {
	String flag = this.getClassName() + ".zrank";
	try {
	    ResLong res = new ResLong();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = 0L;
		return res;
	    }
	    Long ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.zrank(logIndex, key, member);
	    if (null == ret )
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResLong res = new ResLong();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = 0L;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{}  member:{} ", e, key, member);
	    return res;
	}
    } 
    
    public ResBool exists(long logIndex, String key) {
	String flag = this.getClassName() + ".exists";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    Boolean ret = false;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.exists(logIndex, key);

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} ", e, key);
	    return res;
	}
    }
    
    public ResListStr lrange(long logIndex, String key, long start, long end) {
	String flag = this.getClassName() + ".lrange";
	try {
	    ResListStr res = new ResListStr();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = null;
		return res;
	    }
	    List<String> ret = null;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.lrange(logIndex, key, start, end);
	    if (null == ret || ret.size() == 0)
		res.res = ReturnValues.THRIFT_CODE_FAIL;
	    else
		res.res = ReturnValues.THRIFT_CODE_SUCC;

	    res.value = ret;
	    return res;
	} catch (Exception e) {
	    ResListStr res = new ResListStr();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = null;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  start:{} end:{} ", e, key, start, end);
	    return res;
	}
    }
 
    public ResBool setnx(long logIndex, String key, String value, int expired_time) {
	String flag = this.getClassName() + ".lrange";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    
	    long ret = 0L;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.setnx(logIndex, key, value, expired_time);
	 
	    res.res = ReturnValues.THRIFT_CODE_SUCC;
	    res.value = 1 == ret ? true : false;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  value:{} expired_time:{} ", e, key, value, expired_time);
	    return res;
	}
    }

    public ResBool lpush(long logIndex, String key, String[] strings) {
	String flag = this.getClassName() + ".lpush";
	try {
	    ResBool res = new ResBool();
	    RedisClient redisClient = ClusterAlgo
		    .getCurrentRedis(logIndex, key);
	    if (null == redisClient) {
		FRCLogger.getInstance().warn(logIndex, flag,
			"cannot locate the redis key:{} ", null, key);
		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
		res.value = false;
		return res;
	    }
	    
	    long ret = 0L;
	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
		    redisClient);
	    ret = target_redis.lpush(logIndex, key, strings);
	    res.res = ReturnValues.THRIFT_CODE_SUCC;
	    res.value = 1 == ret ? true : false;
	    return res;
	} catch (Exception e) {
	    ResBool res = new ResBool();
	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
	    res.value = false;
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key:{} key:{}  value:{} strings:{} ", e, key, strings);
	    return res;
	}
    }
    
    public ResBool rpush(long logIndex, String key, String[] strings) {
   	String flag = this.getClassName() + ".rpush";
   	try {
   	    ResBool res = new ResBool();
   	    RedisClient redisClient = ClusterAlgo
   		    .getCurrentRedis(logIndex, key);
   	    if (null == redisClient) {
   		FRCLogger.getInstance().warn(logIndex, flag,
   			"cannot locate the redis key:{} ", null, key);
   		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
   		res.value = false;
   		return res;
   	    }
   	    
   	    long ret = 0L;
   	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
   		    redisClient);
   	    ret = target_redis.rpush(logIndex, key, strings);
   	    res.res = ReturnValues.THRIFT_CODE_SUCC;
   	    res.value = 1 == ret ? true : false;
   	    return res;
   	} catch (Exception e) {
   	    ResBool res = new ResBool();
   	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
   	    res.value = false;
   	    FRCLogger.getInstance().warn(logIndex, flag,
   		    "key:{} key:{}  value:{} strings:{} ", e, key, strings);
   	    return res;
   	}
    }
    
    public ResBool expired(long logIndex, String key, int seconds) {
   	String flag = this.getClassName() + ".expired";
   	try {
   	    ResBool res = new ResBool();
   	    RedisClient redisClient = ClusterAlgo
   		    .getCurrentRedis(logIndex, key);
   	    if (null == redisClient) {
   		FRCLogger.getInstance().warn(logIndex, flag,
   			"cannot locate the redis key:{} ", null, key);
   		res.res = ReturnValues.THRIFT_CODE_NO_DATA;
   		res.value = false;
   		return res;
   	    }
   	    
   	    long ret = 0L;
   	    RedisClient target_redis = ClusterAlgo.migrateDB(logIndex, key,
   		    redisClient);
   	    ret = target_redis.expired(logIndex, key, seconds);
   	    res.res = ReturnValues.THRIFT_CODE_SUCC;
   	    res.value = 1 == ret ? true : false;
   	    return res;
   	} catch (Exception e) {
   	    ResBool res = new ResBool();
   	    res.res = ReturnValues.THRIFT_CODE_EXCPETION;
   	    res.value = false;
   	    FRCLogger.getInstance().warn(logIndex, flag,
   		    "key:{} key:{}  value:{} seconds:{} ", e, key, seconds);
   	    return res;
   	}
    }    

     
}
