## Referer

[Java 中文网](https://www.oracle.com/cn/java/)

## Java

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

```

```java
for (;;) 与 while（true）区别
    
●java 编译器lambda设计思想 - final
●语法糖
●闭包
●内存逃逸
●线上gc
●JAVA AGENT - Javaassist、Asm
●debug相关mybatis功能视频
    
极海视频video
```

**hashmap发生死循环**

```markdown
在Java中，`HashMap` 可能会发生死循环的情况通常与哈希碰撞（Hash Collisions）以及哈希桶链表的长度有关。下面是一些可能导致 `HashMap` 死循环的情况：

1. **哈希碰撞：** 哈希碰撞是指两个或多个不同的键映射到相同的哈希桶位置。如果在这个桶位置上的链表或树（具体数据结构取决于 `HashMap` 实现版本）变得非常长，就可能导致性能问题，甚至死循环。这是因为在查找特定键的过程中，`HashMap` 需要遍历该位置上的链表或树，如果这个链表或树太长，就会导致性能下降，可能会出现死循环。

2. **自定义的键类型：** 如果你自定义了键类型，需要确保你正确地实现了 `hashCode()` 和 `equals()` 方法。如果这两个方法没有正确实现，可能会导致键的哈希值计算不正确，从而引发死循环或数据丢失等问题。

3. **多线程环境下的并发修改：** 如果多个线程同时修改 `HashMap`，可能导致数据结构被破坏，进而引发死循环或其他不一致性问题。为了在多线程环境中安全地使用 `HashMap`，可以使用 `ConcurrentHashMap` 或手动进行同步操作。

4. **不适当的负载因子：** 负载因子（load factor）是 `HashMap` 决定何时进行扩容的一个重要参数。如果负载因子设置得太高，就会导致哈希桶链表变得过长，从而降低了性能。在极端情况下，可能会导致死循环。因此，合适的负载因子选择对于 `HashMap` 的性能非常重要。

为了避免 `HashMap` 中的死循环问题，你应该确保使用合适的哈希函数、合适的负载因子以及正确实现键的 `hashCode()` 和 `equals()` 方法。如果在多线程环境中使用 `HashMap`，还需要考虑并发问题，可以选择使用 `ConcurrentHashMap` 或实施适当的同步措施。
```

