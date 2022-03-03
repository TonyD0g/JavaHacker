package The2Class;

import java.util.Scanner;
//假如用C表示摄氏温度，F表示华氏温度，则有：F=C*9/5+32。
//输入一整数表示摄氏温度，根据该公式编程求对应的华氏温度，
// 结果小数点后保留一位有效数字

public class Temperature {
    public static void main(String[] args) {
        float c, f;
        Scanner input = new Scanner(System.in);
        c = input.nextFloat();
        f = ((c*9)/5)+32;
        System.out.println(String.format("%.1f",f));

    }

}


