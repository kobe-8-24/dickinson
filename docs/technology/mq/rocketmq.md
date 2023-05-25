## Referer

[rocketmq官网](https://rocketmq.apache.org/)

> RocketMQ同一个消费组内的消费者订阅量不同tag，会有问题吗？？

​		https://www.51cto.com/article/695701.html

> 【rocketmq客户端】订阅关系一致
>
> https://www.jianshu.com/p/abbc80706ded
>
> https://zhuanlan.zhihu.com/p/370059224

## rocketmq

- 消息队列

```markdown
	- 死信队列
	- 延迟队列
```

- 消息类型

```markdown
	- 事务消息
	- 顺序消息
	- 延迟消息
```

- topic主题、消息队列queue、消费者consumer、消费组consume group 之间关系

```markdown
	- 一个消费组 可以 包含 多个消费者
	- 一个主题 可以 包含 多个 消息队列
	
	- 一个消息队列 同一时间 只允许被 一个消费者 消费
	- 一个消费者 可以消费 多个消费队列
	
	- 一个消费组 对应 一个主题topic，可以订阅 同一个主题下的多个消息队列
	
	- 一个消费组可以订阅一个主题下的多个消息队列，但每个消费组只能与一个主题相关联。
	  如果需要订阅多个主题，可以创建多个消费组，每个消费组与一个主题相关联。
	  这样可以实现多主题的并发消费和处理。
	  
	- RocketMQ 要求同一个消费组内的消费者必须订阅关系一致，如果订阅关系不一致，会出现消息丢失的问题
	- 订阅关系一致是指同一个消费者组下所有消费者所订阅的 Topic、Tag 必须完全一致
```

![img](https://pic1.zhimg.com/80/v2-df9f806108165dcd05cc537c4ad94a04_720w.webp)

![img](https://s6.51cto.com/oss/202112/17/d0cade6c9bf5cb06530b9ae844957452.png)



<img src="https://img-blog.csdnimg.cn/2911b9bca6814f15ac4a8fe2358f15ee.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l1YW5jaGFuZ2xpYW5n,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"  />

<img src="https://img-blog.csdnimg.cn/1f8a917c2f6640f391e1604a409a97cd.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l1YW5jaGFuZ2xpYW5n,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"  />

![在这里插入图片描述](https://img-blog.csdnimg.cn/86023764b9e348d7ae5f881b46a4a221.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l1YW5jaGFuZ2xpYW5n,size_16,color_FFFFFF,t_70)
