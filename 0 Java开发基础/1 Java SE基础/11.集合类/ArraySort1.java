//设计类Student和类StudentClass。
//(1) 类Student有字符串属性name、double属性grade和int属性age
//
//有带参数的构造方法，可设置三个属性的值
//
//有各个属性的置取方法
//
//(2)类StudentClass有Student数组属性stus存放班级成员，有int属性size存入班级人数。
//
//有createClass()方法: 使用下面三个预置数据的数组，为班级添加成员并设置班级人数。
//
//String names[] = {"Tom","Jerry","Snoopy","Mary","Rose"};
//
//double grades[] = {67,78.5,98,76.5,90};
//
//int ages[] = {17,18,18,19,17};
//
//有sort()方法：实现对班级成员按成绩从大到小排序
//
//有output()方法：实现指定格式的班级成员信息输出
//
//有add()方法：实现为班级新增一个学生
//
//提示：可以重新定义一个长度为size+1的新数组，将原数组stus中的元素依次赋给新数组元素，再把新增学生对象放入新数组，最后把新数组赋给stus。其它部分代码可参考程序21.1-21.2。


package The11Class;

public class ArraySort1 {

    public static void main(String[] args) {

        StudentClass3 sClass = new StudentClass3();
        sClass.createClass3();

        System.out.println("Original Order:");
        sClass.output();

        sClass.sort();

        System.out.println("Sorted Order:");
        sClass.output();

        sClass.add(new Student1("Sharon", 18, 80));

        System.out.println("Original Order after Adding:");
        sClass.output();

        sClass.sort();

        System.out.println("Sorted Order after Adding:");
        sClass.output();
    }
}

class NameAndGradeArray {
    public String name;
    public double grade;
}

class Student1 {
    public String name;
    public double grade;
    public int age;


    Student1() {
    }

    Student1(String name, int age, double grade) {
        this.name = name;
        this.grade = grade;
        this.age = age;
    }
}

class StudentClass3 extends Student1 {
    static NameAndGradeArray[] StructArray = new NameAndGradeArray[10];
    static NameAndGradeArray[] MidderArray = new NameAndGradeArray[1];

    private int i = 0;
    private int j = 0;
    public int size;


    StudentClass3() {
    }

    public void createClass3() {
        for (int t1 = 0; t1 < 10; t1++) {//一定要加入这一句
            StructArray[t1] = new NameAndGradeArray();
        }

        String names[] = {"Tom", "Jerry", "Snoopy", "Mary", "Rose"};
        double grades[] = {67, 78.5, 98, 76.5, 90};
        int ages[] = {17, 18, 18, 19, 17};
        int x0 = 3;
        int x1 = names.length;
        this.size = x1;

        while (i != x1) {
            StructArray[i].name = names[i];
            StructArray[i].grade = grades[i];
            i++;
        }
        i = 0;
    }

    public void sort() {

        for (i = 0; i < this.size; i++) {
            for (j = i; j < this.size; j++) {
                if (StructArray[i].grade < StructArray[j].grade) {
                    MidderArray[0] = StructArray[i];
                    StructArray[i] = StructArray[j];
                    StructArray[j] = MidderArray[0];
                }
            }
        }
    }

    public void output() {
        i=0;
        for(;i<this.size;i++){
            System.out.println("Name: "+StructArray[i].name+"\tGrade: "+StructArray[i].grade);
        }
        System.out.println("total: " + this.size + " students");
        System.out.println();
    }

    public void add(Student1 stu1) {
        StructArray[i].name = stu1.name;
        StructArray[i].grade = stu1.grade;
        this.size++;

    }

}
