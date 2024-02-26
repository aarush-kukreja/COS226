import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    // Instance variable to store the current picture
    private Picture currentPicture;

    // Constructor for SeamCarver class
    public SeamCarver(Picture inputPicture) {
        if (inputPicture == null) {
            throw new IllegalArgumentException("Invalid Picture");
        }
        currentPicture = new Picture(inputPicture);
    }

    // Returns a copy of the current picture
    public Picture picture() {
        return new Picture(currentPicture);
    }

    // Returns the width of the current picture
    public int width() {
        return currentPicture.width();
    }

    // Returns the height of the current picture
    public int height() {
        return currentPicture.height();
    }

    // Calculates and returns the energy at a given pixel (x, y)
    public double energy(int x, int y) {
        checkValidity(x, y);
        return calculateEnergy(x, y);
    }

    // Checks the validity of the given coordinates (x, y)
    private void checkValidity(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }

    // Calculates the energy of a pixel at coordinates (x, y)
    private double calculateEnergy(int x, int y) {
        Color leftColor = getWrappedColor(x - 1, y);
        Color rightColor = getWrappedColor(x + 1, y);
        Color topColor = getWrappedColor(x, y - 1);
        Color bottomColor = getWrappedColor(x, y + 1);
        return Math.sqrt(
                colorDifference(leftColor, rightColor) +
                        colorDifference(topColor, bottomColor));
    }

    // Gets the color of a pixel with wrapped coordinates
    private Color getWrappedColor(int x, int y) {
        int wrappedX = (x + width()) % width();
        int wrappedY = (y + height()) % height();
        return new Color(currentPicture.getRGB(wrappedX, wrappedY));
    }

    // Calculates the color difference between two colors
    private double colorDifference(Color c1, Color c2) {
        return Math.pow(c1.getRed() - c2.getRed(), 2) +
                Math.pow(c1.getGreen() - c2.getGreen(), 2)
                + Math.pow(c1.getBlue() - c2.getBlue(), 2);
    }

    // Finds a horizontal seam in the current picture
    public int[] findHorizontalSeam() {
        rotateImage();
        int[] seam = findVerticalSeam();
        validateSeam(seam);
        rotateImage();
        return seam;
    }

    // Rotates the current picture by 90 degrees
    private void rotateImage() {
        Picture rotated = new Picture(height(), width());
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                rotated.setRGB(j, i, currentPicture.getRGB(i, j));
            }
        }
        currentPicture = rotated;
    }

    // Finds a vertical seam in the current picture
    public int[] findVerticalSeam() {
        int w = width(), h = height();
        double[][] energies = new double[h][w];
        double[][] distances = new double[h][w];
        int[][] edgeTo = new int[h][w];
        int[] seam = new int[h];
        Arrays.fill(seam, -1);
        IndexMinPQ<Double> pq = new IndexMinPQ<>(w * h);

        for (int y = 0; y < h; y++) {

            for (int x = 0; x < w; x++) {

                energies[y][x] = energy(x, y);

                if (y == 0) {
                    distances[y][x] = energies[y][x];
                }
                else {
                    distances[y][x] = Double.POSITIVE_INFINITY;
                }

                if (y == 0) {
                    pq.insert(x, distances[y][x]);
                }
            }
        }
        while (!pq.isEmpty()) {
            int index = pq.delMin();
            int row = index / w, col = index % w;
            if (row == h - 1) {
                return extractSeam(edgeTo, col, h);
            }
            updateDistances(row, col, energies, distances, edgeTo, pq, w);
        }
        validateSeam(seam);
        return seam;
    }

    // Updates the distances for finding the seam
    private void updateDistances(int row, int col, double[][] energies,
                                 double[][] distances,
                                 int[][] edgeTo, IndexMinPQ<Double> pq,
                                 int width) {
        for (int i = -1; i <= 1; i++) {
            int nextCol = col + i;
            if (nextCol >= 0 && nextCol < width) {
                double newDist = distances[row][col] + energies[row + 1][nextCol];
                if (newDist < distances[row + 1][nextCol]) {
                    distances[row + 1][nextCol] = newDist;
                    edgeTo[row + 1][nextCol] = col;
                    pq.insert((row + 1) * width + nextCol, newDist);
                }
            }
        }
    }

    // Extracts the seam from the edgeTo array
    private int[] extractSeam(int[][] edgeTo, int endCol, int height) {
        int[] seam = new int[height];
        for (int y = height - 1, x = endCol; y >= 0; y--) {
            seam[y] = x;
            x = edgeTo[y][x];
        }
        return seam;
    }

    // Removes a horizontal seam from the current picture
    public void removeHorizontalSeam(int[] seam) {
        rotateImage();
        removeVerticalSeam(seam);
        rotateImage();
    }

    // Removes a vertical seam from the current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);

        Picture newPic = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) {
            for (int x = 0, newX = 0; x < width(); x++) {
                if (x != seam[y]) {
                    newPic.setRGB(newX++, y, currentPicture.getRGB(x, y));
                }
            }
        }
        currentPicture = newPic;
    }

    // Validates the seam to be removed
    private void validateSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("Seam is null");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("Seam length is incorrect. "
                                                       + "Expected: " +
                                                       height() + ", but got: "
                                                       + seam.length);
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width()) {
                throw new IllegalArgumentException("Seam value out of range at "
                                                           + "index " + i + ": "
                                                           + seam[i]);
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("Seam not continuous at "
                                                           + "index " + i + ": "
                                                           + seam[i]
                                                           + ", previous: "
                                                           + seam[i - 1]);
            }
        }
    }

    // Main method for testing the SeamCarver class
    public static void main(String[] args) {
        Picture p = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(p);

        // Display initial state
        sc.picture().show();
        StdOut.println("Initial width: " + sc.width());
        StdOut.println("Initial height: " + sc.height());
        StdOut.println("Energy at (0,0): " + sc.energy(0, 0));

        // // // Perform seam carving
        // int iterations = 2; // Number of iterations for seam removal
        // for (int i = 0; i < iterations; i++) {
        //     if (sc.width() > 1) { // Check if vertical seam removal is
        //     possible
        //         int[] verticalSeam = sc.findVerticalSeam();
        //         sc.removeVerticalSeam(verticalSeam);
        //     }
        //     if (sc.height() > 1) { // Check if horizontal seam removal is
        //     possible
        //         int[] horizontalSeam = sc.findHorizontalSeam();
        //         sc.removeHorizontalSeam(horizontalSeam);
        //     }
        // }

        // Timing
        sc.picture().show();
        StdOut.println("Final width: " + sc.width());
        StdOut.println("Final height: " + sc.height());

        Picture y = new Picture("6x5.png");
        SeamCarver x = new SeamCarver(y);

        Stopwatch stop = new Stopwatch();
        x.removeVerticalSeam(x.findVerticalSeam());
        x.removeHorizontalSeam(x.findHorizontalSeam());
        StdOut.println(stop.elapsedTime());

        int[] widths = {500, 1000, 1500, 2000};  // Example widths
        int[] heights = {500, 1000, 1500, 2000}; // Example heights

        for (int width : widths) {
            for (int height : heights) {
                // Create a Picture of specified width and height
                Picture picture = createTestPicture(width, height);
                SeamCarver sc = new SeamCarver(picture);

                // Measure time for seam carving operations
                double timeTaken = measureSeamCarvingTime(sc);
                System.out.println("Time for " + width + "x" + height + ": " + timeTaken + " seconds");

                // Assert that the time taken is within expected bounds (example assertion)
                assertTrue(timeTaken < 30); // Assuming 30 seconds as the upper limit for the operation
            }
        }
    }

    private Picture createTestPicture(int width, int height) {
        // Create and return a test picture with the given dimensions
        // (This method should be implemented to create a picture of specified size)
        return new Picture(width, height);
    }

    private double measureSeamCarvingTime(SeamCarver sc) {
        Stopwatch stopwatch = new Stopwatch();

        // Perform the operations
        sc.removeVerticalSeam(sc.findVerticalSeam());
        sc.removeHorizontalSeam(sc.findHorizontalSeam());

        // Return the elapsed time
        return stopwatch.elapsedTime();
    }

    }
}



