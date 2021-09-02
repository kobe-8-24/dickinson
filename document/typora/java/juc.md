## concurrent

```markdown
unsafe类详解

atomic相关以及如何规避ABA问题

native方法

Synchronized、CyclicBarrier、StampedLock、

阻塞队列 ArrayBlockingQueue 和 LinkedBlockingQueue

volatile

重入锁

锁升级 -> 偏向锁、轻量级锁、重量级锁

cyclebarrier

Sync、FairSync、NonfairSync

ArrayBlockingQueue 和 LinkedBlockingQueue

锁的自适应自旋



```

​	

### AbstractQueuedSynchronizer

``` markdown
1、抽象队列同步器

2、几个关键词

state状态：
这是AbstractQueuedSynchronizer里一个万能的属性，具体是什么含义，全看你的使用方式，
比如在CountDownLatch里，它代表了当前到达后正在等待的线程数，在Semaphore里，它则表示当前进去后正在运行的线程数

cas：
AQS里大量用了CAS（Compare and Swap）操作来修改state的值

LockSupport：
AQS里用了大量的LockSupport的park()和unpark()方法，来挂起和唤醒线程

同步队列和条件队列：
sync queue and condition queue，弄清楚这两个队列的关系，AQS也就弄懂大半

公平和非公平：
有线程竞争，就有公平和非公平的问题。锁释放的时候，刚好有个线程过来获取锁，
但这时候线程等待队列里也有线程在等待，到底是给排队时间最久的线程呢(公平)，还是允许新来的线程参与竞争（不公平）？




```













### Semaphore

```markdown
1、信号量 - 线程同时能同时执行的最大数量

2、
```





### CountDownLatch

```markdown
1、当前到达后正在等待的线程数
```





### ReentrantLock

```markdown
1、
```







