package The3Class;

import java.util.Scanner;
//所谓回文数是指具有如下性质的整数：一个整数，当它的各位数字逆序排列，
//形成的整数与原整数相同，这样的数称为回文数。例如，素数11，373，其各位数字对换位置后仍然为11，373，
//因此这两个整数均为回文数。编写函数int loop(int a)，判断一个整数是否为回文数，
//如果a是回文数则返回1，否则返回0。编写程序loop.c，接收控制台输入的两个整数a，b。
//调用loop函数输出a到b之间（包括a和b）的所有回文数

public class PalindromeNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();
        if(a>b)
        {
            int t=a;
            a=b;
            b=t;
        }
        loop(a,b);
    }

    public static void loop(int a, int b) {
        int i, sum = 0;
        for (i = a; i <= b; i++) {
            if (handle(i) == i)
                System.out.println(String.format("%d" , i));
        }

    }

    public static int handle(int num) {
        int sum = 0;
        while (num > 0) {
            sum = sum * 10 + (num % 10);
            num /= 10;
        }
        return sum;
    }

}
