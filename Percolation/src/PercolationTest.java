import java.io.*;
import edu.princeton.cs.algs4.*;

public class PercolationTest {

	  String f;
	  PercolationTest(String file) {
		  f = file;
	  }
	  
	  public static void main(String[] args) throws Exception {
		  
		  File dir = new File("D:/cursos/Algs1/percolation");
		  
		  File[] files = dir.listFiles(new FilenameFilter() {
		      public boolean accept(File dir, String name) {
		          return name.toLowerCase().endsWith(".txt");
		      }
		  });
		  
 		  for (int i = 0; i < files.length; i++) {
//			  PercolationTest p = new PercolationTest("D:/cursos/Algs1/percolation/input20.txt");
			  PercolationTest p = new PercolationTest(files[i].getAbsolutePath());
  			  System.out.print("Procesando " + files[i].getName());
			  p.process();
   		  }
	  
	  }
	  
	  
	  private void process() throws Exception {

		  String line;
		  int row = 1;
		  int col= 1;
		  int rows;
		  int count = 0;
		  
		  BufferedReader br = new BufferedReader(new FileReader(f));
  		  line = br.readLine();
		  rows = Integer.parseInt(line);
	      Percolation per = new Percolation(rows);

//	      System.out.println("Open: " + per.isOpen(1,1));
//	      System.out.println("Fulle: " + per.isFull(1, 1));
    		while ((line = br.readLine()) != null) {
    			String[] toks = line.trim().split("[ \\t]+");
    			if (toks.length == 2) {
//    				System.out.println(++count + " - (" + Integer.parseInt(toks[0]) + "," + Integer.parseInt(toks[1]) + ")");
    			   per.open(Integer.parseInt(toks[0]), Integer.parseInt(toks[1]));
//    			      System.out.println("Open: " + per.isOpen(1,1));
//    			      System.out.println("Fulle: " + per.isFull(1, 5));

			    }
    			/*
    			if (count > 229) {
    				System.out.println("Parar " + count);
    			}
    			*/
    		}
			   if (per.percolates()) {
				   System.out.println("\tSI percola");

			   }
			   else {
			     System.out.println("\tNO percola");
			   }
	  }
}
