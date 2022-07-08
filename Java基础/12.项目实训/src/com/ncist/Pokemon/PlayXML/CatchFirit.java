package com.ncist.Pokemon.PlayXML;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.ncist.Pokemon.Main.Main.useraccount;

public class CatchFirit {
    //抓捕精灵，将新精灵添加到个人xml中
    public static void catch_firit(String spiritNum, String nameText, String englishText, String gradeText, String bloodText, String orignBloodText, String attackPowerText) throws DocumentException {
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
        Element SpiritNums = playersEles.get(4);
        //将SpiritNums节点作为根节点

        int i = 1, x = 0;
        //添加一个精灵，有中文名字，英文名字，等级，经验，血量，初始血量，攻击力。
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
}
