package The8Class;

public class PhoneMobile {
    public static void main(String[] args) {

        Phone phone = new MobilePhone("HUAWEI", "13899999999", "130111111111111111");

        phone.display();    //重写

    }
}

class MobilePhone extends Phone{
    public String type;
    public String number;
    public String number2;

    public MobilePhone(String type, String number, String number2) {
        this.type = type;
        this.number = number;
        this.number2 = number2;
    }
    public void display(){
        System.out.println("Code="+number);
        System.out.println("Brand="+type);
        System.out.println("Ownerld="+number2);
    }
}
class Phone {
    public void display(){

    }

}
