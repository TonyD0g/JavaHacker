## 1.前言

1.必读

[JNDI学习](https://drun1baby.github.io/2022/07/28/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BJNDI%E5%AD%A6%E4%B9%A0/)

[浅析JNDI注入](https://www.mi1k7ea.com/2019/09/15/%E6%B5%85%E6%9E%90JNDI%E6%B3%A8%E5%85%A5/)

---

## 介绍JNDI

JNDI：通俗解释就是一个名字对应一个 Java 对象

**Java Naming**

命名服务是一种键值对的绑定，使应用程序**可以通过键检索值**。

**Java Directory**

目录服务是命名服务的自然扩展。这两者之间的区别在于目录服务中对象可以有属性，而命名服务中对象没有属性。因此，**在目录服务中可以根据属性搜索对象**。

JNDI允许你访问文件系统中的文件，定位远程RMI注册的对象，访问如LDAP这样的目录服务，定位网络上的EJB组件。

**ObjectFactory**

Object Factory用于**将Naming Service**（如RMI/LDAP）**中存储的数据转换为Java中可表达的数据**，如Java中的对象或Java中的基本数据类型。每一个Service Provider可能配有多个Object Factory。

**JNDI注入的问题就是处在可远程下载自定义的ObjectFactory类上**。



JNDI支持的服务：

- LDAP （ Lightweight Directory Access Protocol ） 轻量级目录访问协
- CORBA （ Common Object Request Broker Architecture ） 公共对象请求代理结构服务（不知道干嘛的，用到再说）
- RMI （ Java Remote Method Invocation ） JAVA 远程远程方法调用注册
- DNS （ Domain Name Service ）域名服务

---

JNDI 既然支持4种服务，所以实际上使用时我们申请的url地址**需要带协议**：

(比如这里就是带了rmi协议，表示是rmi协议的)

```java
initialContext.lookup("rmi://localhost:1099/remoteObj");
```

，而回看之前RMI篇时，`registry.lookup`是直接申请的而不写协议的：

```java
registry.lookup("remoteObj");
```

【详情代码查看：JNDIRMIServer.java，JNDIRMIClient.java】，可以和【RMI基础/RMIClient.java】**进行对比**加深了解JNDI。

**JNDI 调用 RMI 服务的时候，虽然 API 是 JNDI 的，但是还是去调用了原生的 RMI 服务。所以RMI如果含有漏洞，JDNI也能利用**

---

## JNDI 注入

**它与所调用服务（LDAP,DNS,CORBA,RMI）无关,都存在这个漏洞**

本质：

和RMI 攻击中的"利用codebase执行任意代码"一样，都是先从本地加载Factory 类，找不到就从远程codebase加载。

**一.前言**

**1.各种版本的防御：**

- JDK  6u45、7u21之后：`java.rmi.server.useCodebaseOnly`的默认值被设置为true。当该值为true时，将禁用自动加载远程类文件，仅从CLASSPATH和当前`JVM`的`java.rmi.server.codebase`指定路径加载类文件。使用这个属性来防止客户端`JVM`从其他Codebase地址上动态加载类，增加了RMI ClassLoader的安全性。
- JDK  6u141、7u131、8u121之后：增加了`com.sun.jndi.rmi.object.trustURLCodebase`选项，默认为false，禁止RMI和CORBA协议使用远程codebase的选项，因此RMI和CORBA在以上的JDK版本上已经无法触发该漏洞，但依然可以通过指定URI为LDAP协议来进行JNDI注入攻击。
- JDK 6u211、7u201、8u191之后：增加了`com.sun.jndi.ldap.object.trustURLCodebase`选项，默认为false，禁止LDAP协议使用远程codebase的选项，把LDAP协议的攻击途径也给禁了。

**因此得知JDK版本对于JNDI注入很重要**

**2.Reference类**

Reference类的构造方法：

- className：远程加载时所使用的类名；
- classFactory：加载的class中需要实例化类的名称；
- classFactoryLocation：远程加载类的地址，提供classes数据的地址**可以是file/ftp/http等协议**；

简单来说就是

**如果你bind时使用了Reference类，那么在你发起Naming或Directory请求时，会执行所对应的Reference类。**

---

#### RMI+Reference：

**就是将恶意的Reference类绑定在RMI注册表中，其中恶意Reference指向远程恶意class文件，当JNDI客户端的lookup()函数参数外部可控或Reference类构造方法的classFactoryLocation参数外部可控时，会使JNDI客户端访问RMI注册表中绑定的恶意Reference类，从而加载远程服务器上的恶意class文件在客户端本地执行，最终实现JNDI注入攻击导致远程代码执行**

​																									——[浅析JNDI注入](https://www.mi1k7ea.com/2019/09/15/%E6%B5%85%E6%9E%90JNDI%E6%B3%A8%E5%85%A5/)



【详情代码查看：RmiReference/示例代码/】

注意：将RMIService.java和JNDIClient.java放在同一目录下，将**EvilObject.class**放在tomcat下并启用tomcat。让RMIService.java加载远程codebase



- JNDI客户端的lookup()函数可控

​		直接传入恶意的URI即可，JNDI客户端会向我们的恶意RMI Registry申请恶意类

​		【详情代码查看：RmiReference/Lookup可控/】

​		注意：将**EvilClassFactory.class**类放在tomcat下并启用tomcat。



**InitialContext.lookup()的上级调用，没准以后有用**：

RMI：

```java
org.springframework.transaction.jta.JtaTransactionManager.readObject()
com.sun.rowset.JdbcRowSetImpl.execute()
javax.management.remote.rmi.RMIConnector.connect()
org.hibernate.jmx.StatisticsService.setSessionFactoryJNDIName(String sfJNDIName)
```

LDAP：

```java
InitialDirContext.lookup()
Spring's LdapTemplate.lookup()
LdapTemplate.lookupContext()
```

- classFactoryLocation参数可控