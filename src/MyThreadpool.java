import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * In this ThreadPool we take care of Callable and Runnable,
 * While Runnable run by numofThread,
 */
public class MyThreadpool {
    private final BlockingQueue<Runnable> queue;
    private final Thread[] myThreads;
    private volatile boolean stop;

    public MyThreadpool(int numOfThreads) {
        this.queue = new LinkedBlockingDeque<>();
        myThreads = new Thread[numOfThreads];
        stop = false;
        for (int i = 0; i < numOfThreads; i++) {
            myThreads[i] = new Thread(() -> {
                while (!stop) {
                    try {
                        queue.take().run();
                    } catch (InterruptedException ignored) {
                    }
                }
            });
            myThreads[i].start();
        }
    }

    public void addRunnable(Runnable r) throws InterruptedException {
        if (!stop) {
            queue.put(r);
        }
    }

    public <V> Future<V> addCallable(Callable<V> c) throws InterruptedException {
        if (!stop) {
            Future<V> f = new Future<>();
            addRunnable(() -> {
                try {
                    f.set(c.call());
                } catch (Exception ignored) {
                }
            });
            return f;
        }
        return null;
    }

    public void stop() {
        this.stop = true;
        for (Thread myThread : myThreads) {
            if (myThread != null) {
                myThread.interrupt();
            }
        }
    }

    public int getSize() {
        return this.queue.size();
    }


}
