# Java

## 1、CompletableFuture

[CompletableFuture基本用法 - 废物大师兄 - 博客园 (cnblogs.com)](https://www.cnblogs.com/cjsblog/p/9267163.html)

## 2、HashMap

### 2.1 死循环

```markdown
在Java中，`HashMap` 可能会发生死循环的情况通常与哈希碰撞（Hash Collisions）以及哈希桶链表的长度有关。下面是一些可能导致 `HashMap` 死循环的情况：

1. **哈希碰撞：** 哈希碰撞是指两个或多个不同的键映射到相同的哈希桶位置。如果在这个桶位置上的链表或树（具体数据结构取决于 `HashMap` 实现版本）变得非常长，就可能导致性能问题，甚至死循环。这是因为在查找特定键的过程中，`HashMap` 需要遍历该位置上的链表或树，如果这个链表或树太长，就会导致性能下降，可能会出现死循环。

2. **自定义的键类型：** 如果你自定义了键类型，需要确保你正确地实现了 `hashCode()` 和 `equals()` 方法。如果这两个方法没有正确实现，可能会导致键的哈希值计算不正确，从而引发死循环或数据丢失等问题。

3. **多线程环境下的并发修改：** 如果多个线程同时修改 `HashMap`，可能导致数据结构被破坏，进而引发死循环或其他不一致性问题。为了在多线程环境中安全地使用 `HashMap`，可以使用 `ConcurrentHashMap` 或手动进行同步操作。

4. **不适当的负载因子：** 负载因子（load factor）是 `HashMap` 决定何时进行扩容的一个重要参数。如果负载因子设置得太高，就会导致哈希桶链表变得过长，从而降低了性能。在极端情况下，可能会导致死循环。因此，合适的负载因子选择对于 `HashMap` 的性能非常重要。

为了避免 `HashMap` 中的死循环问题，你应该确保使用合适的哈希函数、合适的负载因子以及正确实现键的 `hashCode()` 和 `equals()` 方法。如果在多线程环境中使用 `HashMap`，还需要考虑并发问题，可以选择使用 `ConcurrentHashMap` 或实施适当的同步措施。
```

```

基本数据类型
byte、short、int、long、char、float、double、boolean

集合 
- hashmap、concurrenthashmap
- hashmap和treemap的区别，hashmap如何实现，put和get过程
- hashset底层原理

tcp三次握手

封装、继承、多态

接口、抽象类区别

反射、泛型

lambda表达式

数据结构-链表、队列、二分查找

内部类-局部内部类、匿名内部类、静态内部类

设计原则
开闭原则、单一职责、迪米特法则、里式替换、接口隔离、

线程
线程池、异步线程、死锁排查、自旋锁、threadlocal、callable、future、futureTask<V>、completabFuture

原子性atomic

深拷贝、浅拷贝、值传递、引用传递

文件字符字节流

synchronized - 偏向锁、轻量级锁、重量级锁、自旋锁

可重入锁

aqs如何实现可重入锁

reentrantlock、volatile、semphore、condition、wait、await、notify、notifyAll

并发juc

内存逃逸

设计模式 - 发短信、豆芽、邮件等方式, 采用观察者模式、策略模式加配置灵活使用、责任链、单例工厂、工厂模式

java异常 - runtimeException、Exception

本文主要阐述 关于 web项目中 如何搭建使用 单元测试框架 的能力

内容包含但不限于 junit mockito powermocker and so on

log4j / logback /slf4j

lombok

序列化及其作用

String、StringBuilder、StringBuffer区别？？

自定义注解如何实现？？

正则表达式

反射获取 方法名称/参数 等信息

spi机制详解 https://pdai.tech/md/java/advanced/java-advanced-spi.html

for (;;) 与 while（true）区别
    
java 编译器lambda设计思想 - final

语法糖

闭包

内存逃逸

线上gc

JAVA AGENT - Javaassist、Asm

debug相关mybatis功能视频
    
极海视频video
```

## 3、设计模式

[设计模式](https://zhuanlan.zhihu.com/p/93770973)

[Java 23种设计模式全归纳 | 完结版](https://cloud.tencent.com/developer/article/1602270)

[Java中常用的设计模式](https://blog.csdn.net/sugar_no1/article/details/88317950)

[六大原则](https://zhuanlan.zhihu.com/p/93770973)

## 4、分布式事务

[分布式事务](https://zhuanlan.zhihu.com/p/263555694)

[面试必问：分布式事务六种解决方案](https://zhuanlan.zhihu.com/p/183753774)

```
数据库事务
四大特性：ACID
原子性: 构成事务的所有操作，要么都执行完成，要么全部不执行，不可能出现部分成功部分失败的情况。
一致性: 在事务执行前后，数据库的一致性约束没有被破坏。比如：张三向李四转 100 元，转账前和转账后的数据是正确状态这叫一致性，如果出现张三转出 100 元，李四账户没有增加 100 元这就出现了数据错误，就没有达到一致性。
隔离性: 数据库中的事务一般都是并发的，隔离性是指并发的两个事务的执行互不干扰，一个事务不能看到其他事务的运行过程的中间状态。通过配置事务隔离级别可以比避免脏读、重复读问题。
持久性：事务完成之后，该事务对数据的更改会持久到数据库，且不会被回滚。

CAP理论
一致性：一致性是指写操作后的读操作可以读取到最新的数据状态，当数据分布在多个节点上，从任意结点读取到的数据都是最新的状态。
可用性：可用性是指任何事务操作都可以得到响应结果，且不会出现响应超时或响应错误。
分区容错性：通常分布式系统的各各结点部署在不同的子网，这就是网络分区，不可避免的会出现由于网络问题而导致结点之间通信失败，此时仍可对外提供服务，这叫分区容忍性

BASE理论
BASE理论解决CAP理论提出了分布式系统的一致性和可用性不能兼得的问题。
BA：Basically Available，基本可用
系统出现了不可预知的故障，但还是能用，相比较正常的系统而言会有响应时间上的损失和功能上的损失。
S：Soft State，软状态，状态可以有一段时间不同步
什么是软状态呢？相对于原子性而言，要求多个节点的数据副本都是一致的，这是一种“硬状态”。
软状态指的是：允许系统中的数据存在中间状态，并认为该状态不影响系统的整体可用性，即允许系统在多个不同节点的数据副本存在数据延时
E：Eventually Consistent，最终一致，最终数据是一致的就可以了，而不是时时保持强一致。

2pc
准备阶段（Prepare phase）：事务管理器给每个参与者发送 Prepare 消息，每个数据库参与者在本地执行事务，并写本地的 Undo/Redo 日志，此时事务没有提交。（Undo 日志是记录修改前的数据，用于数据库回滚，Redo 日志是记录修改后的数据，用于提交事务后写入数据文件）
提交阶段（commit phase）：如果事务管理器收到了参与者的执行失败或者超时消息时，直接给每个参与者发送回滚（Rollback）消息；否则，发送提交（Commit）消息；参与者根据事务管理器的指令执行提交或者回滚操作，并释放事务处理过程中使用的锁资源。注意：必须在最后阶段释放锁资源。

3pc
就是除了引入超时机制之外，3PC把2PC的准备阶段再次一分为二，这样三阶段提交就有CanCommit、PreCommit、DoCommit三个阶段。

xa
需要本地数据库支持XA协议。
资源锁需要等到两个阶段结束才释放，性能较差

seata
全局事务

tcc
TCC需要注意三种异常处理分别是空回滚、幂等、悬挂

hmily

saga

本地消息表 + job轮询

mq事务消息rocketmq

最大努力通知
发起通知方通过一定的机制最大努力将业务处理结果通知到接收方。

dtm
DTM 是一款 golang 开发的分布式事务管理器，解决了跨数据库、跨服务、跨语言栈更新数据的一致性问题。
```

## 5、JUC并发编程

[JUC并发编程学习笔记（狂神）](https://blog.csdn.net/weixin_44491927/article/details/108560692)

[JUC并发编程](https://www.jianshu.com/p/dd0e3b0e4cae)

[JUC并发编程详细笔记总结](https://zhuanlan.zhihu.com/p/433763631)

```markdown
死锁会不会cpu100%
cpu100%如何排查
内存溢出如何排查
页面假死如何排查
线程分配过多会不会cpu上升
```

## 6、JVM

[Java基础----JVM详解](https://blog.csdn.net/pre_tender/article/details/102155860)

[JVM 详解，大白话带你认识 JVM](https://www.cnblogs.com/xzsj/p/xzsj-backend-java-jvm.html)

[JVM内存模型（详解）](https://zhuanlan.zhihu.com/p/101495810)

[【6问6答】JAVA 应用 CPU 使用率为什么飚升？ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/58338577?utm_id=0)

[死锁一定会造成cpu使用率飙升吗？_数据库死锁会导致cpu过高吗_dotsee的博客-CSDN博客](https://blog.csdn.net/qq_25188255/article/details/90456778)

[线上服务Java进程假死快速排查、分析 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/529350757)

```
Java自带了一些用于分析Java应用程序和JVM的工具，这些工具可以帮助开发人员和系统管理员进行性能调优、内存分析和故障排查。以下是几种Java自带的分析工具：

jmap（Memory Map）：
用于生成Java进程的堆转储快照（heap dump），以及查看堆转储文件的信息。通过分析堆转储文件，可以了解Java堆内存中的对象分布、内存泄漏等问题。

jstack（Java Stack Trace）：
用于生成Java进程的线程转储快照（thread dump），显示所有线程的堆栈跟踪信息。这对于查找死锁、线程阻塞等问题很有帮助。

jstat（JVM Statistics Monitoring Tool）：
用于监视Java进程的各种运行时统计信息，包括垃圾回收情况、堆内存使用情况、类加载数量等。

jconsole（Java Monitoring and Management Console）：
图形化工具，提供对Java应用程序和JVM的监视和管理功能。可以通过jconsole实时监控应用程序的性能指标，并进行线程和内存分析。

jvisualvm（Java VisualVM）：
另一个图形化工具，是JDK自带的可视化监视和分析工具。提供线程分析、内存分析、垃圾回收分析等功能。

jcmd（Java Command）：
Java 7及以上版本提供的命令行工具，用于执行各种诊断命令。可以用来获取线程转储、堆转储，或执行各种运行时操作，如强制垃圾回收等。

jhsdb（JVM Serviceability Debugger）：
JDK 9及以上版本提供的高级调试工具，用于执行诊断命令和动态跟踪JVM的运行状态。

这些工具提供了丰富的功能，可以帮助开发人员定位和解决Java应用程序的性能问题和故障。使用这些工具需要一定的了解和经验，但对于Java应用程序的分析和调优来说是非常有价值的。

Java页面假死是指在Web应用程序中，页面长时间无响应或加载时间非常长，导致用户无法正常访问或操作页面。
这种情况可能由于多种原因引起，下面是详细的分析步骤：

1、确认页面假死：
首先，需要确认页面是否真的处于假死状态。可以通过多个客户端尝试访问页面，检查是否都无响应，或者使用浏览器的开发者工具查看页面请求和响应的状态。

2、检查服务器资源：
确保服务器的CPU、内存、磁盘等资源没有达到饱和状态。可以通过监控工具或者命令行查看服务器资源使用情况，避免因为服务器资源不足导致页面假死。

3、查看日志：
查找应用程序的日志文件，特别是错误日志和访问日志。检查是否有异常或错误信息，这可能会给出一些线索。

4、检查数据库连接：
如果页面中涉及数据库操作，确保数据库连接没有泄漏，以及数据库操作没有耗时过长。可以检查数据库连接池的配置和使用情况。

5、检查网络请求：
如果页面中有向外部服务发送网络请求，确保网络连接正常，没有超时或错误。可以通过网络监控工具查看网络请求的状态。

6、查看线程情况：
使用线程Dump（可以通过jstack命令获取）查看Java应用程序中的线程状态。检查是否有线程阻塞、死锁等情况。

7、分析代码逻辑：
仔细分析页面所涉及的代码逻辑，尤其是可能耗时的部分。查找是否存在性能瓶颈，比如循环次数过多、数据库查询频繁等。

8、使用性能分析工具：
使用一些性能分析工具，例如VisualVM、YourKit等，对Java应用程序进行性能分析，找出耗时操作和资源瓶颈。

9、压力测试：
进行压力测试，模拟多用户访问页面，观察系统的响应情况和性能表现。这有助于找出系统在高负载下的问题。

10、调整服务器配置：
根据分析结果，可能需要调整服务器的配置，如增加内存、优化数据库配置等，以提高系统的性能和稳定性。

页面假死问题可能是复杂的，可能需要综合使用多种方法才能找到根本原因。及时记录并分析日志，保持监控和性能测试的习惯，可以帮助更好地定位和解决页面假死问题。

【常见的垃圾收集器】

cms、g1 可预测停顿时间、stw

jvmti、jps、jmap、jstack、arthas、jconsole、jhat、jvisualvm、mat工具

oom问题排查

直接内存技术
 
类加载器、双亲委派模型

jit预编译

io内存模型

- oom如何排查
- cpu 100%如何排查
- 死锁 cpu利用率多少？while true会cpu 升高吗？频繁youngGc 、线程数很高
- 页面假死如何排查

【什么情况下我们需要破坏双亲委派模型？？】
双亲委派模型（Parent Delegation Model）是Java类加载器的一种工作模式，用于加载类和资源。在这个模型中，每个类加载器都有一个双亲（parent）加载器，它会尝试加载类，如果找不到，则会委派给它的双亲加载器，一直追溯到根加载器。只有在所有双亲加载器都无法加载类时，才会由当前加载器尝试加载。

破坏双亲委派模型通常是一种不建议的做法，因为它可能导致类加载的混乱和冲突，破坏了Java类加载器的原本设计思想。但在某些特定情况下，可能需要考虑破坏双亲委派模型：

1. 自定义类加载器： 如果你需要实现自定义类加载器，以实现特定的加载逻辑，如动态加载类，实现热部署等，你可能需要破坏双亲委派模型。自定义加载器可以在父加载器无法加载类时，尝试自己加载。
2. 类隔离： 在某些情况下，你可能需要隔离不同的类加载器，以确保它们加载的类互不干扰。这通常出现在一些应用服务器或框架中，每个应用都有自己的类加载器，以防止类冲突。
3. 动态代理和字节码增强：一些框架和库，如Spring和Hibernate，使用动态代理和字节码增强来实现AOP（面向切面编程）等功能，这可能需要破坏双亲委派模型以加载被代理的类。

需要注意的是，破坏双亲委派模型可能会导致类加载的复杂性和潜在的问题，因此应该慎重使用，并确保清楚了解其影响和潜在风险。在大多数情况下，最好遵循Java的类加载器机制，只有在确实需要时才考虑破坏这种模型。
```

