package in.dev.gmsk.memory_model;

import in.dev.gmsk.synchronizedcollections.SynchronizedFieldVisibility;

public class MainMethod {

    public static void main(String[] args) {

        OutOfOrderExecution.exampleOne();
        OutOfOrderExecution.exampleTwo();

        Thread thread = new Thread(new FieldVisibility());
        thread.start();

        FieldVisibility fieldVisibility = new FieldVisibility();
        fieldVisibility.writeThread();
        fieldVisibility.readerThread("obj ref invoked...");

        Thread threadOne = new Thread(new SynchronizedFieldVisibility());
        threadOne.start();

        SynchronizedFieldVisibility svf = new SynchronizedFieldVisibility();
        svf.writerThread();
        svf.readerThread(System.out::println, "obj ref invoked...");
    }
}
