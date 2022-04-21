package The6Class;

//public  class  StudentInstantiation2{
//    public  static  void  main(String  []  args){
//        Student  s1  =  new  Student("zhang",  23,  74);
//        s1.display();
//        Student  s2  =  new  Student("lisi",  20,  65);
//        s2.display();
//        Student  s3  =  new  Student("wang",  21,  93);
//        s3.display();
//    }
//}
//
//class  Student1{
//    private  String  name="";
//    private  int  age=0;
//    private  double  grade=0;
//    private  static  int  counter  =  0;
//
//    public  Student(String  name,  int  age,  double  grade){
//        this.name  =  name;
//        this.age  =  age;
//        this.grade  =  grade;
//        //这里填空
//        counter++;
//
//    }
//
//    public  void  display(){
//        System.out.println("Instance  order="  +  counter  +  "  Name="  +  name);
//    }
//}



public  class  StudentInstantiation2{
    public  static  void  main(String  []  args){
        Student2  s  =  new  Student2("zhang",  23,  74);
        s.display();
        s.display(60);
    }
}

class  Student2{
    private  String  name;
    private  int  age;
    private  double  grade;


    Student2()
    {
        this.name  =  "";
        this.age  =  0;
        this.grade  =  0;
    }

    Student2(String name,int age,int grade)
    {
        this.name  =  name;
        this.age  =  age;
        this.grade  =  grade;
    }

    public  void  display(){
        System.out.println("Name="  +  name);
    }

    public  void  display(int  passLine){
        System.out.println("Name="  +  name);
        System.out.println("Age="  +  age);
        if(grade>=  passLine){
            System.out.println("Pass!");
        }
        else{
            System.out.println("Fail!");
        }
    }
}