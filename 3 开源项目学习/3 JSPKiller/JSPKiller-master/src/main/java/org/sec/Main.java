package org.sec;

import com.beust.jcommander.JCommander;
import com.google.googlejavaformat.java.Formatter;
import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.sec.config.Command;
import org.sec.config.Logo;
import org.sec.service.BcelShellClassVisitor;
import org.sec.service.ReflectionShellClassVisitor;
import org.sec.service.SimpleShellClassVisitor;
import org.sec.util.BcelUtil;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Logo.PrintLogo();
        logger.info("start code inspector");
        Command command = new Command();
        JCommander jc = JCommander.newBuilder().addObject(command).build();
        jc.parse(args);
        if (command.help) {
            jc.usage();
            return;
        }
        if (command.file == null || command.file.equals("")) {
            logger.error("file is null");
            return;
        }
        if (command.module == null || command.module.equals("")) {
            logger.warn("no select module");
            logger.info("use default module r");
            command.module = "r";
        }
        start(command);
    }

    private static void start(Command command) {
        try {
            Path path = Paths.get(command.file);
            if (!Files.exists(path)) {
                logger.error("webshell file not exits");
                return;
            }

            // Parse JSP
            byte[] jspBytes = Files.readAllBytes(path);
            String jspCode = new String(jspBytes);
            jspCode = jspCode.replace("<%@", "");

            String tempCode = jspCode.split("<%")[1];
            String finalJspCode = tempCode.split("%>")[0];
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("Webshell.java");
            if (inputStream == null) {
                logger.error("read template error");
                return;
            }

            StringBuilder resultBuilder = new StringBuilder();
            InputStreamReader ir = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(ir);
            String lineTxt;
            while ((lineTxt = reader.readLine()) != null) {
                resultBuilder.append(lineTxt).append("\n");
            }
            ir.close();
            reader.close();

            // Build New Java File
            String templateCode = resultBuilder.toString();
            String finalCode = templateCode.replace("__WEBSHELL__", finalJspCode);

            String formattedCode = new Formatter().formatSource(finalCode);
            Files.write(Paths.get("Webshell.java"), formattedCode.getBytes(StandardCharsets.UTF_8));

            // Dynamically Compile
            File toolsPath = new File(
                    System.getProperty("java.home")
                            .replace("jre", "lib") +
                            File.separator + "tools.jar");
            URL url = toolsPath.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new java.net.URL[]{url});
            Class<?> toolClazz = classLoader.loadClass("javax.tools.ToolProvider");
            Method method = toolClazz.getDeclaredMethod("getSystemJavaCompiler");
            JavaCompiler compiler = (JavaCompiler) method.invoke(null);
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                    null, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(
                    new File("Webshell.java"));
            List<String> optionList = new ArrayList<>();
            optionList.add("-classpath");
            optionList.add("lib.jar");
            optionList.add("-nowarn");
            FileOutputStream outputStream = new FileOutputStream("compile.txt");
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            JavaCompiler.CompilationTask task = compiler.getTask(writer, fileManager,
                    null, optionList, null, compilationUnits);
            task.call();
            writer.close();
            outputStream.close();
            // 动态编译完成，删除文件
            byte[] classData = Files.readAllBytes(Paths.get("Webshell.class"));
            if (!command.debug) {
                Files.delete(Paths.get("Webshell.class"));
                Files.delete(Paths.get("Webshell.java"));
                Files.delete(Paths.get("compile.txt"));
            }

            // Reflection Webshell
            if (command.module.contains("r")) {
                logger.info("start reflection webshell scan");
                try {
                    ClassReader cr = new ClassReader(classData);
                    ReflectionShellClassVisitor cv = new ReflectionShellClassVisitor();
                    cr.accept(cv, ClassReader.EXPAND_FRAMES);
                } catch (Exception e) {
                    logger.info("no reflection webshell");
                }
            }

            // BCEL Webshell
            if (command.module.contains("b")) {
                logger.info("start bcel webshell scan");
                try {
                    ClassReader cr = new ClassReader(classData);
                    BcelShellClassVisitor cv = new BcelShellClassVisitor();
                    cr.accept(cv, ClassReader.EXPAND_FRAMES);
                    Map<String, Object> data = cv.getAnalysisData();
                    if (data.containsKey("load-bcel") && data.containsKey("bcel-bytecode")) {
                        String bcelCode = (String) data.get("bcel-bytecode");
                        bcelCode = bcelCode.substring(8);
                        byte[] byteCode = BcelUtil.decode(bcelCode, true);
                        logger.info("analysis BCEL bytecode");
                        ClassReader bcelCr = new ClassReader(byteCode);
                        SimpleShellClassVisitor bcelCv = new SimpleShellClassVisitor();
                        bcelCr.accept(bcelCv, ClassReader.EXPAND_FRAMES);
                    }
                } catch (Exception e) {
                    logger.info("no bcel webshell");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
