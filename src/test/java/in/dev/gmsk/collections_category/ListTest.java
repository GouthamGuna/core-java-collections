package in.dev.gmsk.collections_category;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static in.dev.gmsk.collections_category._List.*;
import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @Test
    void testConstructorAcceptingCollection_EmptyCollection() {
        Collection<Integer> emptyCollection = new java.util.HashSet<>();

        int result = constructorAcceptingCollection(emptyCollection);

        assertEquals(0, result);
    }

    @Test
    void testConstructorAcceptingCollection_SingleElement() {
        Collection<Integer> singleElementCollection = new java.util.HashSet<>(List.of(1));

        int result = constructorAcceptingCollection(singleElementCollection);

        assertEquals(1, result);
    }

    @Test
    void testConstructorAcceptingCollection_MultipleElements() {
        Collection<Integer> multipleElementsCollection = new java.util.HashSet<>(List.of(1, 2, 3, 4, 5));

        int result = constructorAcceptingCollection(multipleElementsCollection);

        assertEquals(5, result);
    }

    @Test
    void testConstructorAcceptingCollection_DuplicateElements() {
        Collection<Integer> duplicateElementsCollection = new java.util.HashSet<>(List.of(1, 2, 2, 3, 4, 4, 5));

        int result = constructorAcceptingCollection(duplicateElementsCollection);

        assertEquals(5, result);
    }

    @Test
    void testConstructorAcceptingCollection_NullCollection() {
        Collection<Integer> nullCollection = null;

        assertThrows(NullPointerException.class, () -> constructorAcceptingCollection(nullCollection));
    }

    @Test
    void testFindPeakElementInArray_PeakAtEnd() {
        // Arrange
        int[] array = {1, 2, 3, 4, 5, 4, 3, 2, 1};

        // Act
        int peakElement = findPeakElementInArray(array);

        // Assert
        assertEquals(5, peakElement);
    }

    @Test
    void testFindPeakElementInArray_EmptyArray() {
        // Arrange
        int[] array = {};

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> findPeakElementInArray(array));
    }

    @Test
    void testFindPeakElementInArray_SingleElementArray() {
        // Arrange
        int[] array = {1};

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> findPeakElementInArray(array));
    }

    @Test
    void testMultiplePeakElementFinder_EmptyArray() {
        int[] arr = {};
        List<Integer> peaks = _List.multiplePeakElementFinder(arr);
        assertTrue(peaks.isEmpty());
    }

    @Test
    void testMultiplePeakElementFinder_SinglePeak() {
        int[] arr = {5};
        List<Integer> peaks = _List.multiplePeakElementFinder(arr);
        assertEquals(1, peaks.size());
        assertEquals(5, peaks.get(0));
    }

    @Test
    void testMultiplePeakElementFinder_TwoPeaks() {
        int[] arr = {2, 4, 7, 9, 11};
        List<Integer> peaks = _List.multiplePeakElementFinder(arr);
        assertEquals(2, peaks.size());
        assertEquals(7, peaks.get(0));
        assertEquals(11, peaks.get(1));
    }

    @Test
    void testMultiplePeakElementFinder_ThreePeaks() {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        List<Integer> peaks = _List.multiplePeakElementFinder(arr);
        assertEquals(3, peaks.size());
        assertEquals(5, peaks.get(0));
        assertEquals(11, peaks.get(1));
        assertEquals(17, peaks.get(2));
    }

    @Test
    void testMultiplePeakElementFinder_NoPeaks() {
        int[] arr = {1, 2, 3, 4, 5};
        List<Integer> peaks = _List.multiplePeakElementFinder(arr);
        assertTrue(peaks.isEmpty());
    }
}