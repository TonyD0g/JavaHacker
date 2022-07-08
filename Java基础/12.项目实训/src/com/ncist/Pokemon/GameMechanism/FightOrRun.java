//用于判断 是否战斗，如果选择战斗则进入战斗模块，选择逃跑则直接推迟
package com.ncist.Pokemon.GameMechanism;

import com.ncist.Pokemon.Spirit.SpiritOption.SpiritArray;

public class FightOrRun {
    public SpiritArray called;
    public int runFlag; //逃跑选项，如果为1则说明不出战，选择逃跑，会掉相应的钱
}
