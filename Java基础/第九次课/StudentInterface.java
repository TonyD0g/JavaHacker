package The9Class;

public class StudentInterface {
    public static void main(String[] args){
        Student1 stu1 = new Student1();
        stu1.printout();
    }
}
//MoveAble接口
interface MoveAble{
    void printout();
}

//继承了MoveAble接口,就必须重写MoveAble接口的所有方法
class Student1 implements MoveAble{
    public void printout(){
        System.out.println("move with owner");
    }

}
