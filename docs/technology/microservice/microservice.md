## Referer

[微服务定义](https://www.ruanyifeng.com/blog/2022/04/microservice.html)


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

