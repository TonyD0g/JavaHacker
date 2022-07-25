package org.sec.module;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwitchModule {
    private static final Logger logger = Logger.getLogger(SwitchModule.class);

    // 打乱switch-case块的代码
    public static void changeSwitch(MethodDeclaration method, String target) {
        logger.info("change switch statements order");
        String[] a = target.split("\\|");

        // 得到Switch语句为了后文的替换
        SwitchStmt stmt = method.findFirst(SwitchStmt.class).isPresent() ?
                method.findFirst(SwitchStmt.class).get() : null;
        if (stmt == null) {
            return;
        }

        // 得到所有的Case块
        List<SwitchEntry> entryList = method.findAll(SwitchEntry.class);
        for (int i = 0; i < entryList.size(); i++) {
            // Case块的Label是数字
            if (entryList.get(i).getLabels().get(0) instanceof IntegerLiteralExpr) {
                // 拿到具体的数字对象IntegerLiteralExpr
                IntegerLiteralExpr expr = (IntegerLiteralExpr) entryList.get(i).getLabels().get(0);
                // 设置为分发器对应的顺序数字,即替换掉 case块中的label
                expr.setValue(a[i]);
            }
        }

        // 打乱Case块集合,随机置换指定列表元素
        NodeList<SwitchEntry> switchEntries = new NodeList<>();
        Collections.shuffle(entryList);
        switchEntries.addAll(entryList);

        // 塞回原来的Switch中
        stmt.setEntries(switchEntries);
    }

    // 打乱分发器顺序
    public static String shuffle(MethodDeclaration method) {
        logger.info("shuffle dispenser and switch cases");
        Random rand = new Random();
        String result = null;
        rand.setSeed(System.currentTimeMillis());

        // 对 ast中的所有数组表达式 进行处理
        List<ArrayInitializerExpr> arrayExpr = method.findAll(ArrayInitializerExpr.class);
        for (ArrayInitializerExpr expr : arrayExpr) {
            Node target = expr.getChildNodes().get(0);

            if (target instanceof StringLiteralExpr) {

                // StringLiteralExpr对象就是简单的字符串
                String value = ((StringLiteralExpr) target).getValue();

                // 如果包含了这个符号认为是分发器
                // 即将数组中的 0|1|2|3|4|5|6|7|8|9|10|11|12|13 进行打乱
                if (value.contains("|")) {
                    String[] a = value.split("\\|");
                    int length = a.length;

                    // 一个简单的数组打乱算法
                    for (int i = length; i > 0; i--) {
                        int randInd = rand.nextInt(i);
                        String temp = a[randInd];
                        a[randInd] = a[i - 1];
                        a[i - 1] = temp;
                    }

                    // 打乱后的数字再用|拼起来
                    StringBuilder sb = new StringBuilder();
                    for (String s : a) {
                        sb.append(s).append("|");
                    }

                    String finalStr = sb.toString();
                    finalStr = finalStr.substring(0, finalStr.length() - 1);

                    // 打乱后的分发器设置回去
                    ((StringLiteralExpr) target).setValue(finalStr);
                    result = finalStr;
                }
            }
        }
        return result;
    }
}
