import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class PointST<Value> {

    // treemap
    private TreeMap<Point2D, Value> st;

    // construct an empty symbol table of points
    public PointST() {
        st = new TreeMap<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return st.isEmpty();
    }

    // number of points
    public int size() {
        return st.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) throw new IllegalArgumentException();
        st.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return st.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return st.containsKey(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return st.keySet();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> result = new LinkedList<>();
        for (Map.Entry<Point2D, Value> entry : st.entrySet()) {
            Point2D point = entry.getKey();
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        Point2D nearest = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Map.Entry<Point2D, Value> entry : st.entrySet()) {
            Point2D point = entry.getKey();
            double distance = point.distanceSquaredTo(p);
            if (distance < nearestDistance) {
                nearest = point;
                nearestDistance = distance;
            }
        }

        return nearest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> pointST = new PointST<>();
        StdOut.println("Is Empty: " + pointST.isEmpty());

        Point2D point1 = new Point2D(0.2, 0.3);
        Point2D point2 = new Point2D(0.4, 0.7);
        Point2D point3 = new Point2D(0.5, 0.1);

        pointST.put(point1, 1);
        pointST.put(point2, 2);
        pointST.put(point3, 3);

        StdOut.println("Size: " + pointST.size());
        StdOut.println("Get Value: " + pointST.get(point1));
        StdOut.println("Contains: " + pointST.contains(new Point2D(0.2, 0.3)));

        for (Point2D point : pointST.points()) {
            StdOut.println("Point: " + point);
        }

        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.5);
        for (Point2D point : pointST.range(rect)) {
            StdOut.println("In Range: " + point);
        }

        StdOut.println("Nearest to (0.3, 0.4): " +
                               pointST.nearest(new Point2D(0.3, 0.4)));
    }
}
