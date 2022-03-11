package UDF; //UDF: user define function (用户自定义函数)
//String 转换为 Ascii


public class StringToAscii {
    public static void main(String[] args) {
        //字符串转换为Ascii的案例
        String stringTransformAscii = stringTransformAscii("TonyD0g");
        System.out.println("字符串转换为Ascii:" + stringTransformAscii);
        //Ascii转换为字符串的案例
        String asciiTransformString = asciiTransformString("84,111,110,121,68,48,103");
        System.out.println("Ascii转换为字符串:" + asciiTransformString);

        StringAloneToAscii("TonyD0g");
    }

    /**     方法1
     *  字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringTransformAscii(String value){
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1){
                sbu.append((int)chars[i]).append(",");
            } else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    /**     方法2
     *  Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiTransformString(String value){
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
    /**     方法3
     *  String 单字符转 Ascii
     * @param value
     * @return
     */
    public static void StringAloneToAscii(String value){
        int i=0;
        int maxLength = value.length();
        while(i<maxLength) {
            int Ascii = (int) value.charAt(i);
            System.out.println("'"+value.charAt(i) +"'"  + " =>   " + Ascii);
            i++;
        }
    }

}
