package in.dev.gmsk.executor_service;

import java.util.stream.Stream;

public class Task implements Runnable {

    @Override
    public void run() {
        getDummyString();
    }

    private void getDummyString() {
        Stream<String> names = Stream.of("Gowtham", "Sankar", "Jay", "Kumar", "Manoj", "Mani", "Karan");
        names.forEach(p -> System.out.println("p = " + p + "\tThread Name = " + Thread.currentThread().getName()));
    }
}
