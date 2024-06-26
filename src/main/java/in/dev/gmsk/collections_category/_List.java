package in.dev.gmsk.collections_category;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * Random access takes O(1) time
 * Adding element takes amortized constant time O(1)
 * Inserting/Deleting takes O(n) time
 * Searching takes O(n) time for unsorted array and O(log n) for a sorted one
 */

public class _List {

    public static int constructorAcceptingCollection(Collection<Integer> emptyCollection) {

        Collection<Integer> collectionNum = IntStream.range(0, 10).boxed().collect(toSet());

        List<Integer> list = new ArrayList<>(emptyCollection); // collectionNum

        return list.size();
    }

    public static int findPeakElementInArray(int[] array) {

        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Input array must be non-null and have at least two elements.");
        }

        int left = 0, right = array.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] < array[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return array[left];
    }

    public static List<Integer> multiplePeakElementFinder(int[] arr) {
        List<Integer> peaks = new ArrayList<>();
        // Handle empty array case
        if (arr.length == 0) {
            return peaks;
        }
        // Check the first element
        if (arr.length == 1 || arr[0] >= arr[1]) {
            peaks.add(arr[0]);
        }
        // Check middle elements
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i - 1] && arr[i] >= arr[i + 1]) {
                peaks.add(arr[i]);
            }
        }
        // Check the last element
        if (arr[arr.length - 1] >= arr[arr.length - 2]) {
            peaks.add(arr[arr.length - 1]);
        }
        return peaks;
    }
}
