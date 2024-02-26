import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

public class Clustering {

    private int[] clusterId; // Array to store cluster IDs

    // Constructor for the Clustering class
    public Clustering(Point2D[] locations, int k) {
        if (locations == null)
            throw new
                    IllegalArgumentException("Locations array cannot be null.");
        int m = locations.length;
        if (k < 1 || k > m) throw new
                IllegalArgumentException("Invalid value of k: " + k);

        // Check for null elements in the locations array
        for (Point2D location : locations) {
            if (location == null)
                throw new IllegalArgumentException("Location points "
                                                           + "cannot be null.");
        }

        clusterId = new int[m];
        EdgeWeightedGraph graph = new EdgeWeightedGraph(m);

        // Create a weighted graph based on distances between locations
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double weight = locations[i].distanceTo(locations[j]);
                Edge edge = new Edge(i, j, weight);
                graph.addEdge(edge);
            }
        }

        KruskalMST mst = new KruskalMST(graph);

        if (k == 1) {
            // If k is 1, assign all points to a single cluster
            for (int i = 0; i < m; i++) {
                clusterId[i] = 0;
            }
        }
        else {
            EdgeWeightedGraph clusterGraph = new EdgeWeightedGraph(m);
            int edgesAdded = 0;
            for (Edge e : mst.edges()) {
                if (edgesAdded++ < m - k) {
                    int v = e.either(), w = e.other(v);
                    clusterGraph.addEdge(new Edge(v, w, e.weight()));
                }
            }

            CC connectedComponents = new CC(clusterGraph);
            // Assign cluster IDs based on connected components
            for (int i = 0; i < m; i++) {
                clusterId[i] = connectedComponents.id(i);
            }
        }
    }

    // Get the cluster ID of a point
    public int clusterOf(int i) {
        if (i < 0 || i >= clusterId.length)
            throw new IllegalArgumentException("Index out of bounds: " + i);
        return clusterId[i];
    }

    // Reduce dimensions of an input array based on clusters
    public double[] reduceDimensions(double[] input) {
        if (input == null || input.length != clusterId.length)
            throw new IllegalArgumentException("Invalid input array.");

        double[] reduced;
        if (clusterId.length == 1) {
            // If there's only one cluster, return the sum of input values
            reduced = new double[1];
            for (double v : input) reduced[0] += v;
        }
        else {
            int maxClusterId = 0;
            for (int id : clusterId) if (id > maxClusterId) maxClusterId = id;
            reduced = new double[maxClusterId + 1];
            // Sum values within each cluster
            for (int i = 0; i < input.length; i++) {
                reduced[clusterId[i]] += input[i];
            }
        }
        return reduced;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Test Data Preparation
        Point2D[] locations = new Point2D[] {
                new Point2D(0.1, 0.2),
                new Point2D(0.3, 0.4),
                new Point2D(0.5, 0.6)
        };

        // Initialize Clustering with valid parameters
        Clustering clustering = new Clustering(locations, 2);

        // Test clusterOf method
        for (int i = 0; i < locations.length; i++) {
            int cluster = clustering.clusterOf(i);
            assert cluster >= 0 && cluster < 2 :
                    "Cluster ID out of expected range for valid index " + i;
        }

        // Test reduceDimensions method
        double[] input = { 1.0, 2.0, 3.0 };
        double[] reduced = clustering.reduceDimensions(input);
        assert reduced.length == 2 : "Incorrect length of reduced"
                + " dimensions array";

        // Test sum of reduced dimensions equals sum of input
        double sumInput = 0, sumReduced = 0;
        for (double v : input) sumInput += v;
        for (double v : reduced) sumReduced += v;
        assert sumInput == sumReduced : "Sum of reduced dimensions"
                + " does not match sum of input";

        // Edge Case: Invalid cluster index
        boolean caughtInvalidIndex = false;
        try {
            clustering.clusterOf(-1);
        }
        catch (IllegalArgumentException e) {
            caughtInvalidIndex = true;
        }
        assert caughtInvalidIndex : "clusterOf did not throw exception "
                + "for invalid index";

        // Edge Case: Invalid input length in reduceDimensions
        boolean caughtInvalidInputLength = false;
        try {
            clustering.reduceDimensions(new double[] { 1.0, 2.0 });
        }
        catch (IllegalArgumentException e) {
            caughtInvalidInputLength = true;
        }
        assert caughtInvalidInputLength :
                "reduceDimensions did not throw exception for "
                        + "invalid input length";

        StdOut.println("All tests passed successfully.");
    }
}
