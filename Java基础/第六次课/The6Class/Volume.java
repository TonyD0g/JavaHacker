package The6Class;

public  class  Volume{
    public  static  void  main(String  []  args){

        BodyVolume  v1  =  new  BodyVolume();
        BodyVolume  v2  =  new  BodyVolume();

        System.out.println("Ball  Volume="  +  v1.getVolume(10));
        System.out.println("Cylinder  Volume="+v2.getVolume(10,20));
    }
}

class  BodyVolume{
    public double getVolume(double r)
    {
        return  (4/3)*3.14*r*r*r;
    }

    public double getVolume(double r,double h)
    {
        return  3.14*r*r*h;
    }
}
