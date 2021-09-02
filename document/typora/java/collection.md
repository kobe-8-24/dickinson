## 集合

```shell
1、集合可以看作是一种容器，用来存储对象信息。所有集合类都位于java.util包下，但支持多线程的集合类位于java.util.concurrent包下。

2、数组、集合区别
　　1）数组长度不可变化而且无法保存具有映射关系的数据；集合类用于保存数量不确定的数据，以及保存具有映射关系的数据。
　　2）数组元素既可以是基本类型的值，也可以是对象；集合只能保存对象。
　　
3、分类
   Java集合类主要由两个根接口Collection和Map派生出来的
   Collection派生出了三个子接口：List、Set、Queue（Java5新增的队列）
   因此Java集合大致也可分成List、Set、Queue、Map四种接口体系
   
   其中
   List代表了有序可重复集合，可直接根据元素的索引来访问
   Set代表无序不可重复集合，只能根据元素本身来访问
   Queue是队列集合
   Map代表的是存储key-value对的集合，可根据元素的key来访问value
   
   注意：Map不是Collection的子接口
   
   Set、HashSet、SortedSet、Treeset、EnumSet、linkedHashSet、copyOnWriteArraySet
   List、vector、stack、ArrayList、LinkedList、copyOnWriteArrayList
   queue、deque、priorityQueue、linkedList、arrayDeque、BlockingQueue、ConcurrentLinkedQueue
   Map、HashMap、SortedMap、TreeMap、EnumMap、hashtable、weakhashmap、identityHashMap、properties、linkedhashmap、concurrentHashMap 
```

### HashSet

```markdown
```

