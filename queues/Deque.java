import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item; // The generic type representing elements
        // stored in the Deque.
        private Node next; // Reference to the next node in the deque.
        private Node previous;  // Reference to the previous node in the deque.
    }

    private Node first, last; // Reference to first and last node in deque
    private int count; // count amount of items

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.previous = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        count++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        count--;
        Item oldFirst = first.item;
        first = first.next;

        // Set the previous reference of the new first node to null
        if (first != null) {
            first.previous = null;
        }
        else {
            // If there are no more items in the deque,
            // update the last reference to null
            last = null;
        }
        return oldFirst;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        count--;
        Item oldLast = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null; // Set the next reference of the new
            // last node to null
        }
        else {
            // If there's no more item in the deque, update the first
            // reference to null
            first = null;
        }
        return oldLast;
    }

    // iterator for the deque
    public Iterator<Item> iterator() {
        return new Iteration();
    }

    private class Iteration implements Iterator<Item> {
        private Node currentNode = first; // currentNode stores first

        public boolean hasNext() {
            return currentNode != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item oldNode = currentNode.item;
            currentNode = currentNode.next;
            return oldNode;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.println("Testing isEmpty() when deque is empty:");
        StdOut.println("Is deque empty? " + deque.isEmpty()); // Expected: true
        StdOut.println("Deque size: " + deque.size()); // Expected: 0

        // Test addFirst() and iterator()
        StdOut.println("\n\nTesting addFirst():");
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        Iterator<Integer> iterator1 = deque.iterator();

        StdOut.println("Deque contents: "); // Expected: 3 2 1
        while (iterator1.hasNext()) {
            int i = iterator1.next();
            StdOut.print(i + " ");
        }

        // Test size() after adding elements
        StdOut.println("\n\nTesting size() after adding elements:");
        StdOut.println("Deque size: " + deque.size()); // Expected: 3

        // Test addLast()
        StdOut.println("\n\nTesting addLast():");
        deque.addLast(4);
        deque.addLast(5);
        StdOut.println("Deque contents: "); // Expected: 3 2 1 4 5

        Iterator<Integer> iterator2 = deque.iterator();
        while (iterator2.hasNext()) {
            int i = iterator2.next();
            StdOut.print(i + " ");
        }

        // Test removeFirst()
        StdOut.println("\n\nTesting removeFirst():");
        int removedFirst = deque.removeFirst();
        StdOut.println("Removed item from the front: " + removedFirst); //
        // Expected: 3
        StdOut.println(
                "Deque contents after removal: "); // Expected: 2 1 4 5

        Iterator<Integer> iterator3 = deque.iterator();
        while (iterator3.hasNext()) {
            int i = iterator3.next();
            StdOut.print(i + " ");
        }

        // Test removeLast()
        StdOut.println("\nTesting removeLast():");
        int removedLast = deque.removeLast();
        StdOut.println("Removed item from the back: " + removedLast); //
        // Expected: 5
        StdOut.println("Deque contents after removal: "); // Expected: 2 1 4
        Iterator<Integer> iterator4 = deque.iterator();

        while (iterator4.hasNext()) {
            int i = iterator4.next();
            StdOut.print(i + " ");
        }

        // Test size() after removal
        System.out.println("\n\nTesting size() after removal:");
        System.out.println("Deque size: " + deque.size()); // Expected: 3
    }
}


