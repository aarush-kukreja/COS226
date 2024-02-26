Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */
For the deque, used the Node class. There is a private inner class called Node
that represents the individual elements (items) of the deque. Each Node contains
an Item, a reference to the next Node, and a reference to the previous Node.
This structure allows for efficient insertion and removal of elements at both
the front and back of the deque. The main Deque class maintains two
references: first and last, which point to the first and last nodes in the
linked list, respectively. It also keeps track of the number of items in the
deque with the count variable.

As for the randomized queue, it uses an array. The array allowed for the
random functions to work easily, such as the iterator, in which
the suffle function came of good use. The array is of type Item, which is a
generic type parameter for the class. It means that you can
create a RandomizedQueue for any type of objects. The choice of an array-based
data structure is efficient for implementing a randomized queue because it
allows for constant-time (amortized) enqueue and dequeue operations, as well as
constant-time sampling. Initially, the array is created with a size of 8,
but it can dynamically resize itself to accommodate more elements when needed.
When the array becomes full (i.e., count equals the length of the array),
it is resized to twice its current size using the resizer method.
This resizing strategy helps to amortize the cost of resizing operations.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  8n  bytes

Deque:              ~  48n  bytes

The Deque consists of:

private class Node { // 16 (object overhead)
        public Item item;
        private Node next; // 8 (reference)
        private Node previous; // 8 (reference)
    }

    private Node first, last; // 16 (references)
    private int count; // 4 (int)

    (16+8+8+16)n + 4 (+4 padding) = 48n + 8 ~ 48n

The randomized queue consists of (assumption--Each reference
(pointer) to an item in the array takes 8 bytes of memory on a 64-bit system):

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count; // 4 (int)
    private Item[] array; // 8n + 24

    (8n) + 28 (+4 padding) = 8n + 32 ~ 8n

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
No bugs/limitations. I didn't go for the bonus.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I didn't have any serious problems.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

I liked doing the assignment! I thought this was an interesting change from the
previous assignment.
