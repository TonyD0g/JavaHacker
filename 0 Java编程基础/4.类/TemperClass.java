package The4Class;

import java.util.Scanner;

//【问题描述】
//
//编写Temperature类，表示摄氏和华氏两种温度，具有两个属性：
//
//double型浮点数表示温度值，
//
//字符表示单位，C表示摄氏，F表示 华氏。
//
//具有构造方法：可设置度的数值和计量单位。
//
//具有四个普通方法：
//
//getC()方法，返回对应的摄氏度，结果保留一位小数
//
//getF()方法，返回对应的华氏度，结果保留一位小数
//
//toString()方法，返回一个形如 "Temperature is 30.0F"的字符串。
//
//compareTo()方法：参数是另一个Temperature对象，根据两个对象所表示的温度值大小的比较结果 ，返回一个字符串：等于返回“ equals to ”， 小于返回“ less than ” 大于返回“ greater than ”
//
//在Test类中分别 对比0C和30F，-40C和-40F，98C和212F，并输出相应信息。
//
//要求编程实现Temperature类，使给定的Test类能正常运行，并实现指定的输出内容。
class Temperature {
    public double temper;
    public char symbol;

    public Temperature(double inputTemper, char inputSymbol) {
        this.temper = inputTemper;
        this.symbol = inputSymbol;
    }

    public String toString() {
        String output = "Temperature is ";
        return output + this.temper + this.symbol;
    }

    //温度转换
    public String compareTo(Temperature t2) {
        String outcome;
        if (this.symbol == t2.symbol) {
            outcome = this.StringCompare(t2);
            return outcome;
        } else {
            if (t2.symbol == 'C') {
                //C＝5×（F－32）／9，     F＝9×C／5＋32
                //  C   =>  F
                t2.temper = (9 * t2.temper) / 5 + 32;
                t2.symbol = 'F';
                outcome = this.StringCompare(t2);
                return outcome;
            } else {
                //  F   =>  C
                t2.temper = 5 * (t2.temper - 32) / 9;
                t2.symbol = 'C';
                outcome = this.StringCompare(t2);
                return outcome;
            }
        }
    }

    public String StringCompare(Temperature t2) {
        if (this.temper > t2.temper) {
            return " greater than ";
        } else {
            if (this.temper < t2.temper) {
                return " less than ";
            } else {
                return " equals to ";
            }
        }
    }
    public double getF(){
        this.temper = (9 * this.temper) / 5 + 32;
        this.symbol = 'F';
        return this.temper;
    }
    public double getC(){
        this.temper = 5 * (this.temper - 32) / 9;
        this.symbol = 'C';
        return this.temper;
    }
}

public class TemperClass {
    public static void main(String[] args) {
        Temperature t1 = new Temperature(0, 'C');

        Temperature t2 = new Temperature(30, 'F');

        Temperature t3 = new Temperature(-40, 'C');

        Temperature t4 = new Temperature(-40, 'F');

        Temperature t5 = new Temperature(98, 'C');

        Temperature t6 = new Temperature(212, 'F');
        String comStr;
        System.out.println("t1: " + t1.toString());
        System.out.println("t2: " + t2.toString());

        comStr = t1.compareTo(t2);
        System.out.println("t1 is " + comStr + " t2");
        System.out.println("-");

        System.out.println("t3 in F is : "+t3.getF());
        comStr = t3.compareTo(t4);
        System.out.println("t3 is "+comStr+" t4");
        System.out.println("-");

        System.out.println("t6 in C is : "+t6.getC());
        comStr = t5.compareTo(t6);
        System.out.println("t5 is "+comStr+" t6");
    }

}
