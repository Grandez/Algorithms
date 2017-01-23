import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Name:        PercolationStats.
 * @author      javier.gonzalez.grandez@gmail.com
 * @version     1.1 
 * @since       2013-02-14  
 */

public class PercolationStats {
  private double[] means = null;

  private int rows;    
  private double devNormal = 1.96;
  private Stopwatch watch;

  /**
   * Create a new PercolationStats object and launch the experiments.
   * @param rows size of grid
   * @param trials number of iterations
   * @throws IllegalArgumentException Argumentos invalidos
   */

   
  public PercolationStats(int rows, int trials) {
    this.rows = rows;
    this.means = new double[trials];
    for (int i = 0; i < trials; i++) {
      watch = new Stopwatch();
      Percolation per = new Percolation(rows);
      makeProcess(per);
      means[i] = watch.elapsedTime();
    }
  }
   
  /**
   * Obtains mean of set of experiments launched.
   * @return mean of set of experiments.
   */
   
  public double mean() {
    if (means == null) {
      return Double.NaN;
    }
    return StdStats.mean(means);
  }

  /**
  * Obtains standard desviation of set of experiments launched.
  * @return standard desviation
  */

  public double stddev() {
    if (means == null) {
      return Double.NaN;
    }
    return StdStats.stddev(means);  
  }
 
  /**
   * Condindence.
   * @return condidence
   */
  public double confidenceLo() {
    if (means == null) {
      return Double.NaN;
    }
    return (mean() - (stddev() * devNormal));
  }

  /**
   * Confidence Hi.
   * @return confidence
   */
  public double confidenceHi() {
    if (means == null) {
      return Double.NaN;
    }
    return (mean() + (stddev() * devNormal));
  }

  /**
  * Show results on standard output.
  */
    
  private void showResults() {  
    StdOut.printf("mean                    = %f\n", mean());
    StdOut.printf("stddev                  = %f\n", stddev());
    StdOut.print("95% confidence interval = ");
    StdOut.printf("%f", confidenceLo());
    StdOut.print(", ");
    StdOut.printf("%f\n", confidenceHi());
  }

  /**
    * aviso.  
    * @param args N and T
    */
  public static void main(String[] args) {
    int nodes     = 0;
    int iterations = 0;

    if (args.length != 2) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    try {
      nodes      = Integer.parseInt(args[0]);
      iterations = Integer.parseInt(args[1]);
    }  catch (NumberFormatException ex) {
      throw new IllegalArgumentException("Arguments should numeric and positives");
    }

    PercolationStats ps = new PercolationStats(nodes, iterations);
    ps.showResults();
  }
   
  private void makeProcess(Percolation per) {
    int[] rc;
     
    while (!per.percolates()) {
      rc = openBlock(per);
      per.open(rc[0], rc[1]);
    }
  }
   
  private int[] openBlock(Percolation per) {
    int[] rc = new int[2];

    do {
      rc[0] = StdRandom.uniform(rows) + 1;
      rc[1] = StdRandom.uniform(rows) + 1;
    }  while (per.isOpen(rc[0], rc[1]));
    return rc;
  }

}
