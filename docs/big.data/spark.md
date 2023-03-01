## Referer

[spark官方文档-英文](https://spark.apache.org/documentation.html)

[spark官方文档-中文](http://spark.apachecn.org/)

[![img](https://zhuanlan.zhihu.com/p/440179932)
](javascript:void(0))



首发于[大数据&人工智能的两三事](https://www.zhihu.com/column/c_1436089813129080833)



写文章

![点击打开edc的主页](https://picx.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

![最全Spark保姆级面试教程](https://picx.zhimg.com/v2-eab126f3a3f36df5229e051e55252680_720w.jpg?source=172ae18b)

# 最全Spark保姆级面试教程

[![大数据兵工厂](https://picx.zhimg.com/v2-f5a0ad00fc922cdca5b798f6e9f27a3e_l.jpg?source=172ae18b)](https://www.zhihu.com/people/sw-chen-78)

[大数据兵工厂](https://www.zhihu.com/people/sw-chen-78)

任职于国内一线互联网大厂，专攻实时计算、数仓、机器学习领域

关注他

12 人赞同了该文章

本文是历时一周整理的Spark保姆级教程。基于面试角度出发，涉及内容有Spark的相关**概念、架构原理、部署、调优及实战问题**。文中干货较多，希望大家耐心看完。



**一 介绍一下Spark**

Apache Spark是一个分布式、内存级计算框架。起初为加州大学伯克利分校AMPLab的实验性项目，后经过开源，在2014年成为Apache基金会顶级项目之一，现已更新至3.2.0版本。

![img](https://pic4.zhimg.com/80/v2-b9b87c4a413da48f3282fd73181a72c7_720w.webp)



**二 谈一谈Spark的生态体系**

Spark体系包含Spark Core、Spark SQL、Spark Streaming、Spark MLlib及 Spark Graphx。其中Spark Core为核心组件，提供RDD计算模型。在其基础上的众组件分别提供**查询分析、实时计算、机器学、图计算**等功能。

![img](https://pic1.zhimg.com/80/v2-6ef553f2e51c8d41f43560c51f4807e4_720w.webp)



**三 说说Spark的工作流程**

主要考察对Spark运行机制的理解，需要掌握Spark任务提交、资源申请、任务分配等阶段中各组件的协作机制，这里放上Spark官网的工作流程示意图。

> **Tips: 可结合4、5点运行模式原理展开细说**

- 客户端提交任务，创建Driver进程并初始化SparkContext
- SparkContext向Cluster Manager申请资源
- Cluster Manager选择合适的worker节点创建executor进程
- Executor向Driver端注册，并等待其分配task任务
- SparkContext构建DAG图(有向无环图)、划分stage并分配taskset至Executor
- Executor启动Task线程执行具体任务

![img](https://pic1.zhimg.com/80/v2-30921cd330d6efef8d5998bd02620824_720w.webp)

**四 Spark运行模式有哪些？说说你最熟悉的一种**

Spark的运行模式包括**Local、Standalone、Yarn及Mesos**几种。其中Local模式仅用于本地开发，Mesos模式国内几乎不用。在公司中因为大数据服务基本搭载Yarn集群调度，因此Spark On Yarn模式会用的比较多。

![img](https://pic4.zhimg.com/80/v2-4f88af905e8a37e7dfbcb8fa1baa7cd7_720w.webp)

(Spark运行模式流程总体示意图)

Standalone模式是Spark内置的运行模式，常用于小型测试集群。这里我就拿Standalone模式来举例:

- Master为资源调度器，负责executors资源调度
- Worker负责Executor进程的启动和监控
- Driver在客户端启动，负责SparkContext初始化

![img](https://pic2.zhimg.com/80/v2-e8604cb640ad78c3ce61e11b5e08ba35_720w.webp)



**五 谈谈Yarn Cluster和Yarn Client模式的区别**

这是Spark中最普遍的一道面试题，主要是考察对Spark On Yarn 原理掌握的扎实程度。

Yarn Cluster模式的driver进程托管给**Yarn(AppMaster)**管理，通过**yarn UI**或者**Yarn logs**命令查看日志。Yarn Client模式的driver进程运行在**本地客户端**，因资源调度、任务分发会和Yarn集群产生大量网络通信，出现**网络激增**现象，适合本地调试，不建议生产上使用。两者具体执行流程整理如下:

- Yarn Cluster模式

![img](https://pic4.zhimg.com/80/v2-b9239e2e387f9ba7d3b4d2c30b14f307_720w.webp)

- Yarn Client模式

![img](https://pic2.zhimg.com/80/v2-36da1a71b1d1c36cde73465504c2f2a5_720w.webp)



**六 简单讲下RDD的特性**

RDD(分布式弹性数据集)是Spark的基础数据单元，和Mysql数据库中的视图view概念类似，其本身不存储数据，仅作为数据访问的一种虚拟结构。Spark通过对RDD的相互转换操作完成整个计算过程。

![img](https://pic3.zhimg.com/80/v2-cc2c648f688174c7d05200d63684b05e_720w.webp)

- 分布式：RDD本质上可以看成是一组**只读的**、**可分区的**分布式数据集，支持跨节点多台机器上进行并行计算。
- 弹性：数据**优先内存存储**，当计算节点内存不够时，可以把数据刷到磁盘等外部存储，且支持手动设定存储级别。
- 容错性：RDD的**血脉机制**保存RDD的依赖关系，同时支持**Checkpoint容错机制**，当RDD结构更新或数据丢失时可对RDD进行重建。

RDD的创建支持从集合List中parallelize()、外部Text/JSON/JDBC等数据源读取、RDD的Transformation转换等方式，以Scala代码为例：

![img](https://pic4.zhimg.com/80/v2-0c213c8a0e62b9ef13b21bd9f834902b_720w.webp)



**七 RDD的宽依赖和窄依赖了解吗**

这又是一道经典的面试题，切记**不要忽视细节**！Spark中的RDD血脉机制，当RDD数据丢失时，可以根据记录的血脉依赖关系重新计算。而DAG调度中对计算过程**划分stage**，划分的依据也是RDD的依赖关系。

针对不同的函数转换，RDD之间的依赖关系分为宽依赖和窄依赖。宽依赖会产生**shuffle**行为，经历map输出、中间文件落地和reduce聚合等过程。

![img](https://pic4.zhimg.com/80/v2-1bda097caea5eb7e1b34614e9a5280eb_720w.webp)

首先，我们看一下Spark官网中对于宽依赖和窄依赖的定义:

- 宽依赖: 父RDD每个分区被多个子RDD分区使用
- 窄依赖: 父RDD每个分区被子RDD的一个分区使用

这里需要注意的是，网上有些论调是**不正确的**，只各自考虑了一种情况：

- 窄依赖就是一个父分区对应一个子分区**(错误)**
- 宽依赖就是一个父分区对应所有子分区**(错误)**

下面我们结合示意图，分别列出宽依赖和窄依赖存在的四种情况：

- 窄依赖(一个父RDD对应一个子RDD：map/filter、union算子)

![img](https://pic1.zhimg.com/80/v2-27644906fee989e9bdc906b7a89068a4_720w.webp)

- 窄依赖(多个父RDD对应一个子RDD：co-partioned join算子)

![img](https://pic1.zhimg.com/80/v2-1c760bc86a96e3d97bd834baa8927eac_720w.webp)

- 宽依赖(一个父RDD对应多个非全部子RDD: groupByKey算子等)

![img](https://pic4.zhimg.com/80/v2-8d549d61af534464e36b7758d1a2ce43_720w.webp)

- 宽依赖(一个父RDD对应全部子RDD: not co-partioned join算子)

![img](https://pic4.zhimg.com/80/v2-d515261f4cf84bcb2c04df3b2e5e9953_720w.webp)



**八 你用过的Transformation和Action算子有哪些**

Spark中的Transformation操作会生成一个新的RDD，且具有Lazy特性，不触发任务的实际执行。常见的算子有**map**、**filter**、**flatMap**、**groupByKey**、**join**等。一般聚合类算子多数会导致shuffle。

```text
map: 遍历RDD中元素，转换成新元素, 然后用新元素组成一个新的RDD
filter: 遍历RDD中元素进行判断，结果为真则保留，否则删除
flatMap: 与map类似，不过每个元素可返回多个元素
groupByKey: 聚合类算子，根据元素key分组(会产生shuffle)
join: 对包含<key, value>键值对的多个RDD join操作
```

- 官网Transformation算子概览

![img](https://pic3.zhimg.com/80/v2-d8c1b18be666323b42997a7033ad7a2a_720w.webp)

Action操作是对RDD结果进行聚合或输出，此过程会触发Spark Job任务执行，从而执行之前所有的Transformation操作，结果可返回至Driver端。常见的算子有**foreach**、**reduce**、**count**、**saveAsTextFile**等。

```text
foreach: 遍历RDD中元素
reduce: 将RDD中的所有元素依次聚合
count: 遍历RDD元素，进行累加计数
saveAsTextFile: 将RDD结果保存到目标源TextFile中官网Action算子概览
```

- 官网Action算子概览

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='570' height='534'></svg>)



**九 说说job、stage和task的关系**

Job、stage和task是spark任务执行流程中的三个基本单位。其中job是最大的单位，也是Spark Application任务执行的基本单元，由**action算子**划分触发生成。

stage隶属于单个job，根据shuffle算子(**宽依赖**)拆分。单个stage内部可根据数据分区数划分成多个task，由TaskScheduler分发到各个Executor上的task线程中执行。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='875' height='518'></svg>)



**十 Spark为什么这么快**

Spark是一个基于内存的，用于大规模数据处理的统一分析引擎，其运算速度可以达到Mapreduce的**10-100**倍。具有如下特点：

- 内存计算。Spark优先将数据加载到**内存**中，数据可以被快速处理，并可启用**缓存**。
- shuffle过程优化。和Mapreduce的shuffle过程中间文件频繁落盘不同，Spark对Shuffle机制进行了优化，**降低**中间文件的数量并保证内存优先。
- RDD计算模型。Spark具有高效的**DAG调度**算法，同时将RDD计算结果存储在内存中，避免重复计算。



**十一 如何理解DAGScheduler的Stage划分算法**

首先放上官网的RDD执行流程图:

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='620' height='330'></svg>)

针对一段应用代码(如上)，Driver会以Action算子为边界生成DAG调度图。DAGScheduler从DAG末端开始遍历划分Stage，封装成一系列的tasksets移交TaskScheduler，后者根据调度算法, 将taskset分发到相应worker上的Executor中执行。

**1. DAGSchduler的工作原理**

- DAGScheduler是一个面向stage调度机制的高级调度器，为每个job计算stage的**DAG**(有向无环图)，**划分stage**并提交taskset给TaskScheduler
- 追踪每个RDD和stage的物化情况，处理因**shuffle过程丢失**的RDD，重新计算和提交。
- 查找rdd partition 是否cache/checkpoint，提供**优先位置**给TaskScheduler。
- 等待后续TaskScheduler的最佳位置划分

**2. Stage划分算法**

- 从触发action操作的算子开始，从后往前遍历DAG
- 为最后一个rdd创建finalStage
- 遍历过程中如果发现该rdd是宽依赖，则为其生成一个新的stage并与旧stage分隔而开，此时该rdd是新stage的最后一个rdd
- 如果该rdd是窄依赖，将该rdd划分为旧stage内，继续遍历
- 以此类推，继续遍历直至DAG完成

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='833' height='428'></svg>)



**十二 如何理解TaskScheduler的Task分配算法**

TaskScheduler负责Spark中的task任务调度工作。TaskScheduler内部使用TasksetPool调度池机制存放task任务。TasksetPool分为FIFO(先进先出调度)和FAIR(公平调度)。

- FIFO调度: 基于队列思想，使用先进先出原则顺序调度taskset
- FAIR调度: 根据权重值调度，一般选取资源占用率作为标准，可人为设定

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='601' height='355'></svg>)

**1. TaskScheduler的工作原理**

- 负责Application在Cluster Manager上的注册
- 根据不同策略创建TasksetPool资源调度池，初始化pool大小
- 根据task分配算法发送Task到Executor上执行

**2. Task分配算法**

- 首先获取所有的executors，包含executors的ip和port等信息
- 将所有的executors根据shuffle算法进行打散
- 遍历executors。依次尝试本地化级别，最终选择每个task最优位置(结合DAGScheduler优化位置策略)
- 序列化task分配结果，并发送RPC消息等待Executor响应

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='822' height='268'></svg>)



**十三 Spark的本地化级别有哪几种？怎么调优**

移动计算 or 移动数据？这是一个问题。在分布式计算的核心思想中，移动计算永远比移动数据要合算得多，如何合理利用本地化数据计算是值得思考的一个问题。

TaskScheduler在进行task任务分配时，需要根据本地化级别计算最优位置，一般是遵循**就近原则**，选择最近位置和缓存。Spark中的本地化级别在TaskManager中定义，分为五个级别。

**1. Spark本地化级别**

- PROCESS_LOCAL(进程本地化)
  partition和task在同一个executor中，task分配到本地Executor进程。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='403' height='175'></svg>)

- NODE_LOCAL(节点本地化)
  partition和task在同一个节点的不同Executor进程中，可能发生跨进程数据传输

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='569' height='160'></svg>)

- NO_PREF(无位置)
  没有最佳位置的要求，比如Spark读取JDBC的数据
- RACK_LOCAL(机架本地化)
  partition和task在同一个机架的不同worker节点上，可能需要跨机器数据传输

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='223' height='286'></svg>)

- ANY(跨机架): 数据在不同机架上，速度最慢

**2. Spark本地化调优**

在task最佳位置的选择上，DAGScheduler先判断RDD是否有**cache/checkpoint**，即缓存优先；否则TaskScheduler进行本地级别选择等待发送task。

TaskScheduler首先会根据最高本地化级别发送task，如果在**尝试5次**并**等待3s**内还是无法执行，则认为当前资源不足，即**降低本地化**级别，按照PROCESS->NODE->RACK等顺序。

- 调优1：加大spark.locality.wait 全局等待时长
- 调优2：加大spark.locality.wait.xx等待时长(进程、节点、机架)
- 调优3：加大重试次数(根据实际情况微调)

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='719' height='356'></svg>)



**十四 说说Spark和Mapreduce中Shuffle的区别**

Spark中的shuffle很多过程与MapReduce的shuffle类似，都有Map输出端、Reduce端，shuffle过程通过将Map端计算结果分区、排序并发送到Reducer端。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='723' height='208'></svg>)

下面将对Spark和Mapreduce中shuffle过程分开叙述，Mapreduce的shuffle大家都不陌生了，主要重点突出Spark的Shuffle机制做了哪些优化工作。

**1. Hadoop Mapreduce Shuffle**

MapReduce的shuffle需要依赖大量磁盘操作，数据会频繁落盘产生**大量IO**，同时产生大量小文件冗余。虽然缓存buffer区中启用了缓存机制，但是阈值较低且内存空间小。

- 读取输入数据，并根据split大小切分为map任务
- map任务在分布式节点中执行map()计算
- 每个map task维护一个环形的buffer缓存区，存储map输出结果，分区且排序
- 当buffer区域达到阈值时，开始溢写到临时文件中。map task任务结束时进行临时文件合并。此时，整合shuffle map端执行完成
- mapreduce根据partition数启动reduce任务，copy拉取数据
- merge合并拉取的文件
- reduce()函数聚合计算，整个过程完成

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='873' height='370'></svg>)

**2. Spark的Shuffle机制**

Spark1.2以前，默认的shuffle计算引擎是HashShuffleManager，此种Shuffle产生大量的中间磁盘文件，消耗**磁盘IO**性能。在Spark1.2后续版本中，默认的ShuffleManager改成了SortShuffleManager，通过**索引机制**和**合并临时文件**的优化操作，大幅提高shuffle性能。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='796' height='469'></svg>)

(Spark shuffle过程示意)

- HashShuffleManager
  HashShuffleManager的运行机制主要分成两种，一种是普通运行机制，另一种是合并的运行机制。合并机制主要是通过**复用buffer**来优化Shuffle过程中产生的小文件的数量，Hash shuffle本身**不排序**。开启合并机制后，同一个Executor共用一组core，文件个数为**cores** * **reduces**。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='886' height='481'></svg>)

- SortShuffleManager
  SortShuffleManager的运行机制分成两种，普通运行机制和bypass运行机制。当shuffletask的数量小于等于spark.shuffle.sort.bypassMergeThreshold参数的值时(默认200)，会启用**bypass机制**。

  SortShuffleManager机制采用了一个特殊的**内存[数据结构](https://www.zhihu.com/search?q=数据结构&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A440162118})**(Map)，数据优先写入此结构中，当达到阈值时溢写到磁盘中并清空内存数据结构。在过程中对数据进行**排序**并**合并**，减少最终的临时文件数量。ByPass机制下在其基础上加了一个**索引机制**，将数据存放位置记录**hash索引值**，相同hash的数据合并到同一个文件中。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='669' height='409'></svg>)


**十五 Spark的内存是怎么管理的**

Spark内存分为堆内内存和堆外内存，其中堆内内存基于JVM实现，堆外内存则是通过调用JDK Unsafe API管理。在Spark1.6版本前后内存管理模式分为: 静态管理(Static Memory)和统一管理(Unified Memory)。

两种内存管理方式存在很大的差别，内存计算占比也不同，具体细节可查看我的上一篇文章:



**十六 Spark的广播变量和累加器的作用是什么**

Executor接收到TaskScheduler的taskset分发命令，根据rdd分区数在ThreadPool中创建对应的Task线程，每个Task线程拉取并序列化代码，启动分布式计算。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='449' height='248'></svg>)

Spark在计算过程中的算子函数、变量都会由Driver分发到每台机器中，每个Task持有该变量的一个副本拷贝。可是这样会存在两个问题:

> 是否可以只在Executor中存放一次变量，**所有Task共享**
> 分布式计算场景下怎么可以做到**全局计数**

**1. 广播变量(Broadcast)**

在Driver端使用broadcast()将一些大变量(List、Array)持久化，Executor根据broadcastid拉取本地缓存中的Broadcast对象，如果不存在，则尝试远程拉取Driver端持久化的那份Broadcast变量。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='455' height='247'></svg>)

这样所有的Executor均存储了一份变量的**备份**，这个executor启动的task会**共享**这个变量，节省了通信的成本和服务器的资源。注意不能广播RDD，因为RDD不存储数据；同时广播变量只能在**Driver端**定义和修改，**Executor端**只能读取。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='719' height='238'></svg>)

(Broadcast的代码使用)

**2. 累加器(Accumulator)**

Spark累加器支持在Driver端进行全局汇总的计算需求，实现**分布式计数**的功能。累加器在Driver端定义赋初始值，在Excutor端更新，最终在**Driver端**读取最后的汇总值。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='500' height='343'></svg>)

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='604' height='221'></svg>)

(Accumulator的代码使用)



**十七 Spark SQL和Hive SQL的区别**

Hive SQL是Hive提供的SQL查询引擎，底层由**MapReduc**e实现。Hive根据输入的SQL语句执行词法分析、语法树构建、编译、逻辑计划、优化逻辑计划以及物理计划等过程，转化为Map Task和Reduce Task最终交由Mapreduce引擎执行。

- 执行引擎。具有mapreduce的一切特性，适合大批量数据离线处理，相较于Spark而言，速度较慢且IO操作频繁
- 有完整的**hql**语法，支持基本sql语法、函数和**udf**
- 对表数据存储格式有要求，不同存储、压缩格式性能不同

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='696' height='278'></svg>)



Spark SQL底层基于Spark引擎，使用Antlr解析语法，编译生成逻辑计划和物理计划，过程和Hive SQL执行过程类似，只不过Spark SQL产生的物理计划为Spark程序。

- 执行引擎。背靠Spark计算模型，基于内存计算快速高效。
- 可支持SQL和DataFrame等形式，底层转化为Spark算子参与计算。
- 集成了HiveContext接口，基本实现Hive功能



**十八 说下Spark SQL的执行流程**

可以参考Hive SQL的执行流程展开叙述，大致过程一致，具体执行流程如下:

- 输入编写的Spark SQL
- **SqlParser**分析器。进行语法检查、词义分析，生成未绑定的Logical Plan逻辑计划(未绑定查询数据的元数据信息，比如查询什么文件，查询那些列等)
- **Analyzer**解析器。查询元数据信息并绑定，生成完整的逻辑计划。此时可以知道具体的数据位置和对象，Logical Plan 形如from table -> filter column -> select 形式的树结构
- **Optimizer**优化器。选择最好的一个Logical Plan，并优化其中的不合理的地方。常见的例如谓词下推、剪枝、合并等优化操作
- **Planner**使用Planing Strategies将逻辑计划转化为物理计划，并根据最佳策略选择出的物理计划作为最终的执行计划
- 调用Spark Plan Execution执行引擎执行Spark RDD任务

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='699' height='189'></svg>)



**十九 RDD、DataFrame和DataSet的区别**

**1. RDD和DataFrame、Dataset的共性**

三者均为Spark分布式弹性数据集，Spark 2.x 的DataFrame被Dataset合并，现在只有DataSet和RDD。三者有许多相同的算子如filter、map等，且均具有惰性执行机制。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='682' height='270'></svg>)

**2. DataFrame和DataSet的区别**

DataFrame是分布式Row对象的集合，所有record类型均为Row。Dataset可以认为是DataFrame的特例，每个record存储的是强类型值而不是Row，同理Dataframe可以看作Dataset[Row]。



**3. RDD、DataFrame和Dataset转换**

- DataFrame/DataSet转换为RDD

```text
val rdd1 = myDF.rdd
```

- RDD转换为DataFrame/Dataset (spark低版)

```text
import spark.implicits._
val myDF = rdd.map {line=> (line._1,line._2)}
.toDF("col1","col2")
```

- RDD转换为Dataset

```text
import spark.implicits._

case class ColSet(col1:String,col2:Int) extends Serializable 
val myDS = rdd.map {row=>
  ColSet(row._1,row._2)
}.toDS
```

**4. Spark SQL中的RDD和Dataset**

RDD无法支持Spark sql操作，而dataframe和dataset均支持。



**二十 groupbyKey和reduceBykey的区别**

在介绍groupByKey和reduceByKey的区别之前，首先介绍一下什么是聚合算子：

> 根据Key进行分组聚合，解决**<K, V>类型**的数据计算问题

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='417' height='295'></svg>)

在Spark中存在很多聚合算子，常用于处理分类统计等计算场景。

- 分组：groupByKey算子
- 聚合：reduceByKey算子
- 本地聚合：CombineByKey算子

**1. CombineByKey算子**

聚合算子内部调用的基础算子之一，程序调用CombineByKey算子时会在本地预先进行规约计算，类似于Mapreduce Shuffle中Map阶段的Combine阶段，先看一下执行原理:

- 为各分区内所有Key创建累加器对象并赋值
- 每次计算时分区内相同Key的累加器值加一
- 合并各分区内相同Key的值

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='674' height='346'></svg>)

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='730' height='260'></svg>)

