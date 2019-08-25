import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

public class MyThreadpool {
    BlockingQueue<Runnable> q;

    public MyThreadpool() {
        this.q = new LinkedBlockingDeque<>();
    }

    public <V> Future<V> submit(Callable<V> c) {
        Future<V> f = new Future<>();
        new Thread(() -> {
            try {
                f.set(c.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return f;
    }


}
