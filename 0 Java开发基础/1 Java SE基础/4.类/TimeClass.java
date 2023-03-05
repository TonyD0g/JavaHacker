package The4Class;

import java.util.Scanner;
//【问题描述】
//
//MyTime类可以表示时间（小时+分钟），具有两个整数属性和一个字符串属性：
//
//小时值
//
//分钟值
//
//错误消息提示
//
//并具有一个构造方法(与类同名的方法)和三个普通方法：
//
//构造方法：接收两个整数作为参数，并进行合理性判断（小时值应在0-23之间，分钟值 应在0-59之间），如合理则分别用于设定小时值 和分钟值，如不合理，则输出错误提示信息，并将小时值 和分钟值均设为0。
//
//setTime()方法，接收两个整数作为参数，并进行合理性判断（小时值应在0-23之间，分钟值 应在0-59之间），如合理则分别用于设定小时值 和分钟值，如不合理，则输出错误提示信息，并保持原值 不变
//
//showTime()方法，输出时间信息，格式形如“Time is 23:18”
//
//getTime12()方法，输出12小时制的时间信息，格式形如“Time in 12---10:35am”或“Time in 12---10:35pm”
//
//要求编程实现MyTime类，使给定的Test类能正常运行，并实现指定的输出内容。

class MyTime {
    public int hours;
    public int minutes;

    public MyTime(int inputHours, int inputMinutes) {
        if (inputHours >= 0 && inputHours <= 24 && inputMinutes >= 0 && inputMinutes <= 59) {
            hours = inputHours;
            minutes = inputMinutes;
        } else {
            hours = 0;
            minutes = 0;
            System.out.print("invalid time value! ");
        }
    }
    public static void test(){
        System.out.println("success!!!");
    }

    //静态方法不能使用this,如  public static void showTime(){ this.hours } 是错的
    //详解:   http://www.baidu.com/link?url=GYeCU_07AS8_ZDXEHX84gwNoJ4tC96waSkqGC5qkKPS4VP3hhh4LDObIst533k51F2EkEwoM7A7w76x_FUdALrXQU3E2t7RvMfmD_71nCTC
    public  void showTime() {
        System.out.println("Time is " + this.hours + ":" + this.minutes);
    }

    public  void setTime(int inputHours, int inputMinutes) {
        if (inputHours >= 0 && inputHours <= 24 && inputMinutes >= 0 && inputMinutes <= 59) {
            this.hours = inputHours;
            this.minutes = inputMinutes;
        } else {
            System.out.print("invalid time value! ");
        }
    }
    public String getTime12(){
        String timeZone;
        if(this.hours>12){
            this.hours = this.hours - 12;
            timeZone = "pm";
            return this.hours+":"+this.minutes+" "+timeZone;        }
        else
        {
            timeZone = "am";
            return this.hours+":"+this.minutes+" "+timeZone;
        }
    }
}

public class TimeClass {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MyTime mt = new MyTime(25, 43);
        mt.showTime();

       // MyTime.test();  //测试类, 用于测试静态类  [可删]

        mt = new MyTime(10, 35);
        mt.showTime();
        System.out.println("Time in 12---"+mt.getTime12());
        mt.setTime(25,16);
        mt.showTime();

         mt.setTime(23,18);
         mt.showTime();
         System.out.println("Time in 12---"+mt.getTime12());
    }
}
