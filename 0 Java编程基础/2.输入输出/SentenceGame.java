package The2Class;
import java.util.Scanner;
//【问题描述】编程实现以下功能：要求用户从键盘逐行输入某个喜欢的颜色、喜欢的食物、喜欢的动物以及朋友的姓名。然后程序输出下面的内容。括号中的内容用用户输入来替换：
//
//        I had a dream that (姓名) ate a (颜色) (动物) and said that it tasted like (食物)!
//
//        【输入形式】颜色
//
//        食物
//
//        动物
//
//        朋友姓名
//
//        【输出形式】I had a dream that 姓名  ate a 颜色  动物  and said that it tasted like  食物 !
//
//        【样例输入】blue
//                  hamburger
//                  dog
//                  Tom
//        【样例输出】I had a dream that Tom ate a blue dog and said that it tasted like hamburger!
public class SentenceGame {
    public static void main(String[] args){
        System.out.println("Please input your favorite color, food, animal and the best friend line by line:");
        Scanner input = new Scanner(System.in);
        String color = input.nextLine();
        String food = input.nextLine();
        String annimal = input.nextLine();
        String name = input.nextLine();
        System.out.println(String.format("I had a dream that %s ate a %s %s and said that it tasted like %s!",name,color,annimal,food));


    }
}
