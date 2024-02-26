Programming Assignment 7: Fraud Detection

/* *****************************************************************************
 *  Describe how you implemented the Clustering constructor
 **************************************************************************** */

The constructor first checks if the locations array is null, throwing an
IllegalArgumentException if it is. It then validates the value of k, ensuring
it is within the valid range (greater than 0 and less than or equal to the
length of locations). There is also a check for null elements within the
locations array. The code constructs an EdgeWeightedGraph named graph with
vertices equal to the
number of locations. It iterates over pairs of locations, calculating the
Euclidean distance between each pair and creating an edge with this distance
as the weight. These edges are added to the graph. Using Kruskal's algorithm
 (via the KruskalMST class), the code computes the MST
 of the graph. If k is 1, all points are assigned to a single cluster.
For k greater than 1, it constructs a new EdgeWeightedGraph named clusterGraph.
It then adds edges from the MST to this graph, excluding the
heaviest m - k edges to form k connected components.
The CC (Connected Components) class is used on clusterGraph to identify these
connected components. An array clusterId is used to store the cluster IDs for
each point. The connected component ID for each vertex (point) in clusterGraph
is determined using the connectedComponents.id(i) method and stored in clusterId.
The clusterOf method returns the cluster ID for a given point index, with error
handling for index out of bounds. The reduceDimensions method reduces the
dimensionality of an input array by summing the values within each cluster.
Summarizing, the constructor
efficiently handles the clustering of points based on their Euclidean
distances by first creating a graph representation of the points, computing
 its MST, and then identifying connected components based on the desired number
 of clusters. It ensures robust error handling and validation throughout the
  process. The additional methods facilitate the use of the clustering
  in further operations like dimensionality reduction.

/* *****************************************************************************
 *  Describe how you implemented the WeakLearner constructor
 **************************************************************************** */
In the `WeakLearner` class, the constructor method plays a crucial role
by validating the input data and training the model. Initially,
it confirms the integrity of input arrays—`input`, `weights`, and `labels`—by
checking for null values and empty arrays and ensuring that the lengths of these
arrays are consistent. The validation process also includes verifying that
weight values are non-negative and label values are binary (0 or 1). Once
validation is complete, the constructor proceeds to the training phase.
Here, it employs a' train' method that iteratively evaluates each
feature dimension of the input data. It calculates a
threshold value for each dimension that  separates the data points based
on their labels
and corresponding weights. This process involves sorting the data points along
each dimension and analyzing the cumulative weights of correctly and incorrectly
classified points to identify the most effective threshold. The best-performing
dimension, along with its corresponding threshold value and the direction of
classification (sign predictor), is then selected to define the decision
stump's parameters—`dimensionPredictor,` `valuePredictor,` and `signPredictor.`
The `predict` method of the class utilizes these parameters to classify
new samples. The constructor also ensures robust error handling by
throwing `IllegalArgumentException` for invalid inputs. Additionally,
the class includes utility methods to access these model parameters and
a `main` method for basic unit testing, ensuring the model's functionality
and adaptability to varying data sets. This  approach in the
constructor establishes a foundation for the decision
stump, making it a good (weak) learner for boosting algorithms.

/* *****************************************************************************
 *  Consider the large_training.txt and large_test.txt datasets.
 *  Run the boosting algorithm with different values of k and T (iterations),
 *  and calculate the test data set accuracy and plot them below.
 *
 **************************************************************************** */

      k          T         test accuracy       time (seconds)
   --------------------------------------------------------------------------
