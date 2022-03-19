package The4Class;

import java.util.Scanner;

//【问题描述】
//
//        IntReceiver类可以接受指定范围内的整数值，具有下面四个属性：
//
//        最小的可接受值
//
//        最大的可接受值
//
//        输入提示字符串
//
//        错误消息字符串
//
//        并具有一个方法：
//
//        getValue()，功能是显示 输入提示字符串，然后读取键盘输入的一个整数。如果读取的值不在允许范围内，则显示 错误消息字符串，并重新要求用户输入一个新值，重复以上步骤直到输入了一个可接受的值。最后返回读取到的整数。
//
//        要求编程实现IntReceiver类，使给定的Test类能正常运行，并实现指定的输出内容。指定范围为0-100.

class IntReceiver {
    public int value;

    public static int getValue(IntReceiver ir) {
        int flag, num;
        Scanner input = new Scanner(System.in);
        do {

            System.out.print("input an integer:");
            num = input.nextInt();
            ir.value = num;

            if (ir.value > 0 && ir.value < 100) {
                //System.out.println("The value is " + ir.value);
                flag = 1;
            } else {
                System.out.println("invalid input!");
                flag = 0;
            }
        } while (flag != 1);
        return ir.value;
    }
}


public class AllowInteger {

    public static void main(String[] args) {
        int value, num;
        IntReceiver ir = new IntReceiver();
        value = ir.getValue(ir);
        System.out.println("The value is "+value);
    }

}

