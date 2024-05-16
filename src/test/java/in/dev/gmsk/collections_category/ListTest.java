package in.dev.gmsk.collections_category;

import org.junit.jupiter.api.Test;

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
}