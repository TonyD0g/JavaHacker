import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;

class User implements Serializable {
    protected String name;
    protected User parent;

    public User(String name) {
        this.name = name;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
}

public class testUser {
   public static void main(String args[]) throws IOException {
       Base64.Encoder encoder = Base64.getEncoder();


       User user = new User("Bob");
       user.setParent(new User("Josua"));
       ByteArrayOutputStream byteSteam = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(byteSteam);
       oos.writeObject(user);
       System.out.println(encoder.encodeToString(byteSteam.toByteArray()));
   }
}