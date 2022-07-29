private static byte[] Encrypt(byte[] data) throws Exception
    {
        String str = new String(data);

        char c;
        int offset = 3;
        StringBuilder str1 = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            c = str.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (((c - 'a') + offset) % 26 + 'a');
            } else if (c >= 'A' && c <= 'Z') {
                c = (char) (((c - 'A') + offset) % 26 + 'A');
            }
            str1.append(c);
        }


        //转换回 byte[]
        String str2 = str1.toString();
        data = str2.getBytes();

        String key="25f9e794323b4538";
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ((data[i]) ^ (key.getBytes()[i + 1 & 15]));
        }

        byte[] encrypted ;
        Class baseCls;
        try
        {
            baseCls=Class.forName("java.util.Base64");
            Object Encoder=baseCls.getMethod("getEncoder", null).invoke(baseCls, null);
            encrypted= (byte[]) Encoder.getClass().getMethod("encode", new Class[]{byte[].class}).invoke(Encoder, new Object[]{data});
             encrypted= (byte[]) Encoder.getClass().getMethod("encode", new Class[]{byte[].class}).invoke(Encoder, new Object[]{encrypted});

        }
        catch (Throwable error)
        {
            baseCls=Class.forName("sun.misc.BASE64Encoder");
            Object Encoder=baseCls.newInstance();
            String result=(String) Encoder.getClass().getMethod("encode",new Class[]{byte[].class}).invoke(Encoder, new Object[]{data});
            result=result.replace("\n", "").replace("\r", "");
             result=(String) Encoder.getClass().getMethod("encode",new Class[]{byte[].class}).invoke(Encoder, new Object[]{result});
             result=result.replace("\n", "").replace("\r", "");


            encrypted=result.getBytes();

        }


        return encrypted;
    }