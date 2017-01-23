/******************************************************************************
 *  Compilation:  javac InteractivePercolationVisualizer.java
 *  Execution:    java InteractivePercolationVisualizer n
 *  Dependencies: PercolationVisualizer.java Percolation.java
 *                StdDraw.java StdOut.java
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PercolationVision {

	Percolation perc = null;
	int n = 10;
	
	public PercolationVision(int rows) {
//    public static void main(String[] args) {
        // n-by-n percolation system (read from command-line, default = 10)
                  
//        if (args.length == 1) n = Integer.parseInt(args[0]);
         n = rows;
        // repeatedly open site specified my mouse click and draw resulting system
        StdOut.println(n);

        StdDraw.enableDoubleBuffering();
        Percolation perc = new Percolation(n);
        PercolationVisualizer.draw(perc, n);
        StdDraw.show();
	}
	
	public void setPercolation(Percolation per) {
		perc = per;
	}
		
	public void open (int i, int j) {

            // detected mouse click

                // draw n-by-n percolation system
                PercolationVisualizer.draw(perc, n);
                StdDraw.show();
    }
}
