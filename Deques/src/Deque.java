import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Name:        Deque
 * @author      javier.gonzalez.grandez@gmail.com
 * @version     1.1 
 * @since       2016-12-31  
 */

public class Deque<Item> implements Iterable<Item> {
  
  private Node first = null;
  private Node last = null;
  private int size  = 0;
  
  private class Node {
    private Item item;
    private Node next;
    private Node previous;
  }
  
  private class ListIterator implements Iterator<Item> {
    private Node current = first;
    private int  pos = -1;
    
    public boolean hasNext() {
      pos++;
      return current != null; 
    }
    
    public void remove() { 
      throw new UnsupportedOperationException("Operation remove not supoorted");
    }
    
    /**
     * Return next item. 
     * @return Item
     */
    public Item next() {
      Node nn = current;
      if (pos == -1 || current == null) {
        throw new NoSuchElementException("No such element");
      }
      current = current.next;
      return nn.item;
    }
  }
  
  public Deque()  {
  }

  public boolean isEmpty() { // is the deque empty?
    return first == null;  
  }
   
  public int size() { // return the number of items on the deque
    return size;
  }
  
  /**
   * add the item to the front.
   * @param item Item to add
   */
  public void addFirst(Item item) {
    Node node = createNode(item);
    if (first == null) {
      first = node;
      last = node;
    } else {
      first.previous = node;
      node.next = first;
      first = node;
    }
  }
  
  /**
   * add the item to the end.
   * @param item Item to add
   */
  public void addLast(Item item) {  
    Node node = createNode(item);
    if (last == null) {
      first = node;
      last = node;
    } else {
      node.previous = last;
      last.next = node;
      last = node;
    }
  }
   
  /**
    * remove and return the item from the front.
    * @return Item
    */
  public Item removeFirst()  { 
    if (first == null) {
      throw new NoSuchElementException("Queue is empty");
    }
    size--;
    Node node = first;
    first = first.next;
    if (first == null) {
      last = null;
    } else {
      first.previous = null;
    }   
    return node.item;
  }

  /**
   * Quita el ultimo.
   * @return Item
   */
  public Item removeLast() {  // remove and return the item from the end
    if (last == null) {
      throw new NoSuchElementException("Queue is empty");
    }
    size--;
    Node node = last;
    last = last.previous;
    if (last == null) {
      first = null;
    } else {
      last.next = null;
    }
    return node.item;
  }

  @Override
  public Iterator<Item> iterator() { // return an iterator over items in order from front to end
    return new ListIterator();
  }
      
  private Node createNode(Item item) {
    if (item == null) {
      throw new NullPointerException("Item must not be null");
    }
    size++;
    Node node = new Node();
    node.next = null;
    node.previous = null;
    node.item = item;
    return node;
  }
  
  /**
   * Main para test.
   * @param args No se usa
   */
  public static void main(String[] args) {
    String[] test = {"abc---", "A-B-C-", "-", "ABC---"
    };
    Deque<Character> deque = new Deque<Character>();
    for (int idx = 0; idx < test.length; idx++) {
      try {
        for (int pos = 0; pos < test[idx].length(); pos++) {
          char cc = test[idx].charAt(pos);
          if (cc == '-') {
            StdOut.print(deque.removeLast());
          } else if (cc == '+') {
            StdOut.print(deque.removeFirst());
          } else if (Character.isLowerCase(cc)) {
            deque.addFirst(cc);
          } else {
            deque.addLast(cc);
          }
        }    
      } catch (NoSuchElementException ex) {
        StdOut.print("Vacio");
      }
      StdOut.println();
    }
     
  }
}      