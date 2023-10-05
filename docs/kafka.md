## Referer

[kafka官网](https://kafka.apache.org/)

### 零拷贝

### 内核态、用户态、缓存区

### Kafka的性能高如何体现？？？

1.PageCache
2.消息批处理
3.0拷贝节省I/O

Kafka ISR

Pulsar优化Kalfka

Kafka解耦ZK

reblance机制



### 延迟消息实现

Kafka 支持延迟消息通过设置消息的时间戳，可以使得消息在一定时间后才能被消费者消费。在 Kafka 中，消息的时间戳可以通过消息的 `timestamp` 属性来设置。同时，在 Kafka 中，也提供了一种专门的延迟消息的机制，即使用 Kafka 的定时器（Kafka Timer）来实现。

下面是使用 Kafka 定时器实现延迟消息的代码示例：

```
javaCopy code// 导入相关包
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class DelayedMessageProducer {

    public static void main(String[] args) {

        // 配置 Kafka Producer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 创建延迟消息
        String message = "Delayed Message!";
        int delayTime = 5000; // 5 秒钟的延迟时间
        long timestamp = System.currentTimeMillis() + delayTime;

        // 发送消息
        ProducerRecord<String, String> record = new ProducerRecord<>("test", null, timestamp, null, message);
        producer.send(record);

        // 关闭 Kafka Producer
        producer.close();
    }
}
```

在上面的代码示例中，我们首先创建了一个 Kafka Producer，并配置了相应的属性。然后，我们创建了一个消息，并设置了延迟时间为 5 秒钟。最后，我们使用 Kafka Producer 发送了这个延迟消息。

需要注意的是，在 Kafka 中，延迟时间的单位是毫秒。因此，如果要设置延迟时间为 5 秒钟，需要将其转换为毫秒，即 `delayTime = 5000`。

另外，需要在 Kafka Broker 中配置 `message.timestamp.type=CreateTime`，才能够使用消息的时间戳功能。



### kafka windows安装步骤

###### 启动

```shell
## 注意：kafka安装目录不能太深，影响启动！！！

# 启动 zookeeper
zookeeper-server-start.bat ..\..\config\zookeeper.properties

# 启动 kafka服务端
kafka-server-start.bat ..\..\config\server.properties
```

###### 创建/查看topic

```shell
# 查看当前存在的topic
kafka-topics.bat --list --bootstrap-server localhost:9092

# 创建 skywalking_delay_message_topic （不指定分区和副本）
kafka-topics.bat --create --topic skywalking_delay_message_topic --bootstrap-server localhost:9092

#　创建　kafka-test-topic　分区　１　副本　１
kafka-topics.bat  --create --topic　kafka-test-topic --replication-factor 1 --partitions 1 --bootstrap-server localhost:9097 
```

