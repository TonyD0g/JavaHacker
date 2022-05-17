//编写程序，把24小时表示法的时间转换为12小时表示法。
//定义名为TimeFormatException的异常类，如果输入的时间是非法值，
//比如10:65或者abc，程序应该抛出并处理TimeFormatException异常。

package The10Class;

import java.text.SimpleDateFormat;
import java.util.Date;

//https://www.cnblogs.com/wyhuang/p/4092634.html
public class TimeTranferException {
    static int flag = 0;

    public static void main(String[] args) {

        String times[] = {"15:20", "27:10", "16:78", "abc", "6:30"};

        String line = null;

        for (String timeStr : times) {

            System.out.println("===");

            System.out.println("<<<TimeString for format converting in 24-hour notation is " + timeStr);

            try {

                outTime(timeStr);

            } catch (TimeFormatException e) {

                System.out.println(e);

                continue;

            }

        }
        System.out.println("End of program");

    }

    public static void outTime(String line) throws TimeFormatException {

//        String line1 = line.substring(0,2);
        String strDateFormat = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String strDateFormat1 = "HH";
        SimpleDateFormat sdf1 = new SimpleDateFormat(strDateFormat1);
        try {
            String mid = line.substring(0, 2);
            if (mid.contains(":")) {
                strDateFormat1 = "hh";
                sdf1 = new SimpleDateFormat(strDateFormat1);
                Date date2 = sdf1.parse(line);
                mid = sdf1.format(date2);

            }

            String line1;
            int x = Integer.parseInt(mid);
            if (x > 24) {

                System.out.println("TimeFormatException: Invalid Value for Hour!");
            } else {
                try {
                    line1 = line.substring(3, 5);
                } catch (Exception e) {
                    strDateFormat1 = "mm";
                    sdf1 = new SimpleDateFormat(strDateFormat1);
                    Date date2 = sdf1.parse(line);
                    line1 = sdf1.format(date2);
                }

                x = Integer.parseInt(line1);
                if (x > 59) {

                    System.out.println("TimeFormatException: Invalid Value for Minute!");

                } else {
                    int mid1 = 0;
                    try {
                        mid1 = Integer.parseInt(line.substring(0, 2));
                    } catch (Exception e) {
                    }
                    if (mid1 > 12) {
                        Date date1 = sdf.parse(line);
                        System.out.println(">>>Time in 12-hour notation is: " + sdf.format(date1) + " PM");
                    } else {
                        Date date1 = sdf.parse(line);
                        System.out.println(">>>Time in 12-hour notation is: " + sdf.format(date1) + " AM");
                    }

                }

            }


        } catch (Exception e) {

            System.out.println("TimeFormatException: Invalid Value for Time!");

        }

    }
}

class TimeFormatException extends Exception {
    TimeFormatException(String msg) {
        super(msg);
    }

}