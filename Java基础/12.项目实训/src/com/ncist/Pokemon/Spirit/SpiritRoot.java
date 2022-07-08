package com.ncist.Pokemon.Spirit;

import com.ncist.Pokemon.Message.MessageMain;

import java.util.ArrayList;

public class SpiritRoot {
    public int attackPower;    //攻击力
    public int blood;          //血量
    public int grade;           //等级
    public int spiritNum;       //精灵编号
    public String spiritName;   //精灵英文名字
    public String chineseName;  //精灵中文名字
    public String attribute;    //属性
    ArrayList<String> tricksList = new ArrayList<String>();// 招式表，记录每个精灵可以使用的招式

    //判断血量
    public int judge_blood() {
        if (blood <= 0) {
            return 0;//返回0，说明没血了
        }
        return 1;//返回1，说明还有血
    }

    public void add_blood(int addBlood) {
        if(addBlood > 0 ){
            blood = blood + addBlood;
            System.out.println(String.format("\n~~~~~~效果提示~~~~~~\n成功增加 %d 的血量！\n~~~~~~效果提示~~~~~~\n", addBlood));
            MessageMain.stop_key();
        }
        else
        {
            System.out.println(String.format("\n~~~~~~效果提示~~~~~~\n技能释放错误，无法恢复血量！\n~~~~~~效果提示~~~~~~\n", addBlood));
            MessageMain.stop_key();
        }
    }

    public void sub_blood(int subBlood, SpiritRoot enemySpirit1) {
        if(subBlood > 0 ) {
            enemySpirit1.blood = enemySpirit1.blood - subBlood;
            System.out.println(String.format("\n~~~~~~效果提示~~~~~~\n已经造成 %d 的伤害！\n~~~~~~效果提示~~~~~~\n", subBlood));
            MessageMain.stop_key();
        }
        else
        {
            System.out.println(String.format("\n~~~~~~效果提示~~~~~~\n技能释放错误，无法造成伤害！\n~~~~~~效果提示~~~~~~\n", subBlood));
            MessageMain.stop_key();
        }
    }

    //发动攻击，攻击模块
    public void launch_attack(int choiceTrick, SpiritRoot spiritName, SpiritRoot enemySpirit1) {
        switch (spiritName.spiritName) {
            case "Bulbasaur":
                ((Bulbasaur) spiritName).spirit_choice_tricks(choiceTrick, enemySpirit1);
                break;
            case "Charmander":
                ((Charmander) spiritName).spirit_choice_tricks(choiceTrick, enemySpirit1);
                break;
            case "Arbok":
                ((Arbok) spiritName).spirit_choice_tricks(choiceTrick, enemySpirit1);

            default:
                System.out.println("该精灵不存在！");
        }
    }

    //我方精灵使用什么技能
    public void choice_trick(int choiceTrick, SpiritRoot spiritName, SpiritRoot enemySpirit1) {
        System.out.println(String.format("我方{%s精灵}使用了{%s技能}!!!\n", this.spiritName, tricksList.get(choiceTrick)));
        launch_attack(choiceTrick, spiritName, enemySpirit1);
    }


    //对方精灵使用什么技能
    public void choice_he_trick(SpiritRoot spiritName, SpiritRoot enemySpirit1) {
        int i = (int) (Math.random() * 3);
        String text = enemySpirit1.tricksList.get(i);
        System.out.println(String.format("对方{%s精灵}使用了{%s技能}!!!\n", enemySpirit1.spiritName, text));
        launch_attack(i, enemySpirit1, spiritName);
    }

    //展示当前精灵可以使用的所有技能
    public void show_tricks() {
        System.out.println(String.format("~~~~~~\n当前技能有：\n[0] %s,[1] %s,[2] %s,[3] %s\n~~~~~~", this.tricksList.get(0), tricksList.get(1), tricksList.get(2), tricksList.get(3)));
    }

    //展示双方状态
    public void show_state(SpiritRoot enemySpirit1) {
        System.out.println(String.format(
                "~~~~~~~~~~~~\t\t\t\t~~~~~~~~~~~~\n当前精灵的状态：\t\t\t\t对方精灵的状态：\n攻击力：%d\t血量：%d\t\t\t攻击力：%d\t血量：%d\n~~~~~~~~~~~~\t\t\t\t~~~~~~~~~~~~\n"
                , attackPower, blood, enemySpirit1.attackPower, enemySpirit1.blood));
    }

}
