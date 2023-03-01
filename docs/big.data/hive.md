## Referer

[Hive官方文档-英文](https://hive.apache.org/)

[Hive中文文档](https://www.docs4dev.com/docs/zh/apache-hive/3.1.1/reference/Home.html)

![Hive 高频面试题 30 题](https://picx.zhimg.com/v2-7757103f1320aab73e3eb3cb84d91d29_720w.jpg?source=172ae18b)

# Hive 高频面试题 30 题

[![代码小咖](https://pic1.zhimg.com/v2-174c4e02985b676daf85f906154fc912_l.jpg?source=172ae18b)](https://www.zhihu.com/people/xiao-yu-ke-tang-41)

[代码小咖](https://www.zhihu.com/people/xiao-yu-ke-tang-41)

关注她

27 人赞同了该文章

如果你是数据开发、数据研发、或数据分析师，那么这篇文章将对你非常有用。记得转发收藏哦。

## **一、Hive面试题**

**1、hive内部表和外部表的区别**

未被external修饰的是内部表，被external修饰的为外部表。

**区别:**

```text
内部表数据由Hive自身管理，外部表数据由HDFS管理；
内部表数据存储的位置是hive.metastore.warehouse.dir（默认：/user/hive/warehouse），    外部表数据的存储位置由自己制定（如果没有LOCATION，Hive将在HDFS上       的/user/hive/warehouse文件夹下以外部表的表名创建一个文件夹，并将属于这个表的数据存    放在这里）；
删除内部表会直接删除元数据（metadata）及存储数据；删除外部表仅仅会删除元数据，HDFS上的文件并不会被删除。
```

**2、Hive有索引吗**

```text
Hive支持索引（3.0版本之前），但是Hive的索引与关系型数据库中的索引并不相同。并且    Hive索引提供的功能很有限，效率也并不高，因此Hive索引很少使用。

索引适用的场景：

适用于不更新的静态字段。以免总是重建索引数据。每次建立、更新数据后，都要重建索    引以构建索引表。
```

**3、运维如何对hive进行调度**

```text
将hive的sql定义在脚本当中；
使用azkaban或者oozie进行任务的调度；
监控任务调度页面。
```

**4、ORC、Parquet等列式存储的优点**

```text
-   ORC:ORC文件是自描述的，它的元数据使用Protocol Buffers序列化，文件中的数据尽可能的压缩以降低存储空间的消耗；以二进制方式存储，不可以直接读取；自解析，包含许多元数据，这些元数据都是同构ProtoBuffer进行序列化的；会尽可能合并多个离散的区间尽可能的减少I/O次数；在新版本的ORC中也加入了对Bloom Filter的支持，它可以进一 步提升谓词下推的效率，在Hive 1.2.0版本以后也加入了对此的支 持。

-   Parquet:Parquet支持嵌套的数据模型，类似于Protocol Buffers，每一个数据模型的schema包含多个字段，每一个字段有三个属性：重复次数、数据类型和字段名；Parquet中没有Map、Array这样的复杂数据结构，但是可以通过repeated和group组合来实现；通过Striping/Assembly算法，parquet可以使用较少的存储空间表示复杂的嵌套格式，并且通常Repetition level和Definition level都是较小的整数值，可以通过RLE算法对其进行压缩，进一步降低存储空间；Parquet文件以二进制方式存储，不可以直接读取和修改，Parquet文件是自解析的，文件中包括该文件的数据和元数据。
```

**5、数据建模用的哪些模型**

- 星型模型

![img](https://pic3.zhimg.com/80/v2-361850dac76df72abaa69bd874b83f56_720w.webp)



```text
星形模式(Star Schema)是最常用的维度建模方式。星型模式是以事实表为中心，所有的维度表直接连接在事实表上，像星星一样。星形模式的维度建模由一个事实表和一组维表成，且具有以下特点：
a. 维表只和事实表关联，维表之间没有关联；
b. 每个维表主键为单列，且该主键放置在事实表中，作为两边连接的外键；
c. 以事实表为核心，维表围绕核心呈星形分布。
```

- 雪花模型

![img](https://pic1.zhimg.com/80/v2-e1d8bf3fa3b44288f92ec015ecee22a8_720w.webp)



```text
雪花模式(Snowflake Schema)是对星形模式的扩展。雪花模式的维度表可以拥有其他维度表的，虽然这种模型相比星型更规范一些，但是由于这种模型不太容易理解，维护成本比较高，而且性能方面需要关联多层维表，性能比星型模型要低。
```

- 星座模型

![img](https://pic2.zhimg.com/80/v2-03cc37a975d5d6a844b673db333890d1_720w.webp)



```text
星座模式是星型模式延伸而来，星型模式是基于一张事实表的，而星座模式是基于多张事实表的，而且共享维度信息。前面介绍的两种维度建模方法都是多维表对应单事实表，但在很多时候维度空间内的事实表不止一个，而一个维表也可能被多个事实表用到。在业务发展后期，绝大部分维度建模都采用的是星座模式。
```

**6、为什么要对数据仓库分层**

```text
用空间换时间，通过大量的预处理来提升应用系统的用户体验（效率），因此数据仓库会   存在大量冗余的数据。如果不分层的话，如果源业务系统的业务规则发生变化将会影响整个数据清洗过程，工作量巨大。
通过数据分层管理可以简化数据清洗的过程，因为把原来一步的工作分到了多个步骤去完成，相当于把一个复杂的工作拆成了多个简单的工作，把一个大的黑盒变成了一个白盒，每一层的处理逻辑都相对简单和容易理解，这样我们比较容易保证每一个步骤的正确性，当数据发生错误的时候，往往我们只需要局部调整某个步骤即可。
```

**7、使用过Hive解析JSON串吗**

```text
Hive处理json数据总体来说有两个方向的路走：
a.将json以字符串的方式整个入Hive表，然后通过使用UDF函数解析已经导入到hive中的数据，比如使用LATERAL VIEW json_tuple的方法，获取所需要的列名。
b.在导入之前将json拆成各个字段，导入Hive表的数据是已经解析过的。这将需要使用第三方的 SerDe。
```

**8、sort by 和 order by 的区别**

```text
order by 会对输入做全局排序，因此只有一个reducer（多个reducer无法保证全局有序）只有一个reducer，会导致当输入规模较大时，需要较长的计算时间。

sort by不是全局排序，其在数据进入reducer前完成排序. 因此，如果用sort by进行排序，并且设置mapred.reduce.tasks>1， 则sort by只保证每个reducer的输出有序，不保证全局有序。
```

**9、数据倾斜怎么解决**

- 空值引发的数据倾斜
  解决方案：
  第一种：可以直接不让null值参与join操作，即不让null值有shuffle阶段

  

![img](https://pic2.zhimg.com/80/v2-1dba3db4e377ed7dd8dcc822b4d578b9_720w.webp)


第二种：因为null值参与shuffle时的hash结果是一样的，那么我们可以给null值随机赋值，这样它们的hash结果就不一样，就会进到不同的reduce中：



![img](https://pic3.zhimg.com/80/v2-7a6b7ab8be426769ec4c89c937df9192_720w.webp)



- 不同数据类型引发的数据倾斜
  解决方案：
  如果key字段既有string类型也有int类型，默认的hash就都会按int类型来分配，那我们直接把int类型都转为string就好了，这样key字段都为string，hash时就按照string类型分配了：

  

![img](https://pic2.zhimg.com/80/v2-bf9c49616cbe8386bbf74f505f418361_720w.webp)



- 不可拆分大文件引发的数据倾斜
  解决方案：
  这种数据倾斜问题没有什么好的解决方案，只能将使用GZIP压缩等不支持文件分割的文件转为bzip和zip等支持文件分割的压缩方式。
  所以，我们在对文件进行压缩时，为避免因不可拆分大文件而引发数据读取的倾斜，在数据压缩的时候可以采用bzip2和Zip等支持文件分割的压缩算法。
- 数据膨胀引发的数据倾斜
  解决方案：
  在Hive中可以通过参数 hive.new.job.grouping.set.cardinality 配置的方式自动控制作业的拆解，该参数默认值是30。表示针对grouping sets/rollups/cubes这类多维聚合的操作，如果最后拆解的键组合大于该值，会启用新的任务去处理大于该值之外的组合。如果在处理数据时，某个分组聚合的列有较大的倾斜，可以适当调小该值。
- 表连接时引发的数据倾斜
  解决方案：
  通常做法是将倾斜的数据存到分布式缓存中，分发到各个Map任务所在节点。在Map阶段完成join操作，即MapJoin，这避免了 Shuffle，从而避免了数据倾斜。
- 确实无法减少数据量引发的数据倾斜
  解决方案：
  这类问题最直接的方式就是调整reduce所执行的内存大小。
  调整reduce的内存大小使用mapreduce.reduce.memory.mb这个配置。

**10、Hive 小文件过多怎么解决**

```text
使用 hive 自带的 concatenate 命令，自动合并小文件
调整参数减少Map数量
减少Reduce的数量
使用hadoop的archive将小文件归档
```

**11、Hive优化有哪些**

```text
数据存储及压缩
通过调参优化
有效地减小数据集将大表拆分成子表；结合使用外部表和分区表
SQL优化
```

## **二、Hive高频面试点集合**

##### 1、Hive的两张表关联，使用MapReduce怎么实现？

如果其中有一张表为小表，直接使用map端join的方式（map端加载小表）进行聚合。

如果两张都是大表，那么采用联合key，联合key的第一个组成部分是join on中的公共字段，第二部分是一个flag，0代表表A，1代表表B，由此让Reduce区分客户信息和订单信息；在Mapper中同时处理两张表的信息，将join on公共字段相同的数据划分到同一个分区中，进而传递到一个Reduce中，然后在Reduce中实现聚合。

##### 2、请谈一下Hive的特点，Hive和RDBMS有什么异同？

hive是基于Hadoop的一个数据仓库工具，可以将结构化的数据文件映射为一张数据库表，并提供完整的sql查询功能，可以将sql语句转换为MapReduce任务进行运行。其优点是学习成本低，可以通过类SQL语句快速实现简单的MapReduce统计，不必开发专门的MapReduce应用，十分适合数据仓库的统计分析，但是Hive不支持实时查询。

Hive与关系型数据库的区别：



![img](https://pic2.zhimg.com/80/v2-352a1c3e00f82d45583e22f72e389e89_720w.webp)



##### 3、请说明hive中 Sort By，Order By，Cluster By，Distrbute By各代表什么意思？

Order by：会对输入做全局排序，因此只有一个reducer（多个reducer无法保证全局有序）。只有一个reducer，会导致当输入规模较大时，需要较长的计算时间。

Sort by：不是全局排序，其在数据进入reducer前完成排序。1

Distribute by：按照指定的字段对数据进行划分输出到不同的reduce中。

Cluster by：除了具有 distribute by 的功能外还兼具 sort by 的功能。

##### 4、写出Hive中split、coalesce及collect_list函数的用法（可举例）？

split将字符串转化为数组，即：split('a,b,c,d' , ',') ==> ["a","b","c","d"]。

coalesce(T v1, T v2, …) 返回参数中的第一个非空值；如果所有值都为 NULL，那么返回NULL。

collect_list列出该字段所有的值，不去重 => select collect_list(id) from table。

##### 5、 Hive有哪些方式保存元数据，各有哪些特点？

Hive支持三种不同的元存储服务器，分别为：内嵌式元存储服务器、本地元存储服务器、远程元存储服务器，每种存储方式使用不同的配置参数。

内嵌式元存储主要用于单元测试，在该模式下每次只有一个进程可以连接到元存储，Derby是内嵌式元存储的默认数据库。

在本地模式下，每个Hive客户端都会打开到数据存储的连接并在该连接上请求SQL查询。

在远程模式下，所有的Hive客户端都将打开一个到元数据服务器的连接，该服务器依次查询元数据，元数据服务器和客户端之间使用Thrift协议通信。

##### 6、Hive内部表和外部表的区别？

创建表时：创建内部表时，会将数据移动到数据仓库指向的路径；若创建外部表，仅记录数据所在的路径，不对数据的位置做任何改变。

删除表时：在删除表的时候，内部表的元数据和数据会被一起删除， 而外部表只删除元数据，不删除数据。这样外部表相对来说更加安全些，数据组织也更加灵活，方便共享源数据。

##### 7、Hive的函数：UDF、UDAF、UDTF的区别？

UDF：单行进入，单行输出

UDAF：多行进入，单行输出

UDTF：单行输入，多行输出

##### 8、所有的Hive任务都会有MapReduce的执行吗？

不是，从Hive0.10.0版本开始，对于简单的不需要聚合的类似SELECT from

LIMIT n语句，不需要起MapReduce job，直接通过Fetch task获取数据。

##### 9、说说对Hive桶表的理解？

桶表是对数据某个字段进行哈希取值，然后放到不同文件中存储。

数据加载到桶表时，会对字段取hash值，然后与桶的数量取模。把数据放到对应的文件中。物理上，每个桶就是表(或分区）目录里的一个文件，一个作业产生的桶(输出文件)和reduce任务个数相同。

桶表专门用于抽样查询，是很专业性的，不是日常用来存储数据的表，需要抽样查询时，才创建和使用桶表。



![img](https://pic2.zhimg.com/80/v2-71097139851125d2aaecd869bd003d0d_720w.webp)







##### 12、Hive 中的压缩格式TextFile、SequenceFile、RCfile 、ORCfile各有什么区别？

1、TextFile

默认格式，存储方式为行存储，数据不做压缩，磁盘开销大，数据解析开销大。可结合Gzip、Bzip2使用(系统自动检查，执行查询时自动解压)，但使用这种方式，压缩后的文件不支持split，Hive不会对数据进行切分，从而无法对数据进行并行操作。并且在反序列化过程中，必须逐个字符判断是不是分隔符和行结束符，因此反序列化开销会比SequenceFile高几十倍。

2、SequenceFile

SequenceFile是Hadoop API提供的一种二进制文件支持，存储方式为行存储，其具有使用方便、可分割、可压缩的特点。

SequenceFile支持三种压缩选择：NONE，RECORD，BLOCK。Record压缩率低，一般建议使用BLOCK压缩。

优势是文件和hadoop api中的MapFile是相互兼容的

3、RCFile

存储方式：数据按行分块，每块按列存储。结合了行存储和列存储的优点：

首先，RCFile 保证同一行的数据位于同一节点，因此元组重构的开销很低；

其次，像列存储一样，RCFile 能够利用列维度的数据压缩，并且能跳过不必要的列读取；

4、ORCFile

存储方式：数据按行分块 每块按照列存储。

压缩快、快速列存取。

效率比rcfile高，是rcfile的改良版本。

小结：

相比TEXTFILE和SEQUENCEFILE，RCFILE由于列式存储方式，数据加载时性能消耗较大，但是具有较好的压缩比和查询响应。

数据仓库的特点是一次写入、多次读取，因此，整体来看，RCFILE相比其余两种格式具有较明显的优势。

##### 13、Hive表关联查询，如何解决数据倾斜的问题？

1）倾斜原因：map输出数据按key Hash的分配到reduce中，由于key分布不均匀、业务数据本身的特、建表时考虑不周、等原因造成的reduce 上的数据量差异过大。（1）key分布不均匀; （2）业务数据本身的特性; （3）建表时考虑不周; （4）某些SQL语句本身就有数据倾斜;

如何避免：对于key为空产生的数据倾斜，可以对其赋予一个随机值。

2）解决方案

（1）参数调节： hive.map.aggr = true hive.groupby.skewindata=true

有数据倾斜的时候进行负载均衡，当选项设定位true,生成的查询计划会有两个MR Job。第一个MR Job中，Map的输出结果集合会随机分布到Reduce中，每个Reduce做部分聚合操作，并输出结果，这样处理的结果是相同的Group By Key有可能被分发到不同的Reduce中，从而达到负载均衡的目的；第二个MR Job再根据预处理的数据结果按照Group By Key 分布到 Reduce 中（这个过程可以保证相同的 Group By Key 被分布到同一个Reduce中），最后完成最终的聚合操作。

(2）SQL 语句调节：

① 选用join key分布最均匀的表作为驱动表。做好列裁剪和filter操作，以达到两表做join 的时候，数据量相对变小的效果。 ② 大小表Join： 使用map join让小的维度表（1000 条以下的记录条数）先进内存。在map端完成reduce。 ③ 大表Join大表： 把空值的key变成一个字符串加上随机数，把倾斜的数据分到不同的reduce上，由于null 值关联不上，处理后并不影响最终结果。 ④ count distinct大量相同特殊值: count distinct 时，将值为空的情况单独处理，如果是计算count distinct，可以不用处理，直接过滤，在最后结果中加1。如果还有其他计算，需要进行group by，可以先将值为空的记录单独处理，再和其他计算结果进行union。

##### 14、Fetch抓取

Fetch抓取是指，Hive中对某些情况的查询可以不必使用MapReduce计算。例如：SELECT * FROM employees;在这种情况下，Hive可以简单地读取employee对应的存储目录下的文件，然后输出查询结果到控制台。

在hive-default.xml.template文件中hive.fetch.task.conversion默认是more，老版本hive默认是minimal，该属性修改为more以后，在全局查找、字段查找、limit查找等都不走mapreduce。

##### 15、小表、大表Join

将key相对分散，并且数据量小的表放在join的左边，这样可以有效减少内存溢出错误发生的几率；再进一步，可以使用Group让小的维度表（1000条以下的记录条数）先进内存。在map端完成reduce。

实际测试发现：新版的hive已经对小表JOIN大表和大表JOIN小表进行了优化。小表放在左边和右边已经没有明显区别。

##### 16、大表Join大表

1）空KEY过滤 有时join超时是因为某些key对应的数据太多，而相同key对应的数据都会发送到相同的reducer上，从而导致内存不够。此时我们应该仔细分析这些异常的key，很多情况下，这些key对应的数据是异常数据，我们需要在SQL语句中进行过滤。例如key对应的字段为空。2）空key转换 有时虽然某个key为空对应的数据很多，但是相应的数据不是异常数据，必须要包含在join的结果中，此时我们可以表a中key为空的字段赋一个随机的值，使得数据随机均匀地分不到不同的reducer上。

##### 17、Group By

默认情况下，Map阶段同一Key数据分发给一个reduce，当一个key数据过大时就倾斜了。

并不是所有的聚合操作都需要在Reduce端完成，很多聚合操作都可以先在Map端进行部分聚合，最后在Reduce端得出最终结果。1）开启Map端聚合参数设置 （1）是否在Map端进行聚合，默认为True hive.map.aggr = true （2）在Map端进行聚合操作的条目数目 hive.groupby.mapaggr.checkinterval = 100000 （3）有数据倾斜的时候进行负载均衡（默认是false） hive.groupby.skewindata = true

当选项设定为 true，生成的查询计划会有两个MR Job。第一个MR Job中，Map的输出结果会随机分布到Reduce中，每个Reduce做部分聚合操作，并输出结果，这样处理的结果是相同的Group By Key有可能被分发到不同的Reduce中，从而达到负载均衡的目的；

第二个MR Job再根据预处理的数据结果按照Group By Key分布到Reduce中（这个过程可以保证相同的Group By Key被分布到同一个Reduce中），最后完成最终的聚合操作。

##### 18、Count(Distinct) 去重统计

数据量小的时候无所谓，数据量大的情况下，由于COUNT DISTINCT操作需要用一个Reduce Task来完成，这一个Reduce需要处理的数据量太大，就会导致整个Job很难完成，一般COUNT DISTINCT使用先GROUP BY再COUNT的方式替换

尽量避免笛卡尔积，join的时候不加on条件，或者无效的on条件，Hive只能使用1个reducer来完成笛卡尔积



![img](https://pic3.zhimg.com/80/v2-b47e2e8484b768777b82e75ede4174ae_720w.webp)

金三银四抓住机遇，更多面试课程参考下方

[https://www.876it.com](https://link.zhihu.com/?target=https%3A//www.876it.com)

编辑于 2022-03-17 14:45



面试

Hive

Hadoop

赞同 27添加评论

分享

喜欢收藏申请转载



![img](https://pic1.zhimg.com/v2-46eca1cb644de1858128273ebf850435_l.jpg?source=32738c0c)

评论千万条，友善第一条



还没有评论，发表第一个评论吧