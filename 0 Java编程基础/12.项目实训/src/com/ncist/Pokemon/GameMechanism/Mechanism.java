//主要负责游戏的游戏机制，因为是回合制，所以要写回合制的逻辑
package com.ncist.Pokemon.GameMechanism;


import org.dom4j.DocumentException;

public class Mechanism {
    public static void mechanism() throws DocumentException {
        //回合战斗机制
        FightMechanism.Round_system();
    }
}
