namespace java com.frc.thrift.stub

include "data_type.thrift"

const string VERSION = "1.0.0"
 
/****************************************************************************************************
* interface
*****************************************************************************************************/
service FrcService
{ 
	data_type.ResStr getv(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResBool setv(1:i64 logIndex, 2:string caller, 3:string key, 4:string value, 5:string ext),
	data_type.ResBool migrate(1:i64 logIndex, 2:string caller, 3:string key, 4:string host, 5:i32 port, 6:string ext),
	data_type.ResLong incr(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResBool delv(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResStr hget(1:i64 logIndex, 2:string caller, 3:string key, 4:string field, 5:string ext),
	data_type.ResSetStr hVals(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResBool hset(1:i64 logIndex, 2:string caller, 3:string key, 4:string field, 5:string value, 6:string ext),
	data_type.ResBool hexists(1:i64 logIndex, 2:string caller, 3:string key, 4:string field, 5:string ext),
	data_type.ResBool hmset(1:i64 logIndex, 2:string caller, 3:string key, 4:map<string,string> hash, 5:string ext),
	data_type.ResMapStrStr hgetAll(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResListStr hmget(1:i64 logIndex, 2:string caller, 3:string key, 4:list<string> fields, 5:string ext),
	data_type.ResBool hdel(1:i64 logIndex, 2:string caller, 3:string key, 4:string field, 5:string ext),
	data_type.ResBool zadd(1:i64 logIndex, 2:string caller, 3:string key, 4:map<double, string> scoreMembers, 5:string ext),
	data_type.ResBool zaddmember(1:i64 logIndex, 2:string caller, 3:string key, 4:double score, 5:string member, 6:string ext),
	data_type.ResBool zrem(1:i64 logIndex, 2:string caller, 3:string key, 4:list<string> members, 5:string ext),
	data_type.ResSetStr zrange(1:i64 logIndex, 2:string caller, 3:string key, 4:i64 start, 5:i64 ends, 6:string ext),
	data_type.ResSetStr zrevrange(1:i64 logIndex, 2:string caller, 3:string key, 4:i64 start, 5:i64 ends, 6:string ext),
	data_type.ResSetStr zrevrangeByScore(1:i64 logIndex, 2:string caller, 3:string key, 4:i64 start, 5:i64 ends, 6:string ext),
	data_type.ResDouble zscore(1:i64 logIndex, 2:string caller, 3:string key, 4:string member, 5:string ext),
	data_type.ResLong zrevrank(1:i64 logIndex, 2:string caller, 3:string key, 4:string member, 5:string ext),
	data_type.ResLong zrank(1:i64 logIndex, 2:string caller, 3:string key, 4:string member, 5:string ext),
	data_type.ResBool exists(1:i64 logIndex, 2:string caller, 3:string key, 4:string ext),
	data_type.ResListStr lrange(1:i64 logIndex, 2:string caller, 3:string key, 4:i64 start, 5:i64 ends, 6:string ext),	
	data_type.ResBool setnx(1:i64 logIndex, 2:string caller, 3:string key, 4:string value, 5:i32 expired_time, 6:string ext),
	data_type.ResBool lpush(1:i64 logIndex, 2:string caller, 3:string key, 4:list<string> strings, 5:string ext),
	data_type.ResBool rpush(1:i64 logIndex, 2:string caller, 3:string key, 4:list<string> strings, 5:string ext),
	data_type.ResBool expired(1:i64 logIndex, 2:string caller, 3:string key, 4:i32 seconds, 5:string ext),
	string echo(1:i64 logIndex, 2:string caller, 3:string srcStr, 4:string ext)
}
