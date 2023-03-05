package 第一节课;

import java.util.Date;

public class HelloData {
    public static void main(String[] args) {
        Date dt = new Date();
        String dtStr = dt.toString();
        System.out.println(dtStr);
    }
}