package in.dev.gmsk.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUIntensiveTask implements Runnable {

    @Override
    public void run() {
        squareTheElement();
    }

    private void squareTheElement() {

        int[] array = new int[1000000]; //  1 million integers
        for (int i = 0; i < array.length; i++) {
            array[i] = i * i;
        }
        // Perform some CPU-intensive operation on the array
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] * array[i];
            System.out.println("i = " + array[i] + "\tThread Name = " + Thread.currentThread().getName());
        }
    }
}
