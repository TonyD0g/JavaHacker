package JavaShell;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

// jdk8版本
public class Util {
    public static void main(String[] args) throws Exception {
        // 可能遇到的问题: https://blog.csdn.net/m0_55868614/article/details/120728373
        URL url = Util.class.getClassLoader().getResource("JavaShell/ByteCodeEvil.class");

        if (url == null) {
            System.out.println("[-] url is null");
            return;
        }


        URI uri = url.toURI();

        byte[] codeBytes = Files.readAllBytes(Paths.get(uri));
        String base = Base64.getEncoder().encodeToString(codeBytes);
        System.out.println(base);


    }

}