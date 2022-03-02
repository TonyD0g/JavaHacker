package 第二节课;

import java.util.Scanner;   //导入Scanner类

//输入三个数,求其中的最大值
public class MaxNumber {
    public static void main(String[] args) {
        double max;
        double middle;

        System.out.println("Please input the num");

        //定义一个Scanner类的对象
        Scanner str = new Scanner(System.in);
        //调用nextDouble方法，相当于scanf一个double类型的数
        max = str.nextDouble();
        middle = str.nextDouble();
        max = MaxNum(middle, max);
        middle = str.nextDouble();
        max = MaxNum(middle, max);

        System.out.println("The Max num is:" + max);
    }

    public static double MaxNum(double middle, double max) {
        if (middle > max) {
            max = middle;
        }
        return max;
    }

}
