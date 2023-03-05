## 1.前言

1.必读

[Fastjson 1.2.24 版本漏洞分析](https://drun1baby.github.io/2022/08/06/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96Fastjson%E7%AF%8702-Fastjson-1-2-24%E7%89%88%E6%9C%AC%E6%BC%8F%E6%B4%9E%E5%88%86%E6%9E%90/)



环境：

- JDK8u65
- 1.2.22 <= Fastjson <= 1.2.24

pom.xml:

```md
<dependency>
    <groupId>com.unboundid</groupId>
    <artifactId>unboundid-ldapsdk</artifactId>
    <version>4.0.9</version>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.5</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.24</version>
</dependency>
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
    <version>1.12</version>
</dependency>
```

---

## 基于 TemplatesImpl 的利用链

前言：

- 【Java反序列化基础/7 Commons Beanutils.md】

【Java反序列化基础/7 Commons Beanutils.md】中提到 JavaBean,而其中用到 getter

（**即TemplatesImpl 类中的 `getTransletInstance()`**）,恰好的是 `Fastjson`漏洞都是基于 构造函数,setter,getter.因此得出结论，可以基于`TemplatesImpl`进行构造利用链

需要满足的条件：

- 【5 Fastjson基础\1 基础\基础.md】中,**"Fastjson会对满足下列要求的setter/getter方法进行调用"**

因此使用 `TemplatesImpl#getOutputProperties()`



**链子：**

```java
[public]TemplatesImpl#getOutputProperties()  ---> 
[public]TemplatesImpl#newTransformer() ---> 
TemplatesImpl#TransformerImpl(getTransletInstance(), _outputProperties, _indentNumber, _tfactory);
```

【详情代码查看：TemplatesImplChain.java】

---

## 基于 JdbcRowSetImpl 的利用链

`JdbcRowSetImpl` 类里面有一个 `setDataSourceName()` 方法，**里面能填JNDI url**

### JNDI + RMI

EvilObject.class 放在tomcat服务器上，【详情代码查看：JdbcRowSetImpl/JNDILdapServer.java】

### JNDI + LDAP

EvilObject.class 放在tomcat服务器上，【详情代码查看：JdbcRowSetImpl/JNDIRmiServer.java】



## Fastjson 攻击中 jdk 高版本的绕过

依旧用到EL表达式，暂不了解。【详情代码查看：JNDIBypassHighJavaServerEL.java】

