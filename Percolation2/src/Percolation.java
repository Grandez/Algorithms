/**
 * Name:    Percolation
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2013-02-14  
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final boolean[][] grid;
  private final int size;
  private final WeightedQuickUnionUF uf;
  private final WeightedQuickUnionUF isFull;
  private int openSites;

  /**
   * create size-by-size grid, with all sites blocked.
   * 
   * @param size
   *      size of the grid
   */
  public Percolation(int size) {
    this.size = size;
    grid = new boolean[size + 1][size + 1];
    uf = new WeightedQuickUnionUF(1 + size * size + 1);
    isFull = new WeightedQuickUnionUF(1 + size * size);
    for (int ii = 1; ii <= size; ii++) {
      // Connect top
      union(0, ii);
      // Connect bottom
      uf.union(size * size - ii + 1, size * size + 1);
      openSites = 0;
    }
  }

  public int numberOfOpenSites() {
     return openSites;
  }
  
  private void checkBounds(int ii, int jj) {
    if (ii < 1 || ii > size) {
      throw new IndexOutOfBoundsException("row index ii out of bounds");
    }  
    if (jj < 1 || jj > size) {
      throw new IndexOutOfBoundsException("column index jj out of bounds");
    }
  }

  private int xyTo1D(int ii, int jj) {
    return (ii - 1) * size + jj;
  }

  private void union(int p1, int q2) {
    uf.union(p1, q2);
    isFull.union(p1, q2);
  }

  /**
   * open site (row ii, column jj) if it is not already.
   * 
   * @param ii    *      row
   * @param jj    *      column
   */
  public void open(int ii, int jj) {
    checkBounds(ii, jj);
    if (isOpen(ii, jj)) {
      return;
    }
    grid[ii][jj] = true;
    connectTop(ii, jj);
    connectBottom(ii, jj);
    connectLeft(ii, jj);
    connectRight(ii, jj);
    openSites++;
  }

  private void connectRight(int ii, int jj) {
    if (jj == size) {
      return;
    }

    if (isOpen(ii, jj + 1)) {
      int index = xyTo1D(ii, jj);
      int target = xyTo1D(ii, jj + 1);
      union(index, target);
    }
  }

  private void connectLeft(int ii, int jj) {
    if (jj == 1) {
      return;
    }

    if (isOpen(ii, jj - 1)) {
      int index = xyTo1D(ii, jj);
      int target = xyTo1D(ii, jj - 1);
      union(index, target);
    }
  }

  private void connectBottom(int ii, int jj) {
    if (ii == size) {
      return;
    }
    if (isOpen(ii + 1, jj)) {
      int index = xyTo1D(ii, jj);
      int target = xyTo1D(ii + 1, jj);
      union(index, target);
    }
  }

  private void connectTop(int ii, int jj) {
    if (ii == 1) {
      return;
    }
    if (isOpen(ii - 1, jj)) {
      int index = xyTo1D(ii, jj);
      int target = xyTo1D(ii - 1, jj);
      union(index, target);
    }
  }

  /***
   * is site (row ii, column jj) open?.
   * 
   * @param ii
   *      row
   * @param jj
   *      column
   * @return true is site is open
   */
  public boolean isOpen(int ii, int jj) {
    checkBounds(ii, jj);
    return grid[ii][jj];
  }

  /***
   * is site (row ii, column jj) full?.
   * 
   * @param ii la fila
   * @param jj la columna
   * @return true if site is full
   */
  public boolean isFull(int ii, int jj) {
    if (!isOpen(ii, jj)) {
      return false;
    }
    int site = xyTo1D(ii, jj);
    return isFull.connected(0, site);
  }

  /***
   * does the system percolate?
   * 
   * @return true if the system percolates
   */
  public boolean percolates() {
    if (size == 1) {
      return isOpen(1, 1);
    }
    return uf.connected(0, size * size + 1);
  }
}