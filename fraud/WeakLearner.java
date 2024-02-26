import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class WeakLearner {

    // Dimension predictor for the model
    private int dimensionPredictor;

    // Value predictor for the model
    private double valuePredictor;

    // Sign predictor for the model
    private int signPredictor;

    // Number of features in the input data
    private int numFeatures;

    // train the weak learner
    public WeakLearner(double[][] input, double[] weights, int[] labels) {
        validateInput(input, weights, labels);
        this.numFeatures = input[0].length; // Potential NullPointerException
        // if input is empty
        train(input, weights, labels);
    }

    // validate input
    private void validateInput(double[][] input, double[] weights, int[] labels) {
        if (input == null || weights == null || labels == null)
            throw new IllegalArgumentException("One or more arguments are null");
        if (input.length == 0)
            throw new IllegalArgumentException("Input array is empty");
        if (weights.length == 0)
            throw new IllegalArgumentException("Weights array is empty");
        if (labels.length == 0)
            throw new IllegalArgumentException("Labels array is empty");
        if (input.length != weights.length || input.length != labels.length)
            throw new IllegalArgumentException("Array lengths are not equal");
        for (double[] row : input) {
            if (row == null)
                throw new IllegalArgumentException("Input contains "
                                                           + "null sub-array");
            if (row.length != input[0].length)
                throw new IllegalArgumentException("Inconsistent "
                                                           + "sub-array lengths "
                                                           + "in input");
        }
        for (double weight : weights) {
            if (weight < 0)
                throw new IllegalArgumentException("Weights contain a "
                                                           + "negative value");
        }
        for (int label : labels) {
            if (label != 0 && label != 1)
                throw new IllegalArgumentException("Labels are not binary");
        }
    }


    // train
    private void train(double[][] input, double[] weights, int[] labels) {
        int n = input.length;
        double maxWeightedAccuracy = -1;

        for (int dimension = 0; dimension < numFeatures; dimension++) {
            ArrayList<ThresholdAccumulator> accumulators = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                accumulators.add(
                        new ThresholdAccumulator(input[i][dimension],
                                                 weights[i], labels[i]));
            }

            Collections.sort(accumulators);

            double weightedTruePositives = 0;
            double weightedFalsePositives = 0;

            for (ThresholdAccumulator acc : accumulators) {
                if (acc.label == 1) {
                    weightedTruePositives += acc.weight;
                }
                else {
                    weightedFalsePositives += acc.weight;
                }
            }

            double bestThreshold = Double.NEGATIVE_INFINITY;
            double bestAccuracy = 0;
            int bestSign = 1;
            double truePositives = 0;
            double falsePositives = 0;

            for (int i = 0; i < accumulators.size(); i++) {
                ThresholdAccumulator acc = accumulators.get(i);

                if (acc.label == 1) {
                    truePositives += acc.weight;
                }
                else {
                    falsePositives += acc.weight;
                }

                double accuracy = Math.max(truePositives, falsePositives);

                if (accuracy > bestAccuracy) {
                    bestAccuracy = accuracy;
                    bestThreshold = acc.threshold;
                    if (truePositives > falsePositives) {
                        bestSign = 1;
                    }
                    else {
                        bestSign = 0;
                    }
                }

                if (i < accumulators.size() - 1 && acc.threshold !=
                        accumulators.get(
                                i + 1).threshold) {
                    double nextThreshold =
                            (acc.threshold + accumulators.get(i + 1).threshold)
                                    / 2;
                    double max = Math.max(
                            weightedTruePositives - truePositives +
                                    falsePositives,
                            truePositives + weightedFalsePositives -
                                    falsePositives);

                    if (max > bestAccuracy) {
                        bestAccuracy = max;
                        bestThreshold = nextThreshold;

                        if (weightedTruePositives - truePositives
                                + falsePositives
                                > truePositives + weightedFalsePositives -
                                falsePositives) {
                            bestSign = 0;
                        }
                        else {
                            bestSign = 1;
                        }
                    }

                }
            }

            if (bestAccuracy > maxWeightedAccuracy) {
                maxWeightedAccuracy = bestAccuracy;
                this.dimensionPredictor = dimension;
                this.valuePredictor = bestThreshold;
                this.signPredictor = bestSign;
            }
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(double[] sample) {
        if (sample == null || sample.length != numFeatures) {
            throw new IllegalArgumentException(
                    "Sample array is null or length does not match number of "
                            + "features");
        }
        int prediction;
        if (sample[dimensionPredictor] <= valuePredictor) {
            prediction = 1;
        }
        else {
            prediction = 0;
        }
        if (signPredictor == 1) {
            return prediction;
        }
        else {
            return 1 - prediction;
        }
    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dimensionPredictor;
    }

    // return the value the learner uses to separate the data
    public double valuePredictor() {
        return valuePredictor;
    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return signPredictor;
    }

    // threshold accumulator
    private static class ThresholdAccumulator implements
                                              Comparable<ThresholdAccumulator> {
        // threshold
        double threshold;

        // weight
        double weight;

        // label
        int label;

        // threshold accumulator constructor
        ThresholdAccumulator(double threshold, double weight, int label) {
            this.threshold = threshold;
            this.weight = weight;
            this.label = label;
        }

        // compareTo for ThresholdAccumulator
        public int compareTo(ThresholdAccumulator other) {
            return Double.compare(this.threshold, other.threshold);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Test Data Preparation
        double[][] input = {
                { 1.0, 2.0 },
                { 1.5, 2.5 },
                { 2.0, 3.0 }
        };
        double[] weights = { 1.0, 1.0, 1.0 };
        int[] labels = { 1, 0, 1 };

        // Initialize WeakLearner
        WeakLearner learner = new WeakLearner(input, weights, labels);

        // Test dimensionPredictor, valuePredictor, signPredictor
        int dimension = learner.dimensionPredictor();
        double value = learner.valuePredictor();
        int sign = learner.signPredictor();

        assert dimension >= 0 && dimension < input[0].length :
                "Dimension predictor out of range";
        assert value >= 0 : "Value predictor is negative";
        assert (sign == 0 || sign == 1) : "Sign predictor is not binary";

        // Test predict method
        for (double[] sample : input) {
            int prediction = learner.predict(sample);
            assert prediction == 0 || prediction == 1 : "Prediction not binary";
        }

        // Test with different data to ensure dynamic behavior
        double[][] newInput = {
                { 2.0, 1.0 },
                { 2.5, 1.5 },
                { 3.0, 2.0 }
        };
        int[] newLabels = { 0, 1, 0 };

        WeakLearner newLearner = new WeakLearner(newInput, weights, newLabels);
        assert newLearner.dimensionPredictor() != dimension ||
                newLearner.valuePredictor() != value
                : "New learner has same predictors as old learner on "
                + "different data";

        StdOut.println("All tests passed successfully.");
    }
}
