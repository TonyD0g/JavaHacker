//主要写战斗机制
package com.ncist.Pokemon.GameMechanism;

import com.ncist.Pokemon.Message.MessageMain;
import com.ncist.Pokemon.PlayXML.ChangeXml;
import com.ncist.Pokemon.Spirit.*;//导入所有精灵
import com.ncist.Pokemon.Spirit.SpiritOption.Show_Can_Fight;
import com.ncist.Pokemon.Spirit.SpiritOption.SpiritArray;
import com.ncist.Pokemon.Spirit.SpiritOption.choice_ball;
import com.ncist.Pokemon.Spirit.SpiritRoot;
import org.dom4j.DocumentException;

import static com.ncist.Pokemon.Message.MessageMain.input;

public class FightMechanism {
    static int round = 3;      //回合数，回合数为0时，游戏结束
    static int i = 0;          //计数器

    static int choiceTrick = 0;            //选择的技能
    static int randNum = 0;                //随机数，用于查看是先手还是后手
    static int isRun = 0;                   //是否逃跑
    static SpiritRoot callSpirit1;         //召唤的精灵1
    static SpiritRoot callSpirit2;         //召唤的精灵2
    static FightOrRun[] callSpirit;        //战斗或逃跑数组

    //回合制主方法
    public static void Round_system() throws DocumentException {
        //精灵选择主逻辑
        isRun = spirit_choice_main();

        //如果未选择逃跑，则进入战斗
        if (isRun == 0) {
            //回合制主逻辑
            fight_round();
        }

    }

    //精灵选择主逻辑
    public static int spirit_choice_main() throws DocumentException {
        System.out.println("可以出战的精灵:");//选择双方对战的精灵
        //输出你所拥有的精灵

        callSpirit = Show_Can_Fight.choice_fight_spirit();
        while (callSpirit == null) {
            System.out.println("~~~~~~不存在的精灵或者该精灵没有血了！请换个精灵战斗！~~~~~~\n");
            callSpirit = Show_Can_Fight.choice_fight_spirit();
        }
        if (callSpirit[0].runFlag == 1) {
            return 1;
        }

        System.out.println(String.format("我选择出战{%s}", callSpirit[0].called.chineseName));

        //召唤所选择的精灵：
        callSpirit1 = choice_spirit(callSpirit[0].called.englishName);

        //根据用户的xml文件来设置精灵的参数
        set_options(callSpirit1, callSpirit[0].called);

        //机器人随机选择某个精灵与其对战
        callSpirit2 = choice_random_spirit((int) (Math.random() * 3 + 1), callSpirit[0].called);

        MessageMain.stop_key();

        return 0;
    }

    //回合制主逻辑
    public static void fight_round() throws DocumentException {
        int choice;
        while (round != 0) {
            i++;

            System.out.println(String.format("\n-------------第%d回合开始----------------\n", i));

            //先手逻辑

            System.out.println("~~~~~~我方的回合：~~~~~~");

            callSpirit1.show_state(callSpirit2);

            choice = MessageMain.show_can_choice();

            int exitFlag = 0;

            //选择对应的选项
            switch (choice) {
                //使用技能
                case 0:
                    //列出该精灵所拥有的技能
                    callSpirit1.show_tricks();

                    //使用你选择的技能
                    System.out.println("请选择你想要使用的技能：");
                    choiceTrick = input.nextInt();
                    while(choiceTrick > 3 || choiceTrick < 0 ){
                        System.out.println("[-] 请重新选择技能！");
                        choiceTrick = input.nextInt();
                    }
                    callSpirit1.choice_trick(choiceTrick, callSpirit1, callSpirit2);
                    callSpirit1.show_state(callSpirit2);
                    break;

                //抓捕精灵
                default:
                    choice = choice_ball.choice_fairyBall();
                    if (choice != -1) {
                        int outCome = CatchSpirit.catach_spirit_main(choice, String.valueOf(callSpirit2.spiritNum), callSpirit2.chineseName, callSpirit2.spiritName, String.valueOf(callSpirit2.grade), String.valueOf(callSpirit2.blood), String.valueOf((callSpirit2.blood * callSpirit2.grade)+150), String.valueOf(callSpirit2.attackPower));
                        if (outCome == 1) {
                            exitFlag = 1;
                            System.out.println("\n成功抓捕精灵成功！\n-----------------------------------\n");
                            ChangeXml.feedback_xml(1, Integer.toString(callSpirit[0].called.spiritNum),String.valueOf(callSpirit1.blood), String.valueOf(callSpirit1.attackPower), String.valueOf(callSpirit1.grade),String.valueOf(callSpirit[0].called.exp));
                        }
                    }
                    break;

            }
            if (exitFlag == 1) {
                //暂停键
                input.nextLine();
                MessageMain.stop_key();
                break;
            }

            //暂停键
            input.nextLine();
            MessageMain.stop_key();

            //每个回合都先判断一次，如果对战双方某个精灵没血了，说明对方胜利，并退出回合
            if (judge_over(callSpirit1, callSpirit2, Integer.toString(callSpirit[0].called.spiritNum), Integer.toString(callSpirit1.blood), Integer.toString(callSpirit[0].called.attackPower), Integer.toString(callSpirit[0].called.grade), Integer.toString(callSpirit[0].called.exp)) == 1) {
                break;
            }

            System.out.println("~~~~~~对手的回合：~~~~~~");
            //对方精灵开始使用招式:

            //因为是机器人，所以随机抽一个技能来使用
            callSpirit1.choice_he_trick(callSpirit1, callSpirit2);

            //造成了xx效果
            callSpirit1.show_state(callSpirit2);

            if (judge_over(callSpirit1, callSpirit2, Integer.toString(callSpirit[0].called.spiritNum), Integer.toString(callSpirit1.blood), Integer.toString(callSpirit[0].called.attackPower), Integer.toString(callSpirit[0].called.grade), Integer.toString(callSpirit[0].called.exp)) == 1) {
                break;
            }

            //暂停键
            MessageMain.stop_key();

            System.out.println(String.format("\n-------------第%d回合结束----------------\n", i));
            round--;
        }
    }

