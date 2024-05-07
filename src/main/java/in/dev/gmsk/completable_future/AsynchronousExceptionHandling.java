package in.dev.gmsk.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class AsynchronousExceptionHandling {

    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> divideAsync.apply(10, 2))
                .thenApplyAsync(result -> addAsync.apply(result, 5))
                .thenApplyAsync(result -> divideAsync.apply(result, 0))
                .exceptionally(ex -> {
                    System.out.println(STR."Error occurred: \{ex.getMessage()}");
                    return null; // Handle exception gracefully
                })
                .thenAccept(System.out::println); // Print final result
    }

    private static final BiFunction<Integer, Integer, Integer> divideAsync = (dividend, divisor) -> dividend / divisor;

    private static final BiFunction<Integer, Integer, Integer> addAsync = Integer::sum;
}
