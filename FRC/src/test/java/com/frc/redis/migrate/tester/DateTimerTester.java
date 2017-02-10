package com.frc.redis.migrate.tester;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

/** 
 * @author songbin
 * @version 2016年8月31日 上午10:52:02  
 */
public class DateTimerTester {

    @Test
    public void test() {
	fail("Not yet implemented");
    }
    
    @Test
    public void Calendartester(){
	try{
	    for(int index = 0; index < 10; index++){
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		int second = Calendar.getInstance().get(Calendar.SECOND);
		System.out.println("hour:" + hour + "  minute:" + minute + " second:" + second);
		Thread.sleep(1000*10);
	    } 
	}catch(Exception e){
	    
	}
	
	

    }

}