（CombineByKey 代码示意）

**2. ReduceByKey算子**

内部调用CombineByKey算子实现。即先在**本地预聚合**，随后在分布式**节点聚合**，最终返回(K, V) 数据类型的计算结果。通过第一步本地聚合，大幅度减少跨节点shuffle计算的数据量，提高聚合计算的效率。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='825' height='372'></svg>)

**3. GroupByKey算子**

GroupByKey内部禁用CombineByKey算子，将分区内相同Key元素进行组合，不参与聚合计算。此过程会和ReduceByKey一致均会产生Shuffle过程，但是ReduceByKey存在本地预聚合，效率高于GroupByKey。

- 在聚合计算场景下，计算效率低于ReduceBykey
- 可以搭配mapValues算子实现ReduceByKey的聚合计算

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='555' height='326'></svg>)

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='537' height='167'></svg>)

（ReduceByKey和groupByKey 代码示意）



**二十一 coalesce和repartition的区别**

两个算子都可以解决Spark的小文件过多和分区数据倾斜问题。举个例子，在使用Spark进行数据处理的过程中，常常会调用filter方法进行数据预处理，频繁的过滤操作会导致分区数据产生大量小文件碎片，当shuffle过程读取分区文件时极容易产生数据倾斜现象。

Spark通过repartition和coalesce算子来控制分区数量，通过合并小分区的方式保持数据紧凑型，提高分区的利用率。

