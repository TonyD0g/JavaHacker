package test;

import javax.naming.Context;
import javax.naming.InitialContext;

public class JNDIClient {
    public static void main(String[] args) throws Exception {

        String uri = "rmi://127.0.0.1:1099/refObj";
        Context ctx = new InitialContext();
        System.out.println("Using lookup() to fetch object with " + uri);
        ctx.lookup(uri);
    }
}