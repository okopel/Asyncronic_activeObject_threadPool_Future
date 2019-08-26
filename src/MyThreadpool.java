import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * In this ThreadPool we take care of Callable and Runnable,
 * While Runnable run by numofThread,
 */
public class MyThreadpool {
    private final BlockingQueue<Runnable> runnableBlockingQueue;
    private final BlockingQueue<Callable> callableBlockingQueue;
    private final Thread[] myThreads;
    private volatile boolean stop;

    public MyThreadpool(int numOfThreadsRunning, int numOfThreadsCalling) {
        this.runnableBlockingQueue = new LinkedBlockingDeque<>();
        callableBlockingQueue = new LinkedBlockingDeque<>();
        myThreads = new Thread[numOfThreadsRunning + numOfThreadsCalling];
        stop = false;
        for (int i = 0; i < numOfThreadsRunning; i++) {
            myThreads[i] = new Thread(() -> {
                while (!stop) {
                    try {
                        System.out.println("thread  take a task");
                        runnableBlockingQueue.take().run();
                    } catch (InterruptedException ignored) {
                    }
                }
                System.out.println("Thread has gone");
            });
            myThreads[i].start();
        }
        for (int i = numOfThreadsRunning; i < numOfThreadsRunning + numOfThreadsCalling; i++) {
            myThreads[i] = new Thread(() -> {
                while (!stop) {
                    try {
                        callableBlockingQueue.take().call();
                    } catch (Exception ignored) {
                    }
                }
                System.out.println("Thread has gone");
            }
            );
            myThreads[i].start();
        }
    }

    public void addRunnable(Runnable r) throws InterruptedException {
        runnableBlockingQueue.put(r);
    }

    public <V> Future<V> addCallable(Callable<V> c) throws InterruptedException {
        Future<V> f = new Future<>();
        callableBlockingQueue.put(() -> {
            f.set(c.call());
            return f;
        });
        return f;
    }

    public void stop() {
        this.stop = true;
        for (Thread myThread : myThreads) {
            if (myThread != null) {
                myThread.interrupt();
            }
        }
    }
}
