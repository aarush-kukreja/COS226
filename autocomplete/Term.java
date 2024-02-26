import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String q; // query
    private long w; // weight

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) throw new
                IllegalArgumentException("Query can't be null");
        if (weight < 0) throw new IllegalArgumentException("Make weight > 0");
        q = query;
        w = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ComparatorReverse();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) throw new IllegalArgumentException("Input can't be < 0");
        return new ComparatorPrefix(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return q.compareTo(that.q);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return w + "\t" + q;
    }

    private static class ComparatorReverse implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return Long.compare(b.w, a.w);
        }
    }

    private static class ComparatorPrefix implements Comparator<Term> {
        private int x; // length

        // sets x=r
        public ComparatorPrefix(int r) {
            if (r < 0) throw new IllegalArgumentException("Input can't be < 0");
            x = r;
        }

        public int compare(Term a, Term b) {
            String prefixA = a.q.substring(0, Math.min(x, a.q.length()));
            String prefixB = b.q.substring(0, Math.min(x, b.q.length()));
            return prefixA.compareTo(prefixB);
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        // Test 1: Sorting terms by reverse weight order
        Term[] terms1 = {
                new Term("Bigger", 45),
                new Term("Middle", 14),
                new Term("Middle", 2)
        };
        Arrays.sort(terms1, Term.byReverseWeightOrder());
        StdOut.println("Sorted by reverse weight order:");
        for (Term term : terms1) {
            StdOut.println(term);
        }

        // Test 2: Comparing two terms by query
        Term term1 = new Term("GA", 586251);
        Term term2 = new Term("A", 608037);
        int comparisonResult = term1.compareTo(term2);
        StdOut.println("\nComparison result: " + comparisonResult);

        // Test 3: Comparing two items by prefix order (Custom Comparator)
        Term p = new Term("AAACAGG", 9);
        Term q = new Term("AAGA", 1);
        int comparisonResult2 = Term.byPrefixOrder(2).compare(p, q);
        StdOut.println(
                "\nComparison result by prefix order (first 0 characters): "
                        + comparisonResult2);

        // Test 4: Sorting terms by prefix order
        Term[] terms2 = {
                new Term("GTAC", 2),
                new Term("GAGTC", 8)
        };
        Arrays.sort(terms2, Term.byPrefixOrder(3)); // Sort by a prefix of
        // length 3
        StdOut.println("\nSorted by prefix order (first 3 characters):");
        for (Term term : terms2) {
            StdOut.println(term);
        }
    }


}
