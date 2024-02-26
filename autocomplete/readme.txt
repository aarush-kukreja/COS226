Programming Assignment 3: Autocomplete


/* *****************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that is equal to the search key.
 **************************************************************************** */
1. The method sets high  = a.length - 1 (being the last index), low = 0
(being the first index), and result = -1 (this will be returned at the end and
its value will change from -1 to a seperate number if a comparison is a "hit."

2. In a while loop that continues as long as `low` is less than or equal to
`high`, It performs a binary search in the sorted array `a` to find the first
occurrence of the key. The binary search starts with two pointers: `low`
initially set to 0  (pointing to the first element) and `high` initially s
et to `a.length - 1` (pointing to the final element).

5. In each iteration of the loop, it calculates the middle index `mid` as
`(high + low) >>> 1` (using the bitwise right shift operator to efficiently find
the midpoint).

6. It compares the element at index `mid` with the `key` using the provided
`comparator`. Then, one of three things happen:

7a. If the comparison result is 0 (indicating a match), it updates the `result`
 variable to the current `mid` value (which stores the index of the first
 occurrence of the key) and moves the `high` pointer to `mid - 1` to search
 for additional occurrences in the left half of the array.

7b. If the comparison result is greater than 0 (indicating that the `key`
should be in the left half of the array), it updates the `high` pointer to
`mid - 1` to search in the left half.

7c. If the comparison result is less than 0 (indicating that the `key` should be
in the right half of the array), it updates the `low` pointer to `mid + 1` to
search in the right half.

8. The loop continues until it either finds the first occurrence of the `key`
or determines that the `key` is not in the array.

9. Finally, the method returns the `result`, which contains the index of the
first occurrence of the `key` in the sorted array. If the `key` is not found,
it returns -1 as result was declared as -1 from the beginning.


/* *****************************************************************************
 *  Identify which sorting algorithm (if any) that your program uses in the
 *  Autocomplete constructor and instance methods. Choose from the following
 *  options:
 *
 *    none, selection sort, insertion sort, mergesort, quicksort, heapsort
 *
 *  If you are using an optimized implementation, such as Arrays.sort(),
 *  select the principal algorithm.
 **************************************************************************** */

Autocomplete() : mergesort

allMatches() : mergesort

numberOfMatches() : none

/* *****************************************************************************
 *  How many compares (in the worst case) does each of the operations in the
 *  Autocomplete data type make, as a function of both the number of terms n
 *  and the number of matching terms m? Use Big Theta notation to simplify
 *  your answers.
 *
 *  Recall that with Big Theta notation, you should discard both the
 *  leading coefficients and lower-order terms, e.g., Theta(m^2 + m log n).
 **************************************************************************** */

Autocomplete():     Theta( nlogn )
nlogn + n (for .contains)

allMatches():       Theta( mlogn + logn )

firstIndex + lastindex = logn + logn = 2logn --> logn
mergesort -- mlogn

numberOfMatches():  Theta( logn )
firstIndex - logn
lastIndex - logn
firstIndex + lastIndex = 2logn --> logn

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
 None


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
I didn't encounter any problems.



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
I enjoyed it.
