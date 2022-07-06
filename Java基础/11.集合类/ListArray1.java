package The11Class;

import java.util.List;
import java.util.ArrayList;

public class ListArray1 {
    public static void main(String[] args) {
        StudentClass4 sClass = new StudentClass4();
        sClass.createClass4();

        System.out.println("Original Order:");
        System.out.println(sClass.output());

        sClass.sort();

        System.out.println("Sorted Order:");
        System.out.println(sClass.output());
    }
}
class Student4{
    private String name;
    private int age;
    private double grade;
    public Student4(String name, int age, double grade){
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public Double getGrade() {
        return this.grade;
    }

    public String getName() {
        return this.name;
    }
}

class StudentClass4{
    private List<Student4> stuList;
    private int size;
    public StudentClass4(){
        size = 0;
        stuList = null;
    }
    public void createClass4() {
        String names[] = {"Tom","Jerry","Snoopy","Mary","Rose"};
        double grades[] = {67,78.5,98,76.5,90};
        int ages[] = {17,18,18,19,17};
        size = names.length;
        stuList = new ArrayList<Student4>();
        Student4 temp;
        for (int i = 0; i < size; i++) {
            temp = new Student4(names[i], ages[i], grades[i]);
            stuList.add(temp);
        }
    }
    public void sort(){
        Student4 temp;
        //冒泡排序
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size - i; j++) {
                if (stuList.get(j-1).getGrade()>stuList.get(j).getGrade()) {
                    temp = stuList.get(j-1);
                    stuList.set(j-1, stuList.get(j));
                    stuList.set(j,temp);
                }
            }
        }
    }
    public String output() {
        StringBuilder studentInfo = new StringBuilder();
        for (Student4 stu : stuList) {
            studentInfo.append("Name: " + stu.getName()
                    + "\tGrade: "+ stu.getGrade() + "\r\n");
        }
        studentInfo.append("total: " + this.size + " students\r\n");
        return studentInfo.toString();
    }
}

