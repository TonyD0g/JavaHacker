package The5Class;
//输出形式:
//Name=zhang
//Age=55
//Salary=2874.0
//Level=3
//Helmet=1001,white,2016-12-12

public class WorkerClass {
    public static void main(String[] args) {

        Helmet h = new Helmet("1001", "white", "2016-12-12");
        Worker w = new Worker("zhang", 55, 2874, 3, h);

        w.display();
    }
}

class Worker {
    private String name;
    private int age;
    private double salary;
    private int level;
    public Helmet myHelmet;        //还有这里

    //只能改这里
    public Worker(String name, int age, double salary, int level, Helmet myHelmet) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.level = level;
        this.myHelmet = myHelmet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setHelmet(Helmet myHelmet) {
        this.myHelmet = myHelmet;
    }

    public Helmet getHelmet() {
        return this.myHelmet;
    }

    //只能改这里
    public void display() {
        System.out.println("Name="+this.name);
        System.out.println("Age="+this.age);
        System.out.println("Salary="+this.salary);
        System.out.println("Level="+this.level);
        this.myHelmet.display();
    }
}

class Helmet {
    private String code;
    private String color;
    private String checkDate;

    public Helmet(String code, String color, String checkDate) {
        this.code = code;
        this.color = color;
        this.checkDate = checkDate;
    }

    public void display() {
        System.out.println("Helmet=" + code + "," + color + "," + checkDate);
    }
}
