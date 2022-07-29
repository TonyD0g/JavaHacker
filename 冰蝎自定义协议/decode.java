private byte[] Decrypt(byte[] data) throws Exception
{
        byte[] decodebs;
        Class baseCls ;
        try{
                baseCls=Class.forName("java.util.Base64");
                Object Decoder=baseCls.getMethod("getDecoder", null).invoke(baseCls, null);
                decodebs=(byte[]) Decoder.getClass().getMethod("decode", new Class[]{byte[].class}).invoke(Decoder, new Object[]{data});
                decodebs=(byte[]) Decoder.getClass().getMethod("decode", new Class[]{byte[].class}).invoke(Decoder, new Object[]{decodebs});
            }

        catch (Throwable e)
            {
                baseCls = Class.forName("sun.misc.BASE64Decoder");
                Object Decoder=baseCls.newInstance();
                decodebs=(byte[]) Decoder.getClass().getMethod("decodeBuffer",new Class[]{String.class}).invoke(Decoder, new Object[]{new String(data)});
                decodebs=(byte[]) Decoder.getClass().getMethod("decodeBuffer",new Class[]{String.class}).invoke(Decoder, new Object[]{new String(decodebs)});

            }

        String key="25f9e794323b4538";
        for (int i = 0; i < decodebs.length; i++) {
            decodebs[i] = (byte) ((decodebs[i]) ^ (key.getBytes()[i + 1 & 15]));
            }

        

        String str = new String(decodebs);

        char c;
        int offset = 3;
        StringBuilder str1 = new StringBuilder();


        for (int i = 0; i < str.length(); i++) {

            c = str.charAt(i);
            if (c >= 'a' && c <= 'z') {
                    c = (char) (((c - 'a') - offset + 26) % 26 + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                    c = (char) (((c - 'A') - offset + 26) % 26 + 'A');
            }
            str1.append(c);
        }


        //转换回 byte[]
        String str2 = str1.toString();
        decodebs = str2.getBytes();


    return decodebs;
    
}