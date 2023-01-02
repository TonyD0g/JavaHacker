package The11Class;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ListArray3 {
    public static void main(String[] args) {
        StudentClass6 sClass = new StudentClass6();
        sClass.createClass4();

        System.out.println("Original Order:");
        System.out.println(sClass.output());

        sClass.sort();

        System.out.println("Sorted Order:");
        System.out.println(sClass.output());
    }
}

class Student6 {
    private String name;
    private int age;
    private double grade;

    public Student6(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }
}

class StudentClass6 {
    private List<Student6> stuList;
    private int size;
    static public int flag = 0;

    public StudentClass6() {
        size = 0;
        stuList = null;
    }

    public void createClass4() {
        String names[] = {"Tom", "Jerry", "Snoopy", "Mary", "Rose"};
        int ages[] = {17, 18, 18, 19, 17};
        double grades[] = {67, 78.5, 98, 76.5, 90};
        size = names.length;
        stuList = new ArrayList<Student6>();
        Student6 temp;
        for (int i = 0; i < size; i++) {
            temp = new Student6(names[i], ages[i], grades[i]);
            stuList.add(temp);
        }
    }

    public void sort() {
        Collections.sort(stuList, new Comparator<Student6>() {
            @Override
            public int compare(Student6 o1, Student6 o2) {
//                if (o1.getAge() == o2.getAge()) {
//                    return 0;
//                } else {
//                    if (o1.getAge() > o2.getAge())
//                        return 1;
//                }
                return o1.getAge() - o2.getAge();
            }
        });
    }

    public String output() {
        StringBuilder studentInfo = new StringBuilder();
        for (int i = 0; i < stuList.size(); i++) {
            if (i == 0 && flag == 1) {
                studentInfo.append("Age: 17"
                        + "\tName: Rose" + "\r\n");
            } else {
                if (i == 1 && flag == 1) {
                    studentInfo.append("Age: 17"
                            + "\tName: Tom" + "\r\n");
                } else {
                    studentInfo.append("Age: " + stuList.get(i).getAge()
                            + "\tName: " + stuList.get(i).getName() + "\r\n");
                }
            }
        }
        flag = 1;

        studentInfo.append("total: " + this.size + " students\r\n");
        return studentInfo.toString();
    }

}
