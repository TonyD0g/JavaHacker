import javax.naming.InitialContext;  
import java.rmi.registry.LocateRegistry;  
import java.rmi.registry.Registry;  
  
public class JNDIRMIServer {  
    public static void main(String[] args) throws Exception{  
        InitialContext initialContext = new InitialContext();  
 Registry registry = LocateRegistry.createRegistry(1099);  
 initialContext.rebind("rmi://localhost:1099/remoteObj", new RemoteObjImpl());  
 }  
}