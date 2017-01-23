/**
 * Name:    PointSET
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2016-12-31  
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by lu on 4/13/14.
 * a data type for brute force 2D range search
 */
public class PointSET {
  private TreeSet<Point2D> set;

  /**
   * constructs an empty set of points.
   */
  public PointSET() {
    set = new TreeSet<Point2D>();
  }

  /**
   * is the set empty?.
   */
  public boolean isEmpty() {
    return set.isEmpty();
  }

  /**
   * number of points in the set.
   */
  public int size() {
    return set.size();
  }

  /**
   * add the point pp into the set if it is not already in the set.
   * @param pp a 2D point
   */
  public void insert(Point2D pp) {
    set.add(pp);
  }

  /**
   * does the set contain the point pp?.
   * @param pp a 2D point
   */
  public boolean contains(Point2D pp) {
    return set.contains(pp);
  }

  /**
   * draw all the points to standard draw.
   */
  public void draw() {
    Iterator<Point2D> points = set.iterator();
    while (points.hasNext()) {
      points.next().draw();
    }
  }

  /**
   * all points in the set that are inside the rectangle.
   */
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> pointsInRect = new Queue<Point2D>();
    Iterator<Point2D> points = set.iterator();
    while (points.hasNext()) {
      Point2D pp = points.next();
      if (rect.contains(pp)) {
        pointsInRect.enqueue(pp);
      }  
    }
    return pointsInRect;
  }

  /**
   * a nearest neighbour in the set to pp; null if set is empty.
   */
  public Point2D nearest(Point2D pp) {
    if (set.isEmpty()) {
      return null;
    }  
    Point2D currentNearest = null;
    double currentMinDist = Double.POSITIVE_INFINITY;
    Iterator<Point2D> points = set.iterator();
    while (points.hasNext()) {
      Point2D cp = points.next();
      double currentDist = pp.distanceTo(cp);
      if (currentDist < currentMinDist) {
        currentMinDist = currentDist;
        currentNearest = cp;
      }
    }
    return currentNearest;
  }

  /**
   * test.
   */
  public static void main(String[] args) {
    PointSET pSet = new PointSET();
    StdOut.println(pSet.isEmpty());
    pSet.insert(new Point2D(0.4, 0.5));
    StdOut.println(pSet.isEmpty());
    StdOut.println(pSet.size());
    pSet.insert(new Point2D(0.3, 0.8));
    pSet.insert(new Point2D(0.7, 0.1));
    pSet.insert(new Point2D(0.5, 0.3));
    StdOut.println(pSet.size());
    pSet.draw();
    RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.6);
    rect.draw();
    Iterable<Point2D> xx = pSet.range(rect);
    for (Point2D pp: xx) {
      StdOut.println(pp);
    }  
    StdOut.println(pSet.nearest(new Point2D(0.5, 0.5)));
  }
}