package com.ncist.Pokemon.PlayXML;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.ncist.Pokemon.Main.Main.useraccount;

public class JudgeIsHas {
    //查看要抓捕的精灵是否已经有，获取Spirit的编号，查看是否存在有该精灵，如果有，则返回0
    public static int judge_isHas(String spiritNum) throws DocumentException {
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
                //获取Spirit的编号，查看是否存在有该精灵，如果有，则返回0
                return 0;
            }
        }
        return 1;
    }

}
