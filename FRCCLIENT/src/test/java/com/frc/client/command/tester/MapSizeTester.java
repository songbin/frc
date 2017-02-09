package com.frc.client.command.tester;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;
 
public class MapSizeTester {

   

    @Test
    public void mapTester() {
	 
	try {
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    for(int i=0; i < 340000; i++){
	        TesterDomain domain = new TesterDomain(); 
	        String key = "hcode_" + i;
	        domain.setHcode("hcode_" + i);
	        domain.setPlatform("mobile");
	        map.put(key, domain);
	    }
	    
	    Scanner input = new Scanner(System.in);
        String val = null;       // 记录输入的字符串
        do{
            System.out.print("请输入：");
            val = input.next();       // 等待输入值
            System.out.println("您输入的是："+val);
        }while(!val.equals("#"));   // 如果输入的值不是#就继续输入
        System.out.println("你输入了\"#\"，程序已经退出！");
        input.close(); // 关闭资源
        
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
     
}
