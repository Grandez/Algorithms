import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Name:        RandomizeQueue
 * @author      javier.gonzalez.grandez@gmail.com
 * @version     1.1 
 * @since       2016-12-31  
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
   
  private Item[] nodes = null;
  private int size = -1;
  private int items = 0;

  private class ListIterator implements Iterator<Item> {
    private int pos = -1;
    private int base = -1;
    private int inc = 0;
    
    /**
     * Mira si hay un siguiente..
     * @return true
     */
    public boolean hasNext() {
      if (size == -1) {
        return false;
      }
      if (pos == -1) {
        String aux = this.toString();
        int at = aux.indexOf("@");
        long offset = Long.parseLong(aux.substring(at + 1), 16);
        StdRandom.setSeed(System.currentTimeMillis() + offset);
        base = StdRandom.uniform(size);
        inc = ((StdRandom.uniform(17) % 2) == 0) ? 1 : -1;  
        pos = nextIdx(base, inc);
      } else if (pos == base) {
        pos = -2;
        return false;
      } else {
        pos = nextIdx(pos, inc);
      }

      while (pos != base) {
        if (nodes[pos] != null) {
          return true;
        }
        pos = nextIdx(pos, inc);
      }
      return (nodes[pos] != null) ? true : false;
    }
    
    private int nextIdx(int act, int offset) {
      int aux = act + offset;
      if (aux < 0) {
        return size - 1;
      }
      if (aux == size) {
        return 0;
      }
      return aux;
    }
    
    /**
    * Return next item. 
    * @return Item
    */
    public Item next() {
      if (pos < 0) {
        throw new NoSuchElementException("No such element");
      }
      return nodes[pos];
    }
  }
   
  /**
    * construct an empty randomized queue.
    */
  public RandomizedQueue() {
    resizeNodes(10);
  }
   
  /**
    * is the queue empty.
    * @return true
    */
  public boolean isEmpty() {
    return items == 0; 
  }
   
  /**
    * return the number of items on the queue.
    * @return items
    */
  public int size()  {
    return items; 
  }
   
  /**
    * add the item.
    * @param item item de la cola
    */
  public void enqueue(Item item)  {
    if (item == null) {
      throw new NullPointerException("Item must not be null");
    }

    if (items == size) {
      resizeNodes(size * 2);
    }
    int idx = getItem(false);
    nodes[idx] = item;
    items++;
  }
   
  /**
    * remove and return a random item.
    * @return an item
    */
  public Item dequeue() {
    Item tmp = null;  
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }

    int idx = getItem(true);
    tmp = nodes[idx];
    nodes[idx] = null;
    items--;
    if (items > 0 && (size / items) >= 4) {
      resizeNodes(size / 2); 
    }
    return tmp;
  }
   
  /**
    * return (but do not remove) a random item.
    * @return an item without removal
    */
  public Item sample() {
    int idx = getItem(true);
    return nodes[idx];
  }
   
  @Override
  public Iterator<Item> iterator() { // return an iterator over items in order from front to end
    return new ListIterator();
  }


  private int getItem(boolean data) {
    if (items == 0 && data) {
      throw new NoSuchElementException("Queue is empty");
    }
    int pos = StdRandom.uniform(size);
    int inc = (StdRandom.uniform(2) == 0) ? 1 : -1;
    
    while (true) {
      if (!data && nodes[pos] == null)  {
        return pos;
      }
      if (data  && nodes[pos] != null) {
        return pos;
      }
      pos += inc;
      if (pos < 0) {
        pos = size - 1;
      }
      if (pos >= size) {
        pos = 0;
      }  
    }
  }

  private void resizeNodes(int newSize) {
    if (newSize < 3) {
      return;
    }
    int pos = 0;
    size = newSize;

    Item[] aux = (Item[]) new Object[newSize];
    if (nodes != null) {
      for (int idx = 0; idx < nodes.length; idx++) {
        if (nodes[idx] != null) {
          aux[pos++] = nodes[idx];
        }
      }
    }   
    nodes = aux;
  }
     
  /**
   * Main para test. 
   * @param args No se usan
   */
  public static void main(String[] args)   {
    String[] test = {"-+A+--", "ABCDE", "abc---", "A-B-C-", "-", "ABC---"
    };

    for (int idx = 0; idx < test.length; idx++) {
      RandomizedQueue<Character> queue = new RandomizedQueue<Character>();
      for (int pos = 0; pos < test[idx].length(); pos++) {
        try {
          char cc = test[idx].charAt(pos);
          if (cc == '-') {
            StdOut.print(queue.dequeue());
          } else if (cc == '+') {
            StdOut.print(queue.sample());
          } else {
            queue.enqueue(cc);
          }
        } catch (NoSuchElementException ex) {
          StdOut.print("Vacio");
        }         
      }    
      StdOut.println();
      for (Character c : queue) {
        StdOut.print(c + " ");
      }
      StdOut.println();
    }    
  }

}