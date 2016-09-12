package com.frc.thrift.client.frc;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import com.frc.thrift.stub.FrcService;
import com.frc.thrift.stub.FrcService.Client;

/**
 * @author songbin
 * @version 创建时间：2016年7月7日 上午11:16:49
 * 
 */
@SuppressWarnings("rawtypes")
public class FrcFactory extends
	BasePooledObjectFactory<RpcClient<FrcService.Client>> {
    private static Logger logger = Logger.getLogger(FrcFactory.class);
    private String host;
    private int timeout;
    private int port;
    public void setPort(int port) {
        this.port = port;
    }

    public FrcFactory(String host, int timeout, int port) {
	super();
	this.host = host;
	this.timeout = timeout;
	this.port = port;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PooledObject makeObject() throws Exception {
	//logger.debug("makeObject...........");
	try {
	    String host = this.host;
	    int port = this.port;
	    int timeout = this.timeout;
	    TFramedTransport transport = new TFramedTransport(new TSocket(host,
		    port, timeout));
	    TBinaryProtocol protocol = new TBinaryProtocol(transport);
	    FrcService.Client client = new FrcService.Client(protocol, protocol);
	    transport.open();
	    RpcClient<Client> rpcClient = new RpcClient(client, transport);
	    return this.wrap(rpcClient);
	} catch (Exception e) {
	    logger.error("exception", e);
	    return null;
	}

    }

    @Override
    public RpcClient<Client> create() throws Exception {

	return null;
    }

    @Override
    public PooledObject<RpcClient<Client>> wrap(RpcClient<Client> client) {
	return new DefaultPooledObject<RpcClient<Client>>(client);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void activateObject(PooledObject p) throws Exception {

	super.activateObject(p);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void destroyObject(PooledObject p) throws Exception {
	try {
	    RpcClient<Client> rpcClient = (RpcClient<Client>) p.getObject();
	    rpcClient.getTTransport().close();
	   // logger.debug("destroyObject...........");
	    super.destroyObject(p);
	} catch (Exception e) {
	    logger.error(e);
	}

    }

    public PooledObject wrapExt(FrcClient client) {
	//logger.debug("wrapExt...........");
	return new DefaultPooledObject<FrcClient>(client);
    }

    @Override
    public boolean validateObject(PooledObject p) {
	//logger.debug("validateObject....");
	@SuppressWarnings("unchecked")
	RpcClient<Client> rpcClient = (RpcClient<Client>) p.getObject();
	String test_data = "OK";
	try {
	    String resp = rpcClient.getClient().echo(-100, "", test_data, null);
	    return resp.equals(test_data);
	} catch (Exception e) {
	    logger.error(e);
	    return false;
	}

    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPort() {
        return port;
    }
}
