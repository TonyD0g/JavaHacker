package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
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
                ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,   // access 表示当前类的访问标识（access flag）信息。access的取值是ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE，也可以写成ACC_PUBLIC | ACC_ABSTRACT | ACC_INTERFACE。
                "sample/HelloWorld",                         // name 表示当前类的名字，它采用的格式是Internal Name的形式。
                null,                                        // signature 表示当前类的泛型信息。因为在这个接口当中不包含任何的泛型信息，因此它的值为null
                "java/lang/Object",                          // superName 表示当前类的父类信息，它采用的格式是Internal Name的形式。
                null                                         // interfaces 表示当前类实现了哪些接口信息。
        );
        {
            FieldVisitor fv1 = cw.visitField(
                    ACC_PUBLIC + ACC_FINAL + ACC_STATIC, // access参数：表示当前字段或方法带有的访问标识（access flag）信息，例如ACC_PUBLIC、ACC_STATIC和ACC_FINAL等。
                    "LESS", // name参数：表示当前字段或方法的名字。
                    "I", // descriptor参数：表示当前字段或方法的描述符。属性参数
                         // 描述符: B - byte，C - char，D - double，F - float，I - int，J - long，S - short，Z - boolean，V - void，L - 对象类型(如Ljava/lang/String)，数组 - 每一个维度前置使用[表示

                    null, // signature参数：表示当前字段或方法是否带有泛型信息。换句话说，如果不带有泛型信息，提供一个null就可以了；如果带有泛型信息，就需要给它提供某一个具体的值。
                    -1 // value参数：是visitField()方法的第5个参数。这个参数的取值，与当前字段是否为常量有关系。如果当前字段是一个常量，就需要给value参数提供某一个具体的值；如果当前字段不是常量，那么使用null就可以了。
            );
            fv1.visitEnd();
        }

        {
            FieldVisitor fv2 = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0);
            fv2.visitEnd();
        }

        {
            FieldVisitor fv3 = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1);
            fv3.visitEnd();
        }

        {
            MethodVisitor mv1 = cw.visitMethod(
                    ACC_PUBLIC + ACC_ABSTRACT, // access参数：表示当前字段或方法带有的访问标识（access flag）信息，例如ACC_PUBLIC、ACC_STATIC和ACC_FINAL等。
                    "compareTo", // name参数：表示当前字段或方法的名字。
                    "(Ljava/lang/Object;)I", // descriptor参数：返回值
                    // 描述符: B - byte，C - char，D - double，F - float，I - int，J - long，S - short，Z - boolean，V - void，L - 对象类型(如Ljava/lang/String)，数组 - 每一个维度前置使用[表示
                    // 举例: int add(int a, int b): (II)I
                    //      void test(int a, int b): (II)V

                    null,  // signature参数：表示当前字段或方法是否带有泛型信息。换句话说，如果不带有泛型信息，提供一个null就可以了；如果带有泛型信息，就需要给它提供某一个具体的值。
                    null // exceptions参数：是visitMethod()方法的第5个参数。这个参数的取值，与当前方法头（Method Header）中是否具有throws XxxException相关。
            );
            mv1.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }
}
