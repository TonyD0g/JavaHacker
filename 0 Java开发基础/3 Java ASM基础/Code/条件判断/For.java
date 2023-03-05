package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorldGenerateCore {
    public static void main(String[] args) throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloWorld",
                null, "java/lang/Object", null);

        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            mv1.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(0, 0);
            mv1.visitEnd();
        }

        {
            MethodVisitor methodVisitor = cw.visitMethod(ACC_PUBLIC, "test", "()V", null, null);
            Label conditionLabel = new Label();
            Label returnLabel = new Label();

            // 第1段
            methodVisitor.visitCode();
            methodVisitor.visitInsn(ICONST_0);
            // 将 0存储到1号槽位
            methodVisitor.visitVarInsn(ISTORE, 1);

            // 第2段
            methodVisitor.visitLabel(conditionLabel);
            // 将 1号槽位 中的数据加载到 操作数栈 中
            methodVisitor.visitVarInsn(ILOAD, 1);
            // 将 10 压入 操作数栈 中
            methodVisitor.visitIntInsn(BIPUSH, 10);
            // 两者进行比较，如果相等则跳转到 returnLabel
            methodVisitor.visitJumpInsn(IF_ICMPGE, returnLabel);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(ILOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

            // iinc指令, i = i + 1
            methodVisitor.visitIincInsn(1, 1);
            // 回到 for循环中去
            methodVisitor.visitJumpInsn(GOTO, conditionLabel);

            // 第3段
            methodVisitor.visitLabel(returnLabel);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }
}

