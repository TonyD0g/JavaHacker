package The8Class;

public class Inherit {
    public static void main(String[] args) {
        Person[] people = new Person[5];
        people[0] = new Person("Tom");
        people[1] = new Teacher("Kevin", "professor");
        people[2] = new Student("Jerry", 21);
        people[3] = new Undergraduate("John", 22, 3);
        people[4] = new Graduate("Mary", 23, "computer");

        for (Person p: people)
        {
            p.display();
            System.out.println(">>>");
        }
    }
}

class Graduate extends Student {
    public String major;

    public Graduate(String name, int studentNumber, String major) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.major = major;
    }

    public void display() {
        super.display();
        System.out.println("Major: "+major);
    }
}

class Undergraduate extends Student {
    public int grade;

    public Undergraduate(String name, int studentNumber, int grade) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.grade = grade;
    }

    public void display() {
        super.display();
        System.out.println("Grade: "+grade);
    }
}

class Student extends Person {
    public int studentNumber;

    public Student(String name, int studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
    }

    public void display() {
        super.display();
        System.out.println("StudeneNumber: "+studentNumber);
    }

    public Student() {
    }
}

class Teacher extends Person {
    public String professionalTitle;

    public Teacher(String name, String professionalTitle) {
        this.name = name;
        this.professionalTitle = professionalTitle;
    }

    public void display() {
        super.display();
        System.out.println("Professional Title: "+professionalTitle);
    }

    public Teacher() {

    }
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("Name: "+name);
    }

    public Person() {
    }
}
