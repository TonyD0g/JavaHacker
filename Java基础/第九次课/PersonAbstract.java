package The9Class;

public class PersonAbstract {
    public static void main(String[] args) {

        Person p1 = new Teacher("Mr wang");

        p1.speak();

        p1 = new Worker("Worker zhang");

        p1.speak();

    }
}

abstract class Person{
    String name;
    abstract public void speak();
}
class Teacher extends  Person{
    Teacher(String name) {
        this.name=name;
    }
    public void speak(){
        System.out.println(this.name+" said class is over!");
    }
}
class Worker extends  Person{
    Worker(String name) {
        this.name=name;
    }
    public void speak(){
        System.out.println(this.name+" said to have a rest!");
    }
}
