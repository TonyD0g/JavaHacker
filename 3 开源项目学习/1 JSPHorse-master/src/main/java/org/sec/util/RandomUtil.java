package org.sec.util;

import java.util.Random;

public class RandomUtil {
    // 根据字符串长度生成随机变量名 逻辑还是挺清晰的
    public static String getRandomString(int length) {
        try {
            // Sleep保证随机
            Thread.sleep(300);
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(52);
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
