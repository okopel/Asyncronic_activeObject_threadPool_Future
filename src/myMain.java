public class myMain {

    public static void main(String[] args) throws InterruptedException {
        MyThreadpool tp = new MyThreadpool(5, 5);
        Future<String> f = tp.submitCallble(new someCallble());
        Future<String> f2 = tp.submitCallble(new someCallble());
        tp.addRunnble(new someRunnble());
        tp.addRunnble(new someRunnble());
        System.out.println(f.get());
        System.out.println(f2.get());
        tp.stop();
        System.out.println("end of main");
    }
}
