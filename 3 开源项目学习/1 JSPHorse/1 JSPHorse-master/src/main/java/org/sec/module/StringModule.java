package org.sec.module;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import org.apache.log4j.Logger;
import org.sec.util.EncodeUtil;

import java.util.List;
import java.util.Random;
// 对 shell 进行混淆
public class StringModule {
    private static final Logger logger = Logger.getLogger(StringModule.class);
    private static CroptoArray croptoArray;

    // 凯撒加密函数
    public static int encodeString(MethodDeclaration method) {
        logger.info("encode string variables");

        // 取一个随机数
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int offset1 = random.nextInt(9) + 1;

        // 获取文件中的所有字符串,并进行处理
        List<StringLiteralExpr> stringList = method.findAll(StringLiteralExpr.class);
        for (StringLiteralExpr s : stringList) {
            if (s.getParentNode().isPresent()) {

                // 如果是数组中的字符串
                if (s.getParentNode().get() instanceof ArrayInitializerExpr) {
                    // 进行凯撒和base64加密
                    String encode = EncodeUtil.encryption(s.getValue(), offset1);
                    // 可能会有意外的换行
                    encode = encode.replace(System.getProperty("line.separator"), "");
                    // 设置回去
                    s.setValue(encode);
                }
            }
        }
        // 记录偏移量
        return offset1;


    }

    // 凯撒解密函数
    public static void changeRef(MethodDeclaration method, int offset) {
        logger.info("change variable name in global array");

        // 所有的数组访问对象
        List<ArrayAccessExpr> arrayExpr = method.findAll(ArrayAccessExpr.class);
        for (ArrayAccessExpr expr : arrayExpr) {
            // 如果访问的是globalArr
            if (expr.getName().asNameExpr().getNameAsString().equals("globalArr")) {

                // 造一个方法调用对象，调用的是解密dec方法
                MethodCallExpr methodCallExpr = new MethodCallExpr();
                methodCallExpr.setName("dec");
                methodCallExpr.setScope(null);

                // dec方法参数需要的是NodeList对象
                NodeList<Expression> nodeList = new NodeList<>();
                ArrayAccessExpr a = new ArrayAccessExpr();
                a.setName(expr.getName());
                a.setIndex(expr.getIndex());

                // 第一个参数为原来的数组调用
                // (这里说的参数指的是 resources/Dec.java 文件的 dec 函数的两个参数：String str, int offset)

                nodeList.add(a);
                // 记录的offset需要传入第二个参数
                IntegerLiteralExpr intValue = new IntegerLiteralExpr();
                // 塞回去
                intValue.setValue(String.valueOf(offset));
                nodeList.add(intValue);
                // 设置参数
                methodCallExpr.setArguments(nodeList);
                expr.replace(methodCallExpr);
            }
        }
    }
}
