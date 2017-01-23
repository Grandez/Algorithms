/**
 * Name:    Point
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2016-12-31  
 */

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:  java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

  private final int x;   // x-coordinate of this point
  private final int y;   // y-coordinate of this point

  private static class BySlope implements Comparator<Point> {
    private Point p0;  

    public BySlope(Point point) {
      p0 = point;
    }

    public int compare(Point a, Point b) {
      double a1 = p0.slopeTo(a);
      double b1 = p0.slopeTo(b);
      return Double.compare(a1, b1); 
    }
  }
  
  /**
   * Initializes a new point.
   *
   * @param  x the <em>x</em>-coordinate of the point
   * @param  y the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point.
   * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
   * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
   * +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical;
   * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
   *
   * @param  that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    if (this.x == that.x && this.y == that.y) {
      return Double.NEGATIVE_INFINITY;
    }
    
    if (this.x == that.x) {
      return Double.POSITIVE_INFINITY;
    }

    if (this.y == that.y) {
      return +0.0;
    }

    return ((double) that.y - (double) this.y) / ((double) that.x - (double) this.x);
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point
   * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
   *
   * @param  that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument
   *     point (x0 = x1 and y0 = y1);
   *     a negative integer if this point is less than the argument
   *     point; and a positive integer if this point is greater than the
   *     argument point
   */
  public int compareTo(Point that) {
    if (this.y < that.y) {
      return -1;
    }
    if (this.y > that.y) {
      return 1;
    }
    if (this.x < that.x) {
      return -1;
    }
    if (this.x > that.x) {
      return 1;
    }
    return 0;
  }

  /**
   * Compares two points by the slope they make with this point.
   * The slope is defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    return new BySlope(this);
  }


  /**
   * Returns a string representation of this point.
   * This method is provide for debugging;
   * your program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

 /**
   * Unit tests the Point data type.
   */
 /* 
  public static void main(String[] args) {
    //    Point[] points = {new Point(0,0), new Point(1,1), new Point(2,2), new Point(0,1)};
    Point[] points = new Point[0];
    
    for (int ii = 0; ii < points.length; ii++) {
      for (int ij = 0; ij < points.length; ij++) {
        comparar(points[ii], points[ij]);
      }
      for (int ik = points.length - 1; ik >= 0; ik--) {
        comparar(points[ii], points[ik]);
      }
    }
  }

  private static void comparar(Point aa, Point bb) {
    StdOut.print(aa.toString());
    switch (aa.compareTo(bb)) {
      case -1: StdOut.print(" es menor que "); 
                break;
      case +1: StdOut.print(" es mayor que "); 
          break;
      default: StdOut.print(" es igual que ");
    }
    StdOut.println(bb.toString());
  }
*/
}