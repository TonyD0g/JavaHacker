package The9Class;

public class PhoneAbstract {
    public static void main(String[] args){
    MoblePhone1 phoneTest = new MoblePhone1();
    phoneTest.PhoneObject("HUAWEI","130111111111111111");
        System.out.println("Brand is"+phoneTest.Brand);
        System.out.println("OwnerId is"+phoneTest.OwnerId);

    }
}

//抽象类本质上还是个类，只是抽象方法没有任何功能，需要子类自行去重写方法
//public abstract class Phone1 属于公有抽象方法，需要单独的一个文件
abstract class Phone1 {
    String Brand;
    String OwnerId;
    abstract void PhoneObject(String Brand,String OwnerId);
}

class MoblePhone1 extends Phone1{
     void PhoneObject(String Brand,String OwnerId){
        this.Brand=Brand;
        this.OwnerId=OwnerId;
    }
}

