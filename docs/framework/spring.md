## Referer

[Spring官方文档-英文](https://spring.io/)

[Spring框架教程（非常详细）](http://c.biancheng.net/spring/)

[Spring 中文网](https://springref.com/)

```
1、spring的两大特性：
DI依赖注入、aop动态代理

2、cglib、jdk动态代理

3、Java Agent  探针 vs AOP的区别
AOP也可以依赖CGlib，底层字节码的织入和Java Agent 探针字节码增强技术的区别
Java Agent可以理解为是JVM级别的AOP
Spring在实现AOP的时候是在bean放入容器前生成的代理类

Spring AOP 属于运行时增强，主要具有如下特点：
基于动态代理来实现，默认如果使用接口的，用 JDK 提供的动态代理实现，如果是方法则使用 CGLIB 实现
Spring AOP 需要依赖 IOC 容器来管理，并且只能作用于 Spring 容器，使用纯 Java 代码实现
在性能上，由于 Spring AOP 是基于动态代理来实现的，在容器启动时需要生成代理实例，在方法调用上也会增加栈的深度，使得 Spring AOP 的性能不如 AspectJ 的那么好。
Spring AOP 致力于解决企业级开发中最普遍的 AOP(方法织入)


4、spring bean注入方式？？作用域？？默认的哪个？？

```


