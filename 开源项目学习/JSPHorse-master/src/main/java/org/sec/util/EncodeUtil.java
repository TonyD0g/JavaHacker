package org.sec.util;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("all")

// 凯撒加密核心逻辑
public class EncodeUtil {
    public static String encryption(String str, int offset) {
        char c;
        StringBuilder str1 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {

            c = str.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (((c - 'a') + offset) % 26 + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                c = (char) (((c - 'A') + offset) % 26 + 'A');
            } else if (c >= '0' && c <= '9') {
                c = (char) (((c - '0') + offset) % 10 + '0');
            } else {
                str1 = new StringBuilder(str);
                break;
            }
            str1.append(c);
        }

        // 我在原项目的基础上套了层base64,结果发现无法执行了，很奇怪（无报错，加密解密部分均做了处理）
//        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//        String testStr = encoder.encode(str1.toString().getBytes(StandardCharsets.UTF_8));
//        return encoder.encode(testStr.toString().getBytes(StandardCharsets.UTF_8));


        // 凯撒后base64一下
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(str1.toString().getBytes(StandardCharsets.UTF_8));
    }
    // 需要嵌入JSP的解密算法 (未使用过)
    public static String dec(String str, int offset) {
        try {
            // 先Base64解码
            byte[] code = new sun.misc.BASE64Decoder().decodeBuffer(str);
            str = new String(code);
            char c;

            // 然后尝试恺撒密码解密
            StringBuilder str1 = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                c = str.charAt(i);
                if (c >= 'a' && c <= 'z') {
                    c = (char) (((c - 'a') - offset + 26) % 26 + 'a');
                } else if (c >= 'A' && c <= 'Z') {
                    c = (char) (((c - 'A') - offset + 26) % 26 + 'A');
                } else if (c >= '0' && c <= '9') {
                    c = (char) (((c - '0') - offset + 10) % 10 + '0');
                } else {
                    str1 = new StringBuilder(str);
                    break;
                }
                str1.append(c);
            }
            String result = str1.toString();
            // 处理特殊情况
            result = result.replace("\\\"","\"");
            result = result.replace("\\n","\n");
            return result;
        } catch (Exception ignored) {
            return "";
        }
    }
}