**1. 内部实现机制**

首先打开repartition的源码，可以看到方法仅存在一个参数: **numPartitions**(分区数)，这里表示需要合并的分区数量。再细看内部调用的是coalesce(**shuffle=true**)函数，即核心逻辑还是由**coalesce()**实现，且过程会产生**shuffle**操作。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='763' height='104'></svg>)

再次定位到coalesce()方法内部，可以看到根据shuffle的条件判断，先通过生成随机数将partition重新组合，随后生成CoalesceRDD进行后续的逻辑处理。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='968' height='476'></svg>)

**2. 分区重分配原则**

- 当分区数大于原分区时，类型为宽依赖(shuffle过程)，需要把coalesce的shuffle参数设为true，执行HashPartition重新扩大分区，这时调用repartition()
- 当分区数两者相差不大时，类型为窄依赖，可以进行分区合并，这时调用coalesce()
- 当分区数远远小于原分区时，需要综合考虑不同场景的使用

**二十二 说说cache和persist的异同**

1. cache()方法内部调用了persist()
2. persist()方法存在多种缓存级别，默认为Momory
3. cache()只有一个默认的缓存级别MEMORY_ONLY
4. persist()可以根据情况设置其它的缓存级别

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='779' height='429'></svg>)



**二十三 连续登陆问题SQL**

