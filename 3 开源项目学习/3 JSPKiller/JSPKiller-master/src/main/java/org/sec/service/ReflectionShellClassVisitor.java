package org.sec.service;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.JSRInlinerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionShellClassVisitor extends ClassVisitor {
    private Map<String, List<Boolean>> analysisData;

    private String name;
    private String signature;
    private String superName;
    private String[] interfaces;

    public ReflectionShellClassVisitor() {
        super(Opcodes.ASM8);
        this.analysisData = new HashMap<>();
    }

    public Map<String, List<Boolean>> getAnalysisData() {
        return analysisData;
    }


    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        // 不用关注构造方法只分析 invoke 方法即可
        // 因为我们的 JSP Webshell 逻辑都写在 Webshell.invoke 方法中，所以检测逻辑在 ReflectionShellMethodAdapter 类中
        if (name.equals("invoke")) {
            ReflectionShellMethodAdapter reflectionShellMethodAdapter = new ReflectionShellMethodAdapter(
                    Opcodes.ASM8,
                    mv, this.name, access, name, descriptor, signature, exceptions,
                    analysisData
            );
            // 出于兼容性的考虑向后传递
            return new JSRInlinerAdapter(reflectionShellMethodAdapter,
                    access, name, descriptor, signature, exceptions);
        }
        return mv;
    }
}
