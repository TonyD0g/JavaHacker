//
//【问题描述】
//
//        一辆Car有(has)四个轮子(Wheels)和一个发动机(Engine)。现在要求用组合方法设计类Car、类Wheel和类Engine.
//
//        (1) 类Engine 有字符串属性type记录发动机的型号，
//
//        有构造方法，可设置发动机的型号
//
//        有方法start()启动引擎(输出下面样例中包含发动机型号和“starts”的字符串)
//
//        (2)类Wheel有字符串属性type记录轮胎的型号，有整数类型属性index记录当前轮胎编号(1:front-left，2:front-right，3:back-left，4:back-right)，
//
//        有构造方法，可设置轮胎的型号和编号
//
//        有方法roll()表示轮胎正在转动(输出下面样例中包含轮胎型号、轮胎位置和“rolling”的字符串)
//
//        (3)类Car有字符串属性model记录轿车的型号，有属性wheels[]和engine，分别是Wheel类对象数组和Engine类对象
//
//        有构造方法，参数是三个字符串，分别表示轿车的型号、轮胎型号和发动机的型号，
//
//        有方法changeWheel()可以改变指定轮胎的型号，
//
//        有方法start()，先输出下面样例中包含轿车型号和“firing”的字符串，然后调用engine的start()，再调用所有轮胎的roll()，最后显示轿车型号和“running”。
//
//
//
//        要求编程实现类Car、类Wheel和类Engine，使给定的Test类能正常运行，并实现指定的输出内容。
package The5Class;

import java.util.Scanner;

class Engine {
    public String type; //记录轮胎的型号
    public String engineType;   //发动机型号

    public Engine(String engineType) {    //可设置发动机的型号
        this.engineType = engineType;
    }

    public void start() {
        System.out.println(String.format("Engine<%s>  starts!", this.engineType));
    }
}

class Wheel {
    public String type; //记录轮胎的型号

    public Wheel(String type) { //设置轮胎的型号和编号
        this.type = type;
    }

    public void roll(int index) { //表示轮胎正在转动
        if (index == 0)
            System.out.println(String.format("Wheel front-left <%s> is rolling", this.type));
        else {
            if (index == 1) {
                System.out.println(String.format("Wheel front-right <%s> is rolling", this.type));

            } else {
                if (index == 2) {
                    System.out.println(String.format("Wheel back-left <%s> is rolling", this.type));

                } else
                    System.out.println(String.format("Wheel back-right <%s> is rolling", this.type));
            }
        }
    }
}

class Car {
    public String model;  //记录轿车的型号，
    public Wheel[] carWheel = new Wheel[5];       // Wheel类对象数组
    public  int index = 0;

    public Engine carEngine;

    public Car(String model, String wheel, String engine) {   //参数是三个字符串，分别表示轿车的型号、轮胎型号和发动机的型号，
        // model;

        this.model = model;
        //carWheel[index] = new Wheel(wheel);
        carEngine = new Engine(engine);
        for(index=0;index<4;index++){//一定要加入这一句
            carWheel[index]=new Wheel(wheel);
        }
        index = 0;
    }

    public void changeWheel(int x, String model) {  //改变指定轮胎的型号
        carWheel[x-1].type = model;

    }

    public void start() {
        System.out.println(String.format("Car<%s> is firing!", this.model));
        carEngine.start();
        for(int i=0;i<4;i++){
            carWheel[index].roll(index);
            index++;
        }
        System.out.println(String.format("Car<%s> is running", this.model));
    }
}

public class CarClass {
    public static void main(String[] args) {
        String wheel = "BridgeStone";
        String model = "BMW";
        String engine = "Mode L";
        Car carWheel = new Car(model, wheel, engine);
        carWheel.start();

        System.out.println("=================");

        model = "Benz";
        engine = "Model S";
        Car car2 = new Car(model, wheel, engine);
        car2.changeWheel(2, "Michelin");
        car2.start();
    }
}
