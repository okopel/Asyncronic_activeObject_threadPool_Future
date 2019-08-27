import static java.lang.Thread.sleep;

public class myMain {
    public static void main(String[] args) throws Exception {
        MyThreadPool tp = new MyThreadPool(5);
        tp.execute(new someRunnable());
        tp.execute(new someRunnable());
        Future<String> f = tp.submit(new someCallable());
        Future<String> f2 = tp.submit(
                new someCallable()).
                thenExecute((x) -> x + "--" + x).
                thenExecute(String::toUpperCase);
        System.out.println(f.get());
        System.out.println(f2.get());
        tp.submit(
                new someCallable()).
                thenExecute(String::length).
                thenExecute(x -> x * x).
                thenAccept(System.out::println);
        sleep(5000);
        tp.stop();
        System.out.println("end of main");
    }
}