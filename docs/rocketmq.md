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

**RocketMQ**

​                ● 消费模式

​					- 集群模式

​					- 广播模式

​                ● 消费端订阅关系一致（topic、tag数量和顺序）

​					- 参考链接：https://juejin.cn/post/6844903902219862023

​                ● 阿里云实际部署的方式和位置

​                ● rocketmq如何增加机器加快消费？

​               	 ■ consumer 20秒 定时任务 拉取 message queue 计算 和 consumer的消费占比来分配消息

​               	 ■ 环形平均分配

​              	  ■ https://blog.csdn.net/eclipse9527/article/details/122186172

​              	  ■ https://juejin.cn/post/6982127679285755934

​                ● 阿里云rocketmqons tcp协议支持批量消费，http协议暂时不支持批量消费！！！

​                ● Rocketmq 消费队列 有几个不消费？？？

​              	  ■ 订阅关系是否一致

​               	 ■ Broker上面的读写队列数量是否一致

​              	  ■ 横向扩容机器

**Rocketmqons**批量消费

​                ● **Rocketmqons批量消费修改验证 - 丁孙朝**

​               	 ■ **参考：https://help.aliyun.com/document_detail/191213.html**

​               	 ■ **注意：** 确认一下 设置TCP接入域名，进入云消息队列 RocketMQ 版控制台实例详情页面的接入点区域查看。

consumerProperties.setProperty(PropertyKeyConst.NAMESRV_ADDR, MqConfig.NAMESRV_ADDR);



http协议域名：实例[ID.mq-http.cn-hangzhou-A.aliyuncs.com](http://ID.mq-http.cn-hangzhou-A.aliyuncs.com)



tcp协议接入域名：[MQ_INST_1234567890_ABCDEF.mq-amqp.cn-hangzhou-A.aliyuncs.com:5672](http://mq_inst_1234567890_abcdef.mq-amqp.cn-hangzhou-a.aliyuncs.com:5672)



http协议、tcp协议区别以及域名



**缩容消息不丢失**

一个topic在每个broker上创建了128个队列，现在需要将队列缩容到64个，怎么做才能100%不会丢失消息，并且无需重启应用程序？

最佳实践：

​		先缩容写队列128->64，写队列由0 1 2 ......127缩至 0 1 2 ........63。等到64 65 66......127中的消息全部消费完后，

​		再缩容读队列128->64(同时缩容写队列和读队列可能会导致部分消息未被消费)