    //根据用户的xml文件来设置精灵的参数
    public static SpiritRoot set_options(SpiritRoot callSpirit1, SpiritArray callSpirit) {
        callSpirit1.blood = callSpirit.blood;
        callSpirit1.attackPower = callSpirit.attackPower;
        callSpirit1.grade = callSpirit.grade;
        return callSpirit1;
    }

    //判断游戏是否结束
    public static int judge_over(SpiritRoot callSpirit1, SpiritRoot callSpirit2, String spiritNum, String blood, String attackPower, String grade, String exp) throws DocumentException {
        if (callSpirit1.judge_blood() <= 0) {
            System.out.println("我方精灵血量不足，游戏失败!\n-----------------------------------\n");
            //战斗完成，将结果的血量，伤害，等级，经验等传回个人xml中
            ChangeXml.feedback_xml(0, spiritNum, blood, attackPower, grade, exp);
            //暂停键
            MessageMain.stop_key();
            return 1;
        } else {
            if (callSpirit2.judge_blood() <= 0) {
                System.out.println("对方精灵血量不足，游戏胜利!\n-----------------------------------\n");
                //战斗完成，将结果的血量，伤害，等级，经验等传回个人xml中
                ChangeXml.feedback_xml(1, spiritNum, blood, attackPower, grade, exp);
                //暂停键
                MessageMain.stop_key();
                return 1;
            }
        }
        round++;
        return 0;
    }

    //召唤某个精灵
    public static SpiritRoot choice_spirit(String spiritName) {
        //选择一个精灵，把名字导进来
        switch (spiritName) {
            case "Bulbasaur":
                Bulbasaur Bulbasaur1 = new Bulbasaur();
                System.out.println("成功召唤妙蛙种子！");//召唤精灵
                return Bulbasaur1;
            case "Charmander":
                Charmander Charmander1 = new Charmander();
                System.out.println("成功召唤小火龙！");//召唤精灵
                return Charmander1;
            case "Arbok":
                Arbok Arbok1 = new Arbok();
                System.out.println("成功召唤阿帕怪！");//召唤精灵
                return Arbok1;
            default:
                System.out.println("没找到你想要召唤的精灵");
        }
        return null;
    }

    //随机召唤某个精灵
    public static SpiritRoot choice_random_spirit(int randNum, SpiritArray called) {
        switch (randNum) {
            case 1:
                Bulbasaur Bulbasaur1 = new Bulbasaur(called.grade);
                System.out.println("对手成功召唤{妙蛙种子}！");//召唤精灵
                return Bulbasaur1;
            case 2:
                Charmander Charmander1 = new Charmander(called.grade);
                System.out.println("对手成功召唤{小火龙}！");//召唤精灵
                return Charmander1;
            case 3:
                Arbok Arbok1 = new Arbok(called.grade);
                System.out.println("对手成功召唤{阿帕怪}！");//召唤精灵
                return Arbok1;
            default:
                System.out.println("没找到你想要召唤的精灵");
        }
        return null;
    }
}
