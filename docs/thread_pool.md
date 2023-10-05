## Referer

[Java 线程池详解](https://javaguide.cn/java/concurrent/java-thread-pool-summary.html#_2-3-executor-%E6%A1%86%E6%9E%B6%E7%9A%84%E4%BD%BF%E7%94%A8%E7%A4%BA%E6%84%8F%E5%9B%BE)

[Java线程池实现原理及其在美团业务中的实践](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)

[Java 线程池最佳实践](https://javaguide.cn/java/concurrent/java-thread-pool-best-practices.html#%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9F%A5%E8%AF%86%E5%9B%9E%E9%A1%BE)

### 线程 & 线程池

1、创建线程的方式

2、多线程事务

3、go java 线程对比

4、合理配置线程池核心线程数（IO密集型和CPU密集型）

CPU密集型：**CPU核数+1个线程的线程池**

IO密集型：IO包括：数据库交互，文件上传下载，网络传输等

​				参考公式：**CPU核数 /（1 - 阻系数）**
​									比如8核CPU：8/(1 - 0．9)=80个线程数
​									阻塞系数在0.8~0.9之间

​				**CPU核数\*2**

[合理配置线程池核心线程数（IO密集型和CPU密集型）_io密集型和cpu密集型 线程数_beyond_champion的博客-CSDN博客](https://blog.csdn.net/zhuimeng_by/article/details/107891268)

> CPU密集型已经在不断地计算了，再开更多的线程也是得等CPU空闲出来，没意义，不会算得更快了；
> 而IO密集型是大量的再等待，CPU是空闲的，开启更多的线程可以让并发的请求更多，从而降低对外接口（方法）耗时，提升吞吐量。
>
> [(20条消息) 如何区分IO密集型、CPU密集型任务？_音视频开发进阶的博客-CSDN博客](https://blog.csdn.net/zhying719/article/details/109685197)
>
> [面试官：兄弟怎么理解 CPU密集型 和 I/O密集型？-云社区-华为云 (huaweicloud.com)](https://bbs.huaweicloud.com/blogs/344722)
