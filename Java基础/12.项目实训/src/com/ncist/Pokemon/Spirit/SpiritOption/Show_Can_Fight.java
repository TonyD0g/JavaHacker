package com.ncist.Pokemon.Spirit.SpiritOption;

import com.ncist.Pokemon.GameMechanism.FightOrRun;
import com.ncist.Pokemon.Message.MessageMain;
import com.ncist.Pokemon.PlayXML.ChangeXml;
import org.dom4j.DocumentException;

import static com.ncist.Pokemon.Message.MessageMain.input;

public class Show_Can_Fight {
    //展示可以出战的精灵,返回值：精灵英文名字，精灵中文名字
    public static FightOrRun[] choice_fight_spirit() throws DocumentException {
        SpiritArray[] spiritArray1;
        FightOrRun[] FightOrRun1 = new FightOrRun[1];
        FightOrRun1[0] = new FightOrRun();

        //展示可以出战的精灵编号
        spiritArray1 = ChangeXml.show_fight_spirit();

        System.out.println("请选择您想要出战的精灵：(选择 0 即为逃跑)");
        int choice_spirit = input.nextInt();
        int i = 0;
        if (choice_spirit == 0) {
            System.out.println("我方选择逃跑！");
            FightOrRun1[0].runFlag = 1;
            //扣钱
            int money = (int) (Math.random() * 8 + 1);
            System.out.println(String.format("~~~~~~因为走的太快，掉了%d块钱！~~~~~~", money));
            ChangeXml.change_moeny(0, money);
            MessageMain.stop_key();
            return FightOrRun1;
        }
        while (i < spiritArray1.length) {
            if (spiritArray1[i].spiritNum == choice_spirit && spiritArray1[i].blood > 0) {
                FightOrRun1[0].called = spiritArray1[i];
                return FightOrRun1;
            }
            i++;
        }

        return null;
    }

}
