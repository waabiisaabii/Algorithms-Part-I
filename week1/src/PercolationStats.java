/**
 * Algorithms, Assignment 1, Percolation.
 *
 * @author Yachen Lin
 * @date Jan 30, 2018
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * PercolationStats class.
 * @author Yachen Lin
 */
public class PercolationStats {

    private static final double CONSTANT_INTERVAL = 1.96;
    private final double[] thresholds;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    private final int trials;


    /**
     * Perform trials independent experiments on an n-by-n grid.
     *
     * @param n size of grid.
     * @param trials number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Illegal argument.");
        }

        thresholds = new double[trials];
        double numberOfGrid = n * n;
        this.trials = trials;

        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(1 + StdRandom.uniform(n), 1 + StdRandom.uniform(n));
            }
            thresholds[i] = percolation.numberOfOpenSites() / numberOfGrid;
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - CONSTANT_INTERVAL * stddev / Math.sqrt(trials);
        confidenceHi = mean + CONSTANT_INTERVAL * stddev / Math.sqrt(trials);
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return Sample mean of percolation threshold.
     */
    public double mean() {
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        }
        return stddev;
    }

    /**
     * Low endpoint of 95% confidence interval.
     *
     * @return
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * High endpoint of 95% confidence interval.
     *
     * @return
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
     * Test client (described below).
     *
     * @param args
     */
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }
}