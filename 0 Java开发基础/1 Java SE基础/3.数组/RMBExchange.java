package The3Class;

import java.util.Scanner;

//输入一个人民币的整数值（100以内以元为单位），编程找到用10元、5元、2元、1元表示的总数量的最小组合方式。
//
//        【输入形式】
//
//        从控制台输入一个整数值，表示以元为单位的人民币币值。
//
//        【输出形式】
//
//        向控制台输出四个整数（以空格分隔），分别表示兑换成的10元、5元、2元、1元人民币的数量，若没有某个币值，则对应输出0。
//
//        【样例1输入】
//
//        98
//
//        【样例1输出】
//
//        9 1 1 1
//【样例1说明】
//
//输入为98，表示98元人民币，把其兑换成10元、5元、2元、1元表示的总数量的最小组合方式为：
// 9个10元，1个5元，1个2元，1个1元，故输出：9 1 1 1

public class RMBExchange {
    public static void main(String[] args) {
        int out;
        Scanner input = new Scanner(System.in);
        int[] compose = new int[4];
        compose[0] = 1;
        compose[1] = 2;
        compose[2] = 5;
        compose[3] = 10;
        int x = input.nextInt();
        int i = 10, frequency = 4;
        while (frequency > 0) {
            i = compose[frequency - 1];
            out = x / i;
            x = x % i;
            System.out.print(out+" ");
            frequency--;
        }

    }
}
