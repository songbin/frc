package com.frc.client.core;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/** 
 * @author  songbin 
 * @version 创建时间：2016年7月7日  
 *  
 */
public class DefaultObject
{
	 
	private static GenericObjectPoolConfig poolConfig = null;
	 
	
	public static synchronized GenericObjectPoolConfig getPoolConfig()
	{
		if(null == poolConfig)
		{
			poolConfig = new GenericObjectPoolConfig();
			poolConfig.setMaxWaitMillis(3000);
			poolConfig.setMaxIdle(1);
			poolConfig.setMaxTotal(50);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinEvictableIdleTimeMillis(6000);
			poolConfig.setTestOnBorrow(true);
			poolConfig.setTestOnReturn(true);
			//config.set(DefaultObject.getProperties().getInt(ConfigKeys.THRIFT_SOCKET_TIMEOUT, DefaultValue.THRIFT_SOCKET_TIMEOUT));
			
		}
		return poolConfig;
	}
}
