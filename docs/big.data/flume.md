## Referer

[Flume官方文档-英文](https://flume.apache.org/)

[Flume中文文档1.8](https://www.bookstack.cn/read/flumeUserGuideCnDoc-1.8/README.md)

[Flume中文文档1.9](https://www.docs4dev.com/docs/zh/flume/1.9.0/reference/FlumeUserGuide.html#)

### 1.Flume 使用场景

线上数据一般主要是落地（存储到磁盘）或者通过 socket 传输给另外一个系统，这种情况下，你很难推动线上应用或服务去修改接口，实现直接向 kafka里写数据，这时候你可能就需要 flume 这样的系统帮你去做传输。

### 2.Flume丢包问题

单机 upd 的 flume source 的配置，100+M/s 数据量，10w qps flume 就开始大量丢包，因此很多公司在搭建系统时，抛弃了 Flume，自己研发传输系统，但是往往会参考 Flume 的 Source-Channel-Sink 模式。一些公司在 Flume 工作过程中，会对业务日志进行监控，例如 Flume agent中有多少条日志，Flume 到 Kafka 后有多少条日志等等，如果数据丢失保持在1%左右是没有问题的，当数据丢失达到 5%左右时就必须采取相应措施。

### 3.Flume与Kafka的选取

采集层主要可以使用 Flume、Kafka 两种技术。Flume：Flume 是管道流方式，提供了很多的默认实现，让用户通过参数部署，及扩展 API。

Kafka：Kafka 是一个可持久化的分布式的[消息队列](https://cloud.tencent.com/product/cmq?from=10680)。

Kafka 是一个非常通用的系统。你可以有许多生产者和很多的消费者共享多个主题 Topics。相比之下，Flume 是一个专用工具被设计为旨在往 HDFS，HBase 发送数据。它对 HDFS 有特殊的优化，并且集成了 Hadoop 的安全特性。所以，Cloudera 建议如果数据被多个系统消费的话，使用 kafka；如果数据被设计给 Hadoop 使用，使用 Flume。正如你们所知 Flume 内置很多的 source 和 sink 组件。然而，Kafka 明显有一个更小的生产消费者生态系统，并且 Kafka 的社区支持不好。希望将来这种情况会得到改善，但是目前：使用 Kafka 意味着你准备好了编写你自己的生产者和消费者代码。如果已经存在的 Flume Sources 和 Sinks 满足你的需求，并且你更喜欢不需要任何开发的系统，请使用 Flume。Flume 可以使用拦截器实时处理数据。这些对数据屏蔽或者过量是很有用的。Kafka 需要外部的流处理系统才能做到。Kafka 和 Flume 都是可靠的系统，通过适当的配置能保证零数据丢失。然而，Flume 不支持副本事件。于是，如果 Flume 代理的一个节点奔溃了，即使使用了可靠的文件管道方式，你也将丢失这些事件直到你恢复这些磁盘。如果你需要一个高可靠性的管道，那么使用 Kafka 是个更好的选择。Flume 和 Kafka 可以很好地结合起来使用。如果你的设计需要从 Kafka 到Hadoop 的流数据，使用 Flume 代理并配置 Kafka 的 Source 读取数据也是

可行的：你没有必要实现自己的消费者。你可以直接利用 Flume 与 HDFS 及HBase 的结合的所有好处。你可以使用 Cloudera Manager 对消费者的监控，并且你甚至可以添加拦截器进行一些流处理。

### 4.Flume怎么采集数据到Kafka，实现方式

使用官方提供的 flumeKafka 插件，插件的实现方式是自定义了 flume 的sink，将数据从 channle 中取出，通过 kafka 的 producer 写入到 kafka 中，可以自定义分区等。

### 5.Flume管道内存，Flume宕机了数据丢失怎么解决

1）Flume 的 channel分为很多种，可以将数据写入到文件。

2） 防止非首个 agent 宕机的方法数可以做集群或者主备。

### 6.Flume配置方式

Flume 的配置围绕着 source、channel、sink 叙述，flume 的集群是做在agent 上的，而非机器上。

### 7.Flume不采集Nginx日志，通过Logger4j采集日志，优缺点是什么

优点：Nginx 的日志格式是固定的，但是缺少 sessionid，通过 logger4j 采集的日志是带有 sessionid 的，而 session 可以通过 redis 共享，保证了集群日志中的同一 session 落到不同的 tomcat 时，sessionId 还是一样的，而且logger4j 的方式比较稳定，不会宕机。

缺点：不够灵活，logger4j 的方式和项目结合过于紧密，而 flume 的方式比较灵活，拔插式比较好，不会影响项目性能。

### 8.Flume与Kafka采集日志区别，中途时间停止了，怎么记录之前的日志

Flume 采集日志是通过流的方式直接将日志收集到存储层，而 kafka 是将缓存在 kafka 集群，待后期可以采集到存储层。Flume 采集中间停了，可以采用文件的方式记录之前的日志，而 kafka 是采用 offset 的方式记录之前的日志。

### 9.Flume有哪些组件，Flume的source，channel，sink具体是做什么的

1）source：用于采集数据，Source 是产生数据流的地方，同时 Source 会将产生的数据流传输到 Channel，这个有点类似于 Java IO 部分的 Channel。

2）channel：用于桥接 Sources 和 Sinks，类似于一个队列。

3）sink：从 Channel 收集数据，将数据写到目标源(可以是下一个 Source，也可以是 HDFS 或者 HBase)。

注意：要熟悉 source、channel、sink 的类型



--------------------------------------------------------------------------------------------分割线------------------------------------------------------------------------------------------------------------------------



## 1 知道 Flume 的 Channel 是啥吗

Channel 被设计为 Event 中转临时缓冲区，存储 Source 收集并且没有被 Sink 读取的 Event，为平衡 Source 收集和 Sink 读取的速度，可视为 Flume 内部的消息队列。

Channel **线程安全并且具有事务性**，支持 Source 写失败写，和 Sink 读失败重复读的操作。常见的类型包括 Memory Channel，File Channel，Kafka Channel。

## 2 介绍一下 Memory Channel

读写速度**快**，但是存储数据量**小**，Flume 进程挂掉、服务器停机或者重启都会导致数据丢失。资源充足、不关心数据丢失的场景下可以用。

## 3 说说 File Channel

将 event 写入磁盘文件，与 Memory Channel 相比存储容量大，无数据丢失风险。File Channel 数据存储路径可以配置多磁盘文件路径，通过**磁盘并行写入**提高 File Channel 性能。Flume 将 Event 顺序写入到 File Channel 文件的末尾。可以在配置文件中通过设置 maxFileSize 参数配置数据文件大小，当被写入的文件大小达到上限的时候，Flume 会重新创建新的文件存储写入 Event。当一个已经关闭的只读数据文件的 Event 被读取完成，并且 Sink 已经提交读取完成的事务，则 Flume 把存储该数据的**文件删除**。

## 4 说说 Kafka Channel

Memory Channel 有很大的丢数据风险，而且容量一般，File Channel 虽然能缓存更多的消息，**但如果缓存下来的消息还没写入 Sink**，此时 Agent 出现故障则 File Channel 中的消息一样不能被继续使用，直到该 Agent 恢复。而 Kafka Channel 容量大，容错能力强。

有了 Kafka Channel 可以在日志收集层只配置 Source 组件和 Kafka 组件，不需要再配置 Sink 组件，减少了日志收集层启动的进程数，有效降低服务器内存、磁盘等资源的使用率。而日志汇聚层，可以只配置 Kafka Channel 和 Sink，不需要再配置 Source。

`kafka.consumer.auto.offset.reset`，当 Kafka 中没有 Consumer 消费的初始偏移量或者当前偏移量在 Kafka 中不存在（比如数据已经被删除）情况下 Consumer 选择从 Kafka 拉取消息的方式，earliest 表示从最早的偏移量开始拉取，latest 表示从最新的偏移量开始拉取，none 表示如果没有发现该 Consumer 组之前拉取的偏移量则抛出异常。

## 5 介绍一下 Kafka 几种 Sink

1. HDFS Sink: 将 Event 写入 HDFS 文件存储，能够有效长期存储大量数据。
2. Kafka Sink: Flume 通过 Kafka Sink 将 Event 写入到 Kafka 中的主题，其他应用通过订阅主题消费数据。`kafka.producer.acks` 可以设置 Producer 端发送消息到 Broker 之后不需要等待 Broker 返回成功送达的信号。0表示 Producer 发送消息到 Broker 之后不需要等待 Broker 返回成功送达的信号，这种方式吞吐量高，但存在丢失数据的风险。1表示 Broker 接收到消息成功写入本地 log 文件后向 Producer 返回成功 接收的信号，不需要等待所有的 Follower 全部同步完消息后再做回应，这种方式在数据丢失风险和吞吐量之间做了平衡。-1表示 Broker 接收到 Producer 的消息成功写入本地 log 并且等待**所有的 Follower 成功写入本地 log 后向 Producer 返回成功接收的信号**，这种方式能够保证消息不丢失，但是性能最差（层层递进）。

## 6 知道 Flume 的拦截器吗

Source 将 Event 写入到 Channel 之前可以使用拦截器对 Event 进行各种形式的处理，Source 和 Channel 之间可以有多个拦截器，不同拦截器使用不同的规则处理 Event，包括时间、主机、UUID、正则表达式等多种形式的拦截器。

## 7 介绍一下什么是选择器

Source 发送的 Event 通过 Channel 选择器来选择以哪种方式写入到 Channel 中，Flume 提供三种类型 Channel 选择器，分别是复制、复用和自定义选择器。

1. 复制选择器: 一个 Source 以复制的方式将一个 Event 同时写入到多个 Channel 中，不同的 Sink 可以从不同的 Channel 中获取相同的 Event，比如一份日志数据同时写 Kafka 和 HDFS，一个 Event 同时写入两个 Channel，然后不同类型的 Sink 发送到不同的外部存储。
2. 复用选择器: 需要和拦截器配合使用，根据 Event 的头信息中不同键值数据来判断 Event 应该写入哪个 Channel 中。

## 8 了解 Flume 的负载均衡和故障转移吗

目的是为了提高整个系统的容错能力和稳定性。简单配置就可以轻松实现，首先需要设置 Sink 组，同一个 Sink 组内有多个子 Sink，不同 Sink 之间可以配置成负载均衡或者故障转移。