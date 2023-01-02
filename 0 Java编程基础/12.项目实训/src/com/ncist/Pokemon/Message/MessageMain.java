//欢迎菜单，游戏提示等消息都在这写
package com.ncist.Pokemon.Message;

import java.util.Scanner;

public class MessageMain {
    public static Scanner input = new Scanner(System.in); //定义输入的内容


    //展示可选选，比如说要战斗还是用背包的道具
    public static int show_can_choice() {
        System.out.println("\n~~~~~~功能菜单↓~~~~~~\n[0] 使用技能\t[1] 抓捕精灵\n(输入其他数字自动选择[1]号选项)\n~~~~~~功能菜单↑~~~~~~\n");
        int choice = input.nextInt();
        return choice;
    }

    //退出游戏
    public static void exit_game() {
        System.out.println("\n-------------欢迎下次再来游玩!----------------\n-------------版权所有：摆烂小分队----------------");
    }

    //暂停键
    public static void stop_key() {
        System.out.println("请按回车键继续");
        input.nextLine();
    }

    //游戏版权等信息
    public static void banner() {
        System.out.println("\n-------------欢迎来到精灵宝可梦游戏!----------------\n-------------版权所有：摆烂小分队----------------");
    }

    //游戏菜单1
    public static void menu1() {
        System.out.println("\n~~~~~~游戏菜单↓~~~~~~\n0：退出游戏\n1：注册账号\n2：登录账号\n~~~~~~游戏菜单↑~~~~~~");
    }

    //游戏菜单2
    public static void menu2() {
        System.out.println("\n~~~~~~游戏菜单↓~~~~~~\n0：退出游戏\n1：退出账号\n2：查看人物信息\n3：修改出战精灵\n4：生命之泉恢复精灵血量\n5：随机挑战模式\n~~~~~~游戏菜单↑~~~~~~");
    }
}

