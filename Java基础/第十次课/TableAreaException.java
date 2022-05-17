//题目定义了桌子类、圆桌类，在子类圆桌实现了接口CalculateAble的getArea()方法。修改程序如下：
//
//1)  自定义异常AreaException，表示计算面积出现异常。
//
//2)  定义接口方法getArea()抛出异常public double getArea() throws AreaException。
//
//3)  在实现类中具体抛出异常，例如圆桌半径小于0则抛出异常AreaException。


package The10Class;

import java.util.Scanner;

public class TableAreaException {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        double r = sc.nextDouble();

        RoundTable t1 = new RoundTable(3, 100, r);
        System.out.println("Area=" + t1.getArea());

    }

}

interface MoveAble {
    public void move();
}

abstract class TableInfo implements MoveAble {
    int legs;
    int hight;

    public TableInfo(int legs, int hight) {
        this.legs = legs;
        this.hight = hight;
    }

    public void move() {
        System.out.println("被人搬动了！");
    }
}

interface CalculateAble {
    public double getArea();
}

class RoundTable extends TableInfo implements CalculateAble {
    private double r;

    public RoundTable(int legs, int hight, double r) {
        super(legs, hight);
        this.r = r;
    }

    public double getArea() {
        double a = 0;
        try {
            a = test(r);
        } catch (AreaException e) {
            System.out.println("Exception  is:  " + e);

        }
        return a;
    }

    private double test(double r) throws AreaException {
        if (r <= 0) {
            throw new AreaException("Round  table  radius<=0");
        }
        return 3.14 * r * r;
    }


}

class AreaException extends RuntimeException {
    AreaException(String outcome) {
        super(outcome);
    }
}


