package The2Class;

import java.util.Scanner;

//【问题描述】输入两个数，实现两个数的交换，并输出交换后的结果。
//
//        要点提示：
//
//        1)  假设有两个整型变量a和b，设计一个临时变量temp辅助交换
//
//        2)  交换过程如下：
//
//        temp = a;
//
//        a = b;
//
//        b = temp;
//
//        【输入形式】输入两个整数，放在一行，空格隔开
//        【输出形式】输出交换后的整数，放在两行，先输出后输入的整数
//        【样例输入】
//
//        2 14
//        【样例输出】
//
//        14
//
//        2
public class ExchangeNum {
    public static void main(String[] args) {
        int x, y, z;
        Scanner input = new Scanner(System.in);
        x = input.nextInt();
        y = input.nextInt();
        z = x;
        x = y;
        y = z;
        System.out.println(x);
        System.out.println(y);
    }
}
