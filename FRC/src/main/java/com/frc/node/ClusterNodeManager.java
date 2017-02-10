package com.frc.node;

import com.frc.core.redis.RedisClient;
import com.frc.redis.domain.RedisInfo;
import com.frc.utility.FRCLogger;
import com.frc.utility.JSONProvider;

/**
 * @author songbin
 * @version 2016年8月26日
 */
public class ClusterNodeManager {
    
    private String getClassName(){
	return "ClusterManagerCenter";
    }
    
    public void start() {
	
    }
    
    public boolean nodeAdd(long logIndex, RedisInfo redisInfo){
	String flag = this.getClassName() + ".nodeAdd";
	try {
	    FRCLogger.getInstance().info(logIndex, flag, "add:{}", null, JSONProvider.toJSONString(logIndex, redisInfo));
	    
	    RedisClient redisClient = new RedisClient();
	    redisClient.init(logIndex, redisInfo);
	    ClusterAlgo.addRedis(logIndex, redisClient);
	    
	    return true;   
	} catch(Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return false;
	}
    }
    
    public boolean nodeUpdate(long logIndex, RedisInfo redisInfo){
	String flag = this.getClassName() + ".nodeUpdate";
	try {
	    FRCLogger.getInstance().info(logIndex, flag, "update:{}", null, JSONProvider.toJSONString(logIndex, redisInfo));
	    
	     
	    
	    ClusterAlgo.updateRedis(logIndex, redisInfo);
	    
	    return true;   
	} catch(Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return false;
	}
    }
    
    public boolean nodeRemove(long logIndex, RedisInfo redisInfo){
	String flag = this.getClassName() + ".nodeRemove";
	try {
	    FRCLogger.getInstance().info(logIndex, flag, "remove:{}", null, JSONProvider.toJSONString(logIndex, redisInfo));
	    
	    RedisClient redisClient = new RedisClient();
	    redisClient.setRedis_name(redisInfo.getRedis_name());
	    ClusterAlgo.removeRedis(logIndex, redisClient);
	    
	    return true;   
	} catch(Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return false;
	}
    }
    
}
