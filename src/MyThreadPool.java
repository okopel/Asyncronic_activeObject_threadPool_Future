import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * In this ThreadPool we take care of Callable and Runnable,
 * While Runnable run by numOfThread,
 */
public class MyThreadPool {
    private int max;
    private ArrayList<ActiveObject> pool;

    public MyThreadPool(int maxAO) throws Exception {
        if (maxAO < 1) {
            throw new Exception("The max must be larger then 1");
        }
        max = maxAO;
        pool = new ArrayList<>();
        //this.queue = new PriorityBlockingQueue<>(maxAO, (Runnable a, Runnable b) -> {            return a.hashCode() - b.hashCode();        });
    }

    public void execute(Runnable r) throws InterruptedException {
        if (pool.size() < max) {
            ActiveObject ao = new ActiveObject();
            ao.execute(r);
            ao.start();
            pool.add(ao);
        } else {
            int min = Integer.MAX_VALUE;
            ActiveObject minAo = null;
            for (ActiveObject ao : pool) {
                if (ao.getSize() < min) {
                    min = ao.getSize();
                    minAo = ao;
                }
            }
            minAo.execute(r);
        }
    }

    public <V> Future<V> submit(Callable<V> c) throws InterruptedException {
        Future<V> f = new Future<>();
        execute(() -> {
            try {
                f.set(c.call());
            } catch (Exception ignored) {
            }
        });
        return f;
    }

    public void stop() throws InterruptedException {
        for (ActiveObject ao : pool) {
            ao.stop();
        }
    }


}
