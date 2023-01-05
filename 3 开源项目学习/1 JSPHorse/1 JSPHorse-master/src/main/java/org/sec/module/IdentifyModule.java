package org.sec.module;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import org.apache.log4j.Logger;
import org.sec.util.RandomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdentifyModule {
    private static final Logger logger = Logger.getLogger(IdentifyModule.class);

    // 异曲同工，都是调用同一个方法
    public static void doIdentify(MethodDeclaration method) {
        logger.info("do identify operate in method");
        doCallableIdentify(method);
    }

    // 异曲同工，都是调用同一个方法
    public static void doConstructIdentify(ConstructorDeclaration cd) {
        logger.info("do identify operate in constructor");
        doCallableIdentify(cd);
    }

    // 给字符串随机重命名
    private static void doCallableIdentify(CallableDeclaration<?> c) {
        // 键，值
        Map<String, String> vas = new HashMap<>();
        // 所有的数组访问对象,即对ast树的所有 VariableDeclarator 进行操作
        List<VariableDeclarator> vaList = c.findAll(VariableDeclarator.class);
        for (VariableDeclarator va : vaList) {
            String newName = RandomUtil.getRandomString(20);
            vas.put(va.getNameAsString(), newName);
            va.setName(newName);
        }

        //当一个表达式里有个 简单名称 的变量，则属于 NameExpr
        //In int x = a + 3; a is a SimpleName inside a NameExpr.
        // vas map 里的值传回 c 里面
        c.findAll(NameExpr.class).forEach(n -> {
            if (vas.containsKey(n.getNameAsString())) {
                n.setName(vas.get(n.getNameAsString()));
            }
        });
    }
}