这是一个经典的SQL面试题，例如计算平台连续登陆3天以上的用户统计，诸如此类网上存在很多种答案，这里放上其中一种解题思路的SQL实现和DataFrame实现版本。

**1. 实现思路**

- 将用户分组并按照时间排序，并记录rank排名
- 计算dt-rank的差值，差值与用户共同分组
- 统计count并找出 count > 3的用户

**2. Spark DataFrame实现**

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='651' height='210'></svg>)

**3. Spark SQL实现**

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='594' height='402'></svg>)



**二十四 SparkStreaming怎么保证精准一次消费**

实时场景下的Spark Streaming流处理，通过Receiver组件实时接收数据，最终将连续的Dstream数据流转换为微批RDD在Spark引擎中执行。Spark Streaming实时场景中最通用数据源是Kafka，一个高性能、分布式的实时消息队列。Spark Streaming最大化实时消费Kafka分区数据，提供秒级响应计算服务。

![img](data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='756' height='332'></svg>)

Spark Streaming保证**精确一次消费**，需要整个实时系统的各环节均保持强一致性。即可靠的**Kafka端**(数据**可重复**读取、不丢失)、可靠的**消费端**(Spark内部精确一次消费)、可靠的**输出端**(**幂等**性、事务)。

具体细节可查看我的文章：



