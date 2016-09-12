package com.frc.node.tester;
/** 
 * @author songbin
 * @version 2016年9月1日  
 */
import static org.junit.Assert.*;

import org.junit.Test;

import com.frc.core.redis.RedisClient;
import com.frc.redis.domain.RedisInfo;
import com.frc.redis.domain.RedisState;
public class ClusterRedisCmdTester {
    private long logIndex = -100;
    
    private RedisClient getClient(){
	RedisInfo redisInfo = new RedisInfo();
	RedisClient client = new RedisClient();
	redisInfo.setRedis_passwd("");
	redisInfo.setRedis_host("127.0.0.1");
	redisInfo.setRedis_port(6379);
	redisInfo.setRedis_name("test");
	redisInfo.setRedis_vnode_num(20);
	redisInfo.setRedis_state(RedisState.STATE_NORMAL);
	if(!client.init(logIndex, redisInfo)) return null;
	return client;
    }
    @Test
    public void test(){
	System.out.println("OK");
    }
    
    @Test
    public void setTester(){
	boolean ret = this.getClient().set(logIndex, "h", "sasasa");
	if(ret){
	   System.out.println("OK");
	   
	} else {
	    fail("sasass");
	}
    }
    
    @Test
    public void migrateTester(){
	String ret = this.getClient().migrateEx(logIndex, "h", "127.0.0.1", 6385);
	System.out.println(ret);
 
    }
    
    
}
