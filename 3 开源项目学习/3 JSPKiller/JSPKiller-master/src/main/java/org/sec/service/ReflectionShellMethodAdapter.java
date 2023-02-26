package org.sec.service;

import org.apache.log4j.Logger;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sec.core.CoreMethodAdapter;

import java.util.*;

public class ReflectionShellMethodAdapter extends CoreMethodAdapter<String> {
    private Logger logger = Logger.getLogger(ReflectionShellMethodAdapter.class);

    private final int access;
    private final String desc;
    private final Map<String, List<Boolean>> analysisData;

    public ReflectionShellMethodAdapter(int api, MethodVisitor mv, String owner,
                                        int access, String name, String desc,
                                        String signature, String[] exceptions,
                                        Map<String, List<Boolean>> analysisData) {
        super(api, mv, owner, access, name, desc, signature, exceptions);
        this.access = access;
        this.desc = desc;
        this.analysisData = analysisData;
    }

    // 我们可以在 INVOKEINTERFACE 的时候编写如下代码
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (opcode == Opcodes.INVOKEINTERFACE) {
            // 是否符合 request.getParameter() 调用
            boolean getParam = name.equals("getParameter") &&
                    owner.equals("javax/servlet/http/HttpServletRequest") &&
                    desc.equals("(Ljava/lang/String;)Ljava/lang/String;");
            // 符合就加污点标记
            if (getParam) {
                // 注意一定先让父类模拟弹栈调用操作，模拟完栈顶是返回值
                super.visitMethodInsn(opcode, owner, name, desc, itf);
                logger.info("find source: request.getParameter");
                operandStack.get(0).add("get-param");
                return;
            }
        }
        if (opcode == Opcodes.INVOKESTATIC) {
            boolean forName = name.equals("forName") &&
                    owner.equals("java/lang/Class") &&
                    desc.equals("(Ljava/lang/String;)Ljava/lang/Class;");
            if (forName) {
                if (operandStack.get(0).contains("ldc-runtime")) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    logger.info("-> get Runtime class");
                    operandStack.get(0).add("class-runtime");
                    return;
                }
            }
        }
        if (opcode == Opcodes.INVOKEVIRTUAL) {
            boolean getMethod = name.equals("getMethod") &&
                    owner.equals("java/lang/Class") &&
                    desc.equals("(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;");

            boolean invoke = name.equals("invoke") &&
                    owner.equals("java/lang/reflect/Method") &&
                    desc.equals("(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
            boolean append = name.equals("append") &&
                    owner.equals("java/lang/StringBuilder") &&
                    desc.equals("(Ljava/lang/String;)Ljava/lang/StringBuilder;");
            boolean toString = name.equals("toString") &&
                    owner.equals("java/lang/StringBuilder") &&
                    desc.equals("()Ljava/lang/String;");
            if (append) {
                if (operandStack.get(0).size() != 0) {
                    String before = null;
                    if (operandStack.get(1).size() != 0) {
                        before = new ArrayList<>(operandStack.get(1)).get(0);
                    }
                    if (before == null) {
                        before = new ArrayList<>(operandStack.get(0)).get(0);
                    } else {
                        before += new ArrayList<>(operandStack.get(0)).get(0);
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    operandStack.get(0).add(before);
                    return;
                }
            }
            if (toString) {
                if (operandStack.get(0).size() != 0) {
                    List<String> data = new ArrayList<>(operandStack.get(0));
                    StringBuilder builder = new StringBuilder();
                    for (String s : data) {
                        builder.append(s);
                    }
                    String result = builder.toString();
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (result.equals("exec")) {
                        operandStack.get(0).add("ldc-exec");
                    }
                    if (result.equals("getRuntime")) {
                        operandStack.get(0).add("ldc-get-runtime");
                    }
                    return;
                }
            }
            if (getMethod) {
                if (operandStack.get(1).contains("ldc-get-runtime")) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    logger.info("-> get getRuntime method");
                    operandStack.get(0).add("method-get-runtime");
                    return;
                }
                if (operandStack.get(1).contains("ldc-exec")) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    logger.info("-> get exec method");
                    operandStack.get(0).add("method-exec");
                    return;
                }
            }
            if (invoke) {
                if (operandStack.get(0).contains("get-param")) {
                    if (operandStack.get(2).contains("method-exec")) {
                        logger.info("find reflection webshell!");
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                        return;
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    logger.info("-> method exec invoked");
                    return;
                }
            }
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.AASTORE) {
            if (operandStack.get(0).contains("get-param")) {
                logger.info("store request param into array");
                super.visitInsn(opcode);
                // AASTORE 模拟操作之后栈顶是数组引用
                operandStack.get(0).clear();
                // 由于数组中包含了可控变量所以设置 flag
                operandStack.get(0).add("get-param");
                return;
            }
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst.equals("java.lang.Runtime")) {
            super.visitLdcInsn(cst);
            operandStack.get(0).add("ldc-runtime");
            return;
        }
        if (cst.equals("getRuntime")) {
            super.visitLdcInsn(cst);
            operandStack.get(0).add("ldc-get-runtime");
            return;
        }
        if (cst.equals("exec")) {
            super.visitLdcInsn(cst);
            operandStack.get(0).add("ldc-exec");
            return;
        }
        if (cst instanceof String) {
            super.visitLdcInsn(cst);
            operandStack.get(0).add((String) cst);
            return;
        }
        super.visitLdcInsn(cst);
    }
}
