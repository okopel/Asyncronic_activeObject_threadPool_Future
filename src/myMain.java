public class myMain {

    public static void main(String[] args) throws InterruptedException {
        MyThreadpool tp = new MyThreadpool(5, 5);
        tp.addRunnable(new someRunnable());
        tp.addRunnable(new someRunnable());
        Future<String> f = tp.submitCallable(new someCallable());
        Future<String> f2 = tp.submitCallable(new someCallable());
        Future<String> f3 = tp.submitCallable(
                new someCallable()).
                thenExecute((x) -> x + "--" + x).
                thenExecute(String::toUpperCase);
        System.out.println(f.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        tp.stop();
        System.out.println("end of main");

    }
}
