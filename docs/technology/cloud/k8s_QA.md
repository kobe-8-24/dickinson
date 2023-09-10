## k8s 疑问点

![](https://www.guoshaohe.com/wp-content/uploads/2021/05/k8s%E6%80%BB%E4%BD%93%E6%9E%B6%E6%9E%84.png)

1、k8s可以一个namespace一个traefik？？还是整个k8s集群一个traefik？？

2、一个k8s集群可以部署多套控制层吗？？就是一个命名空间

3、ingress如何找到控制器？？比如 ingress nginx 或者 traefik

4、ingress和traefik如何关联

5、traefik和elb如何关联

6、traefik编排文件解读

7、ingress可以跨集群访问应用ui嘛？[你我本非一个世界，为何强求从我的世界经过 | 不同 k8s 集群间服务如何相互访问？ - 掘金 (juejin.cn)](https://juejin.cn/post/7205562168359895095)

8、ingress、service、endpoint、pod

ingress

ingress-service-endpoint-pod

nginx

9、K8S动态扩缩容之HPA

```yaml
apiVersion: autoscaling/v2beta2
kind: HorizontalPodAutoscaler
metadata:
  name: my-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-app-deployment
  minReplicas: 1
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 50
```

10、本地开发代码接入远程K8S集群联调

```markdown
ktconnect 即 kubernetes toolkit
```

11、容器“构造方法”K8S的初始化容器是做什么的

~~~yaml
在Kubernetes（K8S）中，初始化容器（Init Container）是一种特殊类型的容器，它被用于在主要应用容器之前执行一些初始化操作。
这些操作可以确保主要应用容器在启动之前具备所需的环境、数据或其他资源。

初始化容器有以下特点：

1. **执行顺序：** 初始化容器会在主要应用容器启动之前执行。只有当所有初始化容器都成功完成后，K8S才会启动主要应用容器。

2. **独立性：** 初始化容器是独立运行的，它们与主要应用容器隔离，不共享存储卷和网络等资源。

3. **多个初始化容器：** 一个Pod可以配置多个初始化容器，它们将按照定义的顺序依次执行。

4. **错误处理：** 如果任何一个初始化容器失败（即退出状态非零），K8S将尝试重启整个Pod，直到初始化容器成功为止。

初始化容器可以执行以下任务：

- **数据准备：** 例如，初始化容器可以从远程存储或其他应用中获取数据，然后将数据复制到主要应用容器需要的位置。

- **配置加载：** 初始化容器可以从配置中心（如ConfigMap或Secret）中加载配置文件，并将其提供给主要应用容器。

- **数据库初始化：** 初始化容器可以在主要应用容器启动之前创建或初始化数据库，确保数据库在应用启动时处于正确的状态。

- **环境检查：** 初始化容器可以执行一些健康检查或预处理操作，确保主要应用容器能够正常启动。

以下是一个示例Pod定义，其中包含了一个初始化容器和一个主要应用容器：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: init-container-example
spec:
  containers:
  - name: init-container
    image: busybox
    command: ['sh', '-c', 'echo Init Container']
  initContainers:
  - name: init-wait
    image: busybox
    command: ['sh', '-c', 'sleep 10']
  - name: init-config
    image: busybox
    command: ['sh', '-c', 'echo Init Config']
```

在这个示例中，`init-wait`初始化容器会等待10秒钟，然后才会启动`init-config`初始化容器。只有在所有初始化容器成功完成后，主要应用容器才会启动。

通过使用初始化容器，你可以确保你的主要应用容器在启动之前具备必要的条件和环境，从而提高应用的稳定性和可靠性。
~~~

12、真·一键发布 Kubernetes Operator

13、k8s 跨集群或者跨nm访问？？k8s如何配置跨域访问

```markdown
Kubernetes 中的 Service 和 Endpoint 之间的关系通过标签选择器来建立，
Service 通过选择器找到后端的 Pod，Endpoint 则自动生成与 Service 关联的后端 Pod 的网络地址和端口信息。
这使得在 Kubernetes 集群中管理和扩展应用程序的网络服务变得更加灵活和便捷
```

![](./../photo/7e6600224e7d7fa50135d918dce85ca.jpg)

![](./../photo/c076351df6f8cf67c9b5bc601b5367a.jpg)

![f41e5361d4c1294b0b45a3b7de57200](./../photo/f41e5361d4c1294b0b45a3b7de57200.jpg)
