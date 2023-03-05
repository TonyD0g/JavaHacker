package The3Class;

import java.util.Scanner;
//加密规则如下：
//
//        if (str.substring(i, i + 1) + key > 126) then
//
//        EncryptedChar = ((str.substring(i, i + 1) + key)-127) + 32
//
//        else
//
//        EncryptedChar = (str.substring(i, i + 1) + key)
//
//        限定密钥是1~100之间的某个数字。原始消息全部由ASCII码组成，
//
//        编写加密解密功能，实现这个加密系统。输入密钥和一行明文，输出密文；再对密文解密，输出明文。
//
//   提示：String.charAt()方法可用于获取字符串中的某个字符
//
//        String.length()方法可返回字符串长度（字符个数）
//
//        Scanner.nextLine()方法可从键盘输入一行字符

public class EasyCrypto {
    public static void main(String[] args) {
        int i = 0;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a message for encrypt: ");
        String str = input.nextLine();
        System.out.print("Enter a key between 1 to 100: ");
        int key = input.nextInt();
        System.out.println("message: " + str);
        //字符串最大长度:maxnum
        int maxnum = str.length();
        StringAloneToAscii(str, key, maxnum);
        System.out.println("message: " + str);
    }

    public static void StringAloneToAscii(String value, int key, int maxnum) {
        int i = 0, EncryptedChar;
        char[] result = new char[maxnum];
        int maxLength = value.length();
        while (i < maxLength) {
            int Ascii = (int) value.charAt(i);
            if (Ascii + key > 126) {
                EncryptedChar = ((Ascii + key) - 127) + 32;
                result[i] = (char) EncryptedChar;

            } else {
                EncryptedChar = (Ascii + key);
                result[i] = (char) EncryptedChar;
            }
            i++;
        }
        i = 0;
        System.out.printf("result: ");
        while (i < maxLength) {
            System.out.print(result[i]);
            i++;
        }
        System.out.println();
        System.out.printf("result: ");
        i = 0;
        while (i < maxLength) {
            System.out.print(result[i]);
            i++;
        }
        System.out.println();
    }
}