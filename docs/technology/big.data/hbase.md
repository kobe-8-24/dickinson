## Referer

[Hbase官方文档-英文](https://hbase.apache.org/)

[Hbase官方文档-中文](http://hbase.org.cn/)

[![img](https://zhuanlan.zhihu.com/p/323658473)
](javascript:void(0))





写文章

![点击打开edc的主页](https://picx.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

![【技术干货】经典HBase面试真题分享](https://pica.zhimg.com/v2-d74cafb771c2d0af914472f937a3c050_720w.jpg?source=172ae18b)

# 【技术干货】经典HBase面试真题分享

[![海牛大数据](https://picx.zhimg.com/v2-c7f8bd30893e39b9fd4af2c779f608da_l.jpg?source=172ae18b)](https://www.zhihu.com/org/hai-niu-da-shu-ju)

[海牛大数据](https://www.zhihu.com/org/hai-niu-da-shu-ju)

关注



![img](https://pic3.zhimg.com/80/v2-02913c73c5ed127dffc9e58b33be8e0a_720w.webp)



Apache HBase是Hadoop生态系统中的开源、非关系、分布式数据库。HBase面试问题一直以来都是Hadoop面试的重要部分。今天我们将介绍一些基本的和高级的HBase问题。

由于Hadoop开发需要处理许多与数据库相关的活动，以进行数据库采购和编写，因此了解至少一种数据库技能对于Hadoop开发人员至关重要。

此外，企业更喜欢选择非关系数据库，考虑到其许多功能，HBase是Hadoop的理想选择。因此，在任何Hadoop工作面试中都要面对HBase面试问题，这是每个Hadoop专业人士都必须面对的。

## **1.你对Apache HBase了解多少？**

**答：**Apache HBase是基于开源Hadoop的非关系，分布式数据库管理系统。它是承载大型表的理想选择，大型表由一组集群商品硬件之上的大量行和列组成。此外，它是版本化的实时数据库，可为用户提供读/写访问权限。HBase具有以下功能：

- 模块化和线性可伸缩性。
- 保持严格一致的读/写。
- 表的可配置和自动分片。
- 自动支持区域服务器之间的故障转移。
- 基类支持使用Apache HBase表备份Hadoop MapReduce作业。
- 用户可访问的Java API，用于客户端访问。
- 用于实时查询的布隆过滤器和块缓存。
- 用于查询谓词下推的服务器端过滤器。
- 具有REST-ful Web服务的Thrift网关，支持XML，二进制数据编码和Protobuf。
- 基于JRuby的可扩展外壳。
- 它支持使用Hadoop指标子系统将指标导出到Ganglia或文件或通过JMX。

## **2.Apache HBase中使用了哪些不同的操作命令？**

**答：**放置，获取，删除，最后，扫描和递增。

**3.选择HBase作为Hadoop的DBMS的最佳理由是什么？**

**答：**Apache HBase的某些属性使其成为Hadoop的DBMS的最佳选择。这里是其中的一些：

- 可扩展性
- 非常适合处理可容纳数十亿行和列的大型表
- 用户可以实时在数据库上进行读写。
- 与Hadoop兼容，因为它们都是基于Java的。
- 对CRUD操作的广泛操作支持。

## **4.HBase有哪些不同的关键组件？**

**答：**HBase的关键组件是：

- 区域-这些是HBase表的水平划分的行。HBase的此组件包含Hfile和内存数据存储。
- 区域服务器-此组件监视区域。
- HBase Master或HMaster-此组件负责区域分配，还监视区域服务器。
- Zookeeper-它充当客户端和HBase Master组件之间的分布式协调服务，还维护集群中的服务器状态。它监视哪些服务器可用并处于活动状态。除此之外，它还通知服务器何时无法执行。



![img](https://pic4.zhimg.com/80/v2-3d6b53197e7dc13570fd6500848c0837_720w.webp)



## **5.什么是行键？**

**答：**RowKey是HBase中的唯一标识符，用于在逻辑上对表单元进行分组，以确保所有具有相似RowKey的单元都放在同一服务器上。但是，内部RowKey是字节数组。

## **6.RDBMS和HBase表之间有什么区别？**

**答：**

- RDBMS是基于架构的数据库，而HBase是不遵循任何架构的数据模型。
- RDBMS支持内置表分区，但是HBase支持自动分区。
- RDBMS以规范化格式存储数据，而HBase存储非规范化数据。

## **7.WAL是什么意思？**

**答：**WAL代表预写日志。该HBase日志记录表数据中的所有更改，而不管更改方式如何。该日志文件本身是标准序列文件。该文件的主要实用程序是即使在服务器崩溃后也向用户提供数据访问。

## **8.HBase中有哪些不同的目录表？**

**答：**HBase中有两个主要目录表，分别是ROOT和META。ROOT表的目的是跟踪META表，并且META表用于在HBase系统中存储区域。

**9.HBase中的墓碑标记是什么？HBase中有多少个墓碑标记？**

##  

**答：**当用户删除HBase表中的单元格时，尽管该单元格在表中不可见，但仍以标记形式（通常称为逻辑删除标记）保留在服务器中。在压缩期间，逻辑删除标记将从服务器中删除。

有三个墓碑标记：

- 版本删除
- 家庭删除
- 列删除

## **10.提到将HBase视为数据库时的几种情况？**

**答：**这是一些方案：

- 当我们需要转移整个数据库时
- 处理大数据操作
- 当我们需要频繁的内部联接时
- 需要频繁进行事务维护时。
- 当我们需要处理可变模式时。
- 当应用程序需要基于密钥的数据检索时
- 当数据为集合形式时

## **11.HBase和Hive有什么区别？**

**答：**Apache HBase和Apache Hive具有非常不同的基于Hadoop的基础架构。

1. Apache Hive是建立在Hadoop之上的数据仓库，而Apache HBase是基于NoSQL密钥/值并在Hadoop HDFS之上运行的数据存储。
2. Apache Hive允许通过HQL查询存储在HDFS上的数据以进行分析，然后转换为MapReduce作业。相反，Apache HBase操作在其数据库上实时运行，而不是作为MapReduce作业运行。
3. Apache Hive通过其分区功能可以处理有限的数据，而HBase通过其键/值对功能支持处理大量数据。
4. Apache Hive没有版本控制功能，而Apache HBase提供了版本控制数据。

## **12.在HBase中重要的过滤器有哪些？**

**答：**列过滤器，页面过滤器，行过滤器，族过滤器，包含停止过滤器。

**13.什么是色谱柱族？HBase的目的是什么？**

##  

**答：**列族是HBase表中的键，代表数据的逻辑偏差。它会影响HDFS中的数据存储格式。因此，每个HBase表必须至少具有一个列族。

## **14.什么是MemStore？**

**答：**MemStore是HBase中使用的写缓冲区，用于在永久写入之前在内存中累积数据。当MemStore填满数据时，其内容将刷新到磁盘并形成一个新的Hfile。每个列族存在一个MemStore。

## **15.什么是HFile？**

**答：**HFile是HBase底层存储格式。每个HFile都属于一个列族，而一个列族可能具有多个HFile。但是，单个HFile永远不会包含多个列族的数据。

## **16.什么是BlockCache？**

**答：**HBase BlockCache是HBase中使用的另一种数据存储。它用于将最常用的数据保留在JVM堆中。这种数据存储的主要目的是提供对HFiles中数据的访问，以避免读取磁盘。HBase中的每个列族都有自己的BlockCache。同样，BlockCache中的每个块代表数据的单位，而Hfile是一系列块，并在这些块上具有索引。



![img](https://pic4.zhimg.com/80/v2-8a0bdfdc4289ab334aa044f9b8f888f7_720w.webp)



## **HBase 高级面试问答**

## **17.数据如何写入HBase？**

**答：**遵循以下几个步骤将数据写入HBase。首先，当用户更新HBase表中的数据时，它将对提交日志进行记录，该记录在HBase中称为预写日志（WAL）。接下来，将数据存储在内存中的MemStore中。如果内存中的数据超过最大值，则将其作为HFile刷新到磁盘。刷新数据后，用户可以丢弃提交日志。

## **18.HBase中有哪些不同的压缩类型？**

**答：**压缩有两种类型：

- 大型压缩–这里出现了所有基于列的HFile，以创建单个HFile。一旦HFiles被删除，它们将被丢弃
- 次要压缩–在这种情况下，通过合并许多相邻的小HFile创建单个Hfile。小文件是随机选择的。

## **19.可以在HBase中的行中执行迭代吗？请说明。**

**答：**是的，只要任务不是按相反的顺序执行，我们就可以遍历所有行。当HBase将列值存储在磁盘上时，必须正确定义长度，并且必须在其后写入值。因此，如果要以相反的顺序执行迭代，则需要再存储一次值，这是HBase的兼容性和内存问题。

## **20.HBase如何处理写入失败？**

**答：**在像HBase这样的大型分布式系统中，故障很常见。但是，我们可以使用HBase预写日志（WAL）防止数据失败。属于HBase群集的每个服务器都维护一个WAL，以记录HBase数据中发生的更改。除非针对每个写操作在WAL中写入一个新条目，否则它将不会被视为成功。此外，Hadoop分布式文件系统（HDFS）支持HBase。因此，如果HBase发生故障，则将使用WAL从MemStore中清除未刷新的数据。

## **21.从HBase读取数据时，将在返回值之前从哪三个地方对数据进行协调？**

**答：**

- 1. MemStore：它是检查系统中是否有任何未决修改的第一个地方。
- 2. BlockCache：这是用来验证是否最近访问过该块。
- 3. HFiles：磁盘上的相关HFiles。

## **22.你在什么时候不想使用HBase？**

**答：**

- 当数据访问模式在不可变数据上是顺序的时。
- 当数据不大时。
- 何时可以使用Hive之类的替代方法。
- 当我们真正需要关系查询引擎或规范化架构时。

## 23.JAVA API HBASE

~~~java
在Java中，HBase（Hadoop Database）是一个开源的分布式NoSQL数据库，它构建在Hadoop之上，提供了高度可扩展的、面向列的存储系统。HBase的Java API允许开发者使用Java编程语言与HBase进行交互。以下是使用Java API与HBase进行操作的一些常见任务和示例：

**注意：在使用HBase Java API之前，你需要确保已经正确安装和配置了HBase集群。**

1. **连接到HBase集群：**

   ```java
   Configuration conf = HBaseConfiguration.create();
   Connection connection = ConnectionFactory.createConnection(conf);
   ```

2. **创建表：**

   ```java
   Admin admin = connection.getAdmin();
   TableName tableName = TableName.valueOf("mytable");
   HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
   tableDescriptor.addFamily(new HColumnDescriptor("cf1"));
   admin.createTable(tableDescriptor);
   ```

3. **插入数据：**

   ```java
   TableName tableName = TableName.valueOf("mytable");
   Table table = connection.getTable(tableName);
   Put put = new Put(Bytes.toBytes("row1"));
   put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("col1"), Bytes.toBytes("value1"));
   table.put(put);
   ```

4. **获取数据：**

   ```java
   Get get = new Get(Bytes.toBytes("row1"));
   Result result = table.get(get);
   byte[] value = result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("col1"));
   ```

5. **扫描表数据：**

   ```java
   Scan scan = new Scan();
   ResultScanner scanner = table.getScanner(scan);
   for (Result result : scanner) {
       // 处理每一行的数据
   }
   scanner.close();
   ```

6. **删除数据：**

   ```java
   Delete delete = new Delete(Bytes.toBytes("row1"));
   delete.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("col1"));
   table.delete(delete);
   ```

7. **关闭连接：**

   ```java
   connection.close();
   ```

这只是使用HBase Java API执行基本操作的示例。HBase还提供了许多高级功能，如过滤器、事务支持等。你可以根据具体的需求深入学习和使用HBase的Java API，以实现更复杂的数据库操作。要查看更多详细信息和示例，请参考HBase官方文档和API文档。
~~~



## **结论**

希望上述HBase面试问题能帮助你准备Hadoop面试。但是，首先，HBase面试问题首先，你必须了解Hadoop。以上就是今天的内容了，如果对你有所帮助，希望你能够关注、点赞、转发一键三连支持一下。

需要完整学习线路和配套课堂笔记，请回复111。

发布于 2020-12-01 14:20



HBase

大数据

面试

赞同添加评论

分享

喜欢收藏申请转载



![img](https://picx.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

评论千万条，友善第一条



还没有评论，发表第一个评论吧

### 推荐阅读

- # 一文掌握HBase核心知识以及面试问题

- 一、HBase基础和架构 HBase是一个高可靠、高性能、面向列的，主要用于海量结构化和半结构化数据存储的分布式key-value存储系统。它基于Google Bigtable开源实现，但二者有明显的区别：Googl…

- 大数据学习与分享

- # Hbase面试题

- 1 每天百亿数据存入HBase，如何保证数据的存储正确和在规定的时间里全部录入完毕，不残留数据 答：看到这个题目的时候我们要思考的是它在考查什么知识点？ 我们来看看要求： 1）百亿数据：…

- 风中追风zz

- ![全面认识HBase架构（建议收藏）](https://pic1.zhimg.com/v2-d71c1fcc355bf319f21a573ba2b62f7c_250x0.jpg?source=172ae18b)

- # 全面认识HBase架构（建议收藏）

- 阿丸发表于阿丸笔记

- # HBase面试总结

- 一、Hbase的六大特点： （1）、表大：一个表可以有数亿行，上百万列。 （2）、无模式：每行都有一个可排序的主键和任意多的列，列可以根据需要动态增加，同一个表中的不同行的可以有截然不…

- 数据开发爱...发表于大数据组件...