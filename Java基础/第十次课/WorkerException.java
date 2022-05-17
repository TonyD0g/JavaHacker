package The10Class;

public class WorkerException {
    public  static  void  main(String  []  args){
        MobilePhone  phone  =  new  MobilePhone("Apple",  "13811111111");
        Student  s  =  new  Student("zhangsan",  23,  74,  phone);
        s.display();
        phone.print();
    }
}

class  Student{
    private  String  name;
    private  int  age;
    private  double  grade;
    private  MobilePhone  myPhone;

    public  Student(String  name,  int  age,  double  grade,  MobilePhone  myPhone){
        this.name  =  name;
        this.age  =  age;
        this.grade  =  grade;
        this.myPhone  =  myPhone;
        this.myPhone.setOwner(null);
    }

    public  void  display(){
        System.out.println("Name="  +  name);
    }
}

class  MobilePhone{
    private  String  brand;
    private  String  code;
    private  Student  owner;

    public  MobilePhone(String  brand,  String  code){
        this.brand  =  brand;
        this.code  =  code;
    }

    public  Student  getOwner(){
        return  owner;
    }

    public  void  setOwner(Student  owner){
        this.owner  =  owner;
    }
    public  void  print(){
        System.out.println("Code="  +  code);
        try{
            owner.display();
        }
        catch(Exception e)
        {
            System.out.println("Error,  Owner  is  null");
        }
    }
}