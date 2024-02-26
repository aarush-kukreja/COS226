import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

// Other necessary imports for reading files, etc.

public class BoostingAlgorithmTest {

    public static void main(String[] args) {
        // Load the training and test datasets
        DataSet training = new DataSet("large_training.txt");
        DataSet test = new DataSet("large_test.txt");

        // Define the range of k and T values to test
        int[] kValues = { 2, 5, 10, 15, 20 }; // Example values
        int[] tValues = { 10, 50, 100, 150, 200 }; // Example values

        // Variables to keep track of the best performance
        double bestAccuracy = 0;
        int bestK = 0;
        int bestT = 0;
        double bestTime = Double.MAX_VALUE;

        // Start testing
        for (int k : kValues) {
            for (int T : tValues) {
                Stopwatch stopwatch = new Stopwatch();

                // Initialize BoostingAlgorithm with current k value
                BoostingAlgorithm model = new BoostingAlgorithm(training.input, training.labels,
                                                                training.locations, k);

                // Perform T iterations
                for (int t = 0; t < T; t++) {
                    model.iterate();
                }

                // Calculate test accuracy
                double testAccuracy = calculateAccuracy(model, test.input, test.labels);

                double time = stopwatch.elapsedTime();

                // Print results
                StdOut.printf("k = %d, T = %d, Test Accuracy = %.2f, Time = %.2fs\n", k, T,
                              testAccuracy, time);

                // Check if this is the best performance
                if (testAccuracy > bestAccuracy && time < 10) {
                    bestAccuracy = testAccuracy;
                    bestK = k;
                    bestT = T;
                    bestTime = time;
                }
            }
        }

        // Print the best results
        StdOut.println("Best Results:");
        StdOut.printf("k = %d, T = %d, Test Accuracy = %.2f, Time = %.2fs\n", bestK, bestT,
                      bestAccuracy, bestTime);
    }

    // Method to calculate accuracy
    private static double calculateAccuracy(BoostingAlgorithm model, double[][] input,
                                            int[] labels) {
        int correct = 0;
        for (int i = 0; i < input.length; i++) {
            if (model.predict(input[i]) == labels[i]) {
                correct++;
            }
        }
        return (double) correct / input.length;
    }
}
