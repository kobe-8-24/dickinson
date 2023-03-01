## Referer

[Hdfs官方文档-英文](https://hadoop.apache.org/docs/r1.2.1/hdfs_design.html)

[Hdfs官方文档-中文](https://hadoop.apache.org/docs/r1.0.4/cn/hdfs_design.html)

##### 1、HDFS的写流程

```
客户端通过Distributed FileSystem模块向NameNode请求上传文件，NameNode检查目标文件是否已存在，父目录是否存在。
NameNode返回是否可以上传。
客户端请求第一个 Block上传到哪几个DataNode服务器上。
NameNode返回3个DataNode节点，分别为dn1、dn2、dn3。
客户端通过FSDataOutputStream模块请求dn1上传数据，dn1收到请求会继续调用dn2，然后dn2调用dn3，将这个通信管道建立完成。
dn1、dn2、dn3逐级应答客户端。
客户端开始往dn1上传第一个Block（先从磁盘读取数据放到一个本地内存缓存），以Packet为单位，dn1收到一个Packet就会传给dn2，dn2传给dn3；dn1每传一个packet会放入一个应答队列等待应答。
当一个Block传输完成之后，客户端再次请求NameNode上传第二个Block的服务器。（重复执行3-7步）。
```

##### 2、HDFS读数据流程

```
客户端通过Distributed FileSystem向NameNode请求下载文件，NameNode通过查询元数据，找到文件块所在的DataNode地址。
挑选一台DataNode（就近原则，然后随机）服务器，请求读取数据。
DataNode开始传输数据给客户端（从磁盘里面读取数据输入流，以Packet为单位来做校验）。
客户端以Packet为单位接收，先在本地缓存，然后写入目标文件。
```

##### 3、datenode什么情况下不会备份

```
设置备份数为1时, 就不会备份了.
延申—Hadoop中在哪里设置备份数, 是哪个字段：在hdfs-site.xml中的dfs.replication变量.
```

##### 4.HDFS中大量小文件带来的问题以及解决的方案

```
问题：

hadoop中目录、文件和块都会以对象的形式保存在namenode的内存中, 大概每个对象会占用150bytes. 小文件数量多会大量占用namenode的内存; 使namenode读取元数据速度变慢, 启动时间延长; 还因为占用内存过大, 导致gc时间增加等.

解决办法:

两个角度, 一是从根源解决小文件的产生, 二是解决不了就选择合并.

从数据来源入手, 如每小时抽取一次改为每天抽取一次等方法来积累数据量.

如果小文件无可避免, 一般就采用合并的方式解决. 可以写一个MR任务读取某个目录下的所有小文件, 并重写为一个大文件.
```

##### 5.HDFS三个核心组件时什么，分别有什么作用

```
NameNode.：集群的核心, 是整个文件系统的管理节点. 维护着文件系统的文件目录结构和元数据信息、文件与数据块列表的对应关系
DataNode：存放具体数据块的节点, 主要负责数据的读写, 定期向NameNode发送心跳
SecondaryNameNode：辅助节点, 同步NameNode中的元数据信息, 辅助NameNode对fsimage和editsLog进行合并.=
```

##### 6.fsimage和editlogs是做什么用的?

```
fsimage文件存储的是Hadoop的元数据文件, 如果namenode发生故障, 最近的fsimage文件会被载入到内存中, 用来重构元数据的最近状态, 再从相关点开始向前执行edit logs文件中记录的每个事务.
文件系统客户端执行写操作时, 这些事务会首先记录到日志文件中.
在namenode运行期间, 客户端对hdfs的写操作都保存到edit文件中, 久而久之就会造成edit文件变得很大, 这对namenode的运行没有影响, 但是如果namenode重启, 它会将fsimage中的内容映射到内存中, 然后再一条一条执行edit文件中的操作, 所以日志文件太大会导致重启速度很慢. 所以在namenode运行的时候就要将edit logs和fsimage定期合并.
```

##### 7.Linux中的块大小为4KB, 为什么HDFS中块大小为64MB或128MB?

```
块是存储在文件系统中的数据的最小单元. 如果采用4kb的块大小来存放存储在Hadoop中的数据, 就会需要大量的块, 大大增加了寻找块的时间, 降低了读写效率.
并且, 一个map或者一个reduce都是以一个块为单位处理, 如果块很小, mapreduce任务数就会很多, 任务之间的切换开销变大, 效率降低
```

##### 8.并发写入HDFS文件可行吗?

```
不行, 因为客户端通过namenode接收到在数据块上写入的许可后, 那个块会锁定直到写入操作完成, 所以不能在同一个块上写入.
```

##### 9.HDFS放置副本的策略

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030114152541.png)



##### 10. NameNode与SecondaryNameNode 的区别与联系？

```
区别：

NameNode负责管理整个文件系统的元数据，以及每一个路径（文件）所对应的数据块信息。
SecondaryNameNode主要用于定期合并命名空间镜像和命名空间镜像的编辑日志。
联系：

SecondaryNameNode中保存了一份和namenode一致的镜像文件（fsimage）和编辑日志（edits）。
在主namenode发生故障时（假设没有及时备份数据），可以从SecondaryNameNode恢复数据。
```

##### 11.namenode的工作机制

```
第一阶段：NameNode启动

第一次启动NameNode格式化后，创建Fsimage和Edits文件。如果不是第一次启动，直接加载编辑日志和镜像文件到内存。
客户端对元数据进行增删改的请求。
NameNode记录操作日志，更新滚动日志。
NameNode在内存中对元数据进行增删改。
第二阶段：Secondary NameNode工作

Secondary NameNode询问NameNode是否需要CheckPoint。直接带回NameNode是否检查结果。
Secondary NameNode请求执行CheckPoint。
NameNode滚动正在写的Edits日志。
将滚动前的编辑日志和镜像文件拷贝到Secondary NameNode。
Secondary NameNode加载编辑日志和镜像文件到内存，并合并。
生成新的镜像文件fsimage.chkpoint。
拷贝fsimage.chkpoint到NameNode。
NameNode将fsimage.chkpoint重新命名成fsimage。
```

##### 12.datenode工作机制

```
一个数据块在DataNode上以文件形式存储在磁盘上，包括两个文件，一个是数据本身，一个是元数据包括数据块的长度，块数据的校验和，以及时间戳。
DataNode启动后向NameNode注册，通过后，周期性（1小时）的向NameNode上报所有的块信息。
心跳是每3秒一次，心跳返回结果带有NameNode给该DataNode的命令如复制块数据到另一台机器，或删除某个数据块。如果超过10分钟没有收到某个DataNode的心跳，则认为该节点不可用。
集群运行中可以安全加入和退出一些机器。
```

##### 13. 你认为 hadoop 有哪些设计不合理的地方

```
不支持文件的并发写入和对文件内容的随机修改。
不支持低延迟、高吞吐的数据访问。
存取大量小文件，会占用namenode大量内存，小文件的寻址时间超过读取时间。
hadoop环境搭建比较复杂。
数据无法实时处理。
```

