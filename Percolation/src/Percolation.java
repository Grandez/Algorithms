import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Name:        Percolation
 * @author      javier.gonzalez.grandez@gmail.com
 * @version     1.1 
 * @since       2013-02-14  
 */


public class Percolation {

  private WeightedQuickUnionUF grid;   // Grid
      
  private int rows;
  private int open;
  private boolean top    = false;
  private boolean bottom = false;
  
  private int[][] blocks;      // Matriz de bytes
  

  /**
   * Percolation.
   * @param nn size of grid
   */

  public Percolation(int nn) {
    this.rows = nn;

    if (nn <= 0)  {
      throw new IllegalArgumentException("rows should be greater than zero");
    }
      
    blocks = new int[nn][nn]; // Initialize to 0
    // Avoid to calculate nodes * nodes each time

    open = rows * rows;
    grid = new WeightedQuickUnionUF((rows * rows) + 2);

  }

  /**
    * is site (row, col) open.
    */ 
  public boolean isOpen(int rr, int cc)  {
    int[] pos = setRowCol(rr, cc);
    return blocks[pos[0]][pos[1]] == 1;    
  }

  /**
    *  is site (row, col) full.
    * @param rr fila
    * @param cc columna
    * @return rue
    */
  public boolean isFull(int rr, int cc)  {
    if (rr < 1 || cc > rows || cc < 1 || rr > rows) {
      throw new IndexOutOfBoundsException("row and col has to benn beteen 1 and rows");
    }

    int idx = ((rr - 1) * rows) + cc;
    if (isOpen(rr,cc)) {
      return grid.connected(0, idx);
    } else {
      return false;
    }
     
  }
     
  
  /**
   * Abre una celda.
   * @param rr fila 
   * @param cc columna
   */
  public void open(int rr, int cc) {
    if (rr < 1 || cc > rows || cc < 1 || rr > rows) {
      throw new IndexOutOfBoundsException("row and col has to benn beteen 1 and rows");
    }
    if (rr == 1) {
      top = true;
    }
    if (rr == rows) {
      bottom = true;
    }

    int[] pos = setRowCol(rr, cc);
    blocks[pos[0]][pos[1]] = 1; // |= (char) pos[2];
    checkBlock(rr, cc);
    //  print(rr, cc);
    open--;
  }
  
  /**
   * Indica si percola o no.
   * @return true si percola
   */
  public boolean percolates() {
    if (!top && !bottom) {
      return false;
    }
    return grid.connected(0,  (rows * rows) + 1);
  }

  public int numberOfOpenSites() {
    return open;
  }
     
  private void checkBlock(int rr, int cc) {
    for (int i = 0; i < 4; i++) {
      connectBlock(rr, cc, i);
    }
  }
   
  private void connectBlock(int rr, int cc, int mode) {

    int idx = unsetRowCol(rr, cc);
    int nidx = 0;
    boolean unir = false;
           
    switch (mode) {
      case 0: // UP
        if (rr == 1) {
          nidx = 0;
          unir = true;
        } else if (isOpen(rr - 1, cc)) {
          unir = true;
          nidx = unsetRowCol(rr - 1, cc);
        }
        break;
      case 1: // LEFT 
        if (cc == 1) {
          break;
        }
        if (isOpen(rr, cc - 1)) {
          nidx = unsetRowCol(rr, cc - 1);
          unir = true;
        }
        break;
      case 2: // RIGHT 
        if (cc == rows) {
          break;
        }
        if (isOpen(rr, cc + 1)) { 
          nidx = unsetRowCol(rr, cc + 1);
          unir = true;
        }       
        break;
      default: // DOWN
        if (rr == rows) {
          unir = true;
          nidx = (rows * rows) + 1;
        } else if (isOpen(rr + 1, cc)) { 
          nidx = unsetRowCol(rr + 1, cc);
          unir = true;
        }       
        break;
    }
    if (unir) {
      // System.out.println(nidx + "-" + idx);
      if (nidx < idx) {
        grid.union(nidx, idx);
      } else {
        grid.union(idx,  nidx);
      }
    }
  }

  private int[] setRowCol(int rr, int cc) {
    int[] pos = new int [3];  
    pos[0] = --rr;
    pos[1] = --cc;
    pos[2] = 0;
    return pos;
  }

  private int unsetRowCol(int rr, int cc) {
    return ((rr - 1) * rows) + cc;    
  }
  
  
/*
  private void print(int row, int col) {
   
     System.out.println("(" + row + "," + col);
     for (int i = 0; i < rows; i++) {
        for (int j = 0; j < rows; j++) {
        if (isOpen(i+1,j+1)) {
        System.out.print("X");
        }
        else {
        System.out.print(".");
        }
        }
        System.out.println();
     }
   
  }
*/
}
