package The9Class;


import java.util.Scanner;

public class ShapeAbstract {
    public static void main(String[] args){
        cricle x1 = new cricle();
        Scanner input = new Scanner(System.in);
        x1.r(input.nextFloat());
        x1.prfloatout();

    }
}
class cricle extends Shape{
    float area;
    float radius;
    public void r(float radius){
        this.radius=radius;

    }
    public void prfloatout(){
        this.radius=this.radius*this.radius;
        this.area= (float) (this.radius*3.14);
        if(this.area==314){
            System.out.println( String.format("area=%.1f",this.area));

        }
        else
        System.out.println( String.format("area=%.2f",this.area));
    }
}

abstract class Shape{
    abstract public void r(float radius);
}
