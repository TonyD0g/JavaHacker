package The2Class;
//显示一张桌子的信息，包括桌子的形状(长方形、方形、圆形、椭圆形；使用Rect、Square、Circle、Ellipse)、腿数、高度、桌面面积。定义变量来保存桌子的信息，并显示各个信息的值。要点提示：
//
//        1)  显示桌子信息：形状、腿数、高度、面积
//
//        2)  注意各个变量的数据类型，桌子的形状可以用字符串String来存储

import org.w3c.dom.css.Rect;

//【输入形式】给变量初始值：形状：方形，腿数：4，高度：80，面积：1200
//【输出形式】
//
//        Shape:Rect
//
//        Legs:4
//
//        Hight:80
//
//        Area:1200
public class ShowTableInfo {
    public static void main(String[] args) {
        String Shape = "Rect";
        int Legs = 4;
        int Hight = 80;
        int Area = 1200;
        System.out.println("Shape:"+Shape);
        System.out.println("Legs:"+Legs);
        System.out.println("Hight:"+Hight);
        System.out.println("Area:"+Area);
    }
}
