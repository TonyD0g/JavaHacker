## 1.前言

1.必读

[JNDI学习](https://drun1baby.github.io/2022/07/28/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BJNDI%E5%AD%A6%E4%B9%A0/)

[浅析JNDI注入](https://www.mi1k7ea.com/2019/09/15/%E6%B5%85%E6%9E%90JNDI%E6%B3%A8%E5%85%A5/)

Maven:

```md
<dependency>
            <groupId>com.unboundid</groupId>
            <artifactId>unboundid-ldapsdk</artifactId>
            <version>6.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.80</version>
        </dependency>
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.1</version>
        </dependency>
```



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

**既然JNDI支持RMI服务，所以RMI服务的漏洞在JNDI也适用.**

#### **一.前言**

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

RMI Server中的Reference构造函数的classFactoryLocation参数可控。

【详情代码查看：RmiReference/classFactoryLocation可控/】

---

#### LDAP+Reference：

和 RMI+Reference 一样，只不过换成了：`ldap://xxx/xxx`

LDAP服务，[需要导入unboundid-ldapsdk.jar包](https://mvnrepository.com/artifact/com.unboundid/unboundid-ldapsdk)

【详情代码查看：LDAPReference/*】

---

#### CORBA+Reference：

与 RMI+Reference 和 LDAP+Reference 原理类似。

---

#### 结合反序列化漏洞：

漏洞类重写的readObject()方法中**直接或间接调用了可被外部控制的lookup()方法**，导致攻击者可以通过JNDI注入来进行反序列化漏洞的利用。

---

#### 绕过高版本JDK（8u191+）限制

>1.找到一个受害者本地CLASSPATH中的类作为恶意的Reference Factory工厂类，并利用这个本地的Factory类执行命令。
>
>2.利用LDAP直接返回一个恶意的序列化对象，JNDI注入依然会对该对象进行反序列化操作，利用反序列化Gadget完成命令执行。
>
>这两种方式都非常依赖受害者本地CLASSPATH中环境，需要利用受害者本地的Gadget进行攻击

​																																				——[浅析JNDI注入](https://www.mi1k7ea.com/2019/09/15/%E6%B5%85%E6%9E%90JNDI%E6%B3%A8%E5%85%A5/)

**（建议都自己调试下，加深理解，具体步骤可查看师傅们的原文章，不做赘述）**

**1.**找到一个受害者**本地CLASSPATH**中的类作为恶意的Reference Factory工厂类，并利用这个本地的Factory类执行命令。

该恶意 Factory 类必须实现 `javax.naming.spi.ObjectFactory` 接口，实现该接口的 getObjectInstance() 方法。

**[需要依赖 Tomcat 中的 jar 包为：catalina.jar、el-api.jar、jasper-el.jar](https://blog.csdn.net/m0_67401417/article/details/124474374)**

【详情代码查看：Bypass/1/*】



**2.利用LDAP直接返回一个恶意的序列化对象**，JNDI注入依然会对该对象进行反序列化操作，利用反序列化Gadget完成命令执行。

（这里用到了 Fastjson，没学先跳过）

LDAP 服务端支持 Reference,序列化对象。Reference行不通那就用序列化对象。

- Java 对象的 `javaSerializedData` 属性值**不为空**，则客户端的 `obj.decodeObject()` 方法就会对这个字段的内容进行反序列化。
- 服务端 ClassPath 中存在可利用的链子

【详情代码查看：Bypass/2/*】

如果出现 `com.unboundid包` 错误，在`pom.xml`文件中将`com.unboundid`换成别的版本，并刷新maven！

## 如何防御

​																																		——[浅析JNDI注入](https://www.mi1k7ea.com/2019/09/15/%E6%B5%85%E6%9E%90JNDI%E6%B3%A8%E5%85%A5/)

- 使用最新的JDK版本；

- 将外部数据传入`InitialContext.lookup()`方法前先进行严格的过滤；

- 使用安全管理器时，需要仔细审计安全策略