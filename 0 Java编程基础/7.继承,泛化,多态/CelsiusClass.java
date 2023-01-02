//定义摄氏温度类Celsius和华氏温度类Fahrenheit，
//属性都有温度数值和标志，方法都有显示温度值。
//在此基础上进行泛化，得到温度类Temperature。
//温度类属性有：温度值，方法有显示温度值。
//测试类代码如下，定义三个类的对象，实例化，显示各自的信息。
//编写实现摄氏温度类Celsius、华氏温度类Fahrenheit和温度类Temperature，
//可以将所编写的代码和测试类保存到一个文件中上传提交。
package The7Class;

public class CelsiusClass {
    public static void main(String[] args) {

        Temperature t = new Temperature(24);

        t.display();


        Celsius c = new Celsius(24);

        c.display();


        Fahrenheit f = new Fahrenheit(56);

        f.display();
    }
}

class Temperature {
    public double temper;

    Temperature(double temper) {
        this.temper=temper;
    }

    public void display() {
        System.out.println("temperature="+this.temper);
    }
}

class Celsius {
    public double temper;

    Celsius(double temper) {
        this.temper=temper;
    }
    public void display() {
        System.out.println("C "+"temperature="+this.temper);
    }
}

class Fahrenheit {
    public double temper;

    Fahrenheit(double temper) {
        this.temper=temper;
    }
    public void display() {
        System.out.println("F "+"temperature="+this.temper);
    }
}