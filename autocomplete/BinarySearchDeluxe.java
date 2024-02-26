import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key>
            comparator) {
        if (a == null || key == null || comparator == null) throw new
                IllegalArgumentException();
        int high = a.length - 1;
        int low = 0;
        int result = -1;

        while (low <= high) {
            int mid = high + low >>> 1;

            int comparison = comparator.compare(a[mid], key);

            if (comparison == 0) {
                result = mid; // Update result when key is found
                high = mid - 1; // Continue searching in the left half
            }
            else if (comparison > 0) {
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return result;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key>
            comparator) {
        if (a == null || key == null || comparator == null) throw new
                IllegalArgumentException();
        int high = a.length - 1;
        int low = 0;
        int result = -1;

        while (low <= high) {
            int mid = high + low >>> 1;
            int comparison = comparator.compare(a[mid], key);
            if (comparison == 0) {
                result = mid; // Update result when key is found
                low = mid + 1; // Continue searching in the right half
            }
            else if (comparison > 0) {
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Test 1: Using String array and natural order comparator for
        // firstIndexOf
        String[] names = {
                "Max", "David", "David", "David", "David", "David", "David",
                "Eve", "Frank"
        };
        Comparator<String> stringComparator = Comparator.naturalOrder();

        int firstIndex = BinarySearchDeluxe.firstIndexOf(names, "David",
                                                         stringComparator);
        if (firstIndex != -1) {
            StdOut.println("First occurrence of 'David' found at index: "
                                   + firstIndex);
        }
        else {
            StdOut.println("'David' not found in the array.");
        }

        // Test 2: Using String array and natural order comparator for
        // lastIndexOf
        int lastIndex = BinarySearchDeluxe.lastIndexOf(names, "David",
                                                       stringComparator);
        if (lastIndex != -1) {
            StdOut.println("Last occurrence of 'David' found at index: "
                                   + lastIndex);
        }
        else {
            StdOut.println("'David' not found in the array.");
        }

        // Test 3: Using String array and natural order comparator for a
        // key that's not in the array
        int notFoundIndex = BinarySearchDeluxe.firstIndexOf(names, "Fiona",
                                                            stringComparator);
        if (notFoundIndex != -1) {
            StdOut.println("First occurrence of 'Fiona' found at index: " +
                                   notFoundIndex);
        }
        else {
            StdOut.println("'Fiona' not found in the array.");
        }

        // Test 4: Using Integer array and natural order comparator for
        // firstIndexOf
        Comparator<Integer> integerComparator = Comparator.naturalOrder();
        Integer[] numbers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        int firstIntIndex = BinarySearchDeluxe.firstIndexOf(numbers, 0,
                                                            integerComparator);
        if (firstIntIndex != -1) {
            StdOut.println("First occurrence of '0' found at index: "
                                   + firstIntIndex);
        }
        else {
            StdOut.println("'0' not found in the array.");
        }
    }
}
