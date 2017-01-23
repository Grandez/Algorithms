/**
 * Name:       FastCollinearPoints
 * @author     javier.gonzalez.grandez@gmail.com
 * @version    1.1 
 * @since      2016-12-31  
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
  private ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
  private Point[] points; 
  
  /**
   * finds all line segments containing 4 points.
   * @param points array of points
   */
  public FastCollinearPoints(Point[] points)    {
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
  public int numberOfSegments()  {
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
    Segment base = null;

    ArrayList<Segment> l0 = null;    
    ArrayList<Segment> l2 = new ArrayList<Segment>();    

    for (int i1 = 0; i1 < points.length - 3; i1++) {
      l0 = new ArrayList<Segment>();

      for (int i2 = i1 + 1; i2 < points.length; i2++) {
        l0.add(new Segment(points[i1], points[i2]));
      }
      Collections.sort(l0);
      base = null;
      for (Segment s : l0) {
        if (base == null) {
          base = s;
        } else {
          if (Double.compare(base.slope, s.slope) == 0) {
            base.addSegment(s);
          } else {
            if (base.numPoints() > 3) {
              l2.add(base);
            }
            base = s;
          }   
        }
      }
      if (base.numPoints() > 3) {
        l2.add(base);
      }
       
    }
    Collections.sort(l2);
    /*
    System.out.println("Hay segmentos : " + l2.size());
    for (Segment s : l2) {
       System.out.println(s.toString());
    }
    */
    if (l2.isEmpty()) {
      return;
    }
    
    // Aqui mirar duplicados
    base = l2.get(0);
    l0 = new ArrayList<Segment>();
    for (Segment s2 : l2) {
      if (Double.compare(base.slope, s2.slope) == 0) {
        l0.add(s2);
      } else {
        removeDuplicates(l0);
        base = s2;
        l0 = new ArrayList<Segment>();
        l0.add(base);
      }       
    }
    removeDuplicates(l0);
    
  } 

  private void removeDuplicates(ArrayList<Segment> l0) {
    for (int i1 = 0; i1 < l0.size() - 1; i1++) {
      for (int i2 = i1 + 1; i2 < l0.size(); i2++) {
        Segment s1 = l0.get(i1);
        Segment s2 = l0.get(i2);
        if (s1.isValid() && s2.isValid() && inline(s1, s2)) {
          if (s2.numPoints() > s1.numPoints()) {
            s1.valid(false);
          } else {
            s2.valid(false);
          }    
        }
      }     
    }
    for (Segment s1 : l0) {
      if (s1.isValid()) {
        lines.add(new LineSegment(s1.getFirst(), s1.getLast()));
      }
    }
  }
  
  private boolean inline(Segment s1, Segment s2) {
    for (Point p1 : s1.getPoints()) {
      if (s2.contains(p1)) {
        return true;
      }
    }
    return false;
  }
   
  private class Segment implements Comparable<Segment> {
    private ArrayList<Point> points = new ArrayList<Point>();
    private double slope;
    private boolean valid = true;
    
    public Segment(Point p1, Point p2) {
      points.add(p1);
      points.add(p2);
      this.slope = p1.slopeTo(p2);
    }
    
    public double getSlope() {
      return slope;
    }
    
    public Point getFirst() {
      return points.get(0);
    }
    
    public Point getLast() {
      return points.get(points.size() - 1);
    }
    
    public void addSegment(Segment s1) {
      for (Point p1 : s1.getPoints()) {
        if (!this.contains(p1)) {
          points.add(p1);
        }
      }
      Collections.sort(points);
    }
    
    public ArrayList<Point> getPoints() {
      return points;
    }

    public boolean contains(Point that) {
      for (Point p1 : points) {
        if (p1.compareTo(that) == 0) {
          return true;
        }
      }
      return false;
    }
    
    public int numPoints() {
      return points.size();
    }
    
    public void valid(boolean vv) {
      valid = vv;
    }
    
    public boolean isValid() {
      return valid;
    }
     
    public String toString() {
      StringBuilder aux = new StringBuilder();
      aux.append(points.size());
      aux.append(": "); 

      for (Point point : points) {
        aux.append(point.toString());
        aux.append(" -> ");
      }
      aux.append(slope);
      return aux.toString();
    }
    
    public int compareTo(Segment ss) {
      if (this.slope < ss.getSlope()) {
        return -1;
      }
      if (Double.compare(this.slope, ss.getSlope()) > 0) {
        return  1;
      }
      return 0;
    }
  }
}