//程序的主方法,程序从这里直接启动
package com.ncist.Pokemon.Main;

import com.ncist.Pokemon.GameMechanism.Mechanism;
import com.ncist.Pokemon.LoginRegister.CheckLogin;
import com.ncist.Pokemon.LoginRegister.SignAccount;
import com.ncist.Pokemon.Message.MessageMain;
import com.ncist.Pokemon.PlayXML.ChangeXml;
import com.ncist.Pokemon.PlayXML.WannaFight;
import org.dom4j.DocumentException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.ncist.Pokemon.Message.MessageMain.input;


public class Main {
    public static int flag = 0;       //flag为1时，说明账户登录成功,只有账户登录成功后才能游玩
    public static int useraccount;     //玩家账户

    //路径写死，比如说D:\\Pokemon\\XML\\%d.xml
    //public static String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);

    public static void main(String[] args) throws SQLException, DocumentException {
        int dochoice;

        MessageMain.banner();
        while (1 == 1) {
            //进入相对应的模式
            if (flag != 1) {
                MessageMain.menu1();//运行游戏菜单1，选择登录账号，退出账号等

                System.out.print("[+] 请选择相应的功能：");
                Scanner input = new Scanner(System.in);
                dochoice = input.nextInt();
                while (dochoice < 0 || dochoice > 2) {
                    MessageMain.banner();
                    System.out.println("请重新选择游玩的选项！并无该选项！");
                    MessageMain.menu1();
                    System.out.print("请选择相应的功能：");
                    dochoice = input.nextInt();
                }
                switch (dochoice) {
                    case 0:
                        MessageMain.exit_game();
                        System.exit(0);
                        break;
                    case 1:
                        flag = SignAccount.sign_account();
                        break;
                    case 2:
                        flag = CheckLogin.main_for_check(flag);
                        break;

                }
            }
if(flag ==1){
    MessageMain.menu2();
    System.out.print("[+] 请选择相应的功能：");
    input = new Scanner(System.in);
    dochoice = input.nextInt();
    while (dochoice < 0 || dochoice > 5) {
        MessageMain.banner();
        System.out.println("请重新选择游玩的选项！并无该选项！");
        MessageMain.menu2();
        System.out.print("请选择相应的功能：");
        dochoice = input.nextInt();
    }
    switch (dochoice) {
        case 0:
            MessageMain.exit_game();
            System.exit(0);
            break;
        case 1:
            //退出账号
            if (flag == 1) {
                flag = 0;
                System.out.println("~~~~~~您已成功退出登录！~~~~~~");
            }
            break;
        case 2:
            //查看人物信息
            ChangeXml.main_for_showinfo();
            break;
        case 3:
            //修改出战精灵
            ArrayList<Integer> arrayNum = choice_spirit_num();
            WannaFight.wanna_fight(arrayNum);
            break;
        case 4:
            int money = (int) (Math.random() * 5 + 5);
            System.out.println("~~~~~~治疗结果~~~~~~\n[-] 损失了" + money + "金钱！");

            //生命之泉恢复精灵血量
            ChangeXml.recovery_blood();
            ChangeXml.change_moeny(0, money);
            break;
        case 5:
            Mechanism.mechanism();
            break;

    }
}

        }
    }

    public static ArrayList<Integer> choice_spirit_num() {
        ArrayList<Integer> arrayNum = new ArrayList<Integer>();
        //System.out.println("~~~~~~请输入您想要更改的数量（最多为4）~~~~~~");
        //int x = input.nextInt();
        System.out.println("~~~~~~请输入您想要出战的精灵编号，不出战输入0~~~~~~");
        for (int i = 0; i < 4; i++) {
            arrayNum.add(input.nextInt());
        }
        return arrayNum;
    }
}
