package com.ncist.Pokemon.PlayXML;

import com.ncist.Pokemon.Message.MessageMain;
import com.ncist.Pokemon.Spirit.SpiritOption.SpiritArray;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ncist.Pokemon.Main.Main.useraccount;


public class ChangeXml {

    // 详见：https://www.cnblogs.com/xiqingbo/p/java-09.html

    //修改个人xml中所有精灵的血量 恢复血量
    public static void recovery_blood() throws DocumentException {
        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        //选择SpiritNums节点
        Element player = playersEles.get(4);
        //将SpiritNums节点作为根节点
        List<Element> SpiritEles = player.elements();
        //进入Spirit节点,并遍历Spirit节点
        for (Element SpiritElement : SpiritEles) {
            //将原始状态的血量给到blood
            Element bloodText = SpiritElement.element("blood");
            Element orignBloodText = SpiritElement.element("orignBlood");
            bloodText.setText(orignBloodText.getText());
            System.out.println("\n~~~~~~在生命泉水的帮助下，每个精灵都焕然一新~~~~~~");
            MessageMain.stop_key();
            break;
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter(fileName);
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改个人金钱
    public static void change_moeny(int isAdd, int money) throws DocumentException {
        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        Element player = playersEles.get(2);
        int lastMoney;
        if (isAdd == 1) {
            lastMoney = Integer.parseInt(player.getText()) + money;
        } else {
            lastMoney = Integer.parseInt(player.getText()) - money;
        }
        player.setText(String.valueOf(lastMoney));

        //如果没钱了,就给0块钱
        if (Integer.parseInt(player.getText()) < 0) {
            player.setText("0");
        }


        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter(fileName);
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //战斗完成，将结果的血量，伤害，等级，经验等传回个人xml中
    public static void feedback_xml(int isVictory, String spiritNum, String blood, String attackPower, String grade, String exp) throws DocumentException {
        int money = 0;
        if (isVictory == 1) {
            money = (int) (Math.random() * 12 + 10);
            ChangeXml.change_moeny(1, money);
        }

        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        //选择SpiritNums节点
        Element player = playersEles.get(4);
        //将SpiritNums节点作为根节点
        List<Element> SpiritEles = player.elements();
        //进入Spirit节点,并遍历Spirit节点
        Element gradeText, bloodText, attackPowerText, expText, orignBloodText;
        for (Element SpiritElement : SpiritEles) {
            //获取sn属性.并与 传入的 spiritNum 作比较，如果相等则讲结果保存进xml
            Attribute attribute = SpiritElement.attribute("sn");
            if (Objects.equals(attribute.getValue(), spiritNum)) {
                //获取Spirit节点的相关信息,如名字，等级等

                //如果胜利了,则isVictory为1
                if (isVictory == 1) {
                    expText = SpiritElement.element("exp");
                    int randomExp = (int) (Math.random() * 22 + 1);
                    int intExp = Integer.parseInt(exp);
                    intExp = intExp + randomExp;
                    //大于100说明升级了
                    if (intExp > 100) {
                        System.out.println("~~~~~~精灵表现出色，学到了很多东西，成功升级！~~~~~~");
                        intExp = intExp - 100;
                        //等级+1
                        gradeText = SpiritElement.element("grade");
                        int intGrade = Integer.parseInt(grade);
                        intGrade++;
                        gradeText.setText(String.valueOf(intGrade));

                        //因为升级了，所以血量和攻击力要上升
                        bloodText = SpiritElement.element("blood");
                        int intBlood = Integer.parseInt(blood);
                        intBlood = intBlood + (intGrade * 3) + 20;
                        bloodText.setText(String.valueOf(intBlood));

                        orignBloodText = SpiritElement.element("orignBlood");
                        int orignBlood = Integer.parseInt(orignBloodText.getText()) + (intGrade * 3) + 20;
                        orignBloodText.setText(String.valueOf(orignBlood));

                        attackPowerText = SpiritElement.element("attackPower");
                        int intAttackPower = Integer.parseInt(attackPower);
                        intAttackPower = intAttackPower + intGrade + 2;
                        attackPowerText.setText(String.valueOf(intAttackPower));

                        System.out.println(String.format("~~~~~~战斗结果↓~~~~~~\n获得金币：%d\n精灵等级：%d\t精灵经验值：%d\t精灵血量：%d\t精灵攻击力：%d\n~~~~~~战斗结果↑~~~~~~", money, intGrade, intExp, intBlood, intAttackPower));
                    } else {
                        feedback_xml_small(SpiritElement, blood, attackPower, grade, exp);
                        System.out.println(String.format("~~~~~~精灵表现出色，学到了很多东西！~~~~~~\n~~~~~~战斗结果↓~~~~~~\n获得金币：%d\n精灵等级：%s\t精灵经验值：%s\t精灵血量：%s\t精灵攻击力：%s\n~~~~~~战斗结果↑~~~~~~", money, grade, intExp, blood, attackPower));
                    }
                    expText.setText(String.valueOf(intExp));
                } else {
                    feedback_xml_small(SpiritElement, blood, attackPower, grade, exp);
                    System.out.println(String.format("~~~~~~精灵已经很努力了，可惜对手太强了~~~~~~\n~~~~~~战斗结果↓~~~~~~\n获得金币：0\n精灵等级：%s\t精灵经验值：%s\t精灵血量：%s\t精灵攻击力：%s\n~~~~~~战斗结果↑~~~~~~", grade, exp, blood, attackPower));
                }
            }
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter(fileName);
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //feedback_xml的一个小部分
    public static void feedback_xml_small(Element SpiritElement, String blood, String attackPower, String grade, String exp) {
        Element gradeText, bloodText, attackPowerText, expText;
        bloodText = SpiritElement.element("blood");
        bloodText.setText(blood);
        attackPowerText = SpiritElement.element("attackPower");
        attackPowerText.setText(attackPower);
        gradeText = SpiritElement.element("grade");
        gradeText.setText(grade);
        expText = SpiritElement.element("exp");
        expText.setText(exp);
    }

    //（初始化用）用个人信息创建xml文件
    public static void save_info_Xml(String PlayerNameText, String PlayerNumText, String PlayerMoneyText, String spiritNum, String nameText, String englishText, String gradeText, String bloodText, String orignBloodText, String attackPowerText) {
//        Document：表示xml文档信息，是一个树形结构。
//        Eelment：表示xml文件的元素节点，主要是提供一些元素的操作方法。
//        Attribute：表示元素结点中的属性，可以自定义。
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = DocumentHelper.createDocument();
        //创建根元素
        Element root = document.addElement("Players");

        //创建子元素
        Element PlayerName = root.addElement("PlayerName");
        PlayerName.setText(PlayerNameText);
        Element PlayerNum = root.addElement("PlayerNum");
        PlayerNum.setText(PlayerNumText);
        Element PlayerMoney = root.addElement("PlayerMoney");
        PlayerMoney.setText(PlayerMoneyText);

        Element FightSpirit = root.addElement("FightSpirit");
        Element Fight = FightSpirit.addElement("Fight");
        Element FightNum = Fight.addAttribute("sn", "1");
        FightNum.setText(String.valueOf(1));
        for (int i = 2; i < 5; i++) {
            Fight = FightSpirit.addElement("Fight");
            FightNum = Fight.addAttribute("sn", String.valueOf(i));
            FightNum.setText(String.valueOf(0));
        }

        Element SpiritNums = root.addElement("SpiritNums");

        Element Spirit = SpiritNums.addElement("Spirit");
        Element SpiritElement = Spirit.addAttribute("sn", spiritNum);

        Element name = SpiritElement.addElement("name");
        name.setText(nameText);
        Element english = SpiritElement.addElement("english");
        english.setText(englishText);
        Element grade = SpiritElement.addElement("grade");
        grade.setText(gradeText);
        Element exp = SpiritElement.addElement("exp");
        exp.setText("1");
        Element blood = SpiritElement.addElement("blood");
        blood.setText(bloodText);
        Element orignBlood = SpiritElement.addElement("orignBlood");
        orignBlood.setText(orignBloodText);
        Element attackPower = SpiritElement.addElement("attackPower");
        attackPower.setText(attackPowerText);

        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置xml文档的编码为utf-8
        format.setEncoding("utf-8");
        Writer out;
        try {
            //创建一个输出流对象
            out = new FileWriter(fileName);
            //创建一个dom4j创建xml的对象
            XMLWriter writer = new XMLWriter(out, format);
            //调用write方法将doc文档写到指定路径
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //展示要出战的精灵,,返回值：返回一个二维数组，数组中有 精灵编号 和对应的 精灵名字
    public static SpiritArray[] show_fight_spirit() throws DocumentException {
        //用于返回二维数组
        SpiritArray[] spiritArray1 = new SpiritArray[4];
        for (int i = 0; i < 4; i++) {
            spiritArray1[i] = new SpiritArray();
        }
        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        //将FightSpirit节点作为根节点
        Element Fight = playersEles.get(3);
        List<Element> FightEles = Fight.elements();

        String[] stringTextArray1 = new String[4];
        int i = 0;
        //进入FightSpirit节点,并遍历FightSpirit节点
        for (Element FightElement : FightEles) {
            //获取FightSpirit节点的信息,即获取要出战精灵的编号
            stringTextArray1[i] = (FightElement.getText());
            i++;
        }

        i = 0;
        while (i != 4) {
            if (stringTextArray1[i] != null) {
                System.out.printf(stringTextArray1[i] + " ");
            }
            i++;
        }
        System.out.println();

        //选择SpiritNums节点
        Element player = playersEles.get(4);
        //将SpiritNums节点作为根节点
        List<Element> SpiritEles = player.elements();
        //进入Spirit节点,并遍历Spirit节点
        i = 0;
        int snValue, gradeText, bloodText, attackPowerText, exp;
        String nameText, englishName;
        for (; i < stringTextArray1.length; i++) {
            for (Element SpiritElement : SpiritEles) {
                //获取Spirit节点的相关信息,如名字，等级等
                snValue = Integer.parseInt(SpiritElement.attributeValue("sn"));
                if (stringTextArray1[i] == null) {
                    break;
                }
                if (i < stringTextArray1.length - 1 && Integer.parseInt(stringTextArray1[i]) == snValue) {
                    nameText = SpiritElement.elementText("name");
                    englishName = SpiritElement.elementText("english");
                    gradeText = Integer.parseInt(SpiritElement.elementText("grade"));
                    exp = Integer.parseInt(SpiritElement.elementText("exp"));
                    bloodText = Integer.parseInt(SpiritElement.elementText("blood"));
                    attackPowerText = Integer.parseInt(SpiritElement.elementText("attackPower"));
                    spiritArray1[i].spiritNum = snValue;
                    spiritArray1[i].englishName = englishName;
                    spiritArray1[i].chineseName = nameText;
                    spiritArray1[i].grade = gradeText;
                    spiritArray1[i].blood = bloodText;
                    spiritArray1[i].attackPower = attackPowerText;
                    spiritArray1[i].exp = exp;
                    System.out.println(String.format("可出战的精灵编号：%d\t可出战的精灵名字：%s\t可出战的精灵等级：%d\n可出战的精灵经验：%d\t可出战的精灵血量：%d\t\t可出战的精灵攻击力：%d\n", snValue, nameText, gradeText, exp, bloodText, attackPowerText));
                    break;
                }
            }
        }

        return spiritArray1;
    }

    //展示输入的玩家编号的用户信息
    public static void main_for_showinfo() throws DocumentException {
        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        System.out.println("~~~~~~玩家信息：~~~~~~");
        String[] stringTextArray = new String[4];
        String[] stringTextArray1 = new String[4];

        //将子节点当做根节点,重复上诉过程
        for (int i = 0; i < 3; i++) {
            Element player = playersEles.get(i);
            stringTextArray[i] = player.getText();
        }

        //将FightSpirit节点作为根节点
        Element Fight = playersEles.get(3);
        List<Element> FightEles = Fight.elements();
        int i = 0;
        //进入FightSpirit节点,并遍历FightSpirit节点
        for (Element FightElement : FightEles) {
            //获取FightSpirit节点的信息,即获取要出战精灵的编号
            stringTextArray1[i] = FightElement.getText();
            i++;
        }

        //后期 用户ID 和 账户余额 需要强制类型转换
        System.out.printf(String.format("用户名：%s\t\t用户ID：%s\t\t账户余额：%s\t\t已选择要出战的精灵：(0编号则为不出战)", stringTextArray[0], stringTextArray[1], stringTextArray[2]));

        i = 0;
        while (i != 4) {
            if (stringTextArray1[i] != null) {
                System.out.printf(stringTextArray1[i] + " ");
            }
            i++;
        }
        System.out.println();

        //选择SpiritNums节点
        Element player = playersEles.get(4);
        //将SpiritNums节点作为根节点
        List<Element> SpiritEles = player.elements();
        //进入Spirit节点,并遍历Spirit节点
        i = 0;
        int snValue, gradeText, bloodText, attackPowerText, exp;
        System.out.println("~~~~~~第1页~~~~~~");
        for (Element SpiritElement : SpiritEles) {
            //获取Spirit节点的相关信息,如名字，等级等
            snValue = Integer.parseInt(SpiritElement.attributeValue("sn"));
            String nameText = SpiritElement.elementText("name");
            gradeText = Integer.parseInt(SpiritElement.elementText("grade"));
            exp = Integer.parseInt(SpiritElement.elementText("exp"));
            bloodText = Integer.parseInt(SpiritElement.elementText("blood"));
            attackPowerText = Integer.parseInt(SpiritElement.elementText("attackPower"));
            System.out.println(String.format("所拥有的精灵编号：%d\t所拥有的精灵名字：%s\t所拥有的精灵等级：%d\n所拥有的精灵经验值：%d\t所拥有的精灵血量：%d\t\t所拥有的精灵攻击力：%d\n", snValue, nameText, gradeText, exp, bloodText, attackPowerText));
            i++;
            if (i % 4 == 0 && i != 4 && i != 0) { //四个为一组输出
                //暂停键
                MessageMain.stop_key();
                System.out.println(String.format("~~~~~~第%d页~~~~~~", (i / 4) + 1));
            }
        }
        //暂停键
        MessageMain.stop_key();
    }
}

