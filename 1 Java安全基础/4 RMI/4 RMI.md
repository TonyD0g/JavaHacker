# RMI 基础知识

**前言:**

## [Java安全漫谈 - 05.利用codebase攻击RMI Registry](https://t.zsxq.com/BuFy3zF)

 [JAVA安全基础（四）-- RMI机制](https://xz.aliyun.com/t/9261)

### RMI有三个参与方:
RMI Client
RMI Registry
RMI Server

### RMI 通讯过程

首先客户端连接Registry，并在其中寻找Name是Hello的对象，这个对应数据流中的Call消息；然后Registry返回一个序列化的数据，这个就是找到的Name=Hello的对象，这个对应数据流中的ReturnData消息；客户端反序列化该对象，发现该对象是一个远程对象，地址在 192.168.135.142:33769 ，于是再与这个地址建⽴TCP连接；在这个新的连接中，才执行真正远程⽅法调用，也就是 hello() 。—— [JAVA安全基础（四）-- RMI机制](https://xz.aliyun.com/t/9261)



**RMI执行是在远程服务器上,即RMI服务端.**

**用代码来理解：**【代码查看：RemoteObj.java，RemoteObjImpl.java，RMIClient.java，RMIServer.java】

攻击RMI Registry 的部分写在 下面的 [攻击RMI] 处了

---

# [Java安全漫谈 - 06.深入理解RMI协议与序列化对象](https://t.zsxq.com/vZjaiuR)



**序列化数据特征:**	0xACED开头

**RMI Call**:  0x50ACED开头

**反序列化工具**: https://github.com/NickstaDB/SerializationDumper

必须使用语法规则(具体看文章)去解析数据，就和解析PE文件头一样



### **classAnnotations:**

**前言:**

- 子类: 由继承得到的类叫子类

  

在序列化Java类的时候用到了一个类，叫 `ObjectOutputStream` 。这个类内部有一个方法`annotateClass` ， `ObjectOutputStream` 的子类**有需要向序列化后的数据里放任何内容**，都可以重写这个方法，**写入你自己想要写入的数据**。然后反序列化时，就可以读取到这个信息并使用。

所以，我们在分析序列化数据时看到的 `classAnnotations` ，实际上就是 `annotateClass` 方法写入的内容。

---

# 攻击RMI的几种方式

RMI 当中的攻击手法只在 **jdk8u121** 之前才可以进行攻击，因为在 **8u121** 之后，`bind rebind unbind` 这三个方法只能对 localhost 进行攻击

1.必读：

[Java 反序列化之 RMI 专题 02-RMI 的几种攻击方式](https://drun1baby.github.io/2022/07/23/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BRMI%E4%B8%93%E9%A2%9802-RMI%E7%9A%84%E5%87%A0%E7%A7%8D%E6%94%BB%E5%87%BB%E6%96%B9%E5%BC%8F/#toc-heading-1)

[Java安全之RMI反序列化](https://xz.aliyun.com/t/9053#toc-1)

[RMI利用学习](https://xz.aliyun.com/t/11339#toc-0)

2.我所使用的JDK版本：	8u65

---

可以先看下RMI的底层实现：

### `LocateRegistry.createRegistry`的断点之旅：

- [攻击 RMI Registry](https://drun1baby.github.io/2022/07/23/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BRMI%E4%B8%93%E9%A2%9802-RMI%E7%9A%84%E5%87%A0%E7%A7%8D%E6%94%BB%E5%87%BB%E6%96%B9%E5%BC%8F/#toc-heading-3) 和 [RMI利用学习](https://xz.aliyun.com/t/11339#toc-0) 所说的 `RegistryImpl_Skel` 是什么：

  **RegistryImpl_Skel对应的是服务端，而RegistryImpl_Stub对应的是客户端**

  **`RegistryImpl_Skel`所在路径：(jdk8u65\jre\lib\rt.jar\sun\rmi\registry\RegistryImpl_Skel.class)**

  里面有个switch,对应的功能：

  - 0->bind
  - 1->list
  - 2->lookup
  - 3->rebind
  - 4->unbind

**1.先跟着  [RMI源码调试](https://www.cnblogs.com/yyhuni/p/15091121.html)** 

（这个文章用的7u80,建议和它用的一样看它文章分析）

进行调试，来到

【原文中的 "第85行创建了RegistryImpl的代理对象RegistryImpl_stub，这个对象就是后面服务于客户端的RegistryImpl的Stub对象"】，因为版本不同，我这里是 197行,这个 stub 执行完 204行后就转换为 `RegistryImpl_Skel`。

**2.(省略一些相对不重要的过程)**

**3.调用TCPTransport的exportObject方法，然后开启监听。**

最终通过ObjectTable.getTarget()从socket流中获取ObjId，然后通过ObjId获取Target对象，然后调用 `UnicastServerRef#dispatch`,调用链：

```java
UnicastServerRef#dispatch
    UnicastServerRef#oldDispatch
    	RegistryImpl_Skel#dispatch  
```

，最后根据参数（0就是bind，2就是lookup）处理请求，

**所以无论是客户端还是服务端最终处理请求都是通过创建RegistryImpl对象进行调用。**

---

解释这句话（【"回过头去看前面的，在客户端收到信息的时候是一个 Proxy 对象"】——[Java 反序列化之 RMI 专题 02-RMI 的几种攻击方式](https://drun1baby.github.io/2022/07/23/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BRMI%E4%B8%93%E9%A2%9802-RMI%E7%9A%84%E5%87%A0%E7%A7%8D%E6%94%BB%E5%87%BB%E6%96%B9%E5%BC%8F/#toc-heading-1)）：

**RMIClient 的这行语句返回的是 Proxy对象:**

```java
RemoteObj remoteObj = (RemoteObj) registry.lookup("remoteObj");
```

`Proxy.java所在位置：java.lang.reflect.Proxy`

---

### 攻击方法

#### 一.攻击Registry:

**1.list攻击**

只有writeObject，没啥用。【代码查看：AttackRegistry/ListAttack.java】

**2.bind 或 rebind 的攻击**

【代码查看：AttackRegistry/AttackRegistryExp.java】

- 条件1：服务端存在相关组件漏洞，如CC

**思路简化**：`RegistryImpl_Skel` 的`bind/rebind`选项，会调用`readObject`方法。于是我们传入恶意序列化数据（如CC1），并使用`Proxy.newProxyInstance`（**因为RMI Client收到的就是Proxy对象**）去调用恶意序列化数据（如CC1），然后 `bind/rebind` 到RMI Server（命名为`"hack"`）。最后再用RMI Client发起 `"hack"`请求,RMI Server对恶意序列化数据（如CC1）进行反序列化，达成利用。

**3.unbind 或 lookup 的攻击**

大致的思路还是和 `bind/rebind` 思路是一样的，**但是 lookup 这里只可以传入 `String` 类型**，这里我们可以通过伪造 `lookup` 连接请求（仿写原本的`lookup`方法）进行利用，修改 `lookup` 方法代码使其可以传入对象。——[Java 反序列化之 RMI 专题 02-RMI 的几种攻击方式](https://drun1baby.github.io/2022/07/23/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BRMI%E4%B8%93%E9%A2%9802-RMI%E7%9A%84%E5%87%A0%E7%A7%8D%E6%94%BB%E5%87%BB%E6%96%B9%E5%BC%8F/#toc-heading-1)

【代码查看：AttackRegistry/AttackRegistryExp2.java】

---

#### 二.攻击客户端

**a.注册中心攻击客户端:**

除了`unbind`和`rebind`之外其他方法都会返回数据给客户端,且返回的是序列化数据。客户端会自动进行反序列化，达成利用。

Payload自己跑去看文章吧，因为都是用工具生成的Payload。



**b.服务端攻击客户端:**

**1.利用codebase执行任意代码**

- 让RMI Server在本地CLASSPATH里找不到类，才
  会去加载codebase中的类

- 只需要修改 `classAnnotations` 的值，就能控制`codebase`，使其
  指向攻击者的恶意网站,控制了`codebase`,就可以加载恶意类

- 利用条件:

  安装并配置了SecurityManager (暂不知道干什么的)

​		Java版本低于7u21、6u45，或者设置了 `java.rmi.server.useCodebaseOnly=false`

**2.服务端返回参数为Object对象**

- 远程调用方法传递回来的如果是对象，那我们控制恶意服务端，当客户端调用指定方法时，返回恶意对象，客户端反序列化后即达成利用。

  利用条件

  - 传递Object
  - 服务端使用有漏洞的库，如CC
  - jdk版本1.7

  【代码查看：AttackClient】

#### 三.攻击服务端

**a.工具探测危险方法**

- https://github.com/NickstaDB/BaRMIe

**b.Registry攻击服务端：**

如果服务端的某个方法，传递的参数是Object类型的参数，当服务端接收客户端传来的数据时，就会调用readObject，达成利用。

【详情代码查看：[Java安全之RMI反序列化](https://xz.aliyun.com/t/9053#toc-6)】

**c.远程加载对象:**

条件苛刻，自行查阅，暂不了解：https://xz.aliyun.com/t/9053#toc-7

**d.利用URLClassLoader实现回显攻击:**

攻击注册中心时，注册中心遇到异常会直接把异常发回来，返回给客户端。利用URLClassLoader加载远程jar，传入服务端，反序列化后调用其方法，在方法内抛出错误，错误会传回客户端。

1.制作恶意jar包（类似于shell），并记录URL路径

2.RMI 客户端生成恶意序列化数据（如CC1）[包含了恶意jar包的URL路径和command]

3.RMI 客户端`bind`恶意序列化数据

【详情代码查看：[Java安全之RMI反序列化](https://xz.aliyun.com/t/9053#toc-8)】

**e.JEP290,JEP290绕过：暂不了解**

