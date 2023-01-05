package org.sec;

import org.junit.Test;
import org.sec.input.Command;
import org.sec.start.Application;

import java.io.IOException;

public class JSPHorseTest {
    @Test
    public void TestSimple() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.output = "simple.jsp";
        Application.doSimple(command);
    }

    @Test
    public void TestAnt() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.antSword = true;
        command.output = "ant.jsp";
        Application.doAnt(command);
    }

    @Test
    public void TestJavac() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.javacModule = true;
        command.output = "javac.jsp";
        Application.doJavac(command);
    }

    @Test
    public void TestJS() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.jsModule = true;
        command.output = "js.jsp";
        Application.doJavaScript(command);
    }

    @Test
    public void TestExpr() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.exprModule = true;
        command.output = "expr.jsp";
        Application.doExpr(command);
    }

    @Test
    public void TestClassLoader() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.classLoaderModule = true;
        command.output = "cl.jsp";
        Application.doClassLoader(command);
    }

    @Test
    public void TestClassLoaderAsm() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.classLoaderAsmModule = true;
        command.output = "cl-asm.jsp";
        Application.doClassLoaderAsm(command);
    }

    @Test
    public void TestBCEL() throws IOException {
        Command command = new Command();
        command.password = "4ra1n";
        command.bcelModule = true;
        command.output = "bcel.jsp";
        Application.doBcel(command);
    }

    @Test
    public void TestBCELAsm() throws Exception {
        Command command = new Command();
        command.password = "4ra1n";
        command.bcelAsmModule = true;
        command.output = "bcel-asm.jsp";
        Application.doBcelAsm(command);
    }

    @Test
    public void TestProxy() throws Exception {
        Command command = new Command();
        command.password = "4ra1n";
        command.proxyModule = true;
        command.output = "proxy.jsp";
        Application.doProxy(command);
    }

    @Test
    public void TestProxyAsm() throws Exception {
        Command command = new Command();
        command.password = "4ra1n";
        command.proxyAsmModule = true;
        command.output = "proxy-asm.jsp";
        Application.doProxyAsm(command);
    }

    @Test
    public void TestUnicode() throws Exception {
        Command command = new Command();
        command.password = "4ra1n";
        command.proxyAsmModule = true;
        command.output = "proxy-asm-u.jsp";
        command.unicode = true;
        Application.doProxyAsm(command);
    }
}
