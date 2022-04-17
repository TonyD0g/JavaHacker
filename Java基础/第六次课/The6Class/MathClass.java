package The6Class;
//根据一个数学公式求结果，懒得贴了
import java.util.Scanner;

public class MathClass {
    public static void main(String[] args) {
        int x, y, z;
        Scanner in = new Scanner(System.in);

        x = in.nextInt();
        y = in.nextInt();
        z = in.nextInt();
        in.close();

        int result;

        if (x < 0) {

            result = MyMath.f();

        } else if (x >= 0 && y < 0) {

            result = MyMath.f(x);

        } else if (x >= 0 && y >= 0 && z < 0) {

            result = MyMath.f(x, y);

        } else {

            result = MyMath.f(x, y, z);

        }

        System.out.println(result);
    }
}

class MyMath {
    public static int f(){
        return 0;
    }
    public static int f(int x){
        return x*x;
    }
    public static int f(int x,int y){
        return x*x + y*y;
    }
    public static int f(int x,int y,int z){
        return x*x + y*y+ z*z;
    }
}