k = 2, T = 10, Test Accuracy = 0.68, Time = 0.22s
k = 2, T = 50, Test Accuracy = 0.66, Time = 0.28s
k = 2, T = 100, Test Accuracy = 0.68, Time = 0.34s
k = 2, T = 150, Test Accuracy = 0.72, Time = 0.30s
k = 2, T = 200, Test Accuracy = 0.72, Time = 0.39s
k = 5, T = 10, Test Accuracy = 0.79, Time = 0.11s
k = 5, T = 50, Test Accuracy = 0.84, Time = 0.21s
k = 5, T = 100, Test Accuracy = 0.85, Time = 0.35s
k = 5, T = 150, Test Accuracy = 0.85, Time = 0.49s
k = 5, T = 200, Test Accuracy = 0.85, Time = 0.63s
k = 10, T = 10, Test Accuracy = 0.89, Time = 0.11s
k = 10, T = 50, Test Accuracy = 0.95, Time = 0.32s
k = 10, T = 100, Test Accuracy = 0.95, Time = 0.59s
k = 10, T = 150, Test Accuracy = 0.95, Time = 0.87s
k = 10, T = 200, Test Accuracy = 0.95, Time = 1.15s
k = 15, T = 10, Test Accuracy = 0.89, Time = 0.11s
k = 15, T = 50, Test Accuracy = 0.97, Time = 0.44s
k = 15, T = 100, Test Accuracy = 0.97, Time = 0.83s
k = 15, T = 150, Test Accuracy = 0.97, Time = 1.26s
k = 15, T = 200, Test Accuracy = 0.98, Time = 1.65s
k = 20, T = 10, Test Accuracy = 0.89, Time = 0.16s
k = 20, T = 50, Test Accuracy = 0.96, Time = 0.51s
k = 20, T = 100, Test Accuracy = 0.97, Time = 0.97s
k = 20, T = 150, Test Accuracy = 0.97, Time = 1.44s
k = 20, T = 200, Test Accuracy = 0.97, Time = 1.90s

/* *****************************************************************************
 *  Find the values of k and T that maximize the test data set accuracy,
 *  while running under 10 second. Write them down (as well as the accuracy)
 *  and explain:
 *   1. Your strategy to find the optimal k, T.
 *   2. Why a small value of T leads to low test accuracy.
 *   3. Why a k that is too small or too big leads to low test accuracy.
 **************************************************************************** */
Optimal Result: k = 15, T = 200, Test Accuracy = 0.98, Time = 1.65s

1. There were four main things in my strategy:
a. Systematic Exploration: The approach involved systematically iterating over a
predefined range of values for k (number of clusters) and T
(number of iterations). This allowed for a comprehensive assessment of different
configurations.

b. Balancing Complexity and Performance: The values were chosen to balance the
complexity of the model (with k) and the depth of learning (with T) while
keeping the runtime under the 10-second constraint.

c. Incremental Testing: The process ensured that no potential optimal
combination
was overlooked by testing in incremental steps. This step-wise increment in
values helped in understanding the trend of how accuracy changes with different
k and T.

d. Performance Tracking: The best accuracy within the time limit was tracked,
ensuring that the selected k and T were the most accurate and efficient in
computation time.

2. A small T means fewer iterations for the boosting algorithm to learn and
adjust its weights. With limited iterations, the weak learners in the
ensemble have less opportunity to improve their predictions based on the
weighted training data. Furthermore, with fewer iterations, the model is likely
to underfit, meaning it doesn't capture the underlying patterns and complexities
of the data well enough, leading to poor performance on the test dataset.
Moreover, A small T might not allow the model to converge to a good solution,
especially in complex datasets where more iterations are needed for the
algorithm to identify and correct its mistakes.

3. A too small k means fewer clusters, potentially oversimplifying the data
representation. This can lead to important nuances and patterns in the data
being missed, as the reduced dimensionality might not capture all the relevant
information. A too large k can lead to overfitting, where the model starts to
capture noise in the training data as if it were important signal.
This results in poor generalization to new, unseen data (like the test
dataset). The optimal k finds a balance between detail
(not missing important patterns) and generalization (not capturing noise),
providing a dimensionally reduced dataset that preserves best features for
accurate classification.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
NA

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
None.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
 Assignment was great, timing I don't really think so because it bleeds into
 reading week.
