## [Java安全漫谈 - 07.不同语言中的反序列化漏洞](https://t.zsxq.com/NF2NfQf)

### 何为序列化和反序列化

序列化: 在网络上传输的数据按照特定的格式转换为二进制数据

反序列化: 将二进制数据转换回来



`反序列化漏洞`是对一类漏洞的泛指，而不是专指某种反序
列化方法导致的漏洞，比如`Jackson反序列化漏洞`和`Java readObject`造成的反序列化漏洞就是完全不同的两种漏洞。

---

### Java序列化更深入的原因

 `writeObject `允许开发者在序列化流中插入一些自定义数据，进而在反序列化的时候能够使用` readObject `进行读取。

### PHP,JAVA,Python反序列化的区别

**1. PHP**

PHP的序列化是开发者不能参与的，开发者调用 serialize 函数后，序列化的数据就已经完成了，你得到的是一个完整的对象，你并不能在序列化数据流里新增某一个内容，你如果想插入新的内容，只有将其保存在一个属性中。也就是说PHP的序列化、反序列化是一个纯内部的过程，而其 `__sleep` 、`__wakeup`魔术方法的目的就是在序列化、反序列化的前后执行一些操作。



**2.Java**

Java在序列化时一个对象，将会调用这个对象中的 `writeObject `方法，参数类型是
`ObjectOutputStream `，开发者可以将任何内容写入这个stream中；反序列化时，会调用`readObject `，开发者也可以从中读取出前面写入的内容，并进行处理。

`writeObject`方法会先调用`ObjectOutputStream`中的`defaultWriteObject()`方法，该方法会执行默认的序列化机制。然后才是`writeObject`

代码:

```md
private void writeObject(java.io.ObjectOutputStream s) throws
IOException {
    s.defaultWriteObject();
    s.writeObject("This is a object");
 }
```

使用工具解析序列化数据 https://github.com/NickstaDB/SerializationDumper

会发现我们写入的字符串 `This is a object` 被放在 `objectAnnotation` 的位置

**3.Python(危害最高)**

Python的反序列化过程实际上是在执行一个基于栈的虚拟机。我们可以向栈上增、删对象，也可以执行一些指令，比如函数的执行等，甚至可以用这个虚拟机执行一个完整的应用程序。

---

## URLDNS

**注意:**

**1.此处需要实操,跟着别人做一遍**

**2.ysoserial 所使用的java版本为1.8**



**搭环境**

[java 使用 idea 调试 ysoserial](https://www.dandelioncloud.cn/article/details/1432735494202564610)

**边看边实操**

[JAVA反序列化浅析以及URLDNS分析](http://myblog.ac.cn/archives/java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%B5%85%E6%9E%90%E4%BB%A5%E5%8F%8Aurldns%E5%88%86%E6%9E%90)

[JAVA安全-06反序列化篇(2)-URLDNS](https://www.cnblogs.com/-meditation-/articles/16270796.html)

[Java安全漫谈 - 08.认识最简单的Gadget——URLDNS](https://t.zsxq.com/ieMZBQj)

[Java反序列化之URLDNS](https://github.com/Y4tacker/JavaSec/blob/main/%E5%85%B6%E4%BB%96/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BURLDNS/Java%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B9%8BURLDNS.md)



### 名词解释:

**Gadget**: 链子,指的是 从触发位置开始到执行命令的位置结束

**CC**:  `CommonsCollections`, Java中应用广泛的一个库

**ysoserial**: CC链利用的一个著名工具



### [URLDNS源码](https://github.com/frohoff/ysoserial/blob/master/src/main/java/ysoserial/payloads/URLDNS.java)——选自ysoserial

**试着阅读英文，机翻可能更看不懂，不会的单词就查**

---



### **流程剖析:**

**(要看具体代码,这里只是把代码提取出来而已,为了让大家自觉实操遂不贴图.)**



**链子:(左边为层数,右边为函数)**

```
1     HashMap.readObject()
2       HashMap.putVal(HashMap.hash(),x,x,x,x)   
3           URL.hashCode()
4			URLStreamHandler.hashCode()
5				URLStreamHandler.getHostAddress()
6					InetAddress.getByName(host)

```



触发反序列化的方法是 `readObject` ，因为Java开发者（包括Java内置库的开发者）经
常会在这⾥⾯写自己的逻辑，所以导致可以构造利用链。

1. 查看第一层`HashMap.readObject()`方法，发现第二层`HashMap.putVal() ` 中的 `HashMap.hash() `方法

   

2. `HashMap.hash() `方法中有写 `key.hashCode()`，即第3层

   ```java
   static final int hash(Object key) {
           int h;
           return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
       }
   ```

​		所以此时定位到`key`是什么东西：回到 第一层即 `HashMap.readObject()`方法中的 `HashMap.putVal(HashMap.hash(),x,x,x,x) `所在代码片段:

```java
for (int i = 0; i < mappings; i++) {
                @SuppressWarnings("unchecked")
                    K key = (K) s.readObject();
                @SuppressWarnings("unchecked")
                    V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }
```

​	可以看到我们要找的`key` 取决于 `s.readObject()` ,那我们控制了 `s` 不可以了吗

​	`s`正好是 第一层 `HashMap.readObject(ObjectInputStream s)`方法中的参数

​	那我们怎么传入`s`,可以看到`s`被拆为了两个，分别是`(K)` 和`(V)`,他们分别被赋值给了`Key`和`Value`(即键值对)

所以思路出来了，使用`HashMap`的某个方法传入`Key`和`Value`不就大功告成了,这个方法是`HashMap.put()`



3. `HashMap.put()`方法存在于`URLDNS.getObejct`

`URLDNS.getObejct`：

```java
public Object getObject(final String url) throws Exception {
                URLStreamHandler handler = new SilentURLStreamHandler();

                HashMap ht = new HashMap(); 
                URL u = new URL(null, url, handler); 
                ht.put(u, url); 

                Reflections.setFieldValue(u, "hashCode", -1); 

                return ht;
        }
```

`HashMap.put()` ： 

```java
public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

参见**{2.}**可以知道 要使用`key.hashCode()`方法

因为这个`key` 是`URL`类，所以查看`URL`类的`hashCode()`方法,即第三层`URL.hashCode()`

第三层`URL.hashCode()`：

```java
public synchronized int hashCode() {
        if (hashCode != -1)
            return hashCode;

        hashCode = handler.hashCode(this);
        return hashCode;
    }
```

注意这里的`hashCode ! = -1`才能让链子往下走,所以 `URLDNS.getObejct`中:通过反射设置hashCode的值= -1：`Reflections.setFieldValue(u, "hashCode", -1); `

4.第四，五，六层一步一步跟进即可,最后是通过`InetAddress.getByName(host)`发起DNS查询



**P牛原话:**

```md
要构造这个Gadget，只需要初始化⼀个 java.net.URL 对象，作为 key 放在 java.util.HashMap
中；然后，设置这个 URL 对象的 hashCode 为初始值 -1 ，这样反序列列化时将会重新计算
其 hashCode ，才能触发到后面的DNS请求，否则不会调用 URL->hashCode() 。
另外，ysoserial为了防止在生成Payload的时候也执行了URL请求和DNS查询，所以重写了一
个 SilentURLStreamHandler 类，这不是必须的。
```

