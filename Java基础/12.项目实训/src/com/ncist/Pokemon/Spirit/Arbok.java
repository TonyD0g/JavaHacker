package com.ncist.Pokemon.Spirit;

import com.ncist.Pokemon.Trick.Tricks;

public class Arbok extends SpiritRoot {

    public Arbok() {
        attackPower = 24;
        blood = 160;
        spiritNum = 3;
        chineseName = "阿帕怪";
        spiritName = "Arbok";
        add_trick();
    }

    public Arbok(int inputGrade) {
        attackPower = inputGrade * 4 + (int) (Math.random() * 2 + 1) + 24;
        blood = inputGrade * 2 + (int) (Math.random() * 2 + 10) + 180;
        grade = inputGrade;
        spiritNum = 3;
        chineseName = "阿帕怪";
        spiritName = "Arbok";
        add_trick();
    }

    public void add_trick() {
        tricksList.add(Tricks.tornado); //添加招式
        tricksList.add(Tricks.dragonPole); //添加招式
        tricksList.add(Tricks.hurricane); //添加招式
        tricksList.add(Tricks.poisonous); //添加招式
    }

    //使用何种技能
    public void spirit_choice_tricks(int i, SpiritRoot enemySpirit) {
        switch (i) {
            case 0:
                tornado(enemySpirit);
                break;
            case 1:
                dragonPole(enemySpirit);
                break;
            case 2:
                hurricane(enemySpirit);
                break;
            case 3:
                poisonous(enemySpirit);
                break;
            default:
                System.out.println("精灵没有该技能！");
        }
    }

    //招式：龙卷风
    public void tornado(SpiritRoot enemySpirit) {
        //System.out.println("{我方}使用招式：龙卷风!");
        this.sub_blood((30 + (enemySpirit.grade * 3)), enemySpirit);
    }

    //招式：龙极
    public void dragonPole(SpiritRoot enemySpirit) {
        this.sub_blood((enemySpirit.blood - 30) / 2, enemySpirit);
    }

    //招式：hurricane
    public void hurricane(SpiritRoot enemySpirit) {
        this.sub_blood((this.attackPower * 2 + this.blood) / 2, enemySpirit);
    }

    //招式：poisonous
    public void poisonous(SpiritRoot enemySpirit) {
        this.sub_blood((this.attackPower * 2 + enemySpirit.blood * 2 + enemySpirit.grade) / 3, enemySpirit);
    }

}

