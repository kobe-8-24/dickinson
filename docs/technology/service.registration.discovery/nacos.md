## Referer

[nacos官网](https://nacos.io/zh-cn/)

```markdown
1、nacos的基本原理？？和 zk、eureka区别？？

- 服务注册发现
- 配置
- 原理

nacos服务注册、发现discovery、配置config、订阅、原理
```

#### 基于Nacos配置中心实现Gateway规则热更新

```java
// 配置Nacos连接信息
nacos:
  server-addr: ${NACOS_SERVER_ADDR}
  namespace: ${NACOS_NAMESPACE}
  
// 在代码中读取Nacos配置
@Configuration
public class GatewayConfig {
    @Value("${gateway.config}")
    private String gatewayConfig;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("custom", r -> r.path("/custom")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://CUSTOM-SERVICE"))
            .build();
    }
}

// 监听Nacos配置变更
@Service
@NacosPropertySource(dataId = "gateway-config", autoRefreshed = true)
public class NacosConfigListener {

    @NacosValue(value = "${gateway.config}", autoRefreshed = true)
    private String gatewayConfig;

    @Autowired
    private GatewayConfig gatewayConfigUpdater;

    @NacosConfigListener(dataId = "gateway-config")
    public void onConfigChanged(String newConfig) {
        // 更新Gateway规则
        gatewayConfigUpdater.updateRules(newConfig);
    }
}
```

#### Nacos一致性协议:Distro与Raft协议

https://blog.csdn.net/yangshangwei/article/details/131101178

```markdown
Nacos是一个开源的分布式服务发现和配置管理平台，支持多种一致性协议来确保集群中各个节点之间的数据一致性。其中，Nacos内部实现了两种一致性协议，分别是Distro协议和Raft协议。

1. **Distro协议：**
Distro（Distribution）是Nacos内部实现的一种高性能的数据分发协议，用于在集群中同步配置和注册中心的数据。Distro协议基于类似于P2P（点对点）的架构，每个节点可以是数据的提供者和消费者，从而有效地减轻了集群的压力。

Distro协议的特点：
- 支持多种数据同步模式，包括全量同步和增量同步，以及按需同步。
- 提供了数据版本管理，确保数据的一致性和可靠性。
- 采用了推拉结合的方式，同时支持订阅和拉取模式，以适应不同场景的需求。
- 具备较高的性能和扩展性，适用于大规模集群。

2. **Raft协议：**
Raft是一种共识算法，用于维护分布式系统中数据的一致性和可靠性。Nacos的Raft实现主要用于选举集群中的Leader节点，Leader节点负责处理客户端的请求，确保数据的一致性。

Raft协议的特点：
- 提供了强一致性的保证，确保集群中的数据一致性。
- 支持Leader选举，当Leader节点失效时，可以自动选择新的Leader。
- 具备容错性，即使在部分节点故障的情况下仍能保持一致性。
- 适用于分布式状态机的实现，如Nacos中的配置和注册中心。

综合来说，Nacos在不同的场景中采用了不同的一致性协议，Distro协议用于数据分发和同步，而Raft协议用于维护集群中的一致性和Leader选举。这些协议的使用使得Nacos能够保证在分布式环境下提供高可用性和数据一致性的服务。
```

