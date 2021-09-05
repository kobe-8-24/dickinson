## concurrent

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

Sync、FairSync、NonfairSync

锁升级 -> 偏向锁、轻量级锁、重量级锁

锁的自适应自旋

共享锁、排他锁
```

### volatile

```markdown

```

### Synchronized

```markdown
- java关键字
- 内置锁
- 重入锁
- 实现原理：monitor、mutex、exit 
```

### Semaphore

```markdown
- 信号量 - 线程同时能同时执行的最大数量

```

### CountDownLatch

```markdown
- 当前到达后正在等待的线程数
```

### CyclicBarrier

```markdown
- 
```

### StampedLock

```markdown

```

### ReentrantLock

```markdown
- 基于代码层面做锁控制, 作用域更小
- 默认非公平锁，可设置为公平锁
- 可重入锁，持有锁的时候+1，释放锁的时候-1
```

### Atomic

```markdown
- 原子操作
- 如何解决ABA问题
```

### Unsafe

```markdown
```

### CAS

```markdown
- 乐观锁
- 缺点：
      1、会出现ABA问题，引入version来解决这个问题
      2、自旋占用CPU, 消耗资源
```





