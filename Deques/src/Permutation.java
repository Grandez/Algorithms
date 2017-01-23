import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Permutation {

  private RandomizedQueue<String> queue = null;

  /**
   * Main test.
   * @param args A number
   */
  public static void main(String[] args) {

    int max = 0;

    if (args.length != 1) {
      throw new IllegalArgumentException("A number was expected");
    }
    try {
      max = Integer.parseInt(args[0]);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("A number was expected");
    }
    
    Permutation per = new Permutation();
    per.process(max);

  }

  private void process(int max) {
    String line = null;
    queue = new RandomizedQueue<String>();
    try {
      while ((line = StdIn.readString()) != null) {
        // System.out.println(line);
        queue.enqueue(line);
      }
    } catch (NoSuchElementException ex) {
      line = null;
    }
    for (int idx = 0; idx < max; idx++) {
      StdOut.println(queue.dequeue());
    }
  }
/*  
  private void process(int max) {
    
      File dir = new File("D:/cursos/Algs1/queues");
      
      File[] files = dir.listFiles(new FilenameFilter() {
          public boolean accept(File dir, String name) {
              return name.toLowerCase().endsWith(".txt");
          }
      });
      
      for (int i = 0; i < files.length; i++) {
        System.out.println("Procesando " + files[i].getName());
              process2(files[i], max);
      }
  }
  
  private void process2(File file, int max) {
    String line = null;
    queue = new RandomizedQueue<String>();
    try {
       In in = new In(file)  ;
           while ((line = in.readString()) != null) {
             System.out.println(line);
             queue.enqueue(line);
           }
    }
    catch (NoSuchElementException ex) {
      
    }
    for (int idx = 0; idx < max; idx++) {
      StdOut.println(queue.dequeue());
    }
  }
*/
}
