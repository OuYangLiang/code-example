// defines the namespace
namespace java com.personal.oyl.code.example.thrift

service HelloService {
  i32 add(1:i32 num1, 2:i32 num2)
}

service AnotherService {
  string hello(1:string name)
}