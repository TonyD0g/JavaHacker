package The6Class;
//StudentInstantiation
public  class  StudentInstantiation1{
    public  static  void  main(String  []  args){
        Student  s1  =  new  Student("zhang",  23,  74);
        s1.display();
        Student  s2  =  new  Student("lisi",  20,  65);
        s2.display();
        Student  s3  =  new  Student("wang",  21,  93);
        s3.display();
    }
}

class  Student{
    private  String  name="";
    private  int  age=0;
    private  double  grade=0;
    private  static  int  counter  =  0;

    public  Student(String  name,  int  age,  double  grade){
        this.name  =  name;
        this.age  =  age;
        this.grade  =  grade;
        counter  ++;
    }

    public  void  display(){
//        Instance order=1 Name=zhang
//
//        Instance order=2 Name=lisi
//
//        Instance order=3 Name=wang
        System.out.println(String.format("Instance order=%d Name=%s",counter,this.name));

    }
}
