## Referer

[微服务定义](https://www.ruanyifeng.com/blog/2022/04/microservice.html)

<img src="https://img-blog.csdnimg.cn/20201028155034694.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ5MTA3MQ==,size_16,color_FFFFFF,t_70#pic_center" alt="img" style="zoom:200%;" />


```
负载均衡 lb
服务治理
链路监控
流量控制
分布式调用链追踪skywalking
配置中心
```

### 秒杀场景解决方案(极海channel)
```markdown
- 合并队列
- 合并sql
update xx set stock = stock -1 where id = xxxx and stock > 0 -- 行锁
先insert再update
```

- qps、tps、并发数 估算
- 跨系统如何实现幂等
- serverless

