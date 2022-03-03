package The2Class;

import java.util.Scanner;
//BMI指数（身体质量指数，简称体质指数，Body Mass Index），是世界公认的一种评定胖瘦程度的分级方法。用体重（公斤）除以身高（米）的平方得到的数字：
//
//        （BMI）=体重（kg）÷身高^2（m）
//
//        成人的BMI数值范围：
//
//        体重过轻：BMI<18.5
//
//        正常体重：18.5<= BMI  <24
//
//        体重过重：24<=  BMI < 27
//
//        肥胖：27<= BMI
//
//        编程实现以下功能：用户输入体重和身高，之后程序输出相应的BMI值及胖瘦程度。
//
//        BMI值输出四舍五入之后保留一位小数的结果。
//
//        【输入形式】身高
//
//        体重
//        【输出形式】你的BMI值
//
//        你的胖瘦程度
//
//        【样例输入】Enter your height(m): 1.7
//
//        Enter your weight(kg): 65
//        【样例输出】Your BMI is 22.49134948096886
//
//        You are Normal weight.
//
//        【样例说明】

public class BMI {
    public static void main(String[] args) {
        double BMI;
        Scanner input = new Scanner(System.in);
        float height = input.nextFloat();
        System.out.print("Enter your height(m): " );//+ height
        float weight = input.nextFloat();
        System.out.print("Enter your weight(kg): " );//+ weight
        BMI = weight / (height * height);
        System.out.println("Your BMI is " + String.format("%.1f", BMI));//
        // 18.5 24 27
        if (BMI >= 27)
        {
            System.out.println("You are Obese.");
        } else
        {
            if (BMI >= 24 && BMI < 27)
            {
                System.out.println("You are Overweight.");
            } else
            {
                if (BMI >= 18.5 && BMI < 24)
                {
                    System.out.println("You are Normal weight.");
                } else
                {
                    System.out.println("You are Underweight.");
                }
            }

        }
        //Underweight. Obese. Overweight.

    }
}
