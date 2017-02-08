package com.frc.client.command.tester;

/** 
 * @author songbin
 * @version 2016年9月12日 
 */
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.frc.thrift.client.frc.FrcClient;

public class AllCommandsTester {
    long logIndex = -100;
    String host = "127.0.0.1";
    int port = 5806;
    int timeout = 2000;

    @Test
    public void setTester() {
	String key = "test1";
	String value = "this is test";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    boolean ret = client.set(logIndex, key, value);
		client.clear();
	    if (ret) {
		String val = client.get(logIndex, key);
		if (!val.equals(value)) {
		    fail("cannot get key");
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("fail");
	}
    }

    @Test
    public void incrTester() {
	String key = "test_incr";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    long ret = client.incr(logIndex, key, null);
	    if (ret > 0) {
		long ret1 = client.incr(logIndex, key, null);
		if ((ret1 - ret) != 1) {
		    fail("incr fail");
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("fail");
	}
    }

    @Test
    public void delTester() {
	String key = "test_del";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    long ret = client.incr(logIndex, key, null);
	    if (ret > 0) {
		boolean delRet = client.del(ret, key, null);
		if (!delRet) {
		    fail("del failed");
		} else {
		    boolean bExists = client.exists(ret, key, null);
		    if (bExists) {
			fail("cannot delete the key");
		    }
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("fail");
	}
    }

    @Test
    public void hsetTester() {
	String key = "test_hset";
	String field = "k1";
	String value = "v1";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    boolean ret = client.hset(logIndex, key, field, value, null);
	    if (ret) {
		String ret_val = client.hget(logIndex, key, field, null);
		if (!ret_val.equals(value)) {
		    fail("del failed");
		} 
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("fail");
	}
    }

    @Test
    public void hmsetTester() {
	String key = "test_hmset";
	 
	Map<String, String> hash = new HashMap<String, String>();
	try {
	    String field1 = "field1";
	    String field2 = "field2";
	    String val1 = "val1";
	    String val2 = "val2";
	    hash.put(field1, val1);
	    hash.put(field2, val2);
	    
	    FrcClient client = new FrcClient(host, port, timeout);
	    boolean ret = client.hmset(logIndex, key, hash, null);
	    if (ret) {
		Map<String, String> retMap = client.hgetAll(logIndex, key, null);
		if(!retMap.get(field2).equals(val2) || !retMap.get(field1).equals(val1)){
		    fail("fail1");
		}
		
		String ret_val1 = client.hget(logIndex, key, field1, null);
		String ret_val2 = client.hget(logIndex, key, field2, null);
		if(!ret_val1.equals(val1) || !ret_val2.equals(val2)){
		    fail("fail2");
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("exception fail");
	}
    }
    
    @Test
    public void hdelTester() {
	String key = "test_hdel";
	try {
	    String field = "field1";
	    String value = "val1";
	    
	    FrcClient client = new FrcClient(host, port, timeout);
	    client.hset(logIndex, key, field, value, null);
	    boolean ret = client.hdel(logIndex, key, field, null);
	    if (ret) {
		boolean ret_exists = client.hexists(logIndex, key, field, null);
		if(ret_exists){
		    fail("not del");
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("exception fail");
	}
    }
    
    @Test
    public void zaddMapTester() {
	String key = "test_zadd_map";
	try {
	    Map<String, Double> scoreMembers = new HashMap<String, Double>();
	    String member1 = "mem1";
	    String member2 = "mem2";
	    String member3 = "mem3";
	    scoreMembers.put( member1, 1.2);
	    scoreMembers.put( member2, 1.3);
	    scoreMembers.put( member3, 1.4);
	    
	    FrcClient client = new FrcClient(host, port, timeout);
	    
	    boolean ret = client.zadd(logIndex, key, scoreMembers, null);
	    if (ret) {
		Set<String> set = client.zrange(logIndex, key, 0, -1, null);
		if(set.size() == scoreMembers.size()){
		    long rank = client.zrank(logIndex, key, member1, null);
		    if(rank != 0){
			System.out.println("rank:" + rank + "  set:" + set.toString());
			fail("fail2");
		    }
		}
		else{
		    fail("fail1");
		}
	    } else {
		fail("fail");
	    }
	} catch (Exception e) {
	    fail("exception fail");
	}
    }
}
