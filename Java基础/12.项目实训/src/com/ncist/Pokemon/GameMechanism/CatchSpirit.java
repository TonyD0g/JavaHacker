package com.ncist.Pokemon.GameMechanism;

import com.ncist.Pokemon.PlayXML.CatchFirit;
import com.ncist.Pokemon.PlayXML.ChangeXml;
import com.ncist.Pokemon.PlayXML.JudgeIsHas;
import org.dom4j.DocumentException;

public class CatchSpirit {
    //传入一个精灵球fairyBall，不同的精灵球抓捕的概率不一样
    public static int catach_spirit_main(int fairyBall, String spiritNum, String nameText, String englishText, String gradeText, String bloodText, String orignBloodText, String attackPowerText) throws DocumentException {
        int probability = 0;    //抓捕概率
        probability = JudgeIsHas.judge_isHas(spiritNum);
        if(probability!=0){
            //数字越高的精灵球，抓捕的概率越高
            int i = (Integer.parseInt(bloodText) + Integer.parseInt(gradeText) / 2) / 2;
            switch (fairyBall) {
                case 1:
                    probability = ((int) (Math.random() * 30 + 10)) + i;
                    break;
                case 2:
                    probability = ((int) (Math.random() * 35 + 10)) + i;
                    break;
                case 3:
                    probability = ((int) (Math.random() * 40 + 10)) + i;
                    break;
            }
            if (probability > 50) {
                CatchFirit.catch_firit(spiritNum, nameText, englishText, gradeText, bloodText, orignBloodText, attackPowerText);
                System.out.println("\n~~~~~~抓捕结果↓~~~~~~\n抓捕成功！\n~~~~~~抓捕结果↑~~~~~~\n");
                return 1;
            } else {
                System.out.println("\n~~~~~~抓捕结果↓~~~~~~\n抓捕失败！\n~~~~~~抓捕结果↑~~~~~~\n");
                return 0;
            }
        }
        else
        {
            System.out.println("\n~~~~~~抓捕结果↓~~~~~~\n背包中已存在该精灵，无法进行抓捕，否则它会生气的！\n~~~~~~抓捕结果↑~~~~~~\n");
            return 0;
        }
    }
}
