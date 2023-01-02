package The7Class;

public class WorkerClass {
    public static void main(String [] args){

        Worker w = new Worker("zhang",45,4560,1);

        w.display();

    }
}

class Worker extends Person2{
    public int salary;
    public int major;

    Worker(String name,int age,int salary,int major){
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.major=major;
    }
    public void display(){
        System.out.println("Name="+this.name);
    }

}
class Person2{
    public String name;
    public int age;
}