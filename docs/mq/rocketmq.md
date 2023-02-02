## Referer

[rocketmq官网](https://rocketmq.apache.org/)


## rocketmq

```
start mqnamesrv.cmd

start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true

mvn clean package -Dmaven.test.skip=true

java -jar rocketmq-console-ng-1.0.0.jar

127.0.0.1:9876

http://localhost:8080

set NAMESRV_ADDR=localhost:9876

tools.cmd org.apache.rocketmq.example.quickstart.Producer

tools.cmd org.apache.rocketmq.example.quickstart.Consumer

mqshutdown.cmd broker

mqshutdown.cmd namesrv


死信队列、延迟队列

事务消息、顺序消息

MQ延迟消息如何实现？？

消息队列削峰解耦




```


