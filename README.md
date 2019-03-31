# 天成商城

### 一、项目介绍
电商项目开发学习
只实现了部分功能。

### 二、功能

![1](assets/1.png)
### 三、软件架构
软件架构说明：分布式系统架构
![2](assets/2.png)

### 四、各个系统说明
![3](assets/3.png)

### 五、开发流程
![4](assets/4.png)

### 六、搭建工程
#### 6.1搭建父工程
![5](assets/5.png)

#### 6.2搭建core工程
![6](assets/6.png)

#### 6.3搭建admin工程
![7](assets/7.png)
![8](assets/8.png)

### 七、搭建ssm环境
#### 7.1搭建dao环境
![9](assets/9.png)

#### 7.2配置事物
![10](assets/10.png)
![11](assets/11.png)

#### 7.3配置springmvc.xml
![12](assets/12.png)

#### 7.4配置web.xml

### 八、测试
#### 8.1maven方式
在admin的pom.xml中配置
![13](assets/13.png)

### 九、整合通用mapper
![14](assets/14.png)
![15](assets/15.png)
使用
![16](assets/16.png)

### 十、整合分页助手
![17](assets/17.png)
![18](assets/18.png)

### 十一、配置日期转换器和统一的异常处理
#### １１．１配置日期转换器
![19](assets/19.png)
![20](assets/20.png)

#### １１．２编写统一的异常处理
![21](assets/21.png)
编写自定义异常处理器
![22](assets/22.png)

### 十二、后台静态页面
#### １２．１编写通用的页面跳转
![23](assets/23.png)
#### １２．２配置不拦截静态资源
![24](assets/24.png)
web.xml版本2.5以上，否则会有bug。

### 十三、开发添加商品
#### １３．１分析选择类目的js
![25](assets/25.png)
![26](assets/26.png)
前端需要的数据
![27](assets/27.png)

###　十四、开发后台接口
#### １４．１表设计
树状结构
![28](assets/28.png)
####　１４．２编写mapper
![29](assets/29.png)
![30](assets/30.png)

#### １４．３编写service
![31](assets/31.png)
#### １４．４编写comtroller
![32](assets/32.png)

### 十五、Nginx
#### １５．１什么是nginx
	Nginx (engine x) 是一个高性能的HTTP和反向代理服务，也是一个IMAP/POP3/SMTP服务。Nginx是由伊戈尔·赛索耶夫为俄罗斯访问量第二的Rambler.ru站点（俄文：Рамблер）开发的，第一个公开版本0.1.0发布于2004年10月4日。
其将源代码以类BSD许可证的形式发布，因它的稳定性、丰富的功能集、示例配置文件和低系统资源的消耗而闻名。2011年6月1日，nginx 1.0.4发布。
	Nginx是一款轻量级的Web 服务器/反向代理服务器及电子邮件（IMAP/POP3）代理服务器，并在一个BSD-like 协议下发行。其特点是占有内存少，并发能力强，事实上nginx的并发能力确实在同类型的网页服务器中表现较好，中国大陆使用nginx网站用户有：百度、京东、新浪、网易、腾讯、淘宝等。

![33](assets/33.png)

#### １５．２nginx应用场景
    1. http服务器。 nginx是一个服务器可以独立提供http服务，可以做静态(静态资源)服务器。
    2. 虚拟主机  可以实现一台服务器虚拟出多个网站。
    3. 反向代理，负载均衡。  当网站的访问量达到一定的程度以后，单台服务器不能满足用户的请求，需要多态服务器的集群，可以使用nginx做反向代理，并且多态服务器可以平均负载，不会因为某台服务器宕机而崩溃。

#### １５．３http服务器
![34](assets/34.png)

#### １５．４配置虚拟主机(端口)
![35](assets/35.png)

#### １５．５配置虚拟主机（域名）
![36](assets/36.png)

#### １５．６反向代理
![37](assets/37.png)

#### １５．７正向代理
![38](assets/38.png)

#### １５．８配置反向代理
![39](assets/39.png)
nginx  -s  reload   重新加载配置文件

#### １５．９负载均衡策略
1、轮询（默认）
每个请求按时间顺序逐一分配到不同的后端服务器，如果后端服务器down掉，能自动剔除。 
upstream backserver { 
server 192.168.0.14; 
server 192.168.0.15; 
} 

2、指定权重
指定轮询几率，weight和访问比率成正比，用于后端服务器性能不均的情况。 
upstream backserver { 
server 192.168.0.14 weight=10; 
server 192.168.0.15 weight=10; 
}

3、IP绑定 ip_hash
每个请求按访问ip的hash结果分配，这样每个访客固定访问一个后端服务器，可以解决session的问题。 
upstream backserver { 
ip_hash; 
server 192.168.0.14:88; 
server 192.168.0.15:80; 
} 

4、fair（第三方）
按后端服务器的响应时间来分配请求，响应时间短的优先分配。 
upstream backserver { 
server server1; 
server server2; 
fair; 
} 

5、url_hash（第三方）
按访问url的hash结果来分配请求，使每个url定向到同一个后端服务器，后端服务器为缓存时比较有效。 
upstream backserver { 
server squid1:3128; 
server squid2:3128; 
hash $request_uri; 
hash_method crc32; 
} 

### 十六、封装BaseService
#### １６．１编写接口 
![40](assets/40.png)
![41](assets/41.png)
![42](assets/42.png)
![43](assets/43.png)

#### １６．２编写实现类
![44](assets/44.png)
![45](assets/45.png)
![46](assets/46.png)

#### １６．３使用
![47](assets/47.png)

### 十七、新增商品
商品表 sku库存
![48](assets/48.png)
![49](assets/49.png)

#### １７．１编写mapper和service
#### １７．２图片上传js分析
![50](assets/50.png)
![51](assets/51.png)
返回的数据结构
![52](assets/52.png)

####　１７．３编写上传图片的接口
##### １７．３．１配置nginx
![53](assets/53.png)

##### １７．３．２注意配置上传图片的解析器
![54](assets/54.png)

##### １７．３．３准备返回结果的对象
![55](assets/55.png)

### 十八、提交商品
#### １８．１编写接口
![56](assets/56.png)
![57](assets/57.png)

#### １８．２回显的js
![58](assets/58.png)

#### １８．３提交商品
![59](assets/59.png)
![60](assets/60.png)
![61](assets/61.png)
![62](assets/62.png)

### 十九、商品列表和编辑
#### １９．１需要的接口
![63](assets/63.png)
需要的数据格式
![64](assets/64.png)

#### １９．２封装easyUIResult
![65](assets/65.png)

#### １９．３编写接口
![66](assets/66.png)
#### １９．４商品编辑的回显
![67](assets/67.png)
后台编写接口
![68](assets/68.png)
#### １９．５图片的回显
![69](assets/69.png)

#### １９．４商品修改的提交
![70](assets/70.png)
![71](assets/71.png)

### 二十、架构完善
#### ２０．１统一的结果集返回
![72](assets/72.png)
![73](assets/73.png)
![74](assets/74.png)

#### ２０．２改造统一的异常处理
![75](assets/75.png)
![76](assets/76.png)

### 二十一、日志
#### ２０．１日志框架
市面上的日志框架
     JUL,JCL,Jboss-logging ,logback,log4j,log4j2,sl4j....

日志门面
JCL    sl4j   Jboss-logging

日志的实现   
log4j   log4j2  logback
sl4j    使用最多的门面


日志实现

log4j   log4j2  logback

![77](assets/77.png)

门面  slf4j    实现log4j
![78](assets/78.png)


#### ２０．２上传图片添加日志
![79](assets/79.png)
![80](assets/80.png)
![81](assets/81.png)

Spring父子容器

Spring核心  父容器

SpringMvc子容器

安全:

Service不能注入Controller

子容器无法获得父容器加载的properties文件!!!!!!
![82](assets/82.png)
![83](assets/83.png)
![84](assets/84.png)

### 二十一、搭建前台系统
前台系统要求返回json数据的接口给前端（各种客户端）,不能操作操作数据库,需要调用后台系统的接口。

前台系统调用后台

    1. Webservice  xml交互  互联网淘汰
    2. http+json  调用
    3. Dubbo+zookpper  

#### ２１．１配置前台系统nginx
springmvc+spring

配置nginx
![85](assets/85.png)

### 二十二、HttpClient(掌握)
导入jar包
![86](assets/86.png)
#### ２２．１不带参数的get请求
![87](assets/87.png)

#### ２２．２带参数的get请求
![88](assets/88.png)

#### ２２．３post请求
![89](assets/89.png)

#### ２２．４使用工具类改造异常处理
![90](assets/90.png)

#### ２２．５后台系统开放接口
![91](assets/91.png)
![92](assets/92.png)

#### ２２．６前台系统调用
![93](assets/93.png)

Dubbo+zookeeper

学习redis

做缓存

缓存

怎么通知前台系统


消息中间件

### 二十三、Dubbo
    1. WebService 效率不高基于soap协议，互联网项目不推荐使用。
    2. 使用resultful服务  http+json 很多项目应用   如果服务太多，服务间的调用复杂，需要服务治理。
    3. 使用dubbo，使用rpc协议进行远程调用，直接使用socket协议通信，传输效率高，并且可以统计出各个系统之间的调用关系，调用次数。


系统一定是用java写的

#### ２３．１什么是dubbo
随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需一个治理系统确保架构有条不紊的演进。
![94](assets/94.png)
单一应用架构
当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键。
垂直应用架构
当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。
分布式服务架构
当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的分布式服务框架(RPC)是关键。
流动计算架构
当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于提高机器利用率的资源调度和治理中心(SOA)是关键。
![95](assets/95.png)
节点角色说明
节点
角色说明
Provider
暴露服务的服务提供方
Consumer
调用远程服务的服务消费方
Registry
服务注册与发现的注册中心
Monitor
统计服务的调用次数和调用时间的监控中心
Container
服务运行容器

![96](assets/96.png)
节点角色说明
节点
角色说明
Deployer
自动部署服务的本地代理
Repository
仓库用于存储服务应用发布包
Scheduler
调度中心基于访问压力自动增减服务提供者
Admin
统一管理控制台
Registry
服务注册与发现的注册中心
Monitor
统计服务的调用次数和调用时间的监控中心

#### ２３．２注册中心
Zookeeper 是 Apacahe Hadoop 的子项目，是一个树型的目录服务，支持变更推送，适合作为 Dubbo 服务的注册中心，工业强度较高，可用于生产环境，并推荐使用 [1]。
![97](assets/97.png)
动物园管理员

    1. 可以作为集群的管理工具
    2. 可以集中管理配置文件

![98](assets/98.png)
引入dubbo和zookpper的jar包
![99](assets/99.png)

#### ２３．３配置服务的提供方
![100](assets/100.png)

#### ２３．４配置服务的调用方
![101](assets/101.png)

服务调用方需要和服务提供方一模一样的接口，包括包名。

### 二十四、Redis
#### ２４．１Redis介绍
NoSQL，泛指非关系型的数据库。随着互联网web2.0网站的兴起，传统的关系数据库在应付web2.0网站，特别是超大规模和高并发的SNS类型的web2.0纯动态网站已经显得力不从心，暴露了很多难以克服的问题，而非关系型的数据库则由于其本身的特点得到了非常迅速的发展。NoSQL数据库的产生就是为了解决大规模数据集合多重数据种类带来的挑战，尤其是大数据应用难题。
虽然NoSQL的流行与火起来才短短一年的时间，但是不可否认，现在已经开始了第二代运动。尽管早期的堆栈代码只能算是一种实验，然而现在的系统已经更加的成熟、稳定。不过现在也面临着一个严酷的事实：技术越来越成熟——以至于原来很好的NoSQL数据存储不得不进行重写，也有少数人认为这就是所谓的2.0版本。该工具可以为大数据建立快速、可扩展的存储库。

目前主流的nosql数据库

redis,memcahed,mongdb

1.缓存
1.redis
2.memcahed

二者的性能更好？
    1. 从缓存命中率来说memcahed更高，但是redis和memcahed相差不大。
    2. 但是，redis功能更加强大

![102](assets/102.png)

#### ２４．２jedis
![102](assets/102.png)
![103](assets/103.png)
![104](assets/104.png)

#### ２４．３jedis和spring整合
![105](assets/105.png)
![106](assets/106.png)
![107](assets/107.png)
![108](assets/108.png)

#### ２４．４缓存
![109](assets/109.png)

#### ２４．５前台系统暴露删除缓存的接口
![110](assets/110.png)
![111](assets/111.png)

#### ２４．６后台系统更新商品的时候干掉缓存
![112](assets/112.png)

消息中间件
### 二十五、Springdata jpa
#### ２５．１导包
![113](assets/113.png)

#### ２５．２手动配置springDataJpa
![114](assets/114.png)
![115](assets/115.png)

#### ２５．３入门体验
![116](assets/116.png)
![117](assets/117.png)

#### ２５．４Repository  Spring  Data的核心注解
是一个空的接口，标记接口   没有包含任何方法的声明
方案一
![118](assets/118.png)
方案二
![119](assets/119.png)
Repository

CrudRepository   儿子  实现增删改查

PagingAndSortingRepository  孙子  排序和分页

JpaRepository  重孙   更强大

![120](assets/120.png)

#### ２５．５Query注解
![121](assets/121.png)
![122](assets/122.png)

#### ２５．６实体注解的使用
![123](assets/123.png)

### 二十六、单点登录系统
#### ２６．１表结构
![124](assets/124.png)
![125](assets/125.png)

#### ２６．２开发dao
![126](assets/126.png)

### 二十七、注册
#### ２７．１注册service
![127](assets/127.png)
![128](assets/128.png)
#### ２７．２校验
![129](assets/129.png)
![130](assets/130.png)
#### ２７．３controller
![131](assets/131.png)
![132](assets/132.png)
#### ２７．４测试
![133](assets/133.png)
###　二十八、登录
#### ２８．１登录service
![134](assets/134.png)
#### ２８．２登录controller
![135](assets/135.png)
#### ２８．３service
![136](assets/136.png)
![137](assets/137.png)
#### ２８．４controller
![138](assets/138.png)
改进 忽略掉密码
![139](assets/139.png)
#### ２８．５短信
![140](assets/140.png)
![141](assets/141.png)
![142](assets/142.png)

#### ２８．６短信登录
![143](assets/143.png)
![144](assets/144.png)
#### ２８．７短信注册
    1. 发送短信的接口


发送注册短信

     1. 校验没有在我们的平台注册过
             发送短信
                  存储到redis 

    2. 短信注册的接口
              手机号码
              短信验证码

                校验没有在我们的平台注册过
                 
                     校验验证码
                     
                 处理注册
### 二十九、消息中间件
#### ２９．１jms规范
JMS即Java消息服务（Java Message Service）应用程序接口，是一个Java平台中关于面向消息中间件（MOM）的API，用于在两个应用程序之间，或分布式系统中发送消息，进行异步通信。Java消息服务是一个与具体平台无关的API，绝大多数MOM提供商都对JMS提供支持。
JMS是一种与厂商无关的 API，用来访问消息收发系统消息，它类似于JDBC(Java Database Connectivity)。这里，JDBC 是可以用来访问许多不同关系数据库的 API，而 JMS 则提供同样与厂商无关的访问方法，以访问消息收发服务。许多厂商都支持 JMS，包括 IBM 的 MQSeries、BEA的 Weblogic JMS service和 Progress 的 SonicMQ。 JMS 使您能够通过消息收发服务（有时称为消息中介程序或路由器）从一个 JMS 客户机向另一个 JMS客户机发送消息。消息是 JMS 中的一种类型对象，由两部分组成：报头和消息主体。报头由路由信息以及有关该消息的元数据组成。消息主体则携带着应用程序的数据或有效负载。根据有效负载的类型来划分，可以将消息分为几种类型，它们分别携带：简单文本(TextMessage)、可序列化的对象 (ObjectMessage)、属性集合 (MapMessage)、字节流 (BytesMessage)、原始值流 (StreamMessage)，还有无有效负载的消息 (Message)。
Jms提供者   jms接口的一个实现
Jms客户     生产或者发送消息的java应用程序
Jms生产者   创建并发送消息的客户
Jms消费者   接口jms消息的客户

Jms消息     可以在jms客户间传递的数据对象

Jms队列
Jms主题

MQ message queue 消息队列

队列   点对点  一个发送的  一个接收的 qq的私聊
主题   一对多   一个发送的   多个接收的 qq的群发


ActiveMq    RabbitMq  兔子MQ     Kafka     RocketMQ  

#### ２９．２ActiveMq
ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。



点对点    一个生生产者一个消费者
主题     一个生产者多个消费者

![145](assets/145.png)

### 三十、admin发送消息
#### ３０．１步骤
由spring管理
第一步：工厂由apache提供
第二步： (可有可无) 有apache提供的连接池
    第三步：spring单例工厂
第四步:  spring jms templete操作mq
第五步：  注入spring jms templete

#### ３０．２导包
![146](assets/146.png)

#### ３０．３配置
![147](assets/147.png)
![148](assets/148.png)
![149](assets/149.png)

####  ３０．４front接收消息
![150](assets/150.png)
![151](assets/151.png)
![152](assets/152.png)



















































