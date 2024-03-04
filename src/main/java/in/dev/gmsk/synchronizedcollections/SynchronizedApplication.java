package in.dev.gmsk.synchronizedcollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Example : 1
 * <p></p>
 * static Runnable listOperations = () -> {
 * syncCollection.addAll( Arrays.asList( 1, 2, 3, 4, 5, 6 ) );
 * };
 * <p>
 * Example : 2
 * <p>
 * static Runnable listOperations = SynchronizedApplication::run;
 */

public class SynchronizedApplication {

    static Collection<Integer> syncCollection =
            Collections.synchronizedCollection( new ArrayList<>() );

    static Runnable listOperations = () -> syncCollection.addAll( Arrays.asList( 1, 2, 3, 4, 5, 6 ) );

    public static void main(String[] args) {

        try {
            Thread thread = new Thread( listOperations );
            thread.start();
            thread.join();

            System.out.println( "syncCollection.size() : " + syncCollection.size() );
            syncCollection.forEach( (e) -> System.out.println( "e = " + e ) );

        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }
    }
}