发布于 2021-12-02 00:03



Spark

大数据

面试

赞同 12添加评论

分享

喜欢收藏申请转载



![img](https://picx.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

评论千万条，友善第一条



还没有评论，发表第一个评论吧

### 文章被以下专栏收录

- ![大数据&人工智能的两三事](https://picx.zhimg.com/4b70deef7_l.jpg?source=172ae18b)

- ## [大数据&人工智能的两三事](https://www.zhihu.com/column/c_1436089813129080833)

- 个人学习、工作、技能日常分享，希望能够帮助大家

### 推荐阅读

- # Spark面试题（一）

- Spark系列面试题Spark面试题（一）Spark面试题（二）Spark面试题（三）Spark面试题（四）Spark面试题（五）——数据倾斜调优Spark面试题（六）——Spark资源调优Spark面试题（七）——Spark…

- 大数据技术...发表于大数据技术...

- ![Spark面试题(一)](https://pica.zhimg.com/v2-e4dc8d1cb4febc831a6faa5cccc07764_250x0.jpg?source=172ae18b)

- # Spark面试题(一)

- runzh...发表于大数据日记

- # Spark面试题整理（三）

- Spark系列面试题Spark面试题（一）Spark面试题（二）Spark面试题（三）Spark面试题（四）Spark面试题（五）——数据倾斜调优Spark面试题（六）——Spark资源调优Spark面试题（七）——Spark…

- 大数据技术...发表于大数据技术...

- # Spark面试题（四）

- Spark系列面试题Spark面试题（一）Spark面试题（二）Spark面试题（三）Spark面试题（四）Spark面试题（五）——数据倾斜调优Spark面试题（六）——Spark资源调优Spark面试题（七）——Spark…

- 大数据技术...发表于大数据技术...