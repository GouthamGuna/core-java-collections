package in.dev.gmsk.synchronizedcollections;

import java.util.function.Consumer;

public class SynchronizedFieldVisibility implements Runnable{

    int a = 0, b = 0, c = 0;
    volatile int x = 0;

    public void writerThread() {
        a = 1;
        b = 1;
        c = 1;

        synchronized (this) {
            x = 1;
        }
    }

    public void readerThread(Consumer<String> consumer, String msg) {

        synchronized (this) {
            int r = x;
            consumer.accept(msg);
            System.out.println("From : SFV : = " + r);
        }

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
    }

    @Override
    public void run() {
        readerThread(System.out::println, "run method invoked...");
    }
}
