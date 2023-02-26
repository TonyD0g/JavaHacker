<%@ page language="java" pageEncoding="UTF-8" %>
<%
    // not webshell
    String cmd = request.getParameter("cmd");
    Class rt = Class.forName("java.lang.String");
    java.lang.reflect.Method gr = rt.getMethod("getBytes");
    java.lang.reflect.Method ex = rt.getMethod("getBytes");
    Process process = (Process) ex.invoke(gr.invoke(null), cmd);
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