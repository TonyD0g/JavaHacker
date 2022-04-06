package The5Class;
//输出格式:
//Name=zhang
//Age=50
//Salary=4500.0
//Level=1
//My Table is:Round
public class WorkerClass2 {
    public  static  void  main(String[]  args)
    {
        TableInfo  t=new  TableInfo("Round",4,100,7850);
        Worker1  w1  =  new  Worker1("zhang",50,4500,1,t);
        w1.display();
    }
}

class  Worker1  {
    private  String  name;
    private  int  age;
    private  double  salary;
    private  int  level;
    public TableInfo table;//改这里

    //改这里
    public  Worker1(String  name,int  age,double  salary,int  level,TableInfo  table){
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.level=level;
        this.table=table;
    }

    public  Worker1(String  name)
    {
        this.name=name;
        this.age=0;
        this.salary=0;
        this.level=0;
    }

    public  void  setName(String  name){
        this.name=name;
    }
    public  String  getName(){
        return  this.name;
    }

    public  void  setAge(int  age){
        this.age=age;
    }
    public  int  getAge(){
        return  this.age;
    }

    public  void  setSalary(double  salary){
        this.salary=salary;
    }
    public  double  getSalary(){
        return  this.salary;
    }

    public  void  setLevel(int  level){
        this.level=level;
    }
    public  int  getLevel(){
        return  this.level;
    }

    public  void  setTable(TableInfo  table){
        this.table=table;
    }
    public  TableInfo  getTable(){
        return  this.table;
    }

    //改这里
    public  void  display(){
        System.out.println("Name="+this.name);
        System.out.println("Age="+this.age);
        System.out.println("Salary="+this.salary);
        System.out.println("Level="+this.level);
        this.table.print();
    }
}

class  TableInfo{
    String  shape;
    int  legs;
    int  hight;
    double  area;
    public    TableInfo(String  shape,  int  legs,int  hight,double  area){
        this.shape  =  shape;
        this.legs  =  legs;
        this.hight  =  hight;
        this.area  =  area;
    }
    public  void  print(){
        System.out.println("My  Table  is:"  +  shape);
    }
}