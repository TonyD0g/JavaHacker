package org.sec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Webshell {
    public static void invoke(HttpServletRequest request,
                              HttpServletResponse response,
                              PrintWriter out) {
        try {
            __WEBSHELL__
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
