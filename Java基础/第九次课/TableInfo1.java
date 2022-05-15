package The9Class;

public  class  TableInfo1{
    public  static  void  main(String[]  args)
    {
        TableInfo  t1=new  RoundTable  (3,  100,  30.0);
        TableInfo  t2=new  RectangleTable(4,  100,  40.0,  60.0);
        System.out.println("Round  Table  Area"  +  t1.tableArea());
        System.out.println("Rect  Table  Area"  +  t2.tableArea());
    }
}

//抽象类，所有子类的根基。不实现具体功能
abstract  class  TableInfo{
    int  legs;
    int  hight;

    public    TableInfo(  int  legs,int  hight){
        this.legs  =  legs;
        this.hight  =  hight;
    }


    public abstract  double   tableArea();
}

class  RectangleTable  extends  TableInfo{
    private  double  len;
    private  double  width;

    public  RectangleTable(  int  legs,  int  hight,  double  len,  double  width){
        super(legs,hight);
        this.len  =  len;
        this.width  =  width;
    }

    public  double  tableArea(){
        return  len  *  width;
    }
}

class  RoundTable  extends  TableInfo{
    private  double  r;

    public  RoundTable(  int  legs,  int  hight,  double  r){
        super(legs,  hight);
        this.r  =  r;
    }

    public  double  tableArea(){
        return  3.14  *  r  *  r;
    }
}