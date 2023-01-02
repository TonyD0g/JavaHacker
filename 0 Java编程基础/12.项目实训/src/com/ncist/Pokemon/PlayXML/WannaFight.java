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
import java.util.ArrayList;
import java.util.List;

import static com.ncist.Pokemon.Main.Main.useraccount;

public class WannaFight {
    //修改想要出战的精灵
    public static void wanna_fight(ArrayList<Integer> arrayNum) throws DocumentException {
        // 创建SAXReader实例,读xml文件必备
        SAXReader reader = new SAXReader();
        // read()读取指定的XML文档并形成DOM树
        String fileName = String.format("D:\\Coding\\C\\Pokemon\\XML\\%d.xml", useraccount);
        Document document = reader.read(new File(fileName));

        // getRootElement()获取根节点
        Element rootEle = document.getRootElement();
        // elements()获取根节点的子节点
        List<Element> playersEles = rootEle.elements();

        //选择FightSpirit节点
        Element FightEles = playersEles.get(3);
        //将FightSpirit节点作为根节点
        List<Element> fightRoot = FightEles.elements();

        int i = 1, x = 0;
        //对4个fightNum 中分别添加4个精灵编号
        // 23 34 12 11
        for (; x < arrayNum.size(); x++) {
            Element FightEle = fightRoot.get(x);
            FightEle.setText(String.valueOf(arrayNum.get(i - 1)));
            i++;
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

}
