//自定义一个异常类DividedException，它继承了类Exception。
//DividedException类中定义两个构造方法，一个是无参数的构造方法，
//另一个是有参数的构造方法，都是使用基类的构造方法super(String)，定义异常的名字为给定的message，
//或者是使用默认的字符串“dividedException”

package The10Class;

import java.util.Scanner;


public class DividedExceptionMain {
    public static void main(String[] args) {
        ExceptionDemo ed = new ExceptionDemo();
        ed.display();
    }
}


class ExceptionDemo {
    public void display() {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int j = sc.nextInt();
        int k = 0;
        try {
            k = divide(i, j);
        } catch (DividedException e) {
            System.out.println("Exception  is:  " + e);
        } finally {
            System.out.println("k  =  " + k);
        }
    }

    public int divide(int i, int j) throws DividedException {
        if (j == 0) {
            throw new DividedException("Divided  by  zero");
        }
        return i / j;
    }
}
class DividedException extends Exception{
    DividedException(String msg){
        super(msg);

    }
    DividedException(){
        super("dividedException");
    }
}