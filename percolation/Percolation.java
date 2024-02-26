import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // top is the virtual top; bottom is the virtual bottom; n is the size of
    // the matrix for percolation
    private final int top, n, bottom;
    // openSites stores the amount of open sites there are in the matrix
    private int openSites;
    // opened is a 2D array that tracks which sites are opened--"true" cells are
    // open while "false" or null cells are closed
    private final boolean[][] opened;
    // wqf is an instance of the WeightedQuickUnionUF class, and is used for
    // quick-find and quick-union to link opened and filled sites with
    // neighboring opened and filled sites
    private final WeightedQuickUnionUF wqf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        top = 0;
        bottom = n * n + 1;
        wqf = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n][n];
        openSites = 0;
    }

    // checks if row and col are in valid range
    private void checker(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IllegalArgumentException("Input out of range.");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        checker(row, col);

        if (!opened[row][col]) {
            opened[row][col] = true;
            openSites++;
        }

        if (row == 0 && (isOpen(row, col))) {
            wqf.union(getQFIndex(row, col), top);
        }
        if (row == n - 1 && (isOpen(row, col))) {
            wqf.union(getQFIndex(row, col), bottom);
        }

        if (row < n - 1 && isOpen(row + 1, col)) {
            wqf.union(getQFIndex(row, col), getQFIndex(row + 1, col));
        }

        if (row > 0 && isOpen(row - 1, col)) {
            wqf.union(getQFIndex(row, col), getQFIndex(row - 1, col));
        }

        if (col < n - 1 && isOpen(row, col + 1)) {
            wqf.union(getQFIndex(row, col), getQFIndex(row, col + 1));
        }

        if (col > 0 && isOpen(row, col - 1)) {
            wqf.union(getQFIndex(row, col), getQFIndex(row, col - 1));
        }

    }

    // finds QF of regular, private helper method
    private int getQFIndex(int row, int col) {
        return n * row + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checker(row, col);
        return opened[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checker(row, col);
        if (isOpen(row, col)) {
            return wqf.find(top) == wqf.find(getQFIndex(row, col));
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return (wqf.find(top) == wqf.find(bottom));
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        Percolation percolation = new Percolation(n);

        // Testing the constructor
        System.out.println("Constructor Test:");
        System.out.println("Number of open sites after initialization: "
                                   + percolation.numberOfOpenSites());
        System.out.println("Does it percolate after initialization? "
                                   + percolation.percolates());

        // Testing the open method
        System.out.println("\nOpen Method Test:");
        percolation.open(1, 0);
        percolation.open(1, 0);
        percolation.open(2, 0);
        percolation.open(3, 0);
        System.out.println("Number of open sites after opening three sites: "
                                   + percolation.numberOfOpenSites());
        System.out.println("Is site (0, 0) open? " + percolation.isOpen(0, 0));
        System.out.println("Is site (1, 1) open? " + percolation.isOpen(1, 1));

        // Testing the isFull method
        System.out.println("\nIsFull Method Test:");
        System.out.println("Is site (0, 0) full? " + percolation.isFull(0, 0));
        System.out.println("Is site (1, 0) full? " + percolation.isFull(1, 0));

        // Testing the percolates method
        System.out.println("\nPercolates Method Test:");
        System.out.println("Does it percolate after opening two sites? "
                                   + percolation.percolates()); // Depends on n
    }

}
