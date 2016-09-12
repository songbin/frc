namespace java com.frc.thrift.datatype

const string VERSION = "1.0.0"
 
/*bool类型返回结果*/
struct ResBool 
{
  1: i32 res,
  2: bool value
  3: string ext
}

/*String类型返回结果*/
struct ResStr
{
  1: i32 res,
  2: string value
  3: string ext
}

/*long类型返回结果*/
struct ResLong
{
  1: i32 res,
  2: i64 value
  3: string ext
}

/*int类型返回结果*/
struct ResInt
{
  1: i32 res,
  2: i32 value
  3: string ext
}

/*double类型返回结果*/
struct ResDouble
{
  1: i32 res,
  2: double value
  3: string ext
}

/*list<string>类型返回结果*/
struct ResListStr 
{
  1: i32 res,
  2: list<string> value
  3: string ext
}

struct ResLongListStr
{
  1: i32 res,
  2: i64 valueLong,
  3: list<string> valueList
  4: string ext
}

/*Set<string>类型返回结果*/
struct ResSetStr 
{
  1: i32 res,
  2: set<string> value
  3: string ext
}

/*map<string,string>类型返回结果*/
struct ResMapStrStr 
{
  1: i32 res,
  2: map<string,string> value
  3: string ext
}

/*map<double,string>类型返回结果*/
struct ResMapDoubleStr 
{
  1: i32 res,
  2: map<double,string> value
  3: string ext
}

