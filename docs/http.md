## Referer

[http官网](https://developer.mozilla.org/zh-CN/docs/web/http/overview)

http 1.0/2.0/3.0

连接状态 established time_wait

HttpClient在多线程环境下踩坑总结

七层osi、tcp/ip、udp、grpc

DMZ区

ping、telnet

http连接池作用

http长连接短连接

服务器httpClient连接池耗尽问题排查与解决

抖音查看mic架构余胜军 -- http连接耗尽、负载均衡停机重启后连接耗尽/kafka rebalance机制

- http 1.1
		- 一个TCP连接
- http 2.0
		- 多路复用，队头阻塞，线头阻塞
		- 基于TCP
- http 3.0
		- 基于UDP



**HTTP**

HTTP1.1 		

**1**）浏览器请求**url ->**解析域名 **->** 建立**HTTP**连接 **->** 服务器处理文件 **->** 返回数据 **->** 浏览器解析、渲染文件

HTTP2.0 基于TCP 以及 多路复用

HTTP3.0 基于UDP

https://juejin.cn/post/7006508170206003230

https://juejin.cn/post/7069997103824502791