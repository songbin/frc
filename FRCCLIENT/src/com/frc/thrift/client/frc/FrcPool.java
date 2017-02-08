package com.frc.thrift.client.frc;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import com.frc.client.core.DefaultObject;
import com.frc.thrift.stub.FrcService.Client;
import com.frc.utility.FRCLogger;

/**
 * @author songbin
 * @version 创建时间：2016年7月7日 下午1:24:58
 * 
 */
public class FrcPool {
    // private GenericObjectPool pool;
    private ObjectPool<RpcClient<Client>> pool;
    private FrcFactory                    factory = null;

    public FrcPool(String host, int port, int timeout) {
        factory = new FrcFactory(host, timeout, port);
        GenericObjectPoolConfig config = DefaultObject.getPoolConfig();
        pool = new GenericObjectPool<RpcClient<Client>>(this.factory, config);
    }


    private String getClassName() {
        return "FrcPool";
    }

    public void clear(){

        if(null == this.pool) return;
        String flag = this.getClassName() + ".clear";
        try{
            this.pool.clear();
        }catch(Exception e){
            FRCLogger.getInstance().warn(-100, flag, "exception:",e);
        }
    }

    public RpcClient<Client> getObject() {
        String flag = this.getClassName() + ".getObject";
        try {
            //logger.debug("getObject...");
            RpcClient<Client> client = pool.borrowObject();
            return client;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(-100, flag, "", e);
            return null;
        }
    }

    public boolean returnObject(RpcClient<Client> client) {
        String flag = this.getClassName() + ".returnObject";
        try {
            // logger.debug("returnObject...");
            client.getVersion();
            this.pool.returnObject(client);
            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(-100, flag, "", e);
            return false;
        }
    }

    public boolean destroyObject(RpcClient<Client> client) {
        String flag = this.getClassName() + ".destroyObject";
        try {
            // logger.debug("destroyObject...");
            this.pool.invalidateObject(client);
            // return client.close();
            return true;
        } catch (Exception e) {
            FRCLogger.getInstance().warn(-100, flag, "", e);
            return false;
        }
    }
}
