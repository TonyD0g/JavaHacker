package The5Class;
//要求输出:
//Name=zhang
//Phone Number=13800000000

public class TeacherClass2 {
    public static void main(String[] args) {
        MobilePhone1 phone1 = new MobilePhone1("Apple", "13800000000");
        Student1 s = new Student1("zhang", 23, 74, phone1);
        s.display1();
    }

    ;
}

class Student1 {
    private String name;
    private int age;
    private double grade;
    private MobilePhone1 myPhone;

    //只能改这里
    public Student1(String name, int age, double grade, MobilePhone1 myPhone) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.myPhone = myPhone;

    }

    //只能改这里
    public void display1() {
        System.out.println("Name=" + this.name);
        this.myPhone.print();

    }
}

class MobilePhone1 {
    private String brand;
    private String code;

    public MobilePhone1(String brand, String code) {
        this.brand = brand;
        this.code = code;
    }

    public void print() {
        System.out.println("Phone  Number=" + code);
    }
}
