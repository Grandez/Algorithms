import java.util.ArrayList;
import java.util.Arrays;

/**
 * Name:      BruteCollinearPoints
 * @author    javier.gonzalez.grandez@gmail.com
 * @version    1.1 
 * @since     2016-12-31  
 */

public class BruteCollinearPoints {
  private ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
  private Point[] points;  
  
  /**
  * finds all line segments containing 4 points.
  * @param points array of points
  */
  
  public BruteCollinearPoints(Point[] points)   {
    int idx = 0;
    if (points == null) {
      throw new NullPointerException("Null argument");
    }
    this.points = new Point[points.length];
    for (idx = 0; idx < points.length; idx++) {
      if (points[idx] == null) {
        throw new NullPointerException("Null argument");
      }
      this.points[idx] = points[idx];
    }
    Arrays.sort(this.points);
    for (idx = 0; idx < points.length - 1; idx++) {
      if (this.points[idx].compareTo(this.points[idx + 1]) == 0) {
        throw new IllegalArgumentException("Duplicated point"); 
      }
    }
    getSegments();
  }
  
  /**
  * the number of line segments.
  * @return number of segments
  */
  public        int numberOfSegments()  {
    return lines.size(); 
  }
  
  /**
   * the line segments.
   * @return the segments
   */
  public LineSegment[] segments() {
    return lines.toArray(new LineSegment[lines.size()]);
  }
  
  private void getSegments() {
    
    for (int i1 = 0; i1 < points.length - 3; i1++) {
      for (int i2 = i1 + 1; i2 < points.length - 2; i2++) {
        for (int i3 = i2 + 1; i3 < points.length - 1; i3++) {
          for (int i4 = i3 + 1; i4 < points.length; i4++) {
            if (check(i1, i2, i3, i4)) {
              lines.add(new LineSegment(points[i1], points[i4]));
            }
          }
        }
      }
    }
    

  }
  
  private boolean check(int i1, int i2, int i3, int i4) {
    // int[] adj = new int[4];
    // int[] idx = {i1, i2, i3, i4};
    
    double d1 = points[i1].slopeTo(points[i2]);
    double d2 = points[i1].slopeTo(points[i3]);
    
    if (Double.compare(d1, d2) != 0) {
      return false;
    }
    
    double d3 = points[i1].slopeTo(points[i4]);
    
    if (Double.compare(d1, d3) != 0) {
      return false;
    }
    
    return true;
    /*
    for (int a1 = 0; a1 < 3; a1++) {
      for (int a2 = a1 + 1; a2 < 4; a2++) {
        if (adjacent(points[idx[a1]],points[idx[a2]])) {
          adj[a1] = a2;
          break;
        }
      }
    }
    int trz = 0;
    while (adj[trz] != 0 && adj[trz] != 3) {
      trz = adj[trz];
    }
    return adj[trz] == 3;
    */
  }
/*  
  private boolean adjacent(Point p1, Point p2) {
    if (p1.getX() == p2.getX()) {
      return true;
    }
    if (p1.getY() == p2.getY()) {
      return true;
    }
    return false;
  }
*/  
}