//定义了一个接口PayAble，包含计算电话话费的方法pay()。
// 在手机类定义中增加计算话费异常，如果话费小于0则抛出异常。
//要点提示：1)  自定义一个异常类，表示话费小于0的异常；
// 2)  计算话费时如果小于0则抛出异常，在测试类中处理异常。

package The10Class;

import java.util.Scanner;

public class PayAbleException {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int times = sc.nextInt();
        double price = sc.nextDouble();

        double pay2 = 0;

        MobilePhone2 phone1 = new MobilePhone2("13899999999", times, price);

        try {
            pay2 = phone1.pay();
        } catch (PayException e) {
            System.out.println("Exception  is" + e);
        }
        System.out.println("Fee=" + pay2);


    }
}


abstract class Phone {
    private String code;

    public Phone(String code) {
        this.code = code;
    }

    public abstract void display();

}

interface PayAble {
    public double pay() throws PayException;
}

class MobilePhone2 extends Phone implements PayAble {
    private int time;
    private double price;

    public MobilePhone2(String code, int time, double price) {
        super(code);
        this.time = time;
        this.price = price;
    }


    public double pay() throws PayException {
        double p;
        p = time * price;
        if (p <= 0) {
            throw new PayException("Fee  is  0!");
        }
        return p;
    }


    public void display() {
    }

}
class PayException extends RuntimeException{
    PayException(String msg){
        super(msg);
    }
}