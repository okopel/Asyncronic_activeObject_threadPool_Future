import static java.lang.Thread.sleep;

public class myMain {

    public static void main(String[] args) throws InterruptedException {
        MyThreadpool tp = new MyThreadpool(5);


        tp.addRunnable(new someRunnable());
        tp.addRunnable(new someRunnable());
        Future<String> f = tp.addCallable(new someCallable());
        Future<String> f2 = tp.addCallable(
                new someCallable()).
                thenExecute((x) -> x + "--" + x).
                thenExecute(String::toUpperCase);
        System.out.println(f.get());
        System.out.println(f2.get());
        tp.addCallable(
                new someCallable()).
                thenExecute(String::length).
                thenExecute(x -> x * x).
                thenAccept(System.out::println);
        sleep(5000);
        tp.stop();
        System.out.println("end of main");
    }
}