## Shiro

### 怎样检测目标框架使用了Shiro（★）[+]

（镜像问题:

**shiro记住密码功能是怎么实现的？**

）

直接看请求响应中是否有`rememberMe=deleteMe`这样的`Cookie`



### Shiro反序列化怎么检测key（★★★）

实例化一个`SimplePrincipalCollection`并序列化，遍历key列表对该序列化数据进行AES加密，然后加入到`Cookie`的`rememberMe`字段中发送

如果响应头的`Set-Cookie`字段包含`rememberMe=deleteMe`说明不是该密钥，如果什么都不返回，说明当前key是正确的key。实际中可能需要多次这样的请求来确认key



### Shiro 721怎么利用（★★）[+]

（镜像问题:

**shiro721和shiro550的区别？**

​	Shiro-550，只需输入url，即可完成自动化检测和漏洞利用。
​	Shiro-721，需输入url，提供一个有效的rememberMe Cookie，并指定目标操作系统类型

）

需要用到Padding Oracle Attack技术，限制条件是需要已知合法用户的`rememberMe`且需要爆破较长的时间



### 最新版Shiro还存在反序列化漏洞吗（★）[+]

存在，如果密钥是常见的，还是有反序列化漏洞的可能性



### Shiro反序列化Gadget选择有什么坑吗（★★★）[+]

默认不包含CC链包含CB1链。用不同版本的CB1会导致出错，因为`serialVersionUID`不一致

另一个CB1的坑是`Comparator`来自于CC，需要使用如下的才可以在没有CC依赖情况下成功RCE

```java
BeanComparator comparator = new BeanComparator(null, String.CASE_INSENSITIVE_ORDER);
```

最好使用java原生的类。



### Shiro注Tomcat内存马有什么坑吗（★★★★）

Shiro注内存马时候由于反序列化Payload过大会导致请求头过大报错

解决办法有两种：第一种是反射修改Tomcat配置里的请求头限制实现，但这个不靠谱，不同版本`Tomcat`可能修改方式不一致

另外一种更为通用的手段是打过去一个`Loader`的`Payload`加载请求`Body`里的字节码，将内存马字节码写入请求`Body`中。这种方式的缺点是依赖当前请求对象，解决办法是可以写文件后`URLClassLoader`加载



### 有什么办法让Shiro洞不被别人挖到（★★）[+]

发现Shiro洞后，改了其中的key为非通用key。通过已经存在的反序列化可以执行代码，反射改了`RememberMeManager`中的key即可。但这样会导致已登录用户失效，新用户不影响



### [Shiro的权限绕过问题了解吗]([记一次Apache Shiro权限绕过实战-腾讯云开发者社区-腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/2129876))（★★）[+]

主要是和Spring配合时候的问题，例如`/;/test/admin/page`问题，在`Tomcat`判断`/;test/admin/page`为test应用下的`/admin/page`路由，进入到Shiro时被`;`截断被认作为`/`，再进入Spring时又被正确处理为test应用下的`/admin/page`路由，最后导致shiro的权限绕过。后一个修复绕过，是针对动态路由如`/admin/{name}`，原理同上

### Shiro绕WAF的tips（★★）[+]

- [分块传输](https://github.com/c0ny1/chunked-coding-converter)

- [未知Http方法名](https://gv7.me/articles/2021/shiro-deserialization-bypasses-waf-through-unknown-http-method/)

- [垃圾数据](https://gv7.me/articles/2021/java-deserialize-data-bypass-waf-by-adding-a-lot-of-dirty-data/)

- Payload缩小

- [特殊字符]([一次特殊的shiro以及Bypass Waf - 啊吧啊吧](http://120.79.21.98:8090/archives/shirobypass)) (可用的字符为！、@、#（只要不出现BASE编码中可能出现的字符就行）)

  ```python
  def Bypass_waf(str_origin):
      str_list = list(str_origin)    # 字符串转list
      str_list.insert(20, '@')  # 在指定位置插入字符串
      str_list.insert(30, '@')
      str_out = ''.join(str_list)    # 空字符连接
      return  str_out
  ```

  
