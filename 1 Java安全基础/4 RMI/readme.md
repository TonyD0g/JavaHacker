**前言:**

## [Java安全漫谈 - 05.利用codebase攻击RMI Registry](https://t.zsxq.com/BuFy3zF)

 [JAVA安全基础（四）-- RMI机制](https://xz.aliyun.com/t/9261)

### RMI有三个参与方:
RMI Registry
RMI Server
RMI Client

### RMI 通讯过程

首先客户端连接Registry，并在其中寻找Name是Hello的对象，这个对应数据流中的Call消息；然后Registry返回一个序列化的数据，这个就是找到的Name=Hello的对象，这个对应数据流中的ReturnData消息；客户端反序列化该对象，发现该对象是一个远程对象，地址在 192.168.135.142:33769 ，于是再与这个地址建⽴TCP连接；在这个新的连接中，才执行真正远程⽅法调用，也就是 hello() 。-- java安全漫谈



**RMI执行是在远程服务器上,即RMI服务端**



### 利用方法

#### 1.工具探测危险方法

- https://github.com/NickstaDB/BaRMIe

#### 2.利用codebase执行任意代码

- 让RMI Server在本地CLASSPATH里找不到类，才
  会去加载codebase中的类

- 只需要修改 `classAnnotations` 的值，就能控制`codebase`，使其
  指向攻击者的恶意网站,控制了`codebase`,就可以加载恶意类

- 利用条件:

  安装并配置了SecurityManager (暂不知道干什么的)

​		Java版本低于7u21、6u45，或者设置了 java.rmi.server.useCodebaseOnly=false



## [Java安全漫谈 - 06.深入理解RMI协议与序列化对象](https://t.zsxq.com/vZjaiuR)



**序列化数据特征:**	0xACED开头

**RMI Call**:  0x50ACED开头

**反序列化工具**: https://github.com/NickstaDB/SerializationDumper

必须使用语法规则(具体看文章)去解析数据，就和解析PE文件头一样



### **classAnnotations:**

**前言:**

- 子类: 由继承得到的类叫子类

  

在序列化Java类的时候用到了一个类，叫 `ObjectOutputStream` 。这个类内部有一个方法`annotateClass` ， `ObjectOutputStream` 的子类**有需要向序列化后的数据里放任何内容**，都可以重写这个方法，**写入你自己想要写入的数据**。然后反序列化时，就可以读取到这个信息并使用。

所以，我们在分析序列化数据时看到的 `classAnnotations` ，实际上就是 `annotateClass` 方法写入的内容。

