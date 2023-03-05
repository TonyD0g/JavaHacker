package The3Class;

import java.util.Scanner;
//【问题描述】
//
//输入一组无序的整数，编程输出其中出现次数最多的整数及其出现次数。
//
//【输入形式】
//
//先从标准输入读入整数的个数（大于等于1，小于等于100），然后在下一行输入这些整数，各整数之间以一个空格分隔。
//
//【输出形式】
//
//在标准输出上输出出现次数最多的整数及其出现次数，两者以一个空格分隔；若出现次数最多的整数有多个，则按照整数升序分行输出。
//
//【样例输入】
//
//10
//
//0 -50 0 632 5813 -50 9 -50 0 632
//
//【样例输出】
//
//-50 3
//
//0 3
//
//【样例说明】
//
//输入了10个整数，其中出现次数最多的是-50和0，都是出现3次。

class Struct {
    public int key;   //键
    public int value;    //值
}

public class IntegerOccurrences {

    public static void main(String[] args) {
        int x1 = 0, x2 = 0,  flag = 0, i = 0;
        Scanner input = new Scanner(System.in);
        int maxNum = input.nextInt();
        //array1    :   "源"数组
        int[] array1 = new int[maxNum];
        //array2    :   "防重复"数组
        int[] array2 = new int[maxNum];
        //middle    :   "记录次数"数组
        int[] middle = new int[maxNum];
        Struct middlStruct = new Struct();
        System.out.println();

        while (i < maxNum) {
            array1[i] = input.nextInt();
            i++;
        }

        //middle数组赋初值
        while (i < maxNum) {
            array2[i] = -9999;
            i++;
        }
        for (i = 0; i < maxNum; i++) {
            flag = 0;
            for (int y = 0; y < x2; y++) {
                if (array1[i] == array2[y]) {
                    flag = 1;
                    break;
                }
            }
            if (flag != 1) {
                middle[x1] = middle[x1] + 1;
                for (int j = i + 1; j < maxNum; j++) {
                    if (array1[i] == array1[j]) {
                        middle[x1] = middle[x1] + 1;
                    }
                }
                array2[x2] = array1[i];
                x1++;
                x2++;
            }
        }
        //求出最大的 键值
        int max = 0;
        for(i=0;i<x2;i++){
            if(middle[i]>max){
                max = middle[i];
            }
        }

        Struct[] keyValue = new Struct[x2];
        for (i = 0; i < x2; i++) {         //一定要加入这一句
            keyValue[i] = new Struct();
            keyValue[i].key = array2[i];
            keyValue[i].value = middle[i];
        }
        //查找重复次数为最大的 键
        x1 = x2;
        x2 = 0;
        for(i=0;i<x1;i++){
            if(keyValue[i].value==max){
                middlStruct = keyValue[i];
                keyValue[i] = keyValue[x2];
                keyValue[x2] = middlStruct;
                x2 = x2+1;
            }
        }

        //排序
        for (i = 0; i < x2; i++) {
            for (int j = i + 1; j < x2; j++) {
                if (keyValue[i].key > keyValue[j].key) {
                    middlStruct = keyValue[i];
                    keyValue[i] = keyValue[j];
                    keyValue[j] = middlStruct;
                }
            }
        }
        //输出
        for (i = 0; i < x2; i++) {
            System.out.println(keyValue[i].key + " " + keyValue[i].value);
        }
    }

}
