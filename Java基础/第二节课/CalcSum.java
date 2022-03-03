package The2Class;
import java.util.Scanner;

//从键盘输入三个整数，分别存入x,y,z三个整型变量中，计算并输出三个数的和以及平均值
//从键盘输入三个整数，整数之间以空格隔开。
//第一行为三个数的和，整数形式输出；
//第二行为三个数的平均值，浮点数形式输出，小数点后保留两位小数。
public class CalcSum {
    public static void main(String[] args) {
        int x,y,z,sum;
        double aver;
        Scanner input = new Scanner(System.in);
        x = input.nextInt();
        y = input.nextInt();
        z = input.nextInt();
        sum = x+y+z;
        aver = (double) (x+y+z)/3;
        System.out.println(sum);
        System.out.println(String.format("%.2f",aver));
        
        
    }
}
