## Referer

[Spring官方文档-英文](https://spring.io/)

[Spring框架教程（非常详细）](http://c.biancheng.net/spring/)

[Spring 中文网](https://springref.com/)

###### 1、spring的两大特性

```
DI依赖注入、aop动态代理
```

###### 2、cglib、jdk动态代理

###### 3、Java Agent  探针 vs AOP的区别

```
AOP也可以依赖CGlib，底层字节码的织入和Java Agent 探针字节码增强技术的区别
Java Agent可以理解为是JVM级别的AOP
Spring在实现AOP的时候是在bean放入容器前生成的代理类

Spring AOP 属于运行时增强，主要具有如下特点：
基于动态代理来实现，默认如果使用接口的，用 JDK 提供的动态代理实现，如果是方法则使用 CGLIB 实现
Spring AOP 需要依赖 IOC 容器来管理，并且只能作用于 Spring 容器，使用纯 Java 代码实现
在性能上，由于 Spring AOP 是基于动态代理来实现的，在容器启动时需要生成代理实例，在方法调用上也会增加栈的深度，使得 Spring AOP 的性能不如 AspectJ 的那么好。
Spring AOP 致力于解决企业级开发中最普遍的 AOP(方法织入)
```

###### 4、spring bean注入方式？？作用域？？默认的哪个？？

```
【作用域】
singleton（默认）、prototype、request、session、application

【默认singleton优缺点】
优点：
  减少新生成实例的消耗
  减少jvm垃圾回收
  单例的获取bean操作除了第一次生成之外其余的都是从缓存里获取的所以很快
缺点：
  由于所有的请求都共享一个bean实例，不能做到线程安全！

【bean注入方式】
1. @ComponentScan包扫描+组件标注注解@Component(@Controller@Service@Repository)
使用场景：自己写的代码，可以方便的加@Controller/@Service/@Repository/@Component

2、@Configuration+@Bean   
使用场景：导入的第三方包里面的组件，将其他jar包中的类（类没有Component等注解），加载到容器中

3、@Import快速给容器中导入一个组件
   1）@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
   2）ImportSelector:返回需要导入的组件的全类名数组；
   3）ImportBeanDefinitionRegistrar:手动注册bean到容器中

【构造函数】
 
@RestController
@RequestMapping("/test")
public class TestController {
//    @Autowired
    private final List<TestService> testServices;
//    @Autowired
    private final List<ChainAsbtract> chains;
 
//    @Autowired
    public TestController(List<TestService> testServices, List<ChainAsbtract> chains) {
        this.testServices = testServices;
        this.chains = chains;
    }
 
    
}

【autowired】

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private List<TestService> testServices;
    @Autowired
    private List<ChainAsbtract> chains;
 
    private ChainAsbtract target;
 
}
```





5、拦截器 clienthttprequestinterceptor/过滤器 filter

6、注解汇总

```
@configuration
@postconstruct 
@component
@bean
@loadbalanced
@RestTemplate
@importSource
@ComponentScan
@Async
@RefreshScope
```









7、webMvcConfiguer

8、clienthttprequestinterceptor/ClientHttpRequestFactory/URI/HttpMethod/ClientHttpRequest/超时时间

9、切面/自定义注解 结合使用

10、spring常用接口

```
applicationcontextaware
```

11、validation框架

12、



