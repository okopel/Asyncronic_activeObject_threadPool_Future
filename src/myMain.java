public class myMain {

    public static void main(String[] args) throws InterruptedException {
        MyThreadpool tp = new MyThreadpool(5, 5);
        tp.addRunnble(new someRunnble());
        tp.addRunnble(new someRunnble());
        Future<String> f = tp.submitCallble(new someCallble());
        Future<String> f2 = tp.submitCallble(new someCallble());
        Future<String> f3 = tp.submitCallble(
                new someCallble()).
                thenExecute((x) -> x + "--" + x).
                thenExecute(String::toUpperCase);
        System.out.println(f.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        tp.stop();
        System.out.println("end of main");
    }
}
