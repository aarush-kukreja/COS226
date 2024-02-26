import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class ShortestCommonAncestor {
    // Digraph instance representing the DAG
    private final Digraph G;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Argument to constructor cannot "
                                                       + "be null");
        }

        this.G = new Digraph(G);
        // Store the check result
        boolean isRootedDAG = checkIfRootedDAG(G); // Perform the check once
        if (!isRootedDAG) {
            throw new IllegalArgumentException("Digraph is not a rooted DAG");
        }

    }

    // private helper to check
    private boolean checkIfRootedDAG(Digraph c) {
        DirectedCycle cycleFinder = new DirectedCycle(c);
        if (cycleFinder.hasCycle()) {
            return false;
        }

        int rootCount = 0;
        for (int v = 0; v < c.V(); v++) {
            if (c.outdegree(v) == 0) {
                rootCount++;
            }
        }
        return rootCount == 1;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        AncestralPath ap = shortestAncestralPath(v, w);
        return ap.length;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        AncestralPath ap = shortestAncestralPath(v, w);
        return ap.ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        validateVertices(subsetA);
        validateVertices(subsetB);
        AncestralPath ap = shortestAncestralPath(subsetA, subsetB);
        return ap.length;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        validateVertices(subsetA);
        validateVertices(subsetB);
        AncestralPath ap = shortestAncestralPath(subsetA, subsetB);
        return ap.ancestor;
    }

    // unit testing
    public static void main(String[] args) {
        // Example graph based on Princeton's algs4 Digraph class
        Digraph G = new Digraph(13);
        G.addEdge(7, 3);
        G.addEdge(8, 3);
        G.addEdge(3, 1);
        G.addEdge(4, 1);
        G.addEdge(5, 1);
        G.addEdge(9, 5);
        G.addEdge(10, 5);
        G.addEdge(11, 10);
        G.addEdge(12, 10);
        G.addEdge(1, 0);
        G.addEdge(2, 0);
        G.addEdge(6, 3); // Adding this edge removes vertex 6 as a root

        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);

        // Test the length method
        StdOut.println("Length between 3 and 11: " + sca.length(3, 11));
        // Expected: 4

        // Test the ancestor method
        StdOut.println("Ancestor between 3 and 11: " + sca.ancestor(3, 11));
        // Expected: 1

        // Test the lengthSubset method
        Set<Integer> subsetA = new HashSet<>();
        Set<Integer> subsetB = new HashSet<>();
        subsetA.add(7);
        subsetA.add(8);
        subsetB.add(11);
        subsetB.add(12);
        StdOut.println("LengthSubset between {7, 8} and {11, 12}: " +
                               sca.lengthSubset(subsetA, subsetB)); // Expected: 5

        // Test the ancestorSubset method
        StdOut.println(
                "AncestorSubset between {7, 8} and {11, 12}: " +
                        sca.ancestorSubset(subsetA, subsetB)); // Expected: 5

        // Add more tests for corner cases and other scenarios...
    }

    // Validates that the vertex is within the Digraph's bounds
    private void validateVertex(int v) {
        int s = G.V();
        if (v < 0 || v >= s)
            throw new IllegalArgumentException("vertex "
                                                       + v +
                                                       " is not between 0 and "
                                                       + (s - 1));
    }

    // Validates that the iterable contains valid vertices
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        int count = 0;
        for (Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("Null vertex in iterable");
            }
            validateVertex(v);
            count++;
        }
        if (count == 0) {
            throw new IllegalArgumentException("Iterable must contain at "
                                                       + "least one vertex");
        }
    }

    // Finds the shortest ancestral path between two vertices
    private AncestralPath shortestAncestralPath(int v, int w) {
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(G, w);
        return findAncestralPath(bfdpV, bfdpW);
    }

    // Finds the shortest ancestral path for two sets of vertices
    private AncestralPath shortestAncestralPath(Iterable<Integer> subsetA,
                                                Iterable<Integer> subsetB) {
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(G, subsetA);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(G, subsetB);
        return findAncestralPath(bfdpV, bfdpW);
    }

    // Calculates the ancestral path using breadth-first search
    private AncestralPath findAncestralPath(BreadthFirstDirectedPaths bfdpV,
                                            BreadthFirstDirectedPaths bfdpW) {
        int shortestLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int v = 0; v < G.V(); v++) {
            if (bfdpV.hasPathTo(v) && bfdpW.hasPathTo(v)) {
                int length = bfdpV.distTo(v) + bfdpW.distTo(v);
                if (length < shortestLength) {
                    shortestLength = length;
                    ancestor = v;
                }
            }
        }
        return new AncestralPath(ancestor, shortestLength);
    }

    // helper class to encapsulate ancestor and path length
    private static class AncestralPath {
        // The common ancestor in the path
        private final int ancestor;
        // The length of the path
        private final int length;

        // Constructs an AncestralPath with specified ancestor and length
        public AncestralPath(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }
}
