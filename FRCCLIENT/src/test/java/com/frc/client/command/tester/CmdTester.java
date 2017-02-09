package com.frc.client.command.tester;

import static org.junit.Assert.*;

import org.junit.Test;

import com.frc.thrift.client.frc.FrcClient;

/** 
 * @author songbin
 * @version 2016年8月29日 
 */
public class CmdTester {

    long logIndex = -100;
    String host = "127.0.0.1";
    int port = 5806;
    int timeout = 2000;
    @Test
    public void test() {
	System.out.println(System.currentTimeMillis());
    }

    @Test
    public void echoTester() {
	String data = "hello world";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    for (int index = 0; index < 100; index++) {
		String resp = client.echo(logIndex, data);
		System.out.println(resp);
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void setBatTester(){
	String key = "test";
	String value = "this is test";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    for (int index = 0; index < 100; index++) {
		String final_key = key + "_" + index;
		String final_value = value + "[ " + index + "]";
		boolean ret =client.set(logIndex, final_key, final_value);
		if (ret) {
		    System.out.println("key:" + final_key + "   ok");
		} else {
		    System.out.println("key:" + final_value + "  fail");
		}
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void setTester(){
	String key = "test1";
	String value = "this is test";
	try {
	    	FrcClient client = new FrcClient(host, port, timeout);
		boolean ret = client.set(logIndex, key, value);
		if (ret) {
		    System.out.println("ok");
		} else {
		    System.out.println("fail");
		}
	    

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void getBatTester(){
	String key = "test";
	 
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
	    for (int index = 0; index < 100; index++) {
		String final_key = key + "_" + index;
		String value = client.get(logIndex, final_key);
		System.out.println("key(" + final_key + "):" + value);
	    }
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void getTester(){
	String key = "test1";
	 
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
		String value = client.get(logIndex, key);
		System.out.println("key(" + key + "):" + value);
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void incrTester(){
	String key = "incr_key";
	try {
	    FrcClient client = new FrcClient(host, port, timeout);
		long value = client.incr(logIndex, key, null);
		System.out.println("key(" + key + "):" + value);
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}	
    }
}
