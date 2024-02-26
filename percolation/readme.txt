/* *****************************************************************************
 *  Put each response on the same line as the question, after the colon.
 *
 *  Operating system: Windows 11
 *  [ examples are "OS X Ventura 13.5", "Windows 11", and "Ubuntu 22.04" ]
 *
 *  Compiler: JDK 11.0.20
 *  [ an example is "Temurin 11.0.20" ]
 *
 *  Text editor / IDE: IntelliJ 2023.2
 *  [ an example is "IntelliJ 2023.2" ]
 *
 *  Have you taken (part of) this course before: no
 *  ["yes" or "no"]
 *
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: yes
 *  ["yes" or "no"]
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 1: Percolation


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */


/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */
open(): checks if (row, col) is within the proper range, then marks the site
 as open in the ‘opened’ 2D array, increments the openSites counter, then uses
  the Weighted Quick Union-Find data structure to connect the opened site to
   any of its neighboring open sites (top, bottom, left, right). It uses the
    helper method getQFIndex to convert the 2D (row, col) to 1D.
isOpen(): Checks if the site at (row, col) is open by querying the opened array.
isFull(): Checks if the site at (row, col) is full by seeing if it is open and
connected to the virtual top site. It uses the find method of
WeightedQuickUnionUF and the getQFIndex helper method. It also uses the find
 method of WeightedQuickUnionUF to determine if the site is connected to the
 virtual top site.


numberOfOpenSites(): Returns the count of open sites, which is tracked by the
 openSites variable.
percolates(): Uses the find method of WeightedQuickUnionUF to check if the
 virtual top and bottom sites are connected (if they have the same root).

/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 * Between 900 and 930.
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
100 0.132
200 0.599
400 3.865
800 30.328
900 53.35
930 67.616
...

/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */
I started at n=100 and then doubled n for the following input. I was thus able
to see the growth in time exponentially.

/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest value of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
100        0.1
200        0.248
400        0.95
800        5.038
1600      39.24
1950      54.21
2000      61.962


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
Backwash does occur in Percolation.java.




/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
I don’t believe there were any serious problems I encountered.



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
I enjoyed this assignment! I just wonder why the elapsed time is inconsistent
sometimes–larger n values sometimes take
 much less time than relatively smaller n values.
