import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {

    private Term[] terms; // array of terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null || Arrays.asList(terms).contains(null)) {
            throw new IllegalArgumentException("Input array or its elements"
                                                       + " cannot be null.");
        }
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException();
        Term x = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe.firstIndexOf(
                terms, x, Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe.lastIndexOf(
                terms, x, Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1 || lastIndex == -1) {

            // Prefix not found, return an empty array or handle it as needed.
            return new Term[0];
        }

        Term[] terms2 = new Term[1 + lastIndex - firstIndex];

        for (int i = 0; i < terms2.length; i++) {
            terms2[i] = terms[firstIndex++];
        }
        Arrays.sort(terms2, Term.byReverseWeightOrder());
        return terms2;

    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException();

        // Find the first and last index of the prefix using BinarySearchDeluxe
        int firstIndex = BinarySearchDeluxe.firstIndexOf(
                terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe.lastIndexOf(
                terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length()));

        // If the prefix is not found, return 0 matches
        if (firstIndex == -1) {
            return 0;
        }

        // Calculate and return the number of matches
        return lastIndex - firstIndex + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] terms = {
                new Term("No", 3), new Term("Noo", 2), new Term("Nooo", 4),
                new Term("yes", 3)
        };

        Autocomplete ac = new Autocomplete(terms);
        Term[] arr = ac.allMatches("No");

        StdOut.println("Number of Terms that match No: " +
                               ac.numberOfMatches("No"));
        StdOut.println("All Macthes, ordered by Weight: ");

        for (Term x : arr) {
            StdOut.println(x);
        }

    }
}
