## k8s 疑问点

![5ef4746c2a091e97e13a69c9891fa0e](C:\Users\57620\AppData\Local\Temp\WeChat Files\5ef4746c2a091e97e13a69c9891fa0e.png)

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

![03f35cd2fd546a33f2dbe1a539cd060](C:\Users\57620\AppData\Local\Temp\WeChat Files\03f35cd2fd546a33f2dbe1a539cd060.jpg)

![87a95f72dfd6734eb83ae9e81495f15](C:\Users\57620\AppData\Local\Temp\WeChat Files\87a95f72dfd6734eb83ae9e81495f15.jpg)![3861c146975fe5817ef131f70b1a0fe](C:\Users\57620\AppData\Local\Temp\WeChat Files\3861c146975fe5817ef131f70b1a0fe.jpg)