#### 前言

必读：

[Java安全漫谈 - 15.TemplatesImpl在Shiro中的利用](https://t.zsxq.com/JAUBmMz)

【3 CC6.md】

---

**tips：这里只是以CC6为例，之前的CC1,CC3都可以用这种思想改造**



#### shiro反序列化原理：

因为使用了默认的key，所以恶意攻击者利用默认的key伪造任意**rememberMe Cookie**,shiro读取到我们伪造的cookie,然后进行解密和反序列化。进而触发反序列化漏洞。



#### 前面InvokerTransformer为什么要使用getClass？

其实和【3 CC6.md中的"为什么要引入fakeTransformers？"】同出一辙。

最后使用了 `setFieldValue(transformer, "iMethodName", "newTransformer");` 替换成恶意语句。

---

#### Shiro反序列化的核心述求：

**详情代码查看【5 CC6-Shiro.java】**

**如果反序列化流中包含非Java自身的数组，则会出现无法加载类的错误。**——[Java安全漫谈 - 15.TemplatesImpl在Shiro中的利用](https://t.zsxq.com/JAUBmMz)

前面的CC6使用了 Transformer 数组，Transformer 数组是属于CC包的所以不能用，需要找个方法去替代Transformer 数组。

至于为什么，我也不是很理解，因为中间涉及到大量Tomcat对类加载的处理逻辑，难度过高，暂不考虑过程只专注得出的结论，等后面学懂了再回过头看。



**【3 CC6.md 中的“链子流程”】**说过：

`TiedMapEntry.hashCode`调用了`getValue`

`getValue`调⽤了 `this.map.get(key)`，从而调用`LazyMap.get(key)`

LazyMap.get：

```java
public Object get(Object key) {
  // create value for key if key is not currently in the map
  if (map.containsKey(key) == false) {
    Object value = factory.transform(key);
    map.put(key, value);
    return value;
 }
  return map.get(key);
}
```

而且**key**是传入到了`factory.transform`中：`Object value = factory.transform(key);`，只要控制了key,一切都好说，恰好的是`TiedMapEntry`的构造方法可以直接传入key值。

TiedMapEntry 构造方法为：

```java
public TiedMapEntry(Map map, Object key) {
    super();
    this.map = map;
    this.key = key;
}
```



以往构造CC链，对 `LazyMap#get` 方法的参数key是不关心的，因为
通常`Transformer`数组的首个对象是`ConstantTransformer`，我们通过`ConstantTransformer`来初始化恶意对象。
因为此时我们无法使用Transformer数组了，也就不能再用ConstantTransformer了 ——[Java安全漫谈 - 15.TemplatesImpl在Shiro中的利用](https://t.zsxq.com/JAUBmMz)

但是如果我们利用了**TiedMapEntry的构造函数和getValue方法**，就可以自由传值，也就取代了`new ConstantTransformer(obj)`的作用。

将语句改为了：

```java
TiedMapEntry tme = new TiedMapEntry(outerMap, obj);
```

---

#### 需要注意的问题：

​										——[Java安全漫谈 - 15.TemplatesImpl在Shiro中的利用](https://t.zsxq.com/JAUBmMz)

- Shiro不是遇到Tomcat就一定会有数组这个问题
- Shiro-550的修复并不意味着反序列化漏洞的修复，只是默认Key被移除了
- 网上大部分的文章上来就是装一个commons-collections4.0，这个是没有代表性的，不建议将这二者结合起来学习

