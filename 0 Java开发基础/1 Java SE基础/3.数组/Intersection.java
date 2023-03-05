package The3Class;

import java.util.Scanner;

//【问题描述】
//
// 从标准输入中输入两组整数(每组不超过20个整数，每组整数中的元素不重复，并且整数大于等于0)，编程求两组整数的交集，即在两组整数中都出现的整数，并按从小到大顺序排序输出。若交集为空，则什么都不输出。
//
// 【输入形式】
//
// 在两行上分别输入两组整数，以一个空格分隔各个整数，以-1作为输入结束。
//
//【输出形式】
//
// 按从小到大顺序排序输出两组整数的交集（以一个空格分隔各个整数，最后一个整数后的空格可有可无）。
//
// 【样例输入】
//
//5  105  0  4  32  8  7  9  60  -1
// 5  2  87  10  105  0  32  -1
//
//【样例输出】
//
// 0  5  32  105
public class Intersection {
    public static void main(String[] args) {
        int[] array1 = new int[20];
        int[] array2 = new int[20];
        int[] array3 = new int[20];
        int i = 0, arrayLenth1, arrayLenth2 = 0, arrayLenth3 = 0;
        Scanner input = new Scanner(System.in);
        while (i != 20) {
            int x = input.nextInt();
            if (x != -1) {
                array1[i] = x;
                i++;
            } else
                break;
        }
        arrayLenth1 = i;
        i = 0;
        while (i != 20) {
            int x = input.nextInt();
            if (x != -1) {
                array2[i] = x;
                i++;
            } else {
                break;
            }

        }
        arrayLenth2 = i;

        //找相同
        if (arrayLenth2 != 0) {
            int x = 0;
            for (i = 0; i < arrayLenth1; i++) {
                for (int j = 0; j < arrayLenth2; j++) {
                    if (array1[i] == array2[j]) {
                        array3[x] = array1[i];
                        x++;
                        arrayLenth3++;
                    }
                }
            }
            handle(arrayLenth3, array3);
        } else {
            //数组2为空，则直接输出数组1（当然还是要去重和排序）
            handle(arrayLenth1, array1);
        }


    }

    //handle(arrayLenth1, arrayLenth3,array3);
    public static void handle(int arrayLenth3, int[] array3) {
        int i;
        //去重  和    排序
            for (i = 0; i < arrayLenth3; i++) {
                for (int j = i + 1; j < arrayLenth3; j++) {
                    if (array3[i] > array3[j]) {
                        int middle = array3[i];
                        array3[i] = array3[j];
                        array3[j] = middle;
                    }
                    if (array3[i] == array3[j]) {
                        array3[j] = array3[j + 1];
                        arrayLenth3--;
                    }
                }
            }

            //输出
            if (arrayLenth3 != 0) {
                i = 0;
                while (i < arrayLenth3) {
                    System.out.print(array3[i] + " ");
                    i++;
                }
            }
        }
    }



