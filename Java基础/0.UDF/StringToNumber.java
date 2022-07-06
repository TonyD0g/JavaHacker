package UDF;

public class StringToNumber {
    public static void main(String[] args){
        //Sting 转 double
        String str1 = "30.1";
        double num1 = Double.parseDouble(str1);
        System.out.println(num1);
        //Sting 转 int
        String str2 = "30";
        int num2 = Integer.parseInt(str1);
        System.out.println(num1);
    }


}
