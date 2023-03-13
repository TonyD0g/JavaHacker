## CC

### 谈下CC链中三个重要的`Transformer`（★★★）[+]

`ConstantTransformer`类的`transform`方法直接返回传入的类对象

`ChainedTransformer`类中的`transform`方法会链式调用其中的其他`Transformer.transform`方法

`InvokerTransformer`类根据传入参数可以反射调用对应的方法

`InstantiateTransformer`类可以直接实例化对象



### 谈谈CC1（★★★）[+]

原理是`LazyMap.get`可以触发构造的`Transformer`链的`transform`方法导致`RCE`

基于动态代理触发的`LazyMap.get`，在`AnnotationInvocationHanlder`中的`invoke`方法中存在`Map.get`操作



### 谈谈CC2（★★★）[+]

该链用到`TemplatesImpl`类，生成恶意的字节码实例化

不过触发点是`PriorityQueue`类，反射设置属性`TransformingComparator`

`PriorityQueue`类是优先队列，其中包含了排序功能，该功能可以设置比较器`comparator`，而`TransformingComparator`的`compare`方法会调用对象的`transform`方法

于是通过`InvokerTransformer`类的`transform`方法调用`TemplatesImpl.newTransformer`方法导致`RCE`



### 谈谈CC3（★★★）[+]

该链用到`TemplatesImpl`类，生成恶意的字节码实例化

仍然是基于动态代理和`LazyMap.get`触发`InstantiateTransformer`的`transform`方法导致`RCE`



### 谈谈CC4（★★★）[+]

和CC2链一致，不过触发时候不是`InvokerTransformer`而是`InstantiateTransformer`类直接实例化`TrAXFilter`子类执行`<init>/<clinit>`导致`RCE`



### 谈谈CC5（★★★）[+]

还是基于`LazyMap.get`触发的，不过没有动态代理，是通过`BadAttributeValueExpException.readObject()`调用`TiedMapEntry.toString()`

在`TiedMapEntry.getValue()`中存在`Map.get`导致`LazyMap.get`触发`transform`



### 谈谈CC6（★★★）[+]

还是基于`LazyMap.get`触发的，通过`HashMap.readObject()`到达`HashMap.hash()`方法，由于key是`TiedMapEntry`所以调用`TiedMapEntry.hashCode()`

而`hashCode`方法会调用到`TiedMapEntry.getValue()`方法，由于`Map.get`导致`LazyMap.get`触发`transform`



### 谈谈CC7（★★★）[+]

还是基于`LazyMap.get`触发的，通过`Hashtable.readObject()`触发了key的`equals`方法，跟入`AbstractMap.equals`方法

其中包含了`Map.get`导致`LazyMap.get`触发`transform`

但该链有一个坑：哈希碰撞