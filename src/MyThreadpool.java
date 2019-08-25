import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * In this ThreadPool we take care of Callable and Runnble,
 * While Runnable run by numofThread,
 */
public class MyThreadpool {
    private BlockingQueue<Runnable> runnableBlockingQueue;
    private BlockingQueue<Callable> callbleBlockingQueue;
    private volatile boolean stop;
    private int numOfRunnable;
    private int numOfCallable;

    public MyThreadpool(int numOfThreadsRunning, int numOfThreadsCalling) {
        this.numOfRunnable = numOfThreadsRunning;
        this.numOfCallable = numOfThreadsCalling;
        this.runnableBlockingQueue = new LinkedBlockingDeque<>();
        callbleBlockingQueue = new LinkedBlockingDeque<>();

        stop = false;
        for (int i = 0; i < numOfThreadsRunning; i++) {
            new Thread(() -> {
                while (!stop) {
                    try {
                        System.out.println("thread  take a task");
                        runnableBlockingQueue.take().run();
                    } catch (InterruptedException ignored) {
                    }
                }
                System.out.println("Thread has gone");
            }).start();

        }

        for (int i = numOfThreadsRunning; i < numOfThreadsRunning + numOfThreadsCalling; i++) {
            new Thread(() -> {
                while (!stop) {
                    try {
                        callbleBlockingQueue.take().call();
                    } catch (Exception ignored) {
                    }
                }
                System.out.println("Thread has gone");
            }
            ).start();

        }
    }

    public void addRunnble(Runnable r) throws InterruptedException {
        runnableBlockingQueue.put(r);
    }

    public <V> Future<V> submitCallble(Callable<V> c) throws InterruptedException {
        Future<V> f = new Future<>();
        callbleBlockingQueue.put(() -> {
            f.set(c.call());
            return f;
        });
        return f;
    }

    public <V> Future<V> submitImmidateCallable(Callable<V> c) {
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

    public void stop() throws InterruptedException {
        this.stop = true;
        //Enter dummy missions to wake the threads
        for (int i = 0; i < numOfRunnable; i++) {
            this.runnableBlockingQueue.put(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
        for (int i = 0; i < numOfCallable; i++) {
            this.callbleBlockingQueue.put(new Callable() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            });
        }
    }
}
