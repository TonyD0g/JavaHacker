//问题描述】设计学生类Student和它的子类Undergraduate，要求：
//
//（1）Student类有name(姓名）和age（年龄）属性，有一个包含两个参数的构造方法，为两个属性赋值，一个show()方法输出Student的属性信息；
//
//（2）Undergraduate类增加一个major（专业）属性。有一个包含三个参数的构造方法，前两个参数用于给继承的name和age属性赋值，第三个参数给major属性赋值，一个show()方法输出Undergraduate的属性信息。
//
//（3）在测试类中分别 创建Student对象和Undergraduate对象，调用它们的show()。


package The7Class;

// 多构造方法时，必须要写 空参的构造方法！！！！！
public class StudentClass {
    public static void main(String[] args) {

        Student stu1 = new Student(20, "zhangsan");
        stu1.show();
        Undergraduate stu2 = new Undergraduate(21, "lisi", "math");
        stu2.show();
    }
}

class Student {
    public int age;
    public String name;

    Student() {

    }

    public Student(int age, String name) {
        this.name = name;
        this.age = age;

    }

    public void show() {
        System.out.println("information:");
        System.out.println("name=" + name);
        System.out.println("age=" + age);

    }
}

class Undergraduate extends Student {
    public String major;

    Undergraduate() {
        System.out.println("hi");
    }

    Undergraduate(int age, String name, String major) {

        this.age = age;
        this.name = name;
        this.major = major;
    }

    public void show() {
        System.out.println("information:");
        System.out.println("name=" + name);
        System.out.println("age=" + age);
        System.out.println("major=" + major);
    }
}
