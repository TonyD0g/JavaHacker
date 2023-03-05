//package The11Class;
//
//import java.util.*;
//
//public class TreeSetSort1 {
//    public static void main(String[] args) {
//        StudentClass5 sClass = new StudentClass5();
//        sClass.createClass4();
//
//        System.out.println("Original Order:");
//        System.out.println(sClass.output());
//    }
//}
//
//class StudentInfo implements Comparable<StudentInfo> {
//    private String name;
//    private int age;
//    private double grade;
//
//    //自动调用该方法进行比较排序
////    如果指定的数与参数相等返回 0。
////    如果指定的数小于参数返回 -1。
////    如果指定的数大于参数返回 1。
//    @Override
//    public int compareTo(StudentInfo o1) {
//        if (o1.getGrade() > this.grade) {
//            return 1;
//        }
//        else
//        {
//            if(o1.getGrade() < this.grade)
//                return -1;
//        }
//        return 0;
//    }
//
//    public StudentInfo(String name, int age, double grade) {
//        this.name = name;
//        this.age = age;
//        this.grade = grade;
//    }
//
//    public Double getGrade() {
//        return this.grade;
//    }
//
//    public String getName() {
//        return this.name;
//    }
//}
//
//class StudentClass5 {
//    TreeSet<StudentInfo> treeSet = new TreeSet<>();
//    private List<StudentInfo> stuList;
//    private int size;
//
//
//    public StudentClass5() {
//        size = 0;
//        stuList = null;
//    }
//
//    public void createClass4() {
//        String names[] = {"Tom", "Jerry", "Snoopy", "Mary", "Rose"};
//        double grades[] = {67, 78.5, 98, 76.5, 90};
//        int ages[] = {17, 18, 18, 19, 17};
//
//        size = names.length;
//        TreeSet stuList = new TreeSet();
//        StudentInfo temp;
//        for (int i = 0; i < size; i++) {
//             temp = new StudentInfo(names[i], ages[i], grades[i]);
//            treeSet.add(temp);
//            temp=null;
//        }
//    }
//
//    public String output() {
//        StringBuilder studentInfo = new StringBuilder();
//        Iterator<StudentInfo> it = treeSet.iterator();
//
//        while(it.hasNext()){
//            StudentInfo stu = it.next();
//            studentInfo.append("Name: " + stu.getName()
//                    + "\tGrade: " + stu.getGrade() + "\r\n");
//        }
//        studentInfo.append("total: " + this.size + " students\r\n");
//        return studentInfo.toString();
//    }
//
//}
//
//
