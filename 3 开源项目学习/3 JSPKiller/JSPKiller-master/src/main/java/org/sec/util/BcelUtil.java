package org.sec.util;

import java.io.*;
import java.util.zip.GZIPInputStream;

@SuppressWarnings("all")
public class BcelUtil {
    private static int[] MAP_CHAR = new int[256];
    private static final char ESCAPE_CHAR = '$';
    private static final int FREE_CHARS = 48;
    private static int[] CHAR_MAP = new int[FREE_CHARS];

    static {
        int j = 0, k = 0;
        for (int i = 'A'; i <= 'Z'; i++) {
            CHAR_MAP[j] = i;
            MAP_CHAR[i] = j;
            j++;
        }
        for (int i = 'g'; i <= 'z'; i++) {
            CHAR_MAP[j] = i;
            MAP_CHAR[i] = j;
            j++;
        }
        CHAR_MAP[j] = '$';
        MAP_CHAR['$'] = j;
        j++;
        CHAR_MAP[j] = '_';
        MAP_CHAR['_'] = j;
    }

    private static class JavaReader extends FilterReader {
        public JavaReader(Reader in) {
            super(in);
        }

        public int read() throws IOException {
            int b = in.read();
            if (b != ESCAPE_CHAR) {
                return b;
            } else {
                int i = in.read();
                if (i < 0) return -1;
                if (((i >= '0') && (i <= '9')) || ((i >= 'a') && (i <= 'f'))) {
                    int j = in.read();
                    if (j < 0) return -1;
                    char[] tmp = {(char) i, (char) j};
                    int s = Integer.parseInt(new String(tmp), 16);
                    return s;
                } else {
                    return MAP_CHAR[i];
                }
            }
        }

        public int read(char[] cbuf, int off, int len) throws IOException {
            for (int i = 0; i < len; i++) cbuf[off + i] = (char) read();
            return len;
        }
    }

    public static byte[] decode(String s, boolean uncompress) throws IOException {
        char[] chars = s.toCharArray();
        CharArrayReader car = new CharArrayReader(chars);
        JavaReader jr = new JavaReader(car);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int ch;
        while ((ch = jr.read()) >= 0) {
            bos.write(ch);
        }
        bos.close();
        car.close();
        jr.close();
        byte[] bytes = bos.toByteArray();
        if (uncompress) {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
            byte[] tmp = new byte[bytes.length * 3];
            int count = 0;
            int b;
            while ((b = gis.read()) >= 0) tmp[count++] = (byte) b;
            bytes = new byte[count];
            System.arraycopy(tmp, 0, bytes, 0, count);
        }
        return bytes;
    }
}