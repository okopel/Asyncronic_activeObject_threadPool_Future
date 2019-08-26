import static java.lang.Thread.sleep;

public class someRunnable implements Runnable {

    @Override
    public void run() {
        try {
            sleep(1 * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}
