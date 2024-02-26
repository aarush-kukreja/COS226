Programming Assignment 5: K-d Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
The Node class in the KdTreeST class serves as the basic building block for the
2d-tree. Each Node object contains:

A 2D point p of type Point2D that represents its coordinates.
A generic value associated with the point.
A rectangle rect of type RectHV, representing the axis-aligned rectangle
corresponding to this node.
Two child nodes, lb (left/bottom) and rt (right/top), which represent the left
and right subtrees for vertical splits or bottom and top subtrees for horizontal
splits.

/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
The range search method is implemented as range() in KdTreeST. It takes a
rectangle RectHV as its parameter and returns all points within that rectangle.
The method works as follows:

If the current node is null, return.
If the current point lies within the rectangle, add it to the queue.
Recursively search the left/bottom and right/top subtrees if they intersect with
the given rectangle.

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */

The nearest neighbor search is implemented as nearest() in KdTreeST. Given a
point p, it returns the point in the k-d tree that is nearest to p. The
method operates as follows:

If the current node is null, return the current minimum point.
Update the minimum point if the current node's point is closer to p.
Recursively search the subtree that is closer to the point p first.
Then, prune the second subtree if it could not possibly contain a closer point.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:              20                  1.002               19.96

KdTreeST:             420977                1.0               420977

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

No problems


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */

I liked the project!
