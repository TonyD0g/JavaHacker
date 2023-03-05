package The2Class;
import java.util.Scanner;

public class StudentInfo {
    /**
     * main 方法是 Java 主方法
     * 功能：输入一个学生信息并显示
     * 参数：没有用到
     * 返回值：无
     */
    public static void main(String[] args) {
//name 学生姓名
        String name = "张三";
//age 学生年龄
        int age = 23;
//grade 学生成绩
        double grade;

        //定义一个 Scanner 类的对象
        Scanner sc = new Scanner(System.in);
//输入学生姓名,next()方法:读入字符串
        name = sc.next();
//输入学生年龄,nextInt()方法:读入int类型
        age = sc.nextInt();
//输入学生成绩,nextDouble()方法:读入double类型
        grade = sc.nextDouble();
//显示学生信息
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("成绩：" + grade);
    }
}

