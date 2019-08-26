import java.util.function.Consumer;
import java.util.function.Function;

public class Future<V> {

    private V val;
    private Runnable runnable;

    public Future() {
        this.val = null;
        this.runnable = null;
    }

    public synchronized void set(V v) {
        val = v;
        if (runnable != null) {
            runnable.run();
        }
        notifyAll();
    }

    public V get() throws InterruptedException {
        if (val == null) {
            synchronized (this) {
                if (val == null) {
                    wait();
                }
            }
        }
        return val;
    }

    public <R> Future<R> thenExecute(Function<V, R> func) {
        Future<R> f = new Future<>();
        runnable = () -> f.set(func.apply(val));
        return f;
    }

    public void thenAccept(Consumer<V> consumer) {
        runnable = () -> consumer.accept(val);
    }

}

