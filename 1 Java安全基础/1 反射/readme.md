写的很详细了，不重复复制粘贴:

[Java反射](https://github.com/Y4tacker/JavaSec/blob/main/1.%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86/%E5%8F%8D%E5%B0%84/%E5%8F%8D%E5%B0%84.md)

[Java安全漫谈 - 01.Java的动态特性——反射](https://t.zsxq.com/iyJiAMJ)



### 三种初始化的调用顺序

下列三种初始化，调用顺序是什么： —— Java安全漫谈

```java
public class TrainPrint {
{
	System.out.printf("Empty block initial %s\n", this.getClass());
}
static {
	System.out.printf("Static initial %s\n", TrainPrint.class);
}
public TrainPrint() {
	System.out.printf("Initial %s\n", this.getClass());
}
}
```

⾸先调用的是 `static {}` ，其次是` {} `，最后是`构造函数`。



```java
Class.forName(className)
// 等于
Class.forName(className, true, currentLoader)
// Class<?> forName(String name, **boolean** initialize, ClassLoader loader)
```

第⼀个参数是类名；第二个参数表示是否初始化；第三个参数就
是 `ClassLoader`

 `forName` 中的 `initialize=true` 其实就是告诉Java虚拟机是否执行”类初始化“,即 是否执行`static{}`的内容 

---

必读：

[Java安全漫谈 - 02.反射的简单利用](https://t.zsxq.com/iIa2B2j)



### 何为内部类

如果类名的部分包含 `$` 符号, `$` 的作用是查找内部类。

Java的普通类 `C1` 中支持编写内部类` C2` ，而在编译的时候，会生成两个文件： `C1.class` 和
`C1$C2.class` ，我们可以把他们看作两个无关的类，通过 `Class.forName("C1$C2")` 即可加载这个内
部类。——Java安全漫谈



### 单例模式

**为什么要这么写:**

`invoke(obj,args...)`

```java
Class.forName("java.lang.Runtime").getMethod("exec",String.class).invoke(Class.forName("java.lang.Runtime").getMethod("getRuntime").invoke(Class.forName("java.lang.Runtime")),"calc");
```

这里需要引入单例模式

举例：数据库连接只需要建立一次，而不是每次用到数据库的时候再新建立一个连接，此时作为开发者你就可以将数据库连接使用的类的构造函数设置为私有，然后编写一个静态方法`static`来获取	-- Java安全漫谈

```java
public class TrainDB {
  private static TrainDB instance = new TrainDB();
  public static TrainDB getInstance() {
    return instance;
 }
  private TrainDB() {
    // 建立连接的代码...
 }
}
```



Java 中 Runtime 类表示运行时操作类，是一个封装了JVM进程的类，每一个 JVM 都对应着一个 Runtime 类的实例，此实例由 JVM运行时为其实例化。

Runtime 类本身的构造方法是私有化的，如果想取得一个 Runtime 实例，则只能通过：

```java
Runtime run = Runtime.getRuntime();
```

也就是在 Runtime 类中提供了一个静态的 `getRuntime()` 方法，此类可以取得 Runtime 类的实例，既然 Runtime 表示的是每一个 `JVM` 实例，所以也可以通过 Runtime 取得一些系统的信息

- JVM：Java Virtual Mathine(java虚拟机) ，详情看 类加载篇

通过 `Runtime.getRuntime() `来获取到 `Runtime` 对象：

```java
Class.forName("java.lang.Runtime").getMethod("getRuntime").invoke(Class.forName("java.lang.Runtime"))
```

然后再塞进 `{?}`处,**`{?}`只是我所定义的伪代码，意思是占位符**

```java
Class.forName("java.lang.Runtime").getMethod("exec",String.class).invoke({?},"calc.exe");
```

