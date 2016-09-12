package com.frc.node.tester;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import org.junit.Test;

import com.frc.core.redis.RedisClient;
import com.frc.node.ClusterAlgo;
import com.frc.utility.JSONProvider;

/**
 * @author songbin
 * @version 2016年8月26日
 */
public class CluasterAlgoTester {

    private long logIndex = -100;

    @Test
    public void test() {
	fail("Not yet implemented");
    }

    @Test
    public void addRedis2VnodeTetster() {

	String redisName = "testRedis1";
	RedisClient redisClient = new RedisClient();
	redisClient.setRedis_name(redisName);
	ClusterAlgo.addRedis(logIndex, redisClient);

	String redisName2 = "testRedis2";
	RedisClient redisClient2 = new RedisClient();
	redisClient2.setRedis_name(redisName2);
	ClusterAlgo.addRedis(logIndex, redisClient2);

	SortedMap<Long, RedisClient> mapRedis = ClusterAlgo.getMapRedis();

	SortedMap<Long, Long> map = ClusterAlgo.getvNodes();
	System.out.println("******************************");
	int count = 0;
	for (Long key : map.keySet()) {
	    Long redis_hash = map.get(key);

	    RedisClient client = mapRedis.get(redis_hash);
	    String data = JSONProvider.toJSONString(logIndex, client);
	    System.out.println(count++ + "->key:" + key + "   redis hash:"
		    + redis_hash + "   data:" + data);
	}

	HashMap<String, List<Long>> hmap = ClusterAlgo.getMapRedisVnodeList();
	System.out.println("******************************");
	for (String key : hmap.keySet()) {
	    List<Long> list = hmap.get(key);
	    System.out.print(key + ":");
	    for (Long id : list) {
		System.out.print(" " + id);
	    }
	    System.out.println("####################");
	}

    }

}
