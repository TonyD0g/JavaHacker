package The3Class;

import java.util.Scanner;

//假设税前工资和税率如下（s代表税前工资，t代表税率）：
//        s<1000    t=0%
//        1000<=s<2000      t=10%
//        2000<=s<3000  t=15%
//        3000<=s<4000     t=20%
//        4000<=s             t=25%
//        编写一程序，要求用户输入税前工资额，然后用多分支if语句计算税后工资额。

public class Salary {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double s = input.nextDouble();
        double t, finalSalary = 0;
        if (s > 4000) {
            t = 0.25;
            finalSalary = s - s * t;
            System.out.printf("%.2f",finalSalary);
        } else {
            if (s >= 3000 && s < 4000) {
                t = 0.20;
                finalSalary = s - s * t;
                System.out.printf("%.2f",finalSalary);
            } else {
                if (s >= 2000 && s < 3000) {
                    t = 0.15;
                    finalSalary = s - s * t;
                    System.out.printf("%.2f",finalSalary);
                } else {
                    if (s >= 1000 && s < 2000) {
                        t = 0.10;
                        finalSalary = s - s * t;
                        System.out.printf("%.2f",finalSalary);
                    } else {
                        finalSalary = s;
                        System.out.printf("%.2f",finalSalary);
                    }
                }
            }
        }
    }
}
