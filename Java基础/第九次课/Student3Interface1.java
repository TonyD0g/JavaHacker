//Arrays.sort()方法是Java已经实现好的工具类Arrys中用于对数组元素进行排序的方法。它要求数组中的元素已经实现了Comparable接口。
//
//        Comparable接口是Java已经实现好的一个接口，该接口主要代码如下：
//public interface Comparable<T> {
//    public int compareTo(T o);
//}
//接口的CompareTo()方法定义为：
//        返回负数：如果调用对象比参数o"小"。这里"小"的具体含义由程序员自己通过代码来定义；
//        返回正数：如果调用对象比参数o"大"。这里"大"的具体含义由程序员自己通过代码来定义；
//        返回零：如果调用对象与参数o"相等"。这里"相等"的具体含义由程序员自己通过代码来定义。
//
//        提示：可以查看JavaAPI文档，了解更多相关信息。


//JavaAPI文档：https://www.runoob.com/manual/jdk11api/java.rmi/module-summary.html


//要求编程实现Student333类，使给定的Test类能正常运行，并实现按字母顺序输出姓名。
package The9Class;

import java.util.Arrays;


public class Student3Interface1 {

    public static void main(String[] args) {
        Student3[] s = new Student3[4];
        s[0] = new Student3("May");
        s[1] = new Student3("Jack");
        s[2] = new Student3("Armstrong");
        s[3] = new Student3("Linda");
        //sort方法去调用compareTo方法
        Arrays.sort(s);
        System.out.println("according to length order:");
        for (Student3 stu : s) {
            System.out.println(stu.getName());
        }
    }
}


//调用Comparable接口
//public interface Comparable<T> {
//    public int compareTo(T o);
//}
class Student3 implements Comparable<Student3> {

    private String name;

//    String t = "o";

    public Student3(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override

    public int compareTo(Student3 object1) {

        if (this.name.length() > object1.name.length()) {
            return 1;
        } else if (this.name.length() < object1.name.length()) {
            return -1;
        } else
            return 0;
    }

}
