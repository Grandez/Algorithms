import java.io.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Test {

  public static void main(String[] args) {

      File dir = new File("D:/cursos/Algs1/8puzzle");
      
      File[] files = dir.listFiles(new FilenameFilter() {
          public boolean accept(File dir, String name) {
              return name.toLowerCase().endsWith(".txt");
          }
      });
      
      for (int i = 0; i < files.length; i++) {
        StdOut.println("Procesando " + files[i].getName());
        Test test = new Test();
        test.process(files[i]);
      }

  }
  
  private void process(File file) {
    // create initial board from file
    In in = new In(file);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
            
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}