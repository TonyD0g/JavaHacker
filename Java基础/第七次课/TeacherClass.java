package The7Class;

public class TeacherClass {
    public static void main(String[] args)

    {

        Teacher zhang=new Teacher("zhang", 40, 4580, "VP");

        zhang.display();

    }
}

class Teacher extends  Person1{
    public int salary;
    public String major;

    Teacher(String name,int age,int salary,String major){
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.major=major;
    }
    public void display(){
        System.out.println("Name="+this.name);
    }
}

class Person1{
    public String name;
    public int age;
}