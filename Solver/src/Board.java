/**
 * Name:    Board
 * @author    javier.gonzalez.grandez@gmail.com
 * @version   1.1 
 * @since     2016-12-31  
 */

import edu.princeton.cs.algs4.Stack;

public class Board {
  private final int size;
  private final int[][] tiles;

  /**
   * construct a board from an size-by-size array of blocks.
   * block in row i, column j)
   */
  public Board(int[][] blocks) {
    size = blocks.length;
    tiles = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        tiles[i][j] = blocks[i][j];
      }
    }
  }

  private int[][] createGoalBoard() {
    int[][] array = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        array[i][j] = goalValueAt(i, j);
      }
    }

    return array;
  }

  /*
   * board dimension size
   */
  public int dimension() {
    return size;
  }

  /**
   * number of blocks out of place.
   */
  public int hamming() {
    int sum = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (tiles[i][j] != goalValueAt(i, j) && !isEnd(i, j)) {
          sum++;
        }
      }
    }
    return sum;
  }

  private int goalValueAt(int i, int j) {
    if (isEnd(i, j)) {
      return 0;
    }
    return 1 + i * size + j;
  }

  private boolean isEnd(int i, int j) {
    return i == size - 1 && j == size - 1;
  }

  /**
   * sum of Manhattan distances between blocks and goal.
   */
  public int manhattan() {
    int sum = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int value = tiles[i][j];
        if (value != 0 && value != goalValueAt(i, j)) {
          int initialX = (value - 1) / size;
          int initialY = value - 1 - initialX * size;
          int distance = Math.abs(i - initialX)
              + Math.abs(j - initialY);
          sum += distance;
        }
      }
    }
    return sum;
  }

  /*
   * is this board the goal board?
   */
  public boolean isGoal() {
    return tilesEquals(this.tiles, createGoalBoard());
  }

  private boolean tilesEquals(int[][] first, int[][] second) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (first[i][j] != second[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  /*
   * a board obtained by exchanging two adjacent blocks in the same row
   */
  public Board twin() {
    Board board = new Board(tiles);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size - 1; j++) {
        if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
          board.swap(i, j, i, j + 1);
          return board;
        }
      }
    }

    return board;
  }

  private boolean swap(int i, int j, int it, int jt) {
    if (it < 0 || it >= size || jt < 0 || jt >= size) {
      return false;
    }
    int temp = tiles[i][j];
    tiles[i][j] = tiles[it][jt];
    tiles[it][jt] = temp;
    return true;
  }

  /*
   * does this board equal x? (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object x) {
    if (x == this)
      return true;
    if (x == null)
      return false;
    if (x.getClass() != this.getClass())
      return false;

    Board that = (Board) x;
    return this.size == that.size && tilesEquals(this.tiles, that.tiles);
  }

  /*
   * all neighboring boards
   */
  public Iterable<Board> neighbors() {
    int i0 = 0, j0 = 0;
    boolean found = false;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (tiles[i][j] == 0) {
          i0 = i;
          j0 = j;
          found = true;
          break;
        }
      }
      if (found) {
        break;
      }
    }

    Stack<Board> boards = new Stack<Board>();
    Board board = new Board(tiles);
    boolean isNeighbor = board.swap(i0, j0, i0 - 1, j0);
    if (isNeighbor) {
      boards.push(board);
    }
    board = new Board(tiles);
    isNeighbor = board.swap(i0, j0, i0, j0 - 1);
    if (isNeighbor) {
      boards.push(board);
    }
    board = new Board(tiles);
    isNeighbor = board.swap(i0, j0, i0 + 1, j0);
    if (isNeighbor) {
      boards.push(board);
    }
    board = new Board(tiles);
    isNeighbor = board.swap(i0, j0, i0, j0 + 1);
    if (isNeighbor) {
      boards.push(board);
    }

    return boards;
  }

  /**
   * string representation of the board (non-Javadoc).
   * 
   * @see java.lang.Object#toString()
   */
   
  public String toString() {
    StringBuilder ss = new StringBuilder();
    ss.append(size + "\n");
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        ss.append(String.format("%2d ", tiles[i][j]));
      }
      ss.append("\n");
    }
    return ss.toString();
  }
}