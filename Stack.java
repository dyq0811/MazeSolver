import java.util.EmptyStackException;
/** 
 * An interface for the Stack ADT, adapted from Frank Carrano.
 * @author Jadrian Miles
 * @author Anna Rafferty
 * @author Jed Yang
 */
public interface Stack<T>
{
   /**
    * Adds an item to the top of this stack.
    * @param item The item to add.
    */
   public void push(T item);
   
   /**
    * Removes and returns the item from the top of this stack.
    * @return The item at the top of the stack.
    * @throws EmptyStackException if stack is empty.
    */
   public T pop();
   
   /**
    * Returns the item on top of the stack, without removing it.
    * @return The item at the top of the stack.
    * @throws EmptyStackException if stack is empty.
    */
   public T peek();
   
   /** 
    * Returns whether the stack is empty. 
    * @return True if the stack is empty; false otherwise.
    */
   public boolean isEmpty();
   
   /** 
    * Removes all items from the stack. 
    */
   public void clear();
}
