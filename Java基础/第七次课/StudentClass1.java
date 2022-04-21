package The7Class;

public class StudentClass1 {
    public static void main(String [] args){

        Student1 s = new Student1("zhangsan", 23, 86);

        s.display();

    }
}
class Student1 extends Person{

    public int score;

    Student1(String name,int age,int score){
        this.name=name;
        this.age=age;
        this.score=score;
    }
    public void display(){
        System.out.println("Name="+this.name);
    }
}

class Person{
    public String name;
    public int age;
}