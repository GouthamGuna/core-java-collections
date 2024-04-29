package in.dev.gmsk.memory_model;

/**
 * Out of order execution performance driven change done by compiler, JVM, CPU
 */
public class OutOfOrderExecution {

    protected static void exampleOne() {

        int a = 3;
        int b = 2;
        a = a + 1;

        System.out.println("From exampleOne : a = " + a + " b = " + b);
    }

    protected static void exampleTwo() {

        int a = 3;
        a = a + 1;
        int b = 2;

        System.out.println("From exampleTwo : a = " + a + " b = " + b);
    }
}
