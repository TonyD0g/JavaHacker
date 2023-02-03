import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class AClient {
    public static void main(String[] args) throws Exception {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://127.0.0.1:1099");
        Context ctx = new InitialContext(env);
        String uri = "";
        if(args.length == 1) {
            uri = args[0];
            System.out.println("[*]Using lookup() to fetch object with " + uri);
            ctx.lookup(uri);
        } else {
            System.out.println("[*]Using lookup() to fetch object with rmi://127.0.0.1:1099/demo");
            ctx.lookup("demo");
        }
    }
}