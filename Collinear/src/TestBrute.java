import java.io.*;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class TestBrute {

	public static void main(String[] args) {
        TestBrute brute = new TestBrute();
        brute.process();
	}

	private void process() {
	    
	      File dir = new File("D:/cursos/Algs1/collinear");
	      
	      File[] files = dir.listFiles(new FilenameFilter() {
	          public boolean accept(File dir, String name) {
	        	  if (name.toLowerCase().startsWith("input")) {
	                 return name.toLowerCase().endsWith(".txt");
	        	  }
	        	  else {
	        		  return false;
	        	  }
	          }
	      });
	      
	      for (int i = 0; i < files.length; i++) {
	        System.out.println("Procesando " + files[i].getName());
	              process2(files[i]);
	      }
	  }
	  
	  private void process2(File file) {
		int count = 0;
		Integer x;
		Integer y;
		Point[] points = null;  

	    try {
//	       In in = new In(file)  ;
     	In in = new In("D:/cursos/Algs1/collinear/input80.txt");
	       int max = in.readInt();
	       points = new Point[max];
           while ((x = in.readInt()) != null) {
        	   y = in.readInt();
        	   points[count++] = new Point(x,y);
           }
           in.close();
	    }
	    catch (NoSuchElementException ex) {
	      
	    }
	    
	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    StdDraw.clear();
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    //BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();	  

  }	  
}
