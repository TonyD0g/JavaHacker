import java.io.*;

public class readSer {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // 填写 .ser文件的路径
        InputStream inputStream = new FileInputStream("");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object obj = objectInputStream.readObject();
        System.out.println(obj);
    }
}