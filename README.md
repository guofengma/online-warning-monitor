# 监控线上应用异常接入企业微信

#### 依赖
```xml
<dependency>
    <groupId>com.carrying.coder</groupId>
    <artifactId>monitor-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```


#### 配置文件
```properties
qy.weixin.enable=true
qy.weixin.corpID=x
qy.weixin.agentId=x
qy.weixin.secret=x
# 如果不写，默认@all，即应用下所有关联的人
#qy.weixin.touser=
``` 


#### 用法
```java

@Autowired
IMessageSender sender

```