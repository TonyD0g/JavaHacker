package org.sec.util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 流相关操作
 */
public class IOUtil {
    private static final Logger logger = Logger.getLogger(IOUtil.class);

    /**
     * 将输入流复制给输出流实现写文件
     * 注意copy完之后原来的流就不能再读了
     * @param inputStream 输入流
     * @param outputStream 输出流
     */
    public static void copy(InputStream inputStream, OutputStream outputStream) {
        try {
            final byte[] buffer = new byte[4096];
            int n;
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
        } catch (Exception e) {
            logger.error("error ", e);
        }
    }
}
