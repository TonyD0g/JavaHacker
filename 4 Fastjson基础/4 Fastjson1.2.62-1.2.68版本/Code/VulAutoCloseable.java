public class VulAutoCloseable implements AutoCloseable {
    public VulAutoCloseable(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void close() throws Exception {
 
    }
}