## 1.前言：

学习来源:
深入分析Gadgetinspector核心代码 - 4ra1n

---

## 2.简单梳理：

我对原文进行了简写，详情自行查阅原文：

- 输入一个jar文件，可通过URL路径远程加载

- 使用某个方法去分析所输入的jar文件的 **各种继承关系 ，获取所有的方法信息，类信息和继承信息**

- 利用上述获取到的信息进行 **参数的传递分析**和**函数的拓扑逆排序**，得到**污点数据流**

  （通过模拟操作数栈和局部变量表进行分析）

---

## 3.模块最简概述

{想要详情自行查阅所给文章和代码，不做赘述}

**3-1 加载**

目的：获取jar字节码

**3-2 ClassVisit 和 MethodVisit执行顺序**

没啥好说的，直接看文章：

https://lsieun.github.io/java-asm-01/class-visitor-intro.html#mark_2_0_0

https://lsieun.github.io/java-asm-01/method-visitor-intro.html#mark_2_0_0

**3-3 回收class文件**

目的：在JVM执行完后清理jar包中的class文件(利用hook)



**原文不理解的地方：**

3.2 visitMethod 

---

