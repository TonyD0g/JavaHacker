package org.sec.input;

import com.beust.jcommander.Parameter;
// 用户可输入的各种参数
public class Command {
    @Parameter(names = {"-h", "--help"}, description = "Help Info", help = true)
    public boolean help;

    @Parameter(names = {"-p", "--password"}, description = "Webshell Password", required = true)
    public String password;

    @Parameter(names = {"-o", "--output"}, description = "Output File")
    public String output;

    @Parameter(names = {"-u", "--unicode"}, description = "Use Unicode")
    public boolean unicode;

    @Parameter(names = {"--js"}, description = "Use JavaScript Engine")
    public boolean jsModule;

    @Parameter(names = {"--javac"}, description = "Use Javac Module")
    public boolean javacModule;

    @Parameter(names = {"--expr"}, description = "Use Expression Module")
    public boolean exprModule;

    @Parameter(names = {"--bcel"}, description = "Use BCEL Module")
    public boolean bcelModule;

    @Parameter(names = {"--bcel-asm"}, description = "Use BCEL ASM Module")
    public boolean bcelAsmModule;

    @Parameter(names = {"--classloader"}, description = "Use ClassLoader Module")
    public boolean classLoaderModule;

    @Parameter(names = {"--classloader-asm"}, description = "Use ClassLoader ASM Module")
    public boolean classLoaderAsmModule;

    @Parameter(names = {"--proxy"}, description = "Use Proxy Module")
    public boolean proxyModule;

    @Parameter(names = {"--proxy-asm"}, description = "Use Proxy ASM Module")
    public boolean proxyAsmModule;

    @Parameter(names = {"--ant"}, description = "Use Ant Sword")
    public boolean antSword;
}
