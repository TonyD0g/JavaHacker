## 前言

1.必读：

[Java安全漫谈 - 16.commons-collections4与漏洞修复](https://t.zsxq.com/ZBQj2FE)

---



Apache Commons Collections 分为两个版本：

- commons-collections:commons-collections
- org.apache.commons:commons-collections4



之前我们讨论的都是 `commons-collections:commons-collections`，现在讨论 `org.apache.commons:commons-collections4`



#### commons-collections4主要修改特征：

```java
LazyMap.decorate
```

修改为

```java
LazyMap.lazyMap
```

之前看过的CommonsCollections1、CommonsCollections3按修改特征改也是能用的。



## PriorityQueue利⽤链

**在commons-collections中找Gadget的过程，实际上可以简化为，找⼀条从 Serializable#readObject() ⽅法到 Transformer#transform()⽅法的调⽤链。**

CommonsCollections2，其中⽤到的两个关键类是

- `java.util.PriorityQueue`
- `org.apache.commons.collections4.comparators.TransformingComparator`

`PriorityQueue` 有 `readObject`方法

`TransformingComparator` 有`compare`方法，`compare`方法调用了 `transform`方法

#### PriorityQueue是什么？

`PriorityQueue`在Java中是一个优先队列，队列中每一个
元素有自己的优先级。在反序列化这个对象时，为了保证队列顺序，会进行重排序的操作，而排序就涉
及到大小比较，进而执行 java.util.Comparator 接口的 compare() 方法。



实例化 `PriorityQueue` 对象，第⼀个参数是初始化时的⼤小，⾄少需要2个元素才会触发排序和⽐较，
所以是2；第⼆个参数是⽐较时的`Comparator`，传⼊前⾯实例化的`comparator` ——[Java安全漫谈 - 16.commons-collections4与漏洞修复](https://t.zsxq.com/ZBQj2FE)

```java
PriorityQueue queue = new PriorityQueue(2, comparator);
queue.add(1);
queue.add(2);
```

详情代码查看【CC2-PriorityQueue.java】

---

#### 改进PriorityQueue利⽤链

之前讲过，可以⽤ TemplatesImpl 可以构造出⽆Transformer数组的利⽤链，

详情代码查看【CC2-PriorityQueue-TemplatesImpl.java】



## 链子流程：

 `PriorityQueue#readObject()` 中调⽤了 `heapify()` ⽅法，

 `heapify()` 中调⽤了 `siftDown()` ， `siftDown()` 中调⽤了`siftDownUsingComparator()` ， `siftDownUsingComparator()` 中调⽤了`comparator.compare()`，也就是`TransformingComparator.compare()`,`TransformingComparator.compare()`调用了 `transform`方法



## commons-collections反序列化官⽅修复⽅法

 ——[Java安全漫谈 - 16.commons-collections4与漏洞修复](https://t.zsxq.com/ZBQj2FE)

- PriorityQueue的利⽤链是否⽀持在commons-collections 3中使⽤？
- Apache Commons Collections官⽅是如何修复反序列化漏洞的？

**第⼀个问题**，答案不能。因为这条利⽤链中的关键类 `org.apache.commons.collections4.comparators.TransformingComparator` ，在`commons-`
`collections4.0`以前是版本中是**没有实现 `Serializable` 接⼝**的，⽆法在序列化中使⽤。

**第⼆个问题**，Apache Commons Collections官⽅在2015年底得知序列化相关的问题后，就在两个分⽀
上同时发布了新的版本，4.1和3.2.2。

- 3.2.2版本：`FunctorUtils#checkUnsafeSerialization`

⽤于检测反序列化是否安全。如果开发者没有设置全
局配置 `org.apache.commons.collections.enableUnsafeSerialization=true` ，即默认情况下会
抛出异常。

- 4.1版本：常见的危险Transformer类（ InstantiateTransformer 、 InvokerTransformer 、 PrototypeFactory 、 CloneTransforme
  r 等）**不再实现 Serializable 接⼝**，直接从根本上杜绝了这几个类的反序列漏洞
