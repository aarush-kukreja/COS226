import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;

public class Tester {
    public static void main(String[] args) {
        In in = new In("input1M.txt");  // Initialize input stream
        PointST<Integer> pointST = new PointST<>();

        int i = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            pointST.put(new Point2D(x, y), i++);
        }

        // Measure CPU time for nearest-neighbor searches
        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < 1000) {
            Point2D queryPoint = new Point2D(Math.random(), Math.random());
            pointST.nearest(queryPoint);
            count++;
        }
        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;
        System.out.println(
                "PointST: " + count + " nearest-neighbor searches in " + elapsedTime + " seconds.");
    }

}
