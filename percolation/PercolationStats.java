import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE = 1.96;
    // 1D array contains the numbers used for running the calculations
    private final double[] nums;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid values selected");
        }

        nums = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                per.open(StdRandom.uniformInt(0, n), StdRandom.uniformInt(0, n));
            }
            nums[i] = (double) (per.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(nums);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(nums);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (CONFIDENCE * stddev() / (Math.sqrt(nums.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (CONFIDENCE * stddev() / (Math.sqrt(nums.length)));

    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch s = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats run = new PercolationStats(n, trials);
        System.out.println("Mean = " + run.mean());
        System.out.println("stddev()  = " + run.stddev());
        System.out.println("confidenceLow() = " + run.confidenceLow());
        System.out.println("confidenceHigh() = " + run.confidenceHigh());
        System.out.println("Time = " + s.elapsedTime());

    }
}
