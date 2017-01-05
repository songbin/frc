package com.frc.main;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.frc.core.common.ReturnValues;
import com.frc.node.ClusterRedisCmd;
import com.frc.thrift.datatype.ResBool;
import com.frc.thrift.datatype.ResDouble;
import com.frc.thrift.datatype.ResListStr;
import com.frc.thrift.datatype.ResLong;
import com.frc.thrift.datatype.ResMapStrStr;
import com.frc.thrift.datatype.ResSetStr;
import com.frc.thrift.datatype.ResStr;
import com.frc.thrift.stub.FrcService;
import com.frc.utility.FRCLogger;

/** 
 * @author songbin
 * @version 2016年8月29日    
 */
public class FrcServer implements FrcService.Iface {
    private ClusterRedisCmd clusterCmd = new ClusterRedisCmd();
    
    private String getClassName(){
	return "FrcServer";
    } 
    
    @Override
    public ResStr getv(long logIndex, String caller, String key,
	    String ext) throws TException {
	
	String flag = this.getClassName() + ".getv";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} ext:{}", null, caller, key, ext);
	return clusterCmd.get(logIndex, key);
    }

    @Override
    public ResBool setv(long logIndex, String caller, String key, String value,
	    String ext) throws TException {
	
	String flag = this.getClassName() + ".getv";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} value:{} ext:{}", null, caller, key, value, ext);
	return clusterCmd.set(logIndex, key, value);
    }

    @Override
    public String echo(long logIndex, String caller, String srcStr, String ext)
	    throws TException {
	
	String flag = this.getClassName() + ".getv";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  srcStr:{}", null, caller, srcStr);
	return srcStr;
    }

    @Override
    public ResBool migrate(long logIndex, String caller, String key,
	    String host, int port, String ext) throws TException {
	ResBool res = new ResBool();
	res.res = ReturnValues.THRIFT_CODE_SUCC;
	res.value = true;
	return res;
    }

    @Override
    public ResLong incr(long logIndex, String caller, String key, String ext)
	    throws TException {
	String flag = this.getClassName() + ".incr";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} ext:{}", null, caller, key, ext);
	return clusterCmd.incr(logIndex, key);
    }

    @Override
    public ResBool delv(long logIndex, String caller, String key, String ext)
	    throws TException {
	String flag = this.getClassName() + ".delv";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} ext:{}", null, caller, key, ext);
	return clusterCmd.del(logIndex, key);
    }

    @Override
    public ResStr hget(long logIndex, String caller, String key, String field,
	    String ext) throws TException {
	String flag = this.getClassName() + ".hget";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} ext:{}", null, caller, key, ext);
	return clusterCmd.hget(logIndex, key, field);
    }

    @Override
    public ResSetStr hVals(long logIndex, String caller, String key, String ext)
	    throws TException {
	String flag = this.getClassName() + ".hVals";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} ext:{}", null, caller, key, ext);
	return clusterCmd.hVals(logIndex, key);
    }

    @Override
    public ResBool hset(long logIndex, String caller, String key, String field,
	    String value, String ext) throws TException {
	String flag = this.getClassName() + ".hset";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} field:{} value:{} ext:{}", null, caller, key, field, value, ext);
	return clusterCmd.hset(logIndex, key, field, value);
    }

    @Override
    public ResBool hexists(long logIndex, String caller, String key,
	    String field, String ext) throws TException {
	String flag = this.getClassName() + ".hexists";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} field:{} ext:{}", null, caller, key, field, ext);
	return clusterCmd.hexists(logIndex, key, field);
    }

    @Override
    public ResBool hmset(long logIndex, String caller, String key,
	    Map<String, String> hash, String ext) throws TException {
	String flag = this.getClassName() + ".hmset";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} field:{} ext:{}", null, caller, key, hash, ext);
	return clusterCmd.hmset(logIndex, key, hash);
    }

    @Override
    public ResMapStrStr hgetAll(long logIndex, String caller, String key,
	    String ext) throws TException {
	String flag = this.getClassName() + ".hgetAll";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  ext:{}", null, caller, key, ext);
	return clusterCmd.hgetAll(logIndex, key);
    }

    @Override
    public ResListStr hmget(long logIndex, String caller, String key,
	    List<String> fields, String ext) throws TException {
	String flag = this.getClassName() + ".hmget";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  filed:{} ext:{} ", null, caller, key, fields.toString(), ext);
	return clusterCmd.hmget(logIndex, key, fields);
    }

    @Override
    public ResBool hdel(long logIndex, String caller, String key, String field,
	    String ext) throws TException {
	String flag = this.getClassName() + ".hdel";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  filed:{} ext:{} ", null, caller, key, field, ext);
	return clusterCmd.hdel(logIndex, key, field);
    }

    @Override
    public ResBool zaddmember(long logIndex, String caller, String key,
	    double score, String member, String ext) throws TException {
	String flag = this.getClassName() + ".zaddmember";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  score:{} member:{} ext:{} ", null, caller, key, score, member, ext);
	return clusterCmd.zadd(logIndex, key, score, member);
    }

    @Override
	public ResBool zadd(long logIndex, String caller, String key,
			Map<String, Double> scoreMembers, String ext) throws TException {
    	String flag = this.getClassName() + ".zadd";
    	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  scoreMembers:{} ext:{} ", null, caller, key, scoreMembers, ext);
    	return clusterCmd.zadd(logIndex, key, scoreMembers);
	}

	@Override
    public ResBool zrem(long logIndex, String caller, String key,
	    List<String> members, String ext) throws TException {
	String flag = this.getClassName() + ".zrem";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} members:{} ext:{} ", null, caller, key, members, ext);
	return clusterCmd.zrem(logIndex, key, (String[])members.toArray());
    }

    @Override
    public ResSetStr zrange(long logIndex, String caller, String key,
	    long start, long ends, String ext) throws TException {
	String flag = this.getClassName() + ".zrange";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} start:{} ends:{} ext:{} ", null, caller, key, start, ends, ext);
	return clusterCmd.zrange(logIndex, key, start, ends);
    }

    @Override
    public ResSetStr zrevrange(long logIndex, String caller, String key,
	    long start, long ends, String ext) throws TException {
	String flag = this.getClassName() + ".zrevrange";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} start:{} ends:{} ext:{} ", null, caller, key, start, ends, ext);
	return clusterCmd.zrevrange(logIndex, key, start, ends);
    }

    @Override
    public ResSetStr zrevrangeByScore(long logIndex, String caller, String key,
	    long start, long ends, String ext) throws TException {
	String flag = this.getClassName() + ".zrevrangeByScore";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} start:{} ends:{} ext:{} ", null, caller, key, start, ends, ext);
	return clusterCmd.zrevrangeByScore(logIndex, key, start, ends);
    }

    @Override
    public ResDouble zscore(long logIndex, String caller, String key,
	    String member, String ext) throws TException {
	String flag = this.getClassName() + ".zrevrangeByScore";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} member:{} ext:{} ", null, caller, key, member, ext);
	return clusterCmd.zscore(logIndex, key, member);
    }

    @Override
    public ResLong zrevrank(long logIndex, String caller, String key,
	    String member, String ext) throws TException {
	String flag = this.getClassName() + ".zrevrangeByScore";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} member:{} ext:{} ", null, caller, key, member, ext);
	return clusterCmd.zrevrank(logIndex, key, member);
    }

    @Override
    public ResLong zrank(long logIndex, String caller, String key,
	    String member, String ext) throws TException {
	String flag = this.getClassName() + ".zrank";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} member:{} ext:{} ", null, caller, key, member, ext);
	return clusterCmd.zrank(logIndex, key, member);
    }

    @Override
    public ResBool exists(long logIndex, String caller, String key, String ext)
	    throws TException {
	String flag = this.getClassName() + ".zrank";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}   ext:{} ", null, caller, key, ext);
	return clusterCmd.exists(logIndex, key);
    }

    @Override
    public ResListStr lrange(long logIndex, String caller, String key,
	    long start, long ends, String ext) throws TException {
	String flag = this.getClassName() + ".zrank";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} start:{} ends:{} ext:{} ", null, caller, key, start, ends, ext);
	return clusterCmd.lrange(logIndex, key, start, ends);
    }

    @Override
    public ResBool setnx(long logIndex, String caller, String key,
	    String value, int expired_time, String ext) throws TException {
	String flag = this.getClassName() + ".setnx";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{} value:{} expired_time:{} ext:{} ", null, caller, key, value, expired_time, ext);
	return clusterCmd.setnx(logIndex, key, value, expired_time);
    }

    @Override
    public ResBool lpush(long logIndex, String caller, String key,
	    List<String> strings, String ext) throws TException {
	String flag = this.getClassName() + ".setnx";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  strings:{} ext:{} ", null, caller, key, strings, ext);
	return clusterCmd.lpush(logIndex, key, (String[])strings.toArray());
    }

    @Override
    public ResBool rpush(long logIndex, String caller, String key,
	    List<String> strings, String ext) throws TException {
	String flag = this.getClassName() + ".setnx";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  strings:{} ext:{} ", null, caller, key, strings, ext);
	return clusterCmd.rpush(logIndex, key, (String[])strings.toArray());
    }

    @Override
    public ResBool expired(long logIndex, String caller, String key,
	    int seconds, String ext) throws TException {
	String flag = this.getClassName() + ".setnx";
	FRCLogger.getInstance().info(logIndex, flag, "caller:{}  key:{}  seconds:{} ext:{} ", null, caller, key, seconds, ext);
	return clusterCmd.expired(logIndex, key, seconds);
    }

    
    
    

}
