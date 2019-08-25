
public class myMain {

    public static void main(String[] args) throws InterruptedException {
        MyThreadpool tp = new MyThreadpool();
        Future<String> f = tp.submit(new someCallble());
        System.out.println(f.get());
    }
}
