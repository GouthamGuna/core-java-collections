package in.dev.gmsk.vector_api;

import java.util.Arrays;
import java.util.Vector;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5};

        out.println(
                Arrays.toString(traditionalLoop.apply(arr))
        );

        vectorAPIApproach.accept(arr);
    }

    private static final Function<int[], int[]> traditionalLoop = arrOne -> {

        int[] returnArr = new int[arrOne.length];

        for (int i = 0; i < arrOne.length; i++) {
            returnArr[i] = arrOne[i] + 5;
        }

        // System.arraycopy(arrOne, 5, returnArr, 0, arrOne.length);

        return returnArr;
    };

    private static final Consumer<int[]> vectorAPIApproach = arr -> {

        Vector<Integer> result = new Vector<>();

        Vector<Integer> numVector = IntStream.of(arr).boxed().collect(Collectors.toCollection(Vector::new));

        // SIMD-like addition using vector operations
        for (int i = 0; i < numVector.size(); i++) {
            result.add(numVector.get(i) + 5);
        }

        // Convert the result vector back to an array (optional)
        int[] resultArray = result.stream().mapToInt(Integer::intValue).toArray();

        for (int r : resultArray){
            out.print("\nresult arr : "+r);
        }
    };
}
