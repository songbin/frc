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

	public RpcClient(T client, TTransport transport)
	{
		this.client = client;
		this.transport = transport;
	}

	public TTransport getTTransport()
	{
		return transport;
	}

	public T getClient()
	{
		return client;
	}
}
