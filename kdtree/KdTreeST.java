import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.Queue;

public class KdTreeST<Value> {
    // root node of the KD-tree
    private Node root;

    // number of points in the KD-tree
    private int size;


    // Represents a node in the KD-tree
    private class Node {
        // point associated with this node
        private Point2D p;
        // value associated with this point
        private Value value;
        // rectangle corresponding to this node
        private RectHV rect;
        // left/bottom subtree
        private Node lb;
        // right/top subtree
        private Node rt;
    }

    // Constructor
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // Checks if the KD-tree is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the size of the KD-tree
    public int size() {
        return size;
    }

    // Inserts a point-value pair into the KD-tree
    public void put(Point2D p, Value val) {
        if (p == null || val == null) throw new IllegalArgumentException();
        root = put(root, p, val, true, new RectHV(Double.NEGATIVE_INFINITY,
                                                  Double.NEGATIVE_INFINITY,
                                                  Double.POSITIVE_INFINITY,
                                                  Double.POSITIVE_INFINITY));
    }

    // Helper method for inserting a point-value pair into the KD-tree
    private Node put(Node x, Point2D p, Value val, boolean vertical, RectHV rect) {
        if (x == null) {
            Node node = new Node();
            node.p = p;
            node.value = val;
            node.rect = rect;
            size++;
            return node;
        }

        if (x.p.equals(p)) {
            x.value = val;
            return x;
        }

        int cmp;
        if (vertical) {
            cmp = Double.compare(p.x(), x.p.x());
        }
        else {
            cmp = Double.compare(p.y(), x.p.y());
        }

        if (cmp < 0) {
            x.lb = put(x.lb, p, val, !vertical, leftRect(rect, x.p, vertical));
        }
        else {
            x.rt = put(x.rt, p, val, !vertical, rightRect(rect, x.p, vertical));
        }

        return x;
    }

    // Retrieves the value associated with the given point
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p, true);
    }

    // Helper method for retrieving the value associated with the given point
    private Value get(Node x, Point2D p, boolean vertical) {
        if (x == null) return null;

        if (x.p.equals(p)) return x.value;

        int cmp;
        if (vertical) {
            cmp = Double.compare(p.x(), x.p.x());
        }
        else {
            cmp = Double.compare(p.y(), x.p.y());
        }

        if (cmp < 0) return get(x.lb, p, !vertical);
        else return get(x.rt, p, !vertical);
    }

    // Checks if the given point exists in the KD-tree
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // Returns all points in the KD-tree
    public Iterable<Point2D> points() {
        Queue<Point2D> points = new LinkedList<>();
        Queue<Node> queue = new LinkedList<>();
        if (root != null) queue.add(root);

        while (!queue.isEmpty()) {
            Node x = queue.poll();
            points.add(x.p);

            if (x.lb != null) queue.add(x.lb);
            if (x.rt != null) queue.add(x.rt);
        }

        return points;
    }

    // Returns all points that lie within the given rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> points = new LinkedList<>();
        if (root != null) {  // Check for non-empty tree before calling helper
            range(root, rect, points);
        }
        return points;

    }

    // Returns all points that lie within the given rectangle
    private void range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null) return;
        if (rect.contains(x.p)) queue.add(x.p);
        if (x.lb != null && rect.intersects(x.lb.rect)) range(x.lb, rect, queue);
        if (x.rt != null && rect.intersects(x.rt.rect)) range(x.rt, rect, queue);
    }

    // Returns the point nearest to the given point
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null; // Return null if the tree is empty
        return nearest(root, p, root.p, true);
    }

    // Helper method for finding the point nearest to the given point
    private Point2D nearest(Node x, Point2D p, Point2D min, boolean vertical) {
        if (x == null) return min;

        double minDistance = min.distanceSquaredTo(p);
        if (x.rect.distanceSquaredTo(p) >= minDistance) return min;

        double distance = x.p.distanceSquaredTo(p);
        if (distance < minDistance) {
            min = x.p;
        }

        int cmp;
        if (vertical) {
            cmp = Double.compare(p.x(), x.p.x());
        }
        else {
            cmp = Double.compare(p.y(), x.p.y());
        }

        Node first;
        if (cmp < 0) {
            first = x.lb;
        }
        else {
            first = x.rt;
        }

        Node second;
        if (cmp < 0) {
            second = x.rt;
        }
        else {
            second = x.lb;
        }

        min = nearest(first, p, min, !vertical);
        min = nearest(second, p, min, !vertical);

        return min;
    }

    // Helper method for determining the rectangle to the left/bottom of a given point
    private RectHV leftRect(RectHV rect, Point2D p, boolean vertical) {
        if (vertical) return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
        else return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
    }

    // Helper method for determining the rectangle to the right/top of a given point
    private RectHV rightRect(RectHV rect, Point2D p, boolean vertical) {
        if (vertical) return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        else return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
    }

    public static void main(String[] args) {
        KdTreeST<Integer> kdTree = new KdTreeST<>();
        StdOut.println("Is Tree Empty: " + kdTree.isEmpty());

        kdTree.put(new Point2D(0.7, 0.2), 1);
        kdTree.put(new Point2D(0.5, 0.4), 2);
        kdTree.put(new Point2D(0.2, 0.3), 3);
        kdTree.put(new Point2D(0.4, 0.7), 4);
        kdTree.put(new Point2D(0.9, 0.6), 5);

        StdOut.println("Size: " + kdTree.size());

        // Print all points in the kd-tree
        for (Point2D point : kdTree.points()) {
            StdOut.println(point);
        }

        // Find and print all points that are inside a given rectangle
        RectHV rect = new RectHV(0.1, 0.1, 0.6, 0.8);
        StdOut.println("Points in range:");
        for (Point2D point : kdTree.range(rect)) {
            StdOut.println(point);
        }

        // Find the nearest point to a given point
        Point2D queryPoint = new Point2D(0.1, 0.5);
        Point2D nearestPoint = kdTree.nearest(queryPoint);
        StdOut.println("Nearest point to " + queryPoint + ": " + nearestPoint);

        // Check if a specific point exists in the kd-tree
        Point2D pointToCheck = new Point2D(0.7, 0.2);
        StdOut.println("Contains " + pointToCheck + "? " +
                               kdTree.contains(pointToCheck));

        // Retrieve value associated with a specific point
        int value = kdTree.get(pointToCheck);
        StdOut.println("Value associated with " + pointToCheck + ": " + value);

    }
}
