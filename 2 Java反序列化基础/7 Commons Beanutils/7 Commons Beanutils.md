## 1.前言

必读：

https://www.liaoxuefeng.com/wiki/1252599548343744/1260474416351680

[Java安全漫谈 - 17.CommonsBeanutils与无commons-collections的Shiro反序列化利用](https://t.zsxq.com/IqBmuF6)

导包：

```md
<dependency>
            <groupId>commons-beanutils</groupId>
            <artifactId>commons-beanutils</artifactId>
            <version>1.9.2</version>
        </dependency>
```



---

#### Commons Beanutils是什么

`Apache Commons Beanutils` 是 `Apache Commons` 工具集下的另一个项目，它提供了对普通Java类对象（也称为JavaBean）的一些操作方法。



JavaBean一般通过 **"get开头+属性"** 来获取属性的值，**称为getter**

同理，通过 **"set开头+属性"** 来设置属性的值，**称为setter** 

Cat Bean：

```java
final public class Cat {
  private String name = "catalina";
  public String getName() {
    return name;
 }
  public void setName(String name) {
    this.name = name;
 }
}
```

## 2.利用链流程分析：

commons-beanutils中提供了一个静态方法 **PropertyUtils.getProperty** ，让使用者可以直接调用任意JavaBean的getter方法，比如：

```java
PropertyUtils.getProperty(new Cat(), "name"); 
```

 PropertyUtils.getProperty 还支持递归获取属性，比如a对象中有属性b，b对象
中有属性c，我们可以通过 `PropertyUtils.getProperty(a, "b.c");` 的方式进行递归获取。



BeanComparator 是commons-beanutils提供的用来比较两个JavaBean是否相等的类，其实现了
java.util.Comparator 接口。它的compare方法(核心部分)：

BeanComparator.compare：

```java
if ( property == null ) {
    // compare the actual objects
    return internalCompare( o1, o2 );
 }
try {
    final Object value1 = PropertyUtils.getProperty( o1, property );
    final Object value2 = PropertyUtils.getProperty( o2, property );
    return internalCompare( value1, value2 );
 }
```

再看TemplatesImpl利用链：

```java
[public] TemplatesImpl#getOutputProperties() -> 
[public] TemplatesImpl#newTransformer() ->
TemplatesImpl#getTransletInstance() ->
TemplatesImpl#defineTransletClasses() ->
TransletClassLoader#defineClass()
```



可知， **PropertyUtils.getProperty( o1, property )** 这段代码，当o1是一个 `TemplatesImpl` 对
象，而 `property` 的值为 `outputProperties` 时，将会自动调用`getter`，也就是
**TemplatesImpl#getOutputProperties()** 方法，触发代码执行。

初始化时使用正经对象，且 property 为空，这一系列操作是为了初始化的时候不要出错。后面替换成恶意的即可：

```java
setFieldValue(comparator, "property", "outputProperties");
setFieldValue(queue, "queue", new Object[]{obj, obj});
```



相比于ysoserial里的CommonsBeanutils1利用链，本文的利用链`去掉了对 java.math.BigInteger 的`
`使用`，因为ysoserial为了兼容 `property=lowestSetBit` ，但实际上我们**将 property 设置为null即可**(初始化BeanComparator对象时，用无参构造函数)。——[Java安全漫谈 - 17.CommonsBeanutils与无commons-collections的Shiro反序列化利用](https://t.zsxq.com/IqBmuF6)

【详情代码查看 CB.java ，test.java】

### 2-1 CB利用链：

```java
BeanComparator#compare()
    PropertyUtils#getProperty()
		[public] TemplatesImpl#getOutputProperties() 
			[public] TemplatesImpl#newTransformer() 
                        TemplatesImpl#getTransletInstance() 
                            TemplatesImpl#defineTransletClasses() 
                                TransletClassLoader#defineClass()
```



## 3.serialVersionUID是什么

如果两个不同版本的库使用了同一个类，而这两个类可能有一些方法和属性有了变化，此时在序列化通
信的时候就可能因为不兼容导致出现隐患。因此，Java在反序列化的时候提供了一个机制，序列化时会
根据固定算法计算出一个当前类的 serialVersionUID 值，写入数据流中；反序列化时，如果发现对方
的环境中这个类计算出的 serialVersionUID 不同，则反序列化就会异常退出，避免后续的未知隐患。
当然，开发者也可以手工给类赋予一个 serialVersionUID 值，此时就能手工控制兼容性了。

​					——[Java安全漫谈 - 17.CommonsBeanutils与无commons-collections的Shiro反序列化利用](https://t.zsxq.com/IqBmuF6)



**正常使用Shiro的时候不需要依赖于commons-collections，但反序列化利用的时候需要依赖于commons-collections。**

因此引入 **无依赖的Shiro反序列化利用链**，指的是不依赖CC库。

---

## 4.无依赖的Shiro反序列化利用链

在 BeanComparator 类的构造函数中，当没有显式传入 Comparator 的情况下，则默认使用
ComparableComparator 。ComparableComparator  属于CC库，我们要找一个类去替换它。

**需要满足的三个条件：**

- 实现 java.util.Comparator 接口
- 实现 java.io.Serializable 接口
- Java、shiro或commons-beanutils自带，且兼容性强

P牛指出有  **java.lang.String.CaseInsensitiveComparator** 类。

这个 `CaseInsensitiveComparator` 类是 `java.lang.String` 类下的一个内部私有类，其实现了
`Comparator` 和 `Serializable` ，且位于Java的核心代码中，兼容性强，是一个完美替代品。



我们通过 `String.CASE_INSENSITIVE_ORDER` 即可拿到上下文中的 `CaseInsensitiveComparator` 对
象，用它来实例化 `BeanComparator` :

```java
final BeanComparator comparator = new BeanComparator(null,
String.CASE_INSENSITIVE_ORDER);
```

【完整代码查看：CB-shiro无依赖.java】

利用链同 【2-1 CB利用链】

---

 **java.lang.String.CaseInsensitiveComparator** 类可替换为 **java.util.Collections$ReverseComparator**，原理相同。
