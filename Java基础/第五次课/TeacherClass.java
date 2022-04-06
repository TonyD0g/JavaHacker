package The5Class;
//学校出的印度题
//要求输出：
//Phone Number=13800000000
//Name=zhang

public class TeacherClass {
    public static void main(String[] args) {
        MobilePhone phone = new MobilePhone("Apple", "13800000000");
        Student s = new Student("zhang", 23, 74, phone);
        phone.print();
    }
}

class Student {
    private String name;
    private int age;
    private double grade;
    private MobilePhone myPhone;
    public int x=1;

    //只能更改该处
    public Student(String name, int age, double grade, MobilePhone myPhone) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.myPhone = myPhone;
        this.myPhone.setOwner(this);
    }

    public void display() {
        System.out.println("Name=" + name);
    }
}

class MobilePhone {
    private String brand;
    private String code;
    private Student owner;

    public MobilePhone(String brand, String code) {
        this.brand = brand;
        this.code = code;
    }

    public Student getOwner() {
        return owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;

    }

    //只能更改该处
    public void print() {
        System.out.println("Phone Number=" + this.code);
        owner.display();
    }
}