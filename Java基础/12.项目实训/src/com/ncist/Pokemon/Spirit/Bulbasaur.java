//妙蛙种子,水系
package com.ncist.Pokemon.Spirit;

import com.ncist.Pokemon.Trick.Tricks;

public class Bulbasaur extends SpiritRoot {

    public Bulbasaur() {
        attackPower = 18;
        blood = 300;
        spiritNum = 1;
        chineseName = "妙蛙种子";
        spiritName = "Bulbasaur";   //精灵名字
        add_trick();
    }

    public Bulbasaur(int inputGrade) {
        attackPower = inputGrade * 1 + (int) (Math.random() * 2 + 1) + 18;
        blood = inputGrade * 4 + (int) (Math.random() * 2 + 10) + 300;
        grade = inputGrade;
        spiritNum = 1;
        chineseName = "妙蛙种子";
        spiritName = "Bulbasaur";   //精灵名字
        add_trick();
    }

    public void add_trick() {
        tricksList.add(Tricks.tortoise_shell); //添加招式
        tricksList.add(Tricks.beReborn); //添加招式
        tricksList.add(Tricks.defense); //添加招式
        tricksList.add(Tricks.Burst); //添加招式
    }

    //使用何种技能
    public void spirit_choice_tricks(int i, SpiritRoot enemySpirit) {
        switch (i) {
            case 0:
                tortoise_shell(enemySpirit);
                break;
            case 1:
                beReborn(enemySpirit);
                break;
            case 2:
                defense(enemySpirit);
                break;
            case 3:
                Burst(enemySpirit);
                break;
            default:
                System.out.println("精灵没有该技能！");
                break;
        }
    }


    public void tortoise_shell(SpiritRoot enemySpirit) {
        //System.out.println("{我方}使用招式：龟甲!");
        this.sub_blood(130, enemySpirit);
    }

    public void beReborn(SpiritRoot enemySpirit) {
        this.add_blood(enemySpirit.blood  / 2);
    }

    public void defense(SpiritRoot enemySpirit) {
        this.add_blood((enemySpirit.blood - 30) / 2);
        this.sub_blood((enemySpirit.blood - 180 ), enemySpirit);
    }

    public void Burst(SpiritRoot enemySpirit) {
        this.sub_blood(enemySpirit.blood / 2, enemySpirit);
    }
}
