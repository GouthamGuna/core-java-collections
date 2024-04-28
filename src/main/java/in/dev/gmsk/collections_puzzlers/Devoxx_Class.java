package in.dev.gmsk.collections_puzzlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.System.*;

public class Devoxx_Class {

    public static void main(String[] args) {
        _1_arrays_As_List();
        _2_Sublist();
       // _3a_HandlingNulls();
       // _3b_HandlingNulls();
    }

    private static void _1_arrays_As_List() {

        String[] stringOfArray = {"One", "Two", "Three"};
        var stringOfList = Arrays.asList(stringOfArray);

        int[] intOfArray = {1, 2, 3};
        var intOfList = Arrays.asList(intOfArray); // It's boxing the int to Integer

        out.print(stringOfList.contains("One") + " ,");
        out.print(intOfList.contains(1)); // false
    }

    private static void _2_Sublist(){

        var ints = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        var subList = ints.subList(0,0);
        out.print("\nsubList = " + subList);

        subList.addAll(List.of(10, 11, 12));
        out.print("\tints = " + ints);
    }

    private static void _3a_HandlingNulls(){

        String[] strings = {"a", "b", "c", null};

        var list = Stream.of(strings).toList();
        out.println("\nlist size = " + list.size());

        var listOne = List.of(strings);
        out.print("\tlistOne size = " + listOne); // null pointer error came...
    }

    private static void _3b_HandlingNulls(){

        String[] strings = {"a", "b", "c", null};

        var list = Arrays.asList(strings);
        list.removeIf(Objects::isNull);
        out.println("\nlist size = " + list.size()); // java.lang.UnsupportedOperationException
    }
}
