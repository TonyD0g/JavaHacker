## 前言

1.必读：

[Tomcat 内存马（一） Listener 型](https://xz.aliyun.com/t/10358)(主要调试过程看这个，不赘述)

---

## Listener型内存马核心诉求

Listener是最先被加载的, 所以可以利用动态注册恶意的Listener内存马。而Listener分为以下几种：

- ServletContext，服务器启动和终止时触发
- Session，有关Session操作时触发
- Request，访问服务时触发

---

## Listener型内存马核心构造

- 调用 `StandardContext#addApplicationEventListener` 方法， add 我们自己写的恶意 listener



【详情代码参考：Code/ListenerMemshell.jsp】

---

## 三种Listener型内存马(?)

我看很多文章只讲了 ServletRequestListener 内存马，难道只有这一种吗？

待后续研究

- 监听Request对象
  - 寻找继承于`EventListener`的接口，比如 ServletRequestListener 接口