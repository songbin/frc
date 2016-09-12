package com.frc.core.common;
/** 
 * @author  songbin 
 * @version 创建时间：2016年8月19日  
 *  
 */
public class DefaultValues
{
	public static final String PATH_PROPERTIES_DEFAULT = "properties/default.properties";
	public static final String LUA_LOCK_PROCESS_PATH = "script/lua/lock_process.lua";
	
	public static final long THRIFT_MAX_READ_BUF = 16384000L;
	
	public static final String MIGRATE_LOCK_KEY = "frc_migrate_lock";
	
	public static int ZK_ADD_FAIL = 0;
	/**root path . it needn't process*/
	public static int ZK_ADD_ROOT = 1;
	public static int ZK_ADD_SUC = 2;

}
