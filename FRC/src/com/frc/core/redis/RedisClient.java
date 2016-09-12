package com.frc.core.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import com.frc.loader.PropertiesLoader;
import com.frc.redis.domain.RedisInfo;
import com.frc.utility.FRCLogger;
import com.frc.utility.JSONProvider;

public class RedisClient {
    private String getClassName() {
	return "RedisClient";
    }

    private JedisPool jedisPool = null;
    private String redis_name;
    private int redis_vnode_num;
    /** according to {@linkplain com.frc.redis.domain.RedisState RedisState} */
    private int redis_state;
    private String redis_host;
    private int redis_port;
    private String redis_passwd;

    int queMaxLen;
    int queExpLen;

    public boolean init(long logIndex, RedisInfo redisInfo) {
	String flag = getClassName() + ".init";
	try {
	    this.redis_passwd = redisInfo.getRedis_passwd();
	    this.redis_host = redisInfo.getRedis_host();
	    this.redis_port = redisInfo.getRedis_port();
	    this.redis_name = redisInfo.getRedis_name();
	    this.redis_vnode_num = redisInfo.getRedis_vnode_num();
	    this.redis_state = redisInfo.getRedis_state();

	    JedisPoolConfig config = new JedisPoolConfig();
	    PropertiesLoader.getIntance().getRedisLockExpire();

	    config.setMaxActive(PropertiesLoader.getIntance()
		    .getRedisMaxTotal());
	    config.setMaxIdle(PropertiesLoader.getIntance().getRedisMaxIdle());
	    config.setMaxWait(PropertiesLoader.getIntance().getRedisMaxWait());
	    // config.setMinEvictableIdleTimeMillis(PropertiesLoader.getIntance().getre);
	    // config.setMinIdle(minIdle);
	    config.setTestOnBorrow(PropertiesLoader.getIntance()
		    .getRedisTestBorrow());
	    config.setTestOnReturn(PropertiesLoader.getIntance()
		    .getRedisTestReturn());
	    // config.setTestWhileIdle(PropertiesLoader.getIntance().getre);
	    if (StringUtils.isBlank(this.redis_passwd)) {
		jedisPool = new JedisPool(config, this.redis_host,
			this.redis_port);
	    } else {
		jedisPool = new JedisPool(config, this.redis_host,
			this.redis_port, 2000, this.redis_passwd);
	    }
	    return true;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameter:{}", e,
		    JSONProvider.toJSONString(logIndex, redisInfo));
	    return false;
	}

    }

    public String getRedisInfo(long logIndex) {
	StringBuilder sb = new StringBuilder();
	return sb.append(this.redis_host).append(":").append(this.redis_port)
		.toString();
    }

    public void shutDown(long logIndex) {
	String flag = getClassName() + ".shutDown";
	if (jedisPool == null)
	    return;
	try {
	    jedisPool.destroy();
	    jedisPool = null;
	} catch (Exception ex) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "shutting down jedis...", ex);
	}
    }

    /**
     * execute the incr command
     * 
     * @param long logIndex
     * @param String
     *            key target key will be incr
     * @return long success : the values after incr, fail: null
     */
    public Long incr(long logIndex, String key) {
	String flag = getClassName() + ".incr";

	Jedis jds = null;
	long id = -1;
	try {
	    jds = jedisPool.getResource();
	    id = jds.incr(key);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    jedisPool.returnBrokenResource(jds);
	    return null;
	}

	return id;
    }

    /**
     * command del
     * 
     * @param long logIndex
     * @param String
     *            key
     * @return boolean true or false
     */
    public boolean del(long logIndex, String key) {
	String flag = getClassName() + ".del";
	Jedis jds = null;
	boolean res = false;
	try {
	    jds = jedisPool.getResource();
	    jds.del(key);
	    res = true;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = false;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;

    }

    /**
     * @param success
     *            : the value of key . fail: null
     * */
    public String hget(long logIndex, String key, String field) {//
	String flag = getClassName() + ".hget";

	Jedis jds = null;
	String res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.hget(key, field);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;

    }

    public HashSet<String> hVals(long logIndex, String key) {
	String flag = getClassName() + ".hVals";
	Jedis jds = null;
	HashSet<String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = new HashSet<String>(jds.hvals(key));
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    public boolean hset(long logIndex, String key, String field, String value) {
	String flag = getClassName() + ".hset";
	Jedis jds = null;
	boolean res = false;

	try {
	    jds = jedisPool.getResource();
	    jds.hset(key, field, value);
	    res = true;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    jedisPool.returnBrokenResource(jds);
	    return false;
	}

	return res;
    }

    /**
     * @param String
     *            key
     * @param String
     *            field
     * @return true or false
     */
    public boolean hexists(long logIndex, String key, String field) {
	String flag = getClassName() + ".hexists";

	Jedis jds = null;
	boolean res = false;
	try {
	    jds = jedisPool.getResource();
	    res = jds.hexists(key, field);

	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = false;
	}

	return res;
    }

    public boolean hmset(long logIndex, String key, Map<String, String> hash) {
	String flag = getClassName() + ".hmset";
	Jedis jds = null;
	boolean res = false;

	try {
	    jds = jedisPool.getResource();
	    jds.hmset(key, hash);
	    res = true;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    public Map<String, String> hgetAll(long logIndex, String key) {
	String flag = getClassName() + ".hgetAll";

	Jedis jds = null;
	Map<String, String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.hgetAll(key);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    public List<String> hmget(long logIndex, String key, String[] fields) {
	String flag = getClassName() + ".hmget";

	Jedis jds = null;
	List<String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.hmget(key, fields);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    public boolean hdel(long logIndex, String key, String field) {
	String flag = getClassName() + ".hdel";
	Jedis jds = null;
	boolean res = false;

	try {
	    jds = jedisPool.getResource();
	    jds.hdel(key, field);
	    res = true;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    // ////////////////////////////////////////////////////////
    // sorted set
    // ////////////////////////////////////////////////////////
    public boolean zadd(long logIndex, String key,
	    Map<Double, String> scoreMembers) {
	String flag = getClassName() + ".zadd(map)";

	Jedis jds = null;
	boolean res = false;
	try {
	    jds = jedisPool.getResource();
	    jds.zadd(key, scoreMembers);
	    res = true;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    public boolean zadd(long logIndex, String key, double score, String member) {
	String flag = getClassName() + ".zadd";

	Jedis jds = null;
	boolean res = false;
	try {
	    jds = jedisPool.getResource();

	    long ret = jds.zadd(key, score, member);
	    
	    res = 1 == ret ? true : false;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {} score {} member {}", e, key, score,
		    member);
	    res = false;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    public boolean zrem(long logIndex, String key, String[] members) {
	String flag = getClassName() + ".zrem";

	Jedis jds = null;
	boolean res = false;
	try {
	    jds = jedisPool.getResource();

	    long ret = jds.zrem(key, members);
	    res = 1 == ret ? true : false;
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, members {}",
		    e, key, java.util.Arrays.toString(members));

	    res = false;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    public Set<String> zrange(long logIndex, String key, long start, long end) {
	String flag = getClassName() + ".zrange";

	Jedis jds = null;
	Set<String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.zrange(key, start, end);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {}",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    public Set<String> zrevrange(long logIndex, String key, long start, long end) {
	String flag = getClassName() + ".zrevrange";

	Jedis jds = null;
	Set<String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.zrevrange(key, start, end);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {}, start {}, end {}", e, key, start, end);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    public Set<Tuple> zrevrangewithscores(long logIndex, String key,
	    long start, long end) {
	String flag = getClassName() + ".zrevrangewithscores";
	
	Jedis jds = null;
	Set<Tuple> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.zrevrangeWithScores(key, start, end);
	    JSONProvider.toJSONString(logIndex, res);
	    
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {}, start {}, end {}", e, key, start, end);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    public Set<String> zrevrangeByScore(long logIndex, String key,
	    double start, double end) {
	String flag = getClassName() + ".zrevrangeByScore";

	Jedis jds = null;
	Set<String> res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.zrevrangeByScore(key, start, end);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {}, start {}, end {}", e, key, start, end);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return ((res == null || res.isEmpty()) ? null : res);
    }

    /**
     * @return fail : null success: score of the member
     * 
     * */
    public Double zscore(long logIndex, String key, String member) {
	String flag = getClassName() + ".zscore";

	Jedis jds = null;
	double res = -1;
	try {
	    jds = jedisPool.getResource();
	    // 成员的分数值，以字符串形式表示。
	    res = jds.zscore(key, member);

	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {} member:{}", e, key, member);
	    jedisPool.returnBrokenResource(jds);
	    return null;
	}

	return res;
    }

    /**
     * @return fail : null success: score of the member
     * 
     * */
    public Long zrevrank(long logIndex, String key, String member) {
	String flag = getClassName() + ".zrevrank";

	Jedis jds = null;
	Long res = new Long(-1);
	try {
	    jds = jedisPool.getResource();
	    // 返回有序集合中指定成员的索引
	    res = jds.zrevrank(key, member);

	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {} member:{}", e, key, member);
	    jedisPool.returnBrokenResource(jds);
	    return null;
	}

	return res;
    }

    /**
     * @return fail : null success: score of the member
     * 
     * */
    public Long zrank(long logIndex, String key, String member) {
	String flag = getClassName() + ".zrank";

	Long res = new Long(-1);
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.zrank(key, member);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {} member:{}", e, key, member);
	    jedisPool.returnBrokenResource(jds);
	    return null;
	}

	return res;
    }

    // ////////////////////////////////////////////////////////
    // string
    // ////////////////////////////////////////////////////////
    public boolean set(long logIndex, String key, String value) {
	String flag = getClassName() + ".set";

	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    jds.set(key, value);
	    jedisPool.returnResource(jds);

	    return true;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: key {} value:{}", e, key, value);
	    jedisPool.returnBrokenResource(jds);

	    return false;
	}
    }

    public String get(long logIndex, String key) {
	String flag = getClassName() + ".get";

	Jedis jds = null;
	String res = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.get(key);
	    jedisPool.returnResource(jds);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {} ",
		    e, key);
	    res = null;
	    jedisPool.returnBrokenResource(jds);
	}

	return res;
    }

    /**
     * @return false or true
     */
    public boolean exists(long logIndex, String key) {
	String flag = getClassName() + ".exists";

	Jedis jds = null;
	boolean value = false;
	try {
	    jds = jedisPool.getResource();
	    value = jds.exists(key);

	    jedisPool.returnResource(jds);

	    return value;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "parameters: key {} ",
		    e, key);
	    return false;
	}
    }

    /**
     * load lua script
     */
    public String scriptLoad(long logIndex, String script) {
	String flag = getClassName() + ".scriptLoad";

	Jedis jds = null;
	String sha;
	try {
	    jds = jedisPool.getResource();
	    sha = jds.scriptLoad(script);

	    jedisPool.returnResource(jds);

	    return sha;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: script {} ", e, script);

	    return null;
	}
    }

    public Object eval(long logIndex, String script, List<String> keys,
	    List<String> args) {
	String flag = getClassName() + ".eval";

	Object obj = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    obj = jds.eval(script, keys, args);

	    jedisPool.returnResource(jds);

	    return obj;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: script {} ", e, script);
	    return null;
	}
    }

    public Object eval(long logIndex, String script, int keyCount,
	    String[] params) {
	String flag = getClassName() + ".eval(script-keyCount-params)";
	Object obj = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    obj = jds.eval(script, keyCount, params);

	    jedisPool.returnResource(jds);

	    return obj;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "parameters: script {}, keyCount:{}, params:{} ", e,
		    script, keyCount, java.util.Arrays.toString(params));
	    return null;
	}
    }

    public List<String> lrange(long logIndex, String key, long start, long end) {
	String flag = getClassName() + ".lrange";

	List<String> res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.lrange(key, start, end);

	    jedisPool.returnResource(jds);

	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag,
		    "key {}, start:{}, end:{}", e, key, start, end);
	    return null;
	}
    }

    public long setnx(long logIndex, String key, String value, int expired_time) {
	String flag = getClassName() + ".setnx";

	Long res = null;
	Jedis jds = null;
	try {
	    
	    jds = jedisPool.getResource();
	    res = jds.setnx(key, value);
	    if(0 == res) return 0L;
	    
	    int seconds = expired_time;
	    if(expired_time < 0 ){
		seconds = 0;
	    }
	    
	    jds.expire(key, seconds);
	    jedisPool.returnResource(jds);

	    return res;
	    
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, value:{}", e, key, value);
	    return 0L;
	}
    }

    public Long lpush(long logIndex, String key, String[] strings) {
	String flag = getClassName() + ".lpush";

	Long res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.lpush(key, strings);

	    jedisPool.returnResource(jds);

	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, strings:{}",
		    e, key, java.util.Arrays.toString(strings));
	    res = (long) 0;
	    return null;
	}
    }

    public Long rpush(long logIndex, String key, String[] strings) {
	String flag = getClassName() + ".rpush";

	Long res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.rpush(key, strings);

	    jedisPool.returnResource(jds);

	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, strings:{}",
		    e, key, java.util.Arrays.toString(strings));
	    res = (long) 0;
	    return null;
	}
    }

    public long expired(long logIndex, String key, int seconds) {
	String flag = getClassName() + ".expired";

	Long res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.expire(key, seconds);
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, seconds:{}",
		    e, key, seconds);
	    res = (long) 0;
	    return res;
	}
    }
    
    public String migrate(long logIndex, String key, RedisClient dest) {
	String flag = getClassName() + ".migrate";

	String res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.migrate(dest.getRedis_host(), dest.getRedis_port(), key, 0, 1000); 
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, redis:{}", null, key, JSONProvider.toJSONString(logIndex, dest));
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, redis:{}", e, key, JSONProvider.toJSONString(logIndex, dest));
	    res = CmdReturn.MIGRATE_EXCEPTION;
	    return res;
	}
    }
    
    public String migrateEx(long logIndex, String key, String host, int port) {
	String flag = getClassName() + ".migrate";

	String res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.migrate(host, port, key, 0, 1000);  
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "key {}, host:{} port:{}", e, key, host, port );
	    res = CmdReturn.MIGRATE_EXCEPTION;
	    return res;
	}
    }
    
    public Set<String> keys(long logIndex, String pattern) {
	String flag = getClassName() + ".keys";

	Set<String> res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.keys(pattern);
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "pattern:{}", e, pattern);
	    res = null;
	    return res;
	}
    }
    
    public Transaction multi(long logIndex) {
	String flag = getClassName() + ".multi";

	Transaction res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = jds.multi();  
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    res = null;
	    return res;
	}
	
    }
    
    public List<Object> exec(long logIndex, Transaction transaction) {
	String flag = getClassName() + ".exec";

	List<Object> res = null;
	Jedis jds = null;
	try {
	    jds = jedisPool.getResource();
	    res = transaction.exec(); 
	    jedisPool.returnResource(jds);
	    return res;
	} catch (Exception e) {
	    jedisPool.returnBrokenResource(jds);
	    FRCLogger.getInstance().warn(logIndex, flag, "exception:", e);
	    res = null;
	    return res;
	}
    }   

    public String getRedis_name() {
	return redis_name;
    }

    public void setRedis_name(String redis_name) {
	this.redis_name = redis_name;
    }

    public int getRedis_vnode_num() {
	return redis_vnode_num;
    }

    public void setRedis_vnode_num(int redis_vnode_num) {
	this.redis_vnode_num = redis_vnode_num;
    }

    public int getRedis_state() {
	return redis_state;
    }

    public void setRedis_state(int redis_state) {
	this.redis_state = redis_state;
    }

    public String getRedis_host() {
	return redis_host;
    }

    public void setRedis_host(String redis_host) {
	this.redis_host = redis_host;
    }

    public int getRedis_port() {
	return redis_port;
    }

    public void setRedis_port(int redis_port) {
	this.redis_port = redis_port;
    }

    public String getRedis_passwd() {
	return redis_passwd;
    }

    public void setRedis_passwd(String redis_passwd) {
	this.redis_passwd = redis_passwd;
    }

}
