# FRC Instruction
## About FRC
+ FRC means FREE REDIS CLUSTER.
+ It is similar with Twemproxy.

## Compare with other system

<table>
<thead>
<tr>
<td></td><td>FRC</td><td>Codis</td><td>Twemproxy</td><td>Redis Cluster</td>
</tr>
</thead>
<tbody>
<tr>
<td>resharding without restarting cluster</td>
<td>Yes</td>
<td>Yes</td>
<td>No</td>
<td>Yes</td>
</tr>

<tr>
<td>hash tags for multi-key operations</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr>

<tr>
<td>multi-key operations while resharding</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr>

<tr>
<td>Redis clients supporting</td>
<td>Any</td>
<td>Any</td>
<td>Any</td>
<td>-</td>
</tr>

<tr>
<td>Redis clients supporting</td>
<td>No</td>
<td>Yes</td>
<td>-</td>
<td>No</td>
</tr>

<tr>
<td>Language</td>
<td>Java</td>
<td>Go</td>
<td>-</td>
<td>C</td>
</tr>

</tbody>
</table>

## Archtecture of FRC
more infos,can see the Docs.

## FRC Usage
+ For more infos,Please find it in Docs and default.proterties.
+ note: FRC doesn't support redis use password.
+ 1.FRC find redis by zookeeper.
+ 2.Redis have three state(1:Normal 2:del 3:add)
+ 3.Please follow belows to update redis info on zookeeper.
  > create /frc/redis_1 {"redis_state":1,"redis_host":"127.0.0.1","redis_port":6381}<br/>
  
  /frc is the root path which FRC listen on zookeeper.<br/>
  redis_1 is the redis name which you want to set.<br/>
  
## Client Usage

	<dependencies>
		<dependency>
			<groupId>com.frc</groupId>
			<artifactId>frc-client</artifactId>
			<version>1.0.0</version>
		</dependency>
 
	</dependencies>

	<repositories>
		<repository>
			<id>songbin-frc-client</id>
			<url>https://raw.github.com/songbin/frc/tree/master/FRCCLIENT</url>
		</repository>
 

	</repositories>

 

You can use use the FRCClient to invoke the api of FRC.<br/>
The way of invoking api as same as jedis.<br/>
The client has implement the object-pool with common-pool.<br/>
e.g.<br/>
>       FrcClient client = new FrcClient(host, port, timeout);
>       boolean ret = client.set(logIndex, key, value);
>       if (ret) {
>          System.out.println("ok");
>       } else {
>         System.out.println("fail");
>       }
>       client.clear();

> ## Note ##
> FrcClient can only been clear just once at a instance.<br/>
> FrcClient.clear method will destroy all of objects in object-pool.<br/>
> you can invoke clear method when you are to exit from the System.<br/>










