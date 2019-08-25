import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class someCallble implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("call in someCallble");
        sleep(10 * 1000);
        return "moshe";
    }
}
