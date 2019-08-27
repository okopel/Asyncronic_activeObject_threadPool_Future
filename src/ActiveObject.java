import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

public class ActiveObject {
    private BlockingQueue<Runnable> queue;
    private Thread thread;
    private volatile boolean stop;

    public ActiveObject() {
        stop = false;
        queue = new LinkedBlockingDeque<>();
        thread = new Thread(() -> {
            while (!stop) {
                try {
                    queue.take().run();
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public void start() {
        this.thread.start();
    }

    public void execute(Runnable r) throws InterruptedException {
        if (!stop)
            queue.put(r);
    }

    public <V> Future<V> addCallable(Callable<V> c) throws InterruptedException {
        if (!stop) {
            Future<V> f = new Future<>();
            execute(() -> {
                try {
                    f.set(c.call());
                } catch (Exception ignored) {
                }
            });
            return f;
        }
        return null;
    }

    public void stop() throws InterruptedException {
        queue.put(() -> {
            this.stop = true;
        });
    }

    public int getSize() {
        return queue.size();
    }
}
