package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
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
        cw.visit(
                V1_8,                                        // version 表示当前类的版本信息。其取值为Opcodes.V1_8，表示使用Java 8版本
                ACC_PUBLIC,   // access 表示当前类的访问标识（access flag）信息。access的取值是ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE，也可以写成ACC_PUBLIC | ACC_ABSTRACT | ACC_INTERFACE。
                "sample/HelloWorld",                         // name 表示当前类的名字，它采用的格式是Internal Name的形式。
                null,                                        // signature 表示当前类的泛型信息。因为在这个接口当中不包含任何的泛型信息，因此它的值为null
                "java/lang/Object",                          // superName 表示当前类的父类信息，它采用的格式是Internal Name的形式。
                null                                         // interfaces 表示当前类实现了哪些接口信息。
        );
        {
            FieldVisitor fv1 = cw.visitField(
                    ACC_PUBLIC + ACC_FINAL + ACC_STATIC, // access参数：表示当前字段或方法带有的访问标识（access flag）信息，例如ACC_PUBLIC、ACC_STATIC和ACC_FINAL等。
                    "intValue", // name参数：表示当前字段或方法的名字。
                    "I", // descriptor参数：表示当前字段或方法的描述符。属性参数
                         // 描述符: B - byte，C - char，D - double，F - float，I - int，J - long，S - short，Z - boolean，V - void，L - 对象类型( 如Ljava/lang/String; )，数组 - 每一个维度前置使用[表示

                    null, // signature参数：表示当前字段或方法是否带有泛型信息。换句话说，如果不带有泛型信息，提供一个null就可以了；如果带有泛型信息，就需要给它提供某一个具体的值。
                    100 // value参数：是visitField()方法的第5个参数。这个参数的取值，与当前字段是否为常量有关系。如果当前字段是一个常量，就需要给value参数提供某一个具体的值；如果当前字段不是常量，那么使用null就可以了。
            );
            fv1.visitEnd();
        }

        {
            FieldVisitor fv2 = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "strValue", "Ljava/lang/String;", null, "ABC");
            fv2.visitEnd();
        }
        // 构造函数
        {
            MethodVisitor mv1 = cw.visitMethod(
                    ACC_PUBLIC, // access参数：表示当前字段或方法带有的访问标识（access flag）信息，例如ACC_PUBLIC、ACC_STATIC和ACC_FINAL等。
                    "<init>", // name参数：表示当前字段或方法的名字。
                    "()V", // descriptor参数：返回值
                    // 描述符: B - byte，C - char，D - double，F - float，I - int，J - long，S - short，Z - boolean，V - void，L - 对象类型(如Ljava/lang/String)，数组 - 每一个维度前置使用[表示
                    // 举例: int add(int a, int b): (II)I
                    //      void test(int a, int b): (II)V

                    null,  // signature参数：表示当前字段或方法是否带有泛型信息。换句话说，如果不带有泛型信息，提供一个null就可以了；如果带有泛型信息，就需要给它提供某一个具体的值。
                    null // exceptions参数：是visitMethod()方法的第5个参数。这个参数的取值，与当前方法头（Method Header）中是否具有throws XxxException相关。
            );
            // 字节码编写
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            mv1.visitMethodInsn(
                    INVOKESPECIAL, // 相当于反射中的invoke,取值有: INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
                    "java/lang/Object", // 所执行的类型
                    "<init>", // 表示当前字段或方法的名字
                    "()V", // 返回类型
                    false // true则代表这个方法所属的class为 interface
            );
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(
                    0, //     maxStack - maximum stack size of the method.
                    0 // maxLocals - maximum number of local variables for the method.
            );
            mv1.visitEnd();
        }

        // test方法
        {
            MethodVisitor mv2 = cw.visitMethod(ACC_PUBLIC, "test", "(I)V", null, null);
            Label elseLabel = new Label(); // 定义else跳转标签
            Label returnLabel = new Label(); // 定义return跳转标签

            // 第1段
            mv2.visitCode();
            mv2.visitVarInsn(ILOAD, 1);
            mv2.visitJumpInsn(IFNE, elseLabel);

            // 1.获取方法所在类 2.获取参数 3.获取方法,invoke执行
            mv2.visitFieldInsn(
                    GETSTATIC, // GETSTATIC, PUTSTATIC, GETFIELD or PUTFIELD.
                    "java/lang/System", //the internal name of the field's owner class
                    "out", // the field's name.
                    "Ljava/io/PrintStream;" // the field's descriptor (see Type).
            );
            mv2.visitLdcInsn("value is 0");
            mv2.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv2.visitJumpInsn(GOTO, returnLabel);

            // 第2段
            mv2.visitLabel(elseLabel);
            mv2.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv2.visitLdcInsn("value is not 0");
            mv2.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            // 第3段
            mv2.visitLabel(returnLabel);
            mv2.visitInsn(RETURN);
            mv2.visitMaxs(0, 0);
            mv2.visitEnd();
        }
        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }
}
