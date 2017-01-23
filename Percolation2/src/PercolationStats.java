/**
 * Name:    PercolationStats
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2013-02-14  
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] tries;
  private int max;

  /**
   * perform max independent computational experiments on an size-by-size grid.
   * 
   * @param size
   *      size x size grid
   * @param max
   *      number of independent computational experiments
   */
  public PercolationStats(int size, int max) {
    if (size <= 0) {
      throw new IllegalArgumentException("size is lower or equal than 0");
    }
    if (max <= 0) {
      throw new IllegalArgumentException("max is lower or equal than 0");
    }
    this.max = max;

    tries = new double[max];
    for (int i = 0; i < max; i++) {
      Percolation percolation = new Percolation(size);
      double threshold = 0;
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(size) + 1;
        int column = StdRandom.uniform(size) + 1;
        if (!percolation.isOpen(row, column)) {
          threshold++;
          percolation.open(row, column);
        }
      }
      tries[i] = threshold / (size * size);
    }
  }

  /**
   * Mean of tries.
   * @return sample mean of percolation threshold
   */
  public double mean() {
    return StdStats.mean(tries);
  }

  /**
   * stddev of tries.
   * @return sample standard deviation of percolation threshold
   */
  public double stddev() {
    return StdStats.stddev(tries);
  }

  /**
   * @return returns lower bound of the 95% confidence interval.
   */
  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(max);
  }

  /**
   * @return upper bound of the 95% confidence interval.
   */
  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(max);
  }

  /**
   * Main program.
   * @param args size
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      return;
    }

    try {
      int size = Integer.parseInt(args[0]);
      int max = Integer.parseInt(args[1]);

      PercolationStats percolationStats = new PercolationStats(size, max);
      StdOut.println("mean          = "
          + percolationStats.mean());
      StdOut.println("stddev          = "
          + percolationStats.stddev());
      StdOut.println("95% confidence interval = "
          + percolationStats.confidenceLo() + ", "
          + percolationStats.confidenceHi());
    } catch (NumberFormatException e) {
      StdOut.println("Argument must be an integer");
      return;
    }
  }
}
