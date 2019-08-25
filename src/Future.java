public class Future<V> {

    private V val;

    public synchronized void set(V v) {
        val = v;
        System.out.println("Wake from set in Future");
        notifyAll();
    }

    public V get() throws InterruptedException {
        if (val == null) {
            synchronized (this) {
                if (val == null) {
                    System.out.println("I entered to wait in Future");
                    wait();
                }
            }
        }
        return val;
    }
}
