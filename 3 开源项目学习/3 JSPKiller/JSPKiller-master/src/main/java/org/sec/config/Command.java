package org.sec.config;

import com.beust.jcommander.Parameter;

/**
 * 项目本身的一些参数
 */
public class Command {
    @Parameter(names = {"-h", "--help"}, description = "Help Info", help = true)
    public boolean help;

    @Parameter(names = {"-f", "--file"}, description = "Webshell File")
    public String file;

    @Parameter(names = {"-m", "--module"}, description = "Scan Module")
    public String module;

    @Parameter(names = {"--debug"}, description = "Debug")
    public boolean debug;
}
