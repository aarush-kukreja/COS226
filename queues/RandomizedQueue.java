import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count; // counts amount of items
    private Item[] array; // array to store items

    // construct an empty randomized queue
    public RandomizedQueue() {
        int initial = 1;
        array = (Item[]) new Object[initial];
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (count == array.length) {
            resizer(array.length * 2);
        }
        array[count] = item;
        count++;
    }

    // returns a copy of the array
    private void resizer(int factor) {
        Item[] resizedArray = (Item[]) new Object[factor];
        for (int i = 0; i < count; i++) {
            resizedArray[i] = array[i];
        }
        array = resizedArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomnum = StdRandom.uniformInt(count);
        Item toRemove = array[randomnum];
        array[randomnum] = array[count - 1]; // swap with last item so end is
        // null
        array[count - 1] = null; // Clear the reference to the removed item
        count--;

        if (count == array.length / 4 && count > 0) {
            resizer(array.length / 2);
        }
        return toRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomnum = StdRandom.uniformInt(count);
        return array[randomnum];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iteration();
    }

    private class Iteration implements Iterator<Item> {
        private int currentNum; // current position
        private Item[] mixed; // the mixed array which is to be returned

        // for iteration
        public Iteration() {
            currentNum = 0;
            mixed = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) {
                mixed[i] = array[i];
            }
            StdRandom.shuffle(mixed);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return (currentNum < count);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return mixed[currentNum++];

        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        // Test isEmpty() and size() on an empty queue
        StdOut.println(randomizedQueue.isEmpty());
        StdOut.println(randomizedQueue.size());

        // Test enqueue()
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);

        // Test iterator() and randomly iterate through the queue
        Iterator<Integer> iterator = randomizedQueue.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        // Test isEmpty() and size() on a non-empty queue
        StdOut.println("\nIs it empty: " + randomizedQueue.isEmpty());
        StdOut.println("Size: " + randomizedQueue.size());

        // Test sample()
        StdOut.println("Sample: " + randomizedQueue.sample());

        // Test dequeue()
        StdOut.println("Dequeued: " + randomizedQueue.dequeue());

        System.out.println();

        // Test iterator again
        Iterator<Integer> iterator2 = randomizedQueue.iterator();
        while (iterator2.hasNext()) {
            System.out.print(iterator2.next() + " ");
        }
        System.out.println();


    }

}
