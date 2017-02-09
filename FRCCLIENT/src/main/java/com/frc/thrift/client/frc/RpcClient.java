package com.frc.thrift.client.frc;

import org.apache.thrift.transport.TTransport;

/** 
 * @author  songbin 
 * @version 创建时间：2016年7月8日
 *  
 */
public class RpcClient<T>
{
	private TTransport transport;
	private T client;
	private long version;

	public RpcClient(T client, TTransport transport, long version)
	{
		this.client = client;
		this.transport = transport;
		this.version = version;
	}

	public TTransport getTTransport()
	{
		return transport;
	}

	public T getClient()
	{
		return client;
	}

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
	
}
