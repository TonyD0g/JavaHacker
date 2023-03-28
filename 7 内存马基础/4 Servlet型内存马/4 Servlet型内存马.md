## 1.前言

必读：

1.[Tomcat内存马](https://xz.aliyun.com/t/11988) (只读Servlet部分{包含调试和构造})

2.【1 内存马基础.md#Tomcat容器图示】

3.[java内存马专题1-servlet内存马](https://www.bilibili.com/video/BV1E84y1w77R?vd_source=1133eb6c801917b16b324ad28ad16d2e)

---

## Servlet注册过程

1. 调用StandardContext.createWrapper为servlet创建wrapper
2. 配置LoadOnStartup启动优先级
3. 配置ServletName
4. 配置ServletClass
5. addChild添加wrapper到Context
6. addServletMappingDecode添加映射

---

## Servlet优先级

在servlet的配置当中，<load-on-startup>1</load-on-startup>的含义是：

```md
 1.标记容器是否在启动的时候就加载这个servlet。
 2.当值为0或者大于0时，表示容器在应用启动时就加载这个servlet；
 3.当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。
 4.正数的值越小，启动该servlet的优先级越高。
```

---

## Servlet型内存马的核心构造

- 1.获取Context
- 2.生成恶意Servlet
- 3.生成Wrapper,封装进Context
- 4.添加映射

【详情代码查看：Code/ServletMemshell.jsp】