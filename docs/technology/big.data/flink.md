## Referer

[Flink官方文档-英文](https://flink.apache.org/)

[Flink官方文档-中文](https://flink.apache.org/zh/)

[Flink中文文档](https://flink.apachecn.org/#/)

![Flink面试题](https://picx.zhimg.com/v2-8c2f6eddd02b6512f5c60c6e27f2aa8f_720w.jpg?source=172ae18b)

# Flink面试题

[![Gavin](https://pic1.zhimg.com/v2-69d2054abb3c6092b2b9eb9173f09080_l.jpg?source=172ae18b)](https://www.zhihu.com/people/gavin-25-6-66)

[Gavin](https://www.zhihu.com/people/gavin-25-6-66)

关注他

94 人赞同了该文章

## 1、面试题一：应用架构

问题：公司怎么提交的实时任务，有多少 Job Manager？

> **解答： 1. 我们使用 yarn session 模式提交任务。每次提交都会创建一个新的**
> **Flink 集群，为每一个 job 提供一个 yarn-session，任务之间互相独立，互不影响，**
> **方便管理。任务执行完成之后创建的集群也会消失。线上命令脚本如下：**
> **bin/yarn-session.sh -n 7 -s 8 -jm 3072 -tm 32768 -qu root.\*.\* -nm \*-\* -d**
> **其中申请 7 个 taskManager，每个 8 核，每个 taskmanager 有 32768M 内存。**
>
> **2. 集群默认只有一个 Job Manager。但为了防止单点故障，我们配置了高可用。**
> **我们公司一般配置一个主 Job Manager，两个备用 Job Manager，然后结合**
> **ZooKeeper 的使用，来达到高可用。**



## 2、面试题二：压测和监控

**问题：怎么做压力测试和监控？**

> **解答：我们一般碰到的压力来自以下几个方面：**
> **一，产生数据流的速度如果过快，而下游的算子消费不过来的话，会产生背压。**
> **背压的监控可以使用 Flink Web UI(localhost:8081) 来可视化监控，一旦报警就能知**
> **道。一般情况下背压问题的产生可能是由于 sink 这个 操作符没有优化好，做一下**
> **优化就可以了。比如如果是写入 ElasticSearch， 那么可以改成批量写入，可以调**
> **大 ElasticSearch 队列的大小等等策略。**
>
> **二，设置 watermark 的最大延迟时间这个参数，如果设置的过大，可能会造成**
> **内存的压力。可以设置最大延迟时间小一些，然后把迟到元素发送到侧输出流中去。**
> **晚一点更新结果。或者使用类似于 RocksDB 这样的状态后端， RocksDB 会开辟**
> **堆外存储空间，但 IO 速度会变慢，需要权衡。**
>
> **三，还有就是滑动窗口的长度如果过长，而滑动距离很短的话，Flink 的性能**
> **会下降的很厉害。我们主要通过时间分片的方法，将每个元素只存入一个“重叠窗**
> **口”，这样就可以减少窗口处理中状态的写入**



## 3、面试题三：为什么用 Flink

问题：为什么使用 Flink 替代 Spark？

> **解答：主要考虑的是 flink 的低延迟、高吞吐量和对流式数据应用场景更好的支**
> **持；另外，flink 可以很好地处理乱序数据，而且可以保证 exactly-once 的状态一致**
> **性。详见文档第一章，有 Flink 和 Spark 的详细对比。**

参照：

![img](https://pic3.zhimg.com/80/v2-19640b33fbb4b746127551786cf69caa_720w.webp)







## 4．面试题四：checkpoint 的存储

问题：Flink 的 checkpoint 存在哪里？

> **解答：可以是内存，文件系统，或者 RocksDB。**

若想܅具体了解，可以参照这篇：选择一个状态后端(state backend)说明

![img](https://pic1.zhimg.com/80/v2-aff72079b58d2c20ebe617b362dcd600_720w.webp)

![img](https://pic1.zhimg.com/80/v2-de8c1494f6ab269142a0efc65b0569bc_720w.webp)



## 5、面试题五：exactly-once 的保证

问题：如果下级存储不支持事务，Flink 怎么保证 exactly-once？

> **解答：端到端的 exactly-once 对 sink 要求比较高，具体实现主要有幂等写入和**
> **事务性写入两种方式。幂等写入的场景依赖于业务逻辑，更常见的是用事务性写入。**
> **而事务性写入又有预写日志（WAL）和两阶段提交（2PC）两种方式。**
> **如果外部系统不支持事务，那么可以用预写日志的方式，把结果数据先当成状**
> **态保存，然后在收到 checkpoint 完成的通知时，一次性写入 sink 系统。**



## 6、面试题六：状态机制

问题：说一下 Flink 状态机制？

> **解答：Flink 内置的很多算子，包括源 source，数据存储 sink 都是有状态的。在**
> **Flink 中，状态始终与特定算子相关联。Flink 会以 checkpoint 的形式对各个任务的**
> **状态进行快照，用于保证故障恢复时的状态一致性。Flink 通过状态后端来管理状态**
> **和 checkpoint 的存储，状态后端可以有不同的配置选择。**



## 7、面试题七：海量 key 去重

问题：怎么去重？考虑一个实时场景：双十一场景，滑动窗口长度为 1 小时， 滑动距离为 10 秒钟，亿级用户，怎样计算 UV？

> **解答：使用类似于 scala 的 set 数据结构或者 redis 的 set 显然是不行的，**
> **因为可能有上亿个 Key，内存放不下。所以可以考虑使用布隆过滤器（Bloom Filter）**
> **来去重。**



## 8、面试题八：checkpoint 与 spark 比较

问题：Flink 的 checkpoint 机制对比 spark 有什么不同和优势？

> **解答：spark streaming 的 checkpoint 仅仅是针对 driver 的故障恢复做了数据**
> **和元数据的 checkpoint。而 flink 的 checkpoint 机制 要复杂了很多，它采用的是**
> **轻量级的分布式快照，实现了每个算子的快照，及流动中的数据的快照。**



## **9、**面试题九：watermark 机制

问题：请详细解释一下 Flink 的 Watermark 机制？

> **解答：Watermark 本质是 Flink 中衡量 EventTime 进展的一个机制，主要用来处**
> **理乱序数据。**



## **10、**面试题十：exactly-once 如何实现

问题：Flink 中 exactly-once 语义是如何实现的，状态是如何存储的？

> **解答：Flink 依靠 checkpoint 机制来实现 exactly-once 语义，如果要实现端到端**
> **的 exactly-once，还需要外部 source 和 sink 满足一定的条件。状态的存储通过状态**
> **后端来管理，Flink 中可以配置不同的状态后端。**



## **11、**面试题十一：CEP

问题：Flink CEP 编程中当状态没有到达的时候会将数据保存在哪里？

> **解答：在流式处理中，CEP 当然是要支持 EventTime 的，那么相对应的也要**
> **支持数据的迟到现象，也就是 watermark 的处理逻辑。CEP 对未匹配成功的事件序**
> **列的处理，和迟到数据是类似的。在 Flink CEP 的处理逻辑中，状态没有满足的和**
> **迟到的数据，都会存储在一个 Map 数据结构中，也就是说，如果我们限定判断事件**
> **序列的时长为 5 分钟，那么内存中就会存储 5 分钟的数据，这在我看来，也是对内**
> **存的极大损伤之一。**

## **12、**面试题十二：三种时间语义

问题：Flink 三种时间语义是什么，分别说出应用场景？

> **解答：**
> **1. Event Time：这是实际应用最常见的时间语义，具体见文档第七章。**
> **2. Processing Time：没有事件时间的情况下，或者对实时性要求超高的情况下。**
> **3. Ingestion Time：存在多个 Source Operator 的情况下，每个 Source Operator**
> **可以使用自己本地系统时钟指派 Ingestion Time。后续基于时间相关的各种操作，**
> **都会使用数据记录中的 Ingestion Time。**



## 13、面试题十三：数据高峰的处理

问题：Flink 程序在面对数据高峰期时如何处理？

> **解答：使用大容量的 Kafka 把数据先放到消息队列里面作为数据源，再使用**
> **Flink 进行消费，不过这样会影响到一点实时性。**



## **14、**Flink是如何做容错的？

Flink 实现容错主要靠强大的CheckPoint机制和State机制。Checkpoint 负责定时制作分布式快照、对程序中的状态进行备份；State 用来存储计算过程中的中间状态。

## 15、Flink有没有重启策略？说说有哪几种？

Flink 实现了多种重启策略。

- 固定延迟重启策略（Fixed Delay Restart Strategy）
- 故障率重启策略（Failure Rate Restart Strategy）
- 没有重启策略（No Restart Strategy）
- Fallback重启策略（Fallback Restart Strategy）

## 16、说说Flink中的状态存储？

Flink在做计算的过程中经常需要存储中间状态，来避免数据丢失和状态恢复。选择的状态存储策略不同，会影响状态持久化如何和 checkpoint 交互。

Flink提供了三种状态存储方式：MemoryStateBackend、FsStateBackend、RocksDBStateBackend。

## 17、Flink 中的时间有哪几类？

Flink 中的时间和其他流式计算系统的时间一样分为三类：事件时间，摄入时间，处理时间三种。

如果以 EventTime 为基准来定义时间窗口将形成EventTimeWindow,要求消息本身就应该携带EventTime。

如果以 IngesingtTime 为基准来定义时间窗口将形成 IngestingTimeWindow,以 source 的systemTime为准。

如果以 ProcessingTime 基准来定义时间窗口将形成 ProcessingTimeWindow，以 operator 的systemTime 为准。

## 18、Flink 中水印是什么概念，起到什么作用？

Watermark 是 Apache Flink 为了处理 EventTime 窗口计算提出的一种机制, 本质上是一种时间戳。一般来讲Watermark经常和Window一起被用来处理乱序事件。



## 19、Flink 分布式快照的原理是什么？

Flink的分布式快照是根据Chandy-Lamport算法量身定做的。简单来说就是持续创建分布式数据流及其状态的一致快照。

![img](https://pic2.zhimg.com/80/v2-7b2c9036defb754750840bf74abd8d1d_720w.webp)

核心思想是在 input source 端插入 barrier，控制 barrier 的同步来实现 snapshot 的备份和 exactly-once 语义。



## 20、Flink 是如何保证Exactly-once语义的？

Flink通过实现两阶段提交和状态保存来实现端到端的一致性语义。分为以下几个步骤：

- 开始事务（beginTransaction）创建一个临时文件夹，来写把数据写入到这个文件夹里面
- 预提交（preCommit）将内存中缓存的数据写入文件并关闭
- 正式提交（commit）将之前写完的临时文件放入目标目录下。这代表着最终的数据会有一些延迟
- 丢弃（abort）丢弃临时文件

若失败发生在预提交成功后，正式提交前。可以根据状态来提交预提交的数据，也可删除预提交的数据。



## 21、Flink 的 kafka 连接器有什么特别的地方？


Flink源码中有一个独立的connector模块，所有的其他connector都依赖于此模块，Flink 在1.9版本发布的全新kafka连接器，摒弃了之前连接不同版本的kafka集群需要依赖不同版本的connector这种做法，只需要依赖一个connector即可。



## 22、说说 Flink的内存管理是如何做的?

Flink 并不是将大量对象存在堆上，而是将对象都序列化到一个预分配的内存块上。此外，Flink大量的使用了堆外内存。如果需要处理的数据超出了内存限制，则会将部分数据存储到硬盘上。Flink 为了直接操作二进制数据实现了自己的序列化框架。

理论上Flink的内存管理分为三部分：

- Network Buffers：这个是在TaskManager启动的时候分配的，这是一组用于缓存网络数据的内存，每个块是32K，默认分配2048个，可以通过“taskmanager.network.numberOfBuffers”修改
- Memory Manage pool：大量的Memory Segment块，用于运行时的算法（Sort/Join/Shuffle等），这部分启动的时候就会分配。下面这段代码，根据配置文件中的各种参数来计算内存的分配方法。（heap or off-heap，这个放到下节谈），内存的分配支持预分配和lazy load，默认懒加载的方式。
- User Code，这部分是除了Memory Manager之外的内存用于User code和TaskManager本身的数据结构。



## 23、说说 Flink的序列化如何做的?

Java本身自带的序列化和反序列化的功能，但是辅助信息占用空间比较大，在序列化对象时记录了过多的类信息。

Apache Flink摒弃了Java原生的序列化方法，以独特的方式处理数据类型和序列化，包含自己的类型描述符，泛型类型提取和类型序列化框架。

TypeInformation 是所有类型描述符的基类。它揭示了该类型的一些基本属性，并且可以生成序列化器。TypeInformation 支持以下几种类型：

- BasicTypeInfo: 任意Java 基本类型或 String 类型
- BasicArrayTypeInfo: 任意Java基本类型数组或 String 数组
- WritableTypeInfo: 任意 Hadoop Writable 接口的实现类
- TupleTypeInfo: 任意的 Flink Tuple 类型(支持Tuple1 to Tuple25)。Flink tuples 是固定长度固定类型的Java Tuple实现
- CaseClassTypeInfo: 任意的 Scala CaseClass(包括 Scala tuples)
- PojoTypeInfo: 任意的 POJO (Java or Scala)，例如，Java对象的所有成员变量，要么是 public 修饰符定义，要么有 getter/setter 方法
- GenericTypeInfo: 任意无法匹配之前几种类型的类

针对前六种类型数据集，Flink皆可以自动生成对应的TypeSerializer，能非常高效地对数据集进行序列化和反序列化。



## 24、Flink中的Window出现了数据倾斜，你有什么解决办法？

window产生数据倾斜指的是数据在不同的窗口内堆积的数据量相差过多。本质上产生这种情况的原因是数据源头发送的数据量速度不同导致的。出现这种情况一般通过两种方式来解决：

- 在数据进入窗口前做预聚合
- 重新设计窗口聚合的key



## 25、Flink中在使用聚合函数 GroupBy、Distinct、KeyBy 等函数时出现数据热点该如何解决？

数据倾斜和数据热点是所有大数据框架绕不过去的问题。处理这类问题主要从3个方面入手：

（1）在业务上规避这类问题

> 例如一个假设订单场景，北京和上海两个城市订单量增长几十倍，其余城市的数据量不变。这时候我们在进行聚合的时候，北京和上海就会出现数据堆积，我们可以单独数据北京和上海的数据。

（2）Key的设计上

> 把热key进行拆分，比如上个例子中的北京和上海，可以把北京和上海按照地区进行拆分聚合。

（3）参数设置

> Flink 1.9.0 SQL(Blink Planner) 性能优化中一项重要的改进就是升级了微批模型，即 MiniBatch。原理是缓存一定的数据后再触发处理，以减少对State的访问，从而提升吞吐和减少数据的输出量。



## 26、Flink任务延迟高，想解决这个问题，你会如何入手？

在Flink的后台任务管理中，我们可以看到Flink的哪个算子和task出现了反压。最主要的手段是资源调优和算子调优。资源调优即是对作业中的Operator的并发数（parallelism）、CPU（core）、堆内存（heap_memory）等参数进行调优。作业参数调优包括：并行度的设置，State的设置，checkpoint的设置。



## 27、Flink是如何处理反压的？

Flink 内部是基于 producer-consumer 模型来进行消息传递的，Flink的反压设计也是基于这个模型。Flink 使用了高效有界的分布式阻塞队列，就像 Java 通用的阻塞队列（BlockingQueue）一样。下游消费者消费变慢，上游就会受到阻塞。

## 28、Flink的反压和Strom有哪些不同？

Storm 是通过监控 Bolt 中的接收队列负载情况，如果超过高水位值就会将反压信息写到 Zookeeper ，Zookeeper 上的 watch 会通知该拓扑的所有 Worker 都进入反压状态，最后 Spout 停止发送 tuple。

Flink中的反压使用了高效有界的分布式阻塞队列，下游消费变慢会导致发送端阻塞。

二者最大的区别是Flink是逐级反压，而Storm是直接从源头降速。



## 29、Operator Chains（算子链）这个概念你了解吗？

为了更高效地分布式执行，Flink会尽可能地将operator的subtask链接（chain）在一起形成task。每个task在一个线程中执行。将operators链接成task是非常有效的优化：它能减少线程之间的切换，减少消息的序列化/反序列化，减少数据在缓冲区的交换，减少了延迟的同时提高整体的吞吐量。这就是我们所说的算子链。



## 30、Flink什么情况下才会把Operator chain在一起形成算子链？

两个operator chain在一起的的条件：

- 上下游的并行度一致
- 下游节点的入度为1 （也就是说下游节点没有来自其他节点的输入）
- 上下游节点都在同一个 slot group 中（下面会解释 slot group）
- 下游节点的 chain 策略为 ALWAYS（可以与上下游链接，map、flatmap、filter等默认是ALWAYS）
- 上游节点的 chain 策略为 ALWAYS 或 HEAD（只能与下游链接，不能与上游链接，Source默认是HEAD）
- 两个节点间数据分区方式是 forward（参考理解数据流的分区）
- 用户没有禁用 chain



## 31、说说Flink1.9的新特性？

- 支持hive读写，支持UDF
- Flink SQL TopN和GroupBy等优化
- Checkpoint跟savepoint针对实际业务场景做了优化
- Flink state查询



## 32、消费kafka数据的时候，如何处理脏数据？

可以在处理前加一个fliter算子，将不符合规则的数据过滤出去。





编辑于 2020-10-20 14:31



「真诚赞赏，手留余香」

赞赏

还没有人赞赏，快来当第一个赞赏的人吧！

Flink

企业应用

面试问题

赞同 943 条评论

分享

喜欢收藏申请转载



![img](https://pica.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

评论千万条，友善第一条

3 条评论

默认

最新

[![dashi](https://pica.zhimg.com/v2-4805498c66fab3796de5bc6f6a821731_l.jpg?source=06d4cd63)](https://www.zhihu.com/people/fff9c31c5dbaa203b69585cbb9fb009f)

[dashi](https://www.zhihu.com/people/fff9c31c5dbaa203b69585cbb9fb009f)



exactly once 没解释清楚。冲突了

2021-11-18

回复赞

[![梁婷婷](https://picx.zhimg.com/v2-558018d62aaaa0731ea68ed05f131924_l.jpg?source=06d4cd63)](https://www.zhihu.com/people/aafb0944d859af4229a9b9e8a10559ce)

[梁婷婷](https://www.zhihu.com/people/aafb0944d859af4229a9b9e8a10559ce)



第一个 应该是per-job-cluster模式提交任务吧

2021-11-01

回复赞

[![知乎用户lgVl52](https://pic1.zhimg.com/v2-abed1a8c04700ba7d72b45195223e0ff_l.jpg?source=06d4cd63)](https://www.zhihu.com/people/9c57f398285312ebe1dc64e740288480)

[知乎用户lgVl52](https://www.zhihu.com/people/9c57f398285312ebe1dc64e740288480)



有些东西解释的不清楚，建议选择性查看。

2022-04-06

回复赞

### 文章被以下专栏收录

- ![牛牛玩数据](https://picx.zhimg.com/v2-3a342461f680a98a80d6335d4d089bfd_l.jpg?source=172ae18b)

- ## [牛牛玩数据](https://www.zhihu.com/column/c_1193967525065629696)

- 好的想法，好的文章、乐于分享、共同进步

- ![Flink生态圈技术](https://pica.zhimg.com/4b70deef7_l.jpg?source=172ae18b)

- ## [Flink生态圈技术](https://www.zhihu.com/column/c_1242144073907503104)

### 推荐阅读

- # flink面试题

- 6. Flink 的 kafka 连接器有什么特别的地方？Flink源码中有一个独立的connectors模块，所有的其他connector都依赖于此模块，Flink 在1.9版本发布的全新kafka连接器，摒弃了之前连接不同版本…

- 大数据的探索者

- # Flink面试常见问题（实战）

- 一、如何排查生产环境中的反压问题？1.1 概述1、反压产生的场景 反压经常出现在促销、热门活动等场景。短时间内流量陡增造成 数据的堆积或者消费速度变慢。它们有一个共同的特点： 数据的消…

- 像风一样自...发表于flink...

- ![100%会被问到的两道Flink面试题，你会了么?](https://picx.zhimg.com/v2-78ec087da2533b9c9189eb0743e4a001_250x0.jpg?source=172ae18b)

- # 100%会被问到的两道Flink面试题，你会了么?

- 码农铲屎官

- # Flink 面试通关手册

- 概述 2019 年是大数据实时计算领域最不平凡的一年，2019 年 1 月阿里巴巴 Blink （内部的 Flink 分支版本）开源，大数据领域一夜间从 Spark 独步天下走向了两强争霸的时代。Flink 因为其天…

- 王知无发表于大数据成神...
