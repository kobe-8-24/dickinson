# Referer

[k8s官网](http://docs.kubernetes.org.cn/)

## 1、核心概念

### 1.1 客户端

#### 1.1.1 kubectl

```shell
# 作用
命令行工具，操作K8s REST API
```

#### 1.1.2 dashboard

```shell
# 作用
可视化界面UI，作用类似kubectl
```

### 1.2 Master节点（控制面板组件）

#### 1.2.1 kube_apiserver

```shell
# 作用
接口服务，基于rest风格开放k8s接口的服务
```

#### 1.2.2 kube-controller-manager

```shell
# 作用
		控制器管理器，管理各个类型的控制器。针对k8s中的各种资源进行管理。
# 分类
		节点控制器（Node Controller）：负责在节点出现故障时进行通知和响应。
		任务控制器（Job Controller）：监测代表一次性任务的Job对象，然后创建Pods来运行这些任务直至完成！
		端点分片控制器（EndpointSlice controller）：填充端点分片对象以提供 service 和 pod 之间的连接。
		账号服务控制器（ServiceAccount controller）：为新的命名空间创建默认的服务账号（serviceAccount）。
```

#### 1.2.3 cloud-controller-manager

```shell
# 作用
		云控制器管理器：第三方云平台提供的控制器API对接管理功能。
```
#### 1.2.4 kube_scheduler

```shell
# 作用
		调度器：负责将pod基于一定算法，将其调用到更合适的节点（服务器）上。
```

#### 1.2.5 etcd

```shell
# 作用
		可以理解为k8s的数据库，键值类型k-v存储的分布式数据库。提供了基于raft协议实现高可用。
		老版本：基于内存
		新版本：持久化存储
```

### 1.3 Node节点

#### 1.3.1 kubelet

```shell
# 作用
		负责Pod的生命周期、存储、网络
```

#### 1.3.2 kube-proxy

```shell
# 作用
		网络代理，负责service的服务发现负载均衡（4层负载 iptables）
```

#### 1.3.3 pod

```shell
# 作用
		k8s运行的最小单位，一个pod可以有1个或者多个容器
```

#### 1.3.3 container-runtime

```shell
# 作用
		容器运行时环境：docker、containerd、CRI-O
```

### 1.4 其它组件

#### 1.4.1 kube-dns

```shell
# 作用
		负责为整个集群提供DNS解析服务。
```

#### 1.4.2 ingress-controller

![84d576d56ff5d4fa435985f7f0b03ad](C:\Users\57620\AppData\Local\Temp\WeChat Files\84d576d56ff5d4fa435985f7f0b03ad.jpg)

```shell
# 作用
		为服务提供外网入口。
		
1、k8s可以一个namespace一个traefik
2、一套k8s集群可以部署多个控制器嘛
3、ingress如何找到控制器
```

#### 1.4.3 prometheus

```shell
# 作用
		提供资源监控
```

#### 1.4.4 dashboard

```shell
# 作用
		提供gui页面
```

#### 1.4.5 federation

```shell
# 作用
		提供跨可用区的集群
```

#### 1.4.6 fluentd-elastisearch

```shell
# 作用
		提供集群日志采集、存储与查询
```

## 2、 服务分类

### 2.1 分类

```shell
# 有状态服务：
			会对本地环境产生依赖，例如会存储数据到本地磁盘（mysql、redis）
# 无状态服务：
			不会对本地环境产生任何依赖，例如不会存储数据到本地磁盘（nginx）
```

## 3、 资源对象

### 3.1 对象规约和状态

```
对象规划：Spec
状态：Status
```

### 3.2 资源对象

#### 3.2.1 解释

```shell
# 解释
资源 - 类
对象 - 基于类创建的对象
```

#### 3.2.2 资源的分类

##### 3.2.2.1 元空间

```
HPA

PodTemplate

LimitRange
```

##### 3.2.2.2 集群

```
NameSpace

Node

ClusterRole

ClusterRoleBinding
```

##### 3.2.2.3 命名空间

###### 3.2.2.3.1 工作负载型

```properties
1、定义
主要就是指Pod

2、分类
- 副本 	replicas
- 控制器  
  1）适用无状态服务：RC、RS、Deployment
  2）适用有状态服务：StatefulSet
  3）守护进程：DaemonSet
  4）任务/定时任务：Job、CronJob
```

###### 3.2.2.3.2 服务发现

```
service 
		- 暴露方式： NodePort、LoadBlancer、ClusterIP、ExternalName 
		- Referer： https://blog.csdn.net/qq_21187515/article/details/112363072
ingress
```

###### 3.2.2.3.3 配置与存储

```
volume
csi
```

###### 3.2.2.3.4 特殊类型存储

```
configMap
Secret 
```

## 4、专项

### 4.1 Flannel

### 4.2 Deployment

```markdown
1、特点(无状态服务)
	- 创建replica set / pod
	- 滚动 升级/回滚
	- 平滑 扩容/缩容
	- 暂定 与 恢复 deployment
2、	

```

### 4.3 StatefulSet

```markdown
1、特点(有状态服务)
	- 稳定的持久化存储
	- 稳定的网络标识
	- 有序部署、有序扩展
	- 有序收缩、有序删除
2、组成
	- Headless Service：对于有状态服务的DNS管理
	- volumeClaimTemplate：用于创建持久化卷的模板
3、注意事项
    - k8s 1.5版本以上可支持
    - 所有pod的volume必须使用PV或者是管理员事先创建好
    - 为了保证数据安全，删除StatefulSet时不会删除volume
    - statefulset需要一个headless service来定义Dns domain，需要在statefulset之前创建好
4、
```

### 4.4 DaemonSet

```markdown
1、作用
	- 保证在每个node上都运行一个容器副本，常用来部署一些集群的日志、监控或者其它系统管理应用，典型的应用包括：
	日志收集，比如fluentd、logstash等
	系统监控，比如 prometheus node exporter、collectd、New Relic agent、Ganglia gmond等
	系统程序，比如kube-proxy、kube-dns、glusterd、ceph等
```

### 4.5 Service

![image-20230725112326100](C:\Users\57620\AppData\Roaming\Typora\typora-user-images\image-20230725112326100.png)![40a5a705ce45f8b243c63c88d86ae12](C:\Users\57620\AppData\Local\Temp\WeChat Files\40a5a705ce45f8b243c63c88d86ae12.jpg)![e070c977043717d681e1daf53e71d80](C:\Users\57620\AppData\Local\Temp\WeChat Files\e070c977043717d681e1daf53e71d80.jpg)![7606835c031204c88c136be26490ef5](C:\Users\57620\AppData\Local\Temp\WeChat Files\7606835c031204c88c136be26490ef5.jpg)

![efa23bbff06ccd9ec3cd72895498800](C:\Users\57620\AppData\Local\Temp\WeChat Files\efa23bbff06ccd9ec3cd72895498800.png)![image-20230725152856911](C:\Users\57620\AppData\Roaming\Typora\typora-user-images\image-20230725152856911.png)

```markdown
1、功能
	- pod内部以及pod与pod之间通信，解决东西流量
	- 实现k8s集群内部网络调用、负载均衡（四层负载）
2、解释

Annotations：指定SLB
loadbalancer ingress：172.37.43.203（总部开发环境中台网关地址）
	port：30012（对外暴露的地址），运营平台同步鉴权 访问 http://172.37.43.203:30012
	targetPort：可以转发的目标端口
NodePort：k8s集群暴露的端口，对外可提供访问 http://k8s集群ip:NodePort 提供访问
Endpoints：service转到endpoints，再经过iptables路由到kube-porxy到pause容器
```

### 4.6 Ingress

```markdown
1、功能
	- 实现将k8s内部服务暴露给外网访问的服务，解决南北流量
	
2、实现方案
	- ingress-nginx:反向代理、负载均衡(七层)
```

### 4.7 Pod

```markdown
1、组成
	- 容器
	- pause：用于 pod内部容器 共享 网络、文件系统
```

### 4.8 Job/CronJob

```markdown
1、作用
	- 一次性任务，运行完成后Pod销毁，不再重新启动新容器
	- cronjob 增加 定时功能 
```

### 4.9 Volume	

```markdown
1、概念
	- 存储卷/数据卷，共享pod中容器使用的数据，用来放持久化的数据，比如数据库数据
```

### 4.10 ConfigMap

```markdown
1、特点(有状态服务)
	- 
```

### 4.11 Secret

```markdown
1、特点(有状态服务)
	- 
```



