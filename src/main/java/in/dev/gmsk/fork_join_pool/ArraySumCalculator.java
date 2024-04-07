package in.dev.gmsk.fork_join_pool;

import java.util.concurrent.RecursiveTask;

public class ArraySumCalculator extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 0;
    private int[] array;
    private int start, end;

    ArraySumCalculator(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = start + (end - start) / 2;
            ArraySumCalculator leftTask = new ArraySumCalculator(array, start, mid);
            ArraySumCalculator rightTask = new ArraySumCalculator(array, mid, end);

            leftTask.fork();
            int rightSum = rightTask.compute();
            int leftSum = leftTask.join();

            return leftSum + rightSum;
        }
    }
}
