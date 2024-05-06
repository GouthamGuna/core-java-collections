package in.dev.gmsk.completable_future;

import in.dev.gmsk.service.AccountService;
import in.dev.gmsk.service.impl.AccountsImpl;
import in.dev.gmsk.util.JDBCConnection;
import in.dev.gmsk.util.model.Accounts;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CompletableFuture to handle asynchronous tasks.
 * <p/>
 * Remember to handle exceptions appropriately (e.g., using .exceptionally() or .handle() on the CompletableFuture).
 */

public class Sample {

    private static final JDBCConnection jdbcConnection = new JDBCConnection(
            "jdbc:mariadb://localhost:3306/pearl", "root", "asus@root"
    );
    private static final AccountService service = new AccountsImpl();

    public static void main(String[] args) {
        //cfExampleOne();
        cfExampleTwo();
        cfExampleTwoPointOne();

        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(Sample::a);
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(Sample::b);

        // Run method c() once either cf1 or cf2 completes
        CompletableFuture<Void> combined = CompletableFuture.allOf(cf1, cf2);
        combined.thenRun(Sample::c);

        // Wait for completion (optional)
        combined.join();

        // Run method getVouchersList()
        CompletableFuture<Map<String, Double>> futureResult = getVouchersListByFuture();

        // Handle the result when it's available
        futureResult.thenAccept(map -> {
            System.out.println("Vouchers List:");
            map.forEach((heading, amount) -> System.out.println(STR."\{heading}: \{amount}"));
        });

        // Wait for completion (optional)
        futureResult.join();

        // this is processing doing by completable future
        Map<String, Double> vouchersMap = getVouchersMap();
        System.out.println("Vouchers List:");
        vouchersMap.forEach((heading, amount) -> System.out.println(STR."\{heading}: \{amount}"));
    }

    public static int compute(int n) {
        if (n <= 0) {
            throw new RuntimeException("Invalid input");
        }
        return n * 2;
    }

    public static CompletableFuture<Integer> create(int n) {
        return CompletableFuture.supplyAsync(() -> compute(n));
    }

    /**
     * Why thenApply and thenAccept what they do?
     * <p></p>
     * |            Stream        |    CompletableFuture       |
     * |:------------------------:|:--------------------------:|
     * | map(Function(<T, R>))    |                            |
     * | in abstraction name is   |  thenApply();              |
     * | apply so that... CF      |                            |
     * |                          |                            |
     * | forEach(Consumer <T>)    |                            |
     * | abstraction name is      |  thenAccept();             |
     * | accept                   |                            |
     * <p></p>
     * In stream forEach the terminal operation but thenAccept
     * is not terminal ops...
     */

    public static void cfExampleOne() {
        create(-4) // return CompletableFuture<Integer>
                .thenApply(data -> data + 1.0) // return CompletableFuture<Double>
                .exceptionally(err -> {
                    System.out.println("err = " + err);
                    // return 100.0; // return CompletableFuture<Integer>
                    throw new RuntimeException("This beyond repair.");
                })
                .thenAccept(System.out::println) // return CompletableFuture<Void>
                .thenRun(() -> System.out.println("Log some info..."))
                .thenRun(() -> System.out.println("Some thing do..."))
                .exceptionally(err -> {
                    System.out.println("err = " + err);
                    throw new RuntimeException("Sorry.");
                });
    }

    public static void cfExampleTwo() {
        var cfOne = create(2); // 4
        var cfTwo = create(3); // 6

        //cfOne.thenCombine( cfTwo, (dataOne, dataTwo) -> dataOne + dataTwo ).thenAccept( System.out::println );

        cfOne.thenCombine(cfTwo, Integer::sum).thenAccept(System.out::println);
    }

    /**
     * Stream                         CompletableFuture
     * map(f11) => Stream<R>
     * map(f1n) => Stream<List<R>>
     * <p>
     * flatMap(f1n) => Stream<R>            theCompose
     */

    public static void cfExampleTwoPointOne() {

        create(2)
                //.thenApply( Sample::create )
                .thenCompose(Sample::create)
                .thenAccept(System.out::println);

    }

    static void a() {
        System.out.println("Method a() executed.");
    }

    static void b() {
        System.out.println("Method b() executed.");
    }

    static void c() {
        System.out.println("Method c() executed.");
    }

    public static CompletableFuture<Map<String, Double>> getVouchersListByFuture() {

        System.out.println(STR."Thread currentThread() : \{Thread.currentThread()}");
        System.out.println(STR."Thread getName() : \{Thread.currentThread().getName()}");

        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 31);

        Stream<Accounts> byAll = service.findByAll(jdbcConnection);

        Map<String, Double> map = byAll.filter(o -> Objects.equals(o.getLocationId(), "5"))
                .filter(o -> o.getVoucherType().equals("P"))
                .filter(getAccountsPredicate(startDate, endDate))
                .collect(Collectors.groupingBy(Accounts::getAccountsHeadingName,
                        Collectors.summingDouble(Accounts::getAmount)));

        return CompletableFuture.supplyAsync(() -> map);
    }

    @SneakyThrows
    public static Map<String, Double> getVouchersMap() {

        CompletableFuture<Map<String, Double>> future = CompletableFuture.supplyAsync(() -> {

            System.out.println(STR."Thread currentThread() : \{Thread.currentThread()}");
            System.out.println(STR."Thread getName() : \{Thread.currentThread().getName()}");

            LocalDate startDate = LocalDate.of(2023, 4, 1);
            LocalDate endDate = LocalDate.of(2024, 5, 31);

            Stream<Accounts> byAll = service.findByAll(jdbcConnection);

            return byAll.filter(o -> Objects.equals(o.getLocationId(), "5"))
                    .filter(o -> o.getVoucherType().equals("P"))
                    .filter(getAccountsPredicate(startDate, endDate))
                    .collect(Collectors.groupingBy(Accounts::getAccountsHeadingName,
                            Collectors.summingDouble(Accounts::getAmount)));

        }, Executors.newCachedThreadPool()); // Threads that have not been used for sixty seconds are terminated and removed from the cache.

        return future.get();
    }

    private static Predicate<Accounts> getAccountsPredicate(LocalDate startDate, LocalDate endDate) {
        return o -> (o.getVoucherDate().isAfter(startDate) || o.getVoucherDate().isEqual(startDate))
                && (o.getVoucherDate().isBefore(endDate) || o.getVoucherDate().isEqual(endDate));
    }
}