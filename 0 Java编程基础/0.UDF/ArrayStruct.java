package UDF;
//  结构体数组
import java.util.Scanner;

class p{
    int x,y;
}
public class ArrayStruct {
    static Scanner in=new Scanner(System.in);

    public static  void main(String[] args) {
        int n=in.nextInt();
        p a=new p();
        a.x=10;
        //最大n值  :   3
        p[] b=new p[3];
        for(int i=0;i<3;i++){//一定要加入这一句
            b[i]=new p();
        }
        for(int i=0;i<n;i++){
            b[i].x=1;
            b[i].y=2;
        }
        for(int i=0;i<n;i++){
            System.out.println(b[i].x+b[i].y);
        }
    }
}





