//【问题描述】
//Java基础类库java.util包中有一个表示日期的Date类，
//自己设计一个MyDate类继承Date类，添加方法 MyDate addDays(int days)，
//计算并返回当前日期加上days天后的MyDate对象；添加方法  int getDays(MyDate dt)，
//计算并返回MyDate对象dt与当前日期对象之间相差的天数。测试类代码如下，
//创建一个表示2020年1月1日的MyDate类对象(1577808000000L是2020.1.1 0点距1970.1.1 0点以来的毫秒数），
//然后为它增加100天得到新的MyDate类对象，再计算两个日期的天数差。编程实现MyDate类，
//可以将所编写的代码和测试类保存到一个文件中上传提交。
package The7Class;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class ExtendsDate {
    public static void main(String[] args) {

        SimpleDateFormat time1 = new SimpleDateFormat("yyyy MM dd");

        time1.setTimeZone(TimeZone.getTimeZone("GMT+8"));


        MyDate dt = new MyDate(1577808000000L);
        //把一个Date类型的时间转换成我们设置好的时间格式，返回是String字符串
        String dtStr = time1.format(dt);
        System.out.println(dtStr);


        MyDate md = dt.addDays(100);

        String mdStr = time1.format(md);

        System.out.println(mdStr);


        int days = dt.getDays(md);

        System.out.print(mdStr + " - " + dtStr + ": ");

        System.out.println(days + "days");

    }
}

class MyDate extends Date {
    public String sec;
    public long orign;

    MyDate() {
    }

    MyDate(long date1) {
        this.orign = date1;
        //参考：https://www.baidu.com/link?url=5omIbkLWOI4p1IMJIMpz_Sfjp-oap4RMUjdEHQ-dp3DkEbIcaKNYHhH8dIhYCcAM9CAtD02nhqGgkT3wyCWIQ5gJjpiVhc5cBJJ9Wl03h73&wd=&eqid=f014a8250003418800000006625e1a3b
        this.setTime(date1);
    }

    public MyDate addDays(int days) {
        //参考:https://www.runoob.com/java/java-date-time.html
        Calendar c1 = Calendar.getInstance();
        c1.setTime(this);
        c1.add(Calendar.DATE, days); //加天数

        long date2 = 100;
        date2 = date2 * 24 * 60 * 60 * 1000;

        long mid = this.getTime();
        mid = date2 + mid;

        this.setTime(mid);  //给该对象设值
        return this;
    }

    public int getDays(MyDate dt) {
        long t1 = this.orign;
        long t2 = dt.getTime();

        t2 = Math.abs(t2 - t1);

        t2 = t2 / (24 * 60 * 60 * 1000);

        return (int)t2;
    }

}