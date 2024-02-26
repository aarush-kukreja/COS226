import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class SeamCarverTest {

    private static void performExperiment(int width, int height) {
        // Load or create an image with the specified dimensions
        Picture picture = new Picture(width, height);

        // Instantiate SeamCarver with the picture
        SeamCarver seamCarver = new SeamCarver(picture);

        // Start timer
        Stopwatch stopwatch = new Stopwatch();

        // Perform seam carving operations
        seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.findHorizontalSeam();
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());

        // Stop timer and print elapsed time
        double time = stopwatch.elapsedTime();
        StdOut.printf("W = %d, H = %d, Time = %.2f seconds\n", width, height, time);
    }

    public static void main(String[] args) {
        // Define different sizes for the experiments
        int[] widths = { 1000, 2000, 4000, 8000, 16000, 32000 }; // Example values
        int[] heights = { 1000, 2000, 4000, 8000, 16000, 32000 }; // Example values

        // Perform experiments keeping W constant
        int constantW = 2000;
        for (int height : heights) {
            performExperiment(constantW, height);
        }

        // Perform experiments keeping H constant
        int constantH = 2000;
        for (int width : widths) {
            performExperiment(width, constantH);
        }
    }
}
