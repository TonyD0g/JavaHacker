//小火龙,火系
package com.ncist.Pokemon.Spirit;

import com.ncist.Pokemon.Trick.Tricks;

public class Charmander extends SpiritRoot {

    public Charmander() {
        attackPower = 22;
        blood = 180;
        spiritNum = 2;
        chineseName = "小火龙";
        spiritName = "Charmander";
        add_trick();
    }

    public Charmander(int inputGrade) {
        attackPower = inputGrade * 2 + (int) (Math.random() * 2 + 1) + 22;
        blood = inputGrade * 3 + (int) (Math.random() * 2 + 10) + 180;
        grade = inputGrade;
        spiritNum = 2;
        chineseName = "小火龙";
        spiritName = "Charmander";
        add_trick();
    }

    public void add_trick() {
        tricksList.add(Tricks.tornado); //添加招式
        tricksList.add(Tricks.dragonPole); //添加招式
        tricksList.add(Tricks.hurricane); //添加招式
        tricksList.add(Tricks.storm); //添加招式
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
                storm(enemySpirit);
                break;
            default:
                System.out.println("精灵没有该技能！");
                break;
        }
    }

    //招式：龙卷风
    public void tornado(SpiritRoot enemySpirit) {
        //System.out.println("{我方}使用招式：龙卷风!");
        this.sub_blood((30+(enemySpirit.grade * 3)+(this.attackPower * 4)), enemySpirit);
    }

    //招式：龙极
    public void dragonPole(SpiritRoot enemySpirit) {
        this.sub_blood((enemySpirit.blood - 30) / 2, enemySpirit);
    }

    //招式：hurricane
    public void hurricane(SpiritRoot enemySpirit) {
        this.sub_blood((this.attackPower * 2 + this.blood / 2) , enemySpirit);
    }

    //招式：storm
    public void storm(SpiritRoot enemySpirit) {
        this.sub_blood((this.attackPower * 2 + enemySpirit.blood) / 2, enemySpirit);
    }

}
