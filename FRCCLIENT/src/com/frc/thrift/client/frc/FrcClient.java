package com.frc.thrift.client.frc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.frc.thrift.datatype.ResBool;
import com.frc.thrift.datatype.ResDouble;
import com.frc.thrift.datatype.ResListStr;
import com.frc.thrift.datatype.ResLong;
import com.frc.thrift.datatype.ResMapStrStr;
import com.frc.thrift.datatype.ResSetStr;
import com.frc.thrift.datatype.ResStr;
import com.frc.thrift.stub.FrcService.Client;
import com.frc.utility.FRCLogger;

public class FrcClient {
    
    private static String caller = "FRC";
    private FrcPool pool = null;
    
    private String getClassName(){
	return "FrcClient";
    }
    
    public FrcClient(String host, int port, int timeout){
	this.pool = new FrcPool(host, port, timeout);
    }

    public String get(long logIndex, String key) throws Exception {
	String flag = this.getClassName() + ".getv";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResStr res = client.getClient().getv(logIndex, caller, key, null);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}

    }
    
    public boolean set(long logIndex, String key, String value) throws Exception {
	String flag = this.getClassName() + ".setv";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().setv(logIndex, caller, key, value, null);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}

    }
    
    public long incr(long logIndex, String key, String ext) throws Exception {
	String flag = this.getClassName() + ".incr";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResLong res = client.getClient().incr(logIndex, flag, key, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    
    public boolean del(long logIndex, String key, String ext) throws Exception {
	String flag = this.getClassName() + ".del";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().delv(logIndex, flag, key, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
	 
    }
    
    public String hget(long logIndex, String key, String field, String ext) throws Exception {
	String flag = this.getClassName() + ".hget";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResStr res = client.getClient().hget(logIndex, flag, key, field, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    
    public Set<String> hVals(long logIndex, String key, String ext) throws Exception {
	String flag = this.getClassName() + ".hVals";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResSetStr res = client.getClient().hVals(logIndex, flag, key, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean hset(long logIndex, String key, String field, String value, String ext) throws Exception {
	String flag = this.getClassName() + ".hset";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().hset(logIndex, flag, key, field, value, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean hexists(long logIndex, String key, String field, String ext) throws Exception {
	String flag = this.getClassName() + ".hexists";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().hexists(logIndex, flag, key, field, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean hmset(long logIndex, String key, Map<String, String> hash, String ext) throws Exception {
	String flag = this.getClassName() + ".hmset";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().hmset(logIndex, flag, key, hash, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Map<String, String> hgetAll(long logIndex, String key, String ext) throws Exception {
	String flag = this.getClassName() + ".hgetAll";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResMapStrStr res = client.getClient().hgetAll(logIndex, flag, key, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public List<String> hmget(long logIndex, String key, List<String> fields, String ext) throws Exception {
	String flag = this.getClassName() + ".hmget";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResListStr res = client.getClient().hmget(logIndex, flag, key, fields, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean hdel(long logIndex, String key, String field, String ext) throws Exception {
	String flag = this.getClassName() + ".hdel";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().hdel(logIndex, flag, key, field, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean zadd(long logIndex, String key,
	    Map<Double, String> scoreMembers, String ext) throws Exception {
	
	String flag = this.getClassName() + ".zadd";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().zadd(logIndex, flag, key, scoreMembers, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean zadd(long logIndex, String key, double score, String member, String ext) throws Exception {
	String flag = this.getClassName() + ".zadd";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().zaddmember(logIndex, flag, key, score, member, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean zrem(long logIndex, String key, String[] members, String ext) throws Exception {
	String flag = this.getClassName() + ".zrem";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    List<String> memberList = new ArrayList<String>();
	    Collections.addAll(memberList, members);
	    ResBool res = client.getClient().zrem(logIndex, flag, key, memberList, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}    
    }
    
    public Set<String> zrange(long logIndex, String key, long start, long end, String ext) throws Exception {
	String flag = this.getClassName() + ".zrange";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResSetStr res = client.getClient().zrange(logIndex, flag, key, start, end, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Set<String> zrevrange(long logIndex, String key, long start, long end, String ext) throws Exception {
	String flag = this.getClassName() + ".zrevrange";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResSetStr res = client.getClient().zrange(logIndex, flag, key, start, end, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Set<String> zrevrangeByScore(long logIndex, String key, long start, long end, String ext) throws Exception {
	String flag = this.getClassName() + ".zrevrangeByScore";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResSetStr res = client.getClient().zrange(logIndex, flag, key, start, end, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Double zscore(long logIndex, String key, String member, String ext) throws Exception {
	String flag = this.getClassName() + ".zscore";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResDouble res = client.getClient().zscore(logIndex, flag, key, member, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Long zrevrank(long logIndex, String key, String member, String ext) throws Exception {
	String flag = this.getClassName() + ".zrevrank";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResLong res = client.getClient().zrevrank(logIndex, flag, key, member, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public Long zrank(long logIndex, String key, String member, String ext) throws Exception {
	String flag = this.getClassName() + ".zrank";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResLong res = client.getClient().zrank(logIndex, flag, key, member, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean exists(long logIndex, String key, String ext) throws Exception {
	String flag = this.getClassName() + ".zrank";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().exists(logIndex, flag, key, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    public List<String> lrange(long logIndex, String key, long start, long end, String ext) throws Exception {
	String flag = this.getClassName() + ".lrange";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResListStr res = client.getClient().lrange(logIndex, flag, key, start, end, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    public boolean setnx(long logIndex, String key, String value, int expired_time, String ext) throws Exception {
	String flag = this.getClassName() + ".setnx";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().setnx(logIndex, flag, key, value, expired_time, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    public boolean lpush(long logIndex, String key, String[] strings, String ext) throws Exception {
	String flag = this.getClassName() + ".lpush";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    List<String> strList = new ArrayList<String>();
	    Collections.addAll(strList, strings);
	    ResBool res = client.getClient().lpush(logIndex, flag, key, strList, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}	
    }
    
    public boolean rpush(long logIndex, String key, String[] strings, String ext) throws Exception {
	String flag = this.getClassName() + ".rpush";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    List<String> strList = new ArrayList<String>();
	    Collections.addAll(strList, strings);
	    ResBool res = client.getClient().lpush(logIndex, flag, key, strList, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    public boolean expired(long logIndex, String key, int seconds, String ext) throws Exception {
	String flag = this.getClassName() + ".expired";
	RpcClient<Client> client = this.pool.getObject();
	try {
	    ResBool res = client.getClient().expired(logIndex, flag, key, seconds, ext);
	    this.pool.returnObject(client);
	    return res.value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, key);
	    this.pool.destroyObject(client);
	    throw e;
	}
    }
    
    
    public String echo(long logIndex, String data) throws Exception {
	String flag = this.getClassName() + ".echo";
	
	RpcClient<Client> client = this.pool.getObject();
	try {
	    String value = client.getClient().echo(logIndex, caller, data, null);
	    this.pool.returnObject(client);
	    return value;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "key:{}", e, data);
	    this.pool.destroyObject(client);
	    throw e;
	}

    }

}
