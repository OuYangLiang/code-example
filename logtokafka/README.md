# logtokafka

Sample。实现AppenderBase，将log日志直接写入Kafka，消息格式如下：

```shell
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 1","timestamp":1507907887499}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 2","timestamp":1507907887724}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 3","timestamp":1507907887724}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 4","timestamp":1507907887725}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 5","timestamp":1507907887725}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 6","timestamp":1507907887725}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 7","timestamp":1507907887726}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 8","timestamp":1507907887726}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 9","timestamp":1507907887726}
{"level":"INFO","logger":"com.personal.oyl.test.logtokafka.App","message":"Message ... 10","timestamp":1507907887727}
```