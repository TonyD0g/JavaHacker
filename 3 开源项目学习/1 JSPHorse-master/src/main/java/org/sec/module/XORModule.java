package org.sec.module;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class XORModule {
    private static final Logger logger = Logger.getLogger(XORModule.class);

    // 数字异或
    public static void doXOR(MethodDeclaration method) {
        logger.info("do integer xor operate in method");
        doCallableXOR(method);
    }

    // 数字异或
    public static void doXORForConstruct(ConstructorDeclaration cd) {
        logger.info("do integer xor operate in constructor");
        doCallableXOR(cd);
    }

    // 数字异或 主函数
    private static void doCallableXOR(CallableDeclaration<?> c) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 遍历所有的简单数字对象，并处理
        List<IntegerLiteralExpr> integers = c.findAll(IntegerLiteralExpr.class);
        for (IntegerLiteralExpr i : integers) {
            // 原来的数字a
            int value = Integer.parseInt(i.getValue());
            // 随机的数字b
            int key = random.nextInt(1000000) + 1000000;
            // c=a^b
            int cipherNum = value ^ key;
            // 用一个括号包裹a^b防止异常
            EnclosedExpr enclosedExpr = new EnclosedExpr();
            BinaryExpr binaryExpr = new BinaryExpr();

            // ast中构造一个c^b
            binaryExpr.setLeft(new IntegerLiteralExpr(String.valueOf(cipherNum)));
            binaryExpr.setOperator(BinaryExpr.Operator.XOR);
            binaryExpr.setRight(new IntegerLiteralExpr(String.valueOf(key)));

            // 塞回去
            enclosedExpr.setInner(binaryExpr);
            i.replace(enclosedExpr);
        }
    }
}
