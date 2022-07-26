package org.sec;

public class AntDec {
    public static byte[] base64Decode(String str) throws Exception {
        String[] globalArr = new String[]{"sun.misc.BASE64Decoder",
                "decodeBuffer", "java.util.Base64", "getDecoder", "decode"};
        try {
            Class clazz = Class.forName(globalArr[0]);
            return (byte[]) clazz.getMethod(globalArr[1], String.class).invoke(clazz.newInstance(), str);
        } catch (Exception e) {
            Class clazz = Class.forName(globalArr[2]);
            Object decoder = clazz.getMethod(globalArr[3]).invoke(null);
            return (byte[]) decoder.getClass().getMethod(globalArr[4], String.class).invoke(decoder, str);
        }
    }
}