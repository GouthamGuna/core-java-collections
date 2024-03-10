package in.dev.gmsk.completable_future;

import java.util.concurrent.CompletableFuture;

public class Sample {

    public static void main(String[] args) {
        //  cfExampleOne();
        cfExampleTwo();
        cfExampleTwoPointOne();
    }

    public static int compute(int n) {
        if (n <= 0) {
            throw new RuntimeException( "Invalid input" );
        }
        return n * 2;
    }

    public static CompletableFuture<Integer> create(int n) {
        return CompletableFuture.supplyAsync( () -> compute( n ) );
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
        create( -4 ) // return CompletableFuture<Integer>
                .thenApply( data -> data + 1.0 ) // return CompletableFuture<Double>
                .exceptionally( err -> {
                    System.out.println( "err = " + err );
                    // return 100.0; // return CompletableFuture<Integer>
                    throw new RuntimeException( "This beyond repair." );
                } )
                .thenAccept( System.out::println ) // return CompletableFuture<Void>
                .thenRun( () -> System.out.println( "Log some info..." ) )
                .thenRun( () -> System.out.println( "Some thing do..." ) )
                .exceptionally( err -> {
                    System.out.println( "err = " + err );
                    throw new RuntimeException( "Sorry." );
                } );
    }

    public static void cfExampleTwo() {
        var cfOne = create( 2 ); // 4
        var cfTwo = create( 3 ); // 6


        /*
          cfOne.thenCombine( cfTwo, (dataOne, dataTwo) -> dataOne + dataTwo )
                          .thenAccept( System.out::println );
          */

        cfOne.thenCombine( cfTwo, Integer::sum )
                .thenAccept( System.out::println );
    }

    /**
     * Stream                         CompletableFuture
     * map(f11) => Stream<R>
     * map(f1n) => Stream<List<R>>
     * <p>
     * flatMap(f1n) => Stream<R>            theCompose
     */

    public static void cfExampleTwoPointOne() {

        create( 2 )
                //.thenApply( Sample::create )
                .thenCompose( data -> create( data ) )
                .thenAccept( System.out::println );

    }
}
