import static java.lang.Thread.sleep;

public class someRunnble implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("run has started");
            sleep(1 * 1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("run has finished");
    }
}
