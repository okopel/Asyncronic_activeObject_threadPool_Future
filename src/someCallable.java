import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class someCallable implements Callable<String> {
    private String s;

    public someCallable(String s) {
        this.s = s;
    }

    @Override
    public String call() throws Exception {
        sleep(1 * 1000);
        return s;
    }
}
