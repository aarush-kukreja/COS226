Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */
The algorithm for finding a vertical seam in an image involves calculating
the energy of each pixel, which signifies the importance of that pixel in the
image. For vertical seams, the algorithm iterates through each row, starting
from the top, and selects a path downward, choosing at each step the pixel with
the lowest cumulative energy from the top row to the current row. This process
 is achieved using a dynamic programming approach, where the cumulative energy
 of each pixel is updated based on its predecessors in the above row. The
 minimum energy path from top to bottom forms the vertical seam.

To find a horizontal seam, the image is first rotated 90 degrees,
then the same vertical seam-finding algorithm is applied. After
finding the seam, the image is rotated back to its original orientation.




/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */
Seam carving is most effective on images where the important content
 (like objects or focal points) is not distributed evenly across
 the image. It works well with images that have large areas of low
 energy (like skies, blank walls, or uniform backgrounds) as these
 areas can be carved out without significantly altering the perceptual
 content of the image.

Images not suitable for seam carving are those where important details
are spread uniformly across the image. In such cases, removing seams
may lead to noticeable distortion or loss of important content.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) =

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
1000           0.86             NA              NA
2000           1.50             1.74            0.56
4000           2.95             1.97            0.68
8000           5.63             1.91            0.65
16000           11.50            2.04            0.71
32000           31.75            2.76            1.01


(keep H constant)
 H = 2000
 multiplicative factor (for W) =

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
1000            1.01             NA             NA
2000            2.01            1.99            0.69
4000            4.04            2.01            0.70
8000             7.73           1.91            0.65
16000            14.01          1.81            0.59
32000            31.12          2.22            0.80


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^1.01 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */

Average log ratio for W constant, H changing:
(0.56+0.68+0.65+0.71+1.01)/5 = 0.72
(0.69+0.70+0.65+0.59+0.80)/5 = 0.69

For W = 2000, H = 2000 -- time is 1.50s
1.50 = C * 2000^0.69 * 2000^0.72
C = 3.32*10^-5

Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:

    ~
        3.32*10^-5 * W^0.69 * H^0.72


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None.


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

None.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
Enjoyed.
