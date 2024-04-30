# Example for cpu intensive task

 * Here are a few examples of CPU-intensive tasks in Java programming.

 `Sorting algorithms : ` Implementing sorting algorithms like QuickSort, MergeSort, or HeapSort can be CPU-intensive, especially for large datasets. For example, you can use the Arrays.sort() method in Java to sort an array of integers.
 
 `Matrix multiplication : ` Performing matrix multiplication can be a CPU-intensive task, especially for large matrices. You can use the java.lang.Math class to perform matrix multiplication.
 
 `Cryptography : ` Implementing cryptographic algorithms like RSA or AES can be CPU-intensive, especially for large data sets. You can use the java.security package to perform cryptographic operations.
 
 `Computation-intensive algorithms : ` Algorithms like prime number generation, factorial calculation, or Fibonacci sequence calculation can be CPU-intensive. For example, you can use the BigInteger class in Java to calculate large prime numbers.
 
 `Data compression : ` Compressing large datasets can be CPU-intensive, especially if you’re using algorithms like Huffman coding or LZW compression. You can use the java.util.zip package to compress and decompress files.

 Here’s an example of a simple CPU-intensive task in Java:

    public class CPUIntensiveTask {
        public static void main(String[] args) {
            int[] array = new int[1000000];
            for (int i = 0; i < array.length; i++) {
                array[i] = i * i;
            }
            // Perform some CPU-intensive operation on the array
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] * array[i];
            }
        }
    }

 This example creates an array of 1 million integers, squares each element, and then squares each element again. This task is CPU-intensive because it performs a large number of arithmetic operations.