/**
 * Name:    KdTree
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2016-12-31  
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
  private Node root;    // root of KdTree
  private int size;      // size of KdTree
  private int num;    // number of nodes visited before finding nearest

  private static class Node {
    private Point2D p2d;    // the point
    private RectHV rect;  // the axis-aligned rectangle corresponding to this node
    private Node lb;    // the left/bottom subtree
    private Node rt;    // the right/top subtree
  }

  /**
   * is the tree empty?.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * number of nodes in the tree.
   */
  public int size() {
    return size;
  }

  /**
   * insert point p2d into the KdTree if it is not already there.
   * @param p2d a 2D point
   */
  public void insert(Point2D p2d) {
    if (size == 0) {
      root = new Node();
      root.p2d = p2d;
      root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      size++;
    }
    insert(root, p2d, true);
  }

  // helper for insert
  private void insert(Node xx, Point2D p2d, boolean isVertical) {
    if (xx.p2d.equals(p2d)) {
      return;
    }  
    double cmp = isVertical ? p2d.x() - xx.p2d.x() : p2d.y() - xx.p2d.y();
    RectHV rr = xx.rect;
    if (cmp < 0) {
      if (xx.lb == null) {
        Node nn = new Node();
        nn.p2d = p2d;
        if (isVertical) {
          nn.rect = new RectHV(rr.xmin(), rr.ymin(), xx.p2d.x(), rr.ymax());
        } else {
          nn.rect = new RectHV(rr.xmin(), rr.ymin(), rr.xmax(), xx.p2d.y());
        }
        xx.lb = nn;
        size++;
      } else {
        insert(xx.lb, p2d, !isVertical);
      }
    } else {
      if (xx.rt == null) {
        Node nn = new Node();
        nn.p2d = p2d;
        if (isVertical) {
          nn.rect = new RectHV(xx.p2d.x(), rr.ymin(), rr.xmax(), rr.ymax());
        } else {
          nn.rect = new RectHV(rr.xmin(), xx.p2d.y(), rr.xmax(), rr.ymax());
        }
        xx.rt = nn;
        size++;
      } else {
        insert(xx.rt, p2d, !isVertical);
      }
    }
  }

  /**
   * does the KdTree contain point p2d?.
   * @param p2d a 2D point
   */
  public boolean contains(Point2D p2d) {
    return contains(root, p2d, true);
  }

  // helper for contains
  private boolean contains(Node xx, Point2D p2d, boolean isVertical) {
    if (xx == null) {
      return false;
    }  
    if (xx.p2d.equals(p2d)) {
      return true;
    }  
    double cmp = isVertical ? p2d.x() - xx.p2d.x() : p2d.y() - xx.p2d.y();
    if (cmp < 0) {
      return contains(xx.lb, p2d, !isVertical);
    } else {
      return contains(xx.rt, p2d, !isVertical);
    }  
  }

  /**
   * draw all the points to standard draw.
   */
  public void draw() {
    draw(root, true);
  }

  // helper for draw
  private void draw(Node nn, boolean isVertical) {
    if (nn == null) {
      return;
    }  
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    StdDraw.point(nn.p2d.x(), nn.p2d.y());
    StdDraw.setPenRadius();
    if (isVertical) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(nn.p2d.x(), nn.rect.ymin(), nn.p2d.x(), nn.rect.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(nn.rect.xmin(), nn.p2d.y(), nn.rect.xmax(), nn.p2d.y());
    }
    draw(nn.lb, !isVertical);
    draw(nn.rt, !isVertical);
  }

  /**
   * all points in the set that are inside the rectangle.
   */
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> pointsInRect = new Queue<Point2D>();
    range(root, rect, pointsInRect);
    return pointsInRect;
  }

  // helper for range
  private void range(Node nn, RectHV rr, Queue<Point2D> qq) {
    if (nn == null || !nn.rect.intersects(rr)) {
      return;
    }   
    if (rr.contains(nn.p2d)) {
      qq.enqueue(nn.p2d);
    }  
    range(nn.lb, rr, qq);
    range(nn.rt, rr, qq);
  }

  /**
   * a nearest neighbour in the set to p2d; null if set is empty.
   */
  public Point2D nearest(Point2D p2d) {
    if (size == 0) {
      return null;
    }  
    return nearest(root, p2d, root.p2d, true);
  }

  // helper for nearest
  private Point2D nearest(Node nn, Point2D p2d, Point2D currentNear, boolean isVertical) {
    double currentMinDist = currentNear.distanceSquaredTo(p2d);
    if (nn == null || currentMinDist <= nn.rect.distanceSquaredTo(p2d)) {
      return currentNear;
    }  
    if (currentMinDist > nn.p2d.distanceSquaredTo(p2d)) {
      currentNear = nn.p2d;
    }   
    num++;
    double cmp = isVertical ? p2d.x() - nn.p2d.x() : p2d.y() - nn.p2d.y();
    if (cmp < 0) {
      Point2D lbNear = nearest(nn.lb, p2d, currentNear, !isVertical);
      if (nn.rt == null || lbNear.distanceSquaredTo(p2d) <= nn.rt.rect.distanceSquaredTo(p2d)) {
        return lbNear;
      } else {
        Point2D rtNear = nearest(nn.rt, p2d, currentNear, !isVertical);
        return lbNear.distanceSquaredTo(p2d) <= rtNear.distanceSquaredTo(p2d) ? lbNear : rtNear;
      }
    } else {
      Point2D rtNear = nearest(nn.rt, p2d, currentNear, !isVertical);
      if (nn.lb == null || rtNear.distanceSquaredTo(p2d) <= nn.lb.rect.distanceSquaredTo(p2d)) {
        return rtNear;
      } else {
        Point2D lbNear = nearest(nn.lb, p2d, currentNear, !isVertical);
        return lbNear.distanceSquaredTo(p2d) <= rtNear.distanceSquaredTo(p2d) ? lbNear : rtNear;
      }
    }
  }

  /**
   * test.
   */
  public static void main(String[] args) {
    String filename = args[0];
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double xx = in.readDouble();
      double yy = in.readDouble();
      Point2D p2d = new Point2D(xx, yy);
      kdtree.insert(p2d);
    }
    kdtree.draw();
    StdOut.println(kdtree.nearest(new Point2D(.81, .30)));
    StdOut.println(kdtree.num);
  }
}