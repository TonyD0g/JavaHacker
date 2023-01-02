package com.ncist.Pokemon.Spirit.SpiritOption;

import com.ncist.Pokemon.PlayXML.ChangeXml;
import org.dom4j.DocumentException;

import static com.ncist.Pokemon.Message.MessageMain.input;

public class choice_ball {
    //选择精灵球
    public static int choice_fairyBall() throws DocumentException {
        System.out.println("[+] 选择想要使用的精灵球:\n[0]普通精灵球 5块\t[1]中级精灵球 7块\t[2]高级精灵球 10块");
        int choice = input.nextInt();
        switch (choice) {
            case 0:
                ChangeXml.change_moeny(0,5);
                System.out.println("[+] 选择了购买普通精灵球，正在抓捕");
                return 0;
            case 1:
                ChangeXml.change_moeny(0,1);
                System.out.println("[+] 选择了购买中级精灵球，正在抓捕");
                return 1;
            case 2:
                ChangeXml.change_moeny(0,2);
                System.out.println("[+] 选择了购买高级精灵球，正在抓捕");
                return 2;
            default:
                System.out.println("[-] 选择了不买任何精灵球，无法抓捕");
                return -1;
        }
    }

}
