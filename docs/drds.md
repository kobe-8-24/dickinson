## DRDS

> 企业互联网架构平台 Apsara Aliware的“三驾马车”（EDAS/DRDS/MQ）	
>
> 阿里云 主要产品和服务 ------ https://zhuanlan.zhihu.com/p/347318096

[阿里分布式数据库DRDS - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/126217935)

### 1、介绍

```properties
# 将后端数据库聚合成一个逻辑库，提供数据垂直拆分、水平拆分、读写分离等功能
# 具备功能比较齐全的分布式SQL查询、优化、执行引擎，提供分布式事务管理方案等等
# DRDS已经不是一个简单的数据库代理、分库分表中间件。
```

### 2、功能简介

```properties
DRDS的基础原理是Sharding，即数据分片，是典型的水平扩展分布式数据库模型，和传统单机数据库share anything架构不同，DRDS采用的是share nothing架构。

DRDS主要解决单机数据库容量瓶颈，单机数据库扩展困难，传统数据库使用成本高的问题，是高性价比，低运维成本的分布式数据库。
主要场景是大规模在线数据操作，如 : 高并发实时交易，海量数据存储和访问，数据库云上备份容灾。

DRDS兼容MySQL协议和语法，支持分库分表、平滑扩容、服务升降配、透明读写分离，分布式事务，分布式数据库生命周期管理。

share nothing架构核心思路利用普通的服务器，将单机数据拆分到底层的多个数据库实例上，
通过统一的Proxy集群(DRDS 节点)进行SQL解析优化、路由和结果聚合，对外暴露简单唯一的数据库链接。

DRDS不支持Mysql视图、存储过程、跨库外键和级联删除，不支持自定义数据类型、流程控制类语句、自定义函数。
```
