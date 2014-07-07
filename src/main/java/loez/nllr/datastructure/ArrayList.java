package loez.nllr.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of an ArrayList.
 * @author ljleppan
 * @param <T>   Class of input values.
 */
public class ArrayList<T>  implements Iterable<T>{
    /**
     * Default size of the backing array.
     */
    private final static int DEFAULT_SIZE = 8;
    
    /**
     * Minimum size of the backing array.
     */
    private final static int MIN_SIZE = 8;
    
    /**
     * Threshold for determining whether or not to scale the backing array.
     * Scaling down happens when at (1 - THRESHOLD) usage.
     * Scaling up happens when at (THRESHOLD) usage.
     */
    private final static double SCALING_TRESHOLD = 0.75;
    
    /**
     * Determines how much to scale the backing array.
     */
    private final static int SCALING_CONSTANT = 2;
    
    private int limit = DEFAULT_SIZE;
    private int size = 0;
    private int modCount = 0;
    
    private Object[] array = new Object[DEFAULT_SIZE];
    
    /**
     * Adds a value to the ArrayList.
     * @param value The value.
     */
    public void add(T value) {
        array[size] = value;
        size++;
        modCount++;
        checkCapacity();
    }
    
    /**
     * Returns a value from the specified index.
     * @param index A valid index.
     * @return      The value that was found in said index.
     */
    public T get(int index){
        if (validIndex(index)){
            return (T) array[index];
        }
        throw new IndexOutOfBoundsException();
    }
    
    /**
     * Returns the size of the (accessible) ArrayList.
     * @return  The size.
     */
    public int size(){
        return this.size;
    }
    
    /**
     * Checks whether or not a value is present in the ArrayList.
     * @param value Value to look for.
     * @return      True if value was found, False otherwise.
     */
    public boolean contains(T value){
        return indexOf(value) != -1;
    }
    
    /**
     * Returns the index of the first matching item that was found in the ArrayList.
     * @param value Value to search for.
     * @return      Index of first matching element or -1 if no matching element was found.
     */
    public int indexOf(T value){
        for (int i = 0; i < size; i++) {
            if ((value == null && array[i] == null) || (value != null && value.equals(array[i]))){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * @return True if the ArrayList is empty, False otherwise.
     */
    public boolean isEmpty(){
        return size == 0;
    }
    
    /**
     * Removes all items from the ArrayList and resets the backing array's size.
     */
    public void clear(){
        array = new Object[DEFAULT_SIZE];
        limit = DEFAULT_SIZE;
        modCount++;
        size = 0;
    }
    
    /**
     * Removes a value from the specified index. 
     * The ArrayList is condensed after removal of value.
     * @param index Index of the value.
     */
    public void remove(int index){
        if (!validIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        
        while(validIndex(index)){
            array[index] = array[index+1];
            index++;
        }
        size--;
        modCount++;
        checkCapacity();
    }
    
    /**
     * Remove the first instance of the provided value from the ArrayList.
     * The ArrayList is condensed after removal.
     * @param value Value to remove.
     */
    public void remove(T value){
        int index = indexOf(value);
        if (index != -1){
            remove(index);
        }
    }
    
    /**
     * Sets the value at an index
     * @param index Index
     * @param value New value
     */
    public void set(int index, T value){
        if (index <= size && index >= 0){
            array[index] = value;
        }
    }
    
    /**
     * Checks whether an index is a valid for use.
     * @param index The index.
     * @return      True if index is valid.
     */
    private boolean validIndex(int index){
        return (index >= 0 && index < size);
    }

    /**
     * Checks the capacity of the backing array, changing it if necessary.
     */
    private void checkCapacity() {
        if (size > limit * SCALING_TRESHOLD){
            int newLimit = limit * SCALING_CONSTANT;
            changeCapacity(newLimit);
        } else if (size < (limit * (1.0 - SCALING_TRESHOLD))) {
            int newLimit = limit / SCALING_CONSTANT;
            if (newLimit >= MIN_SIZE){
                changeCapacity(newLimit);
            }
        }
    }
    
    /**
     * Changes the capacity of the backing array.
     * @param newLimit 
     */
    private void changeCapacity(int newLimit){
        Object[] newArray = new Object[newLimit];
        int smallerLimit = Math.min(newLimit, limit);
        System.arraycopy(array, 0, newArray, 0, smallerLimit);
        array = newArray;
        limit = newLimit;
    }
    
    /**
     * PACKAGE VISIBLE, FOR UNIT TESTING.
     * @return The size of the current backing array. 
     */
    int limit(){
        return limit;
    }

    /**
     * Returns an iterator over the ArrayList's contents.
     * Required for Iterable.
     * @return  The iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator<>();
    }
    
    /**
     * An implementation of Iterator.
     * Required for Iterable.
     * @param <T>   Class of iterated content.
     */
    public class ArrayListIterator<T> implements Iterator<T>{
        private int expectedModCount = modCount;
        private int index = 0;
        
        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
            
            return index < (size);
        }

        @Override
        public T next() {
            if (index >= size){
                throw new NoSuchElementException();
            }
            
            return (T) array[index++];
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
            
            ArrayList.this.remove(index);
            expectedModCount++;
        }
    }
}
