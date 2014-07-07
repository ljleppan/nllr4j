package loez.nllr.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of the HashSet.
 * The backing data structure is a HashMap.
 * @author      ljeppan
 * @param <E>   Class of elements
 */
public class HashSet<E> implements Iterable<E>{    
    private static final Object DUMMY = new Object();
    
    private HashMap<E, Object> map = new HashMap<>();
    private int size = 0;
    private int modCount = 0;

    /**
     * Adds a new, unique, element to the set.
     * Duplicate elements will not be added. Does not allow for null elements.
     * @param e Element
     */
    public void add(E e){
        if (e != null){
            if (!map.containsKey(e)) {
                map.put(e, DUMMY);
                size++;
                modCount++;
            }
        }
    }
    
    /**
     * Removes an element from the set.
     * @param e Element
     */
    public void remove(E e){
        if (e != null){
            if (map.containsKey(e)) {
                map.remove(e);
                size--;
                modCount++;
            }
        }
    }
    
    /**
     * Checks if an element is present in the set.
     * @param e Element
     * @return  True if element is present, false if not
     */
    public boolean contains(E e){
        return map.containsKey(e);
    }
    
    /**
     * Adds all elements from the supplied ArrayList.
     * @param elements  An ArrayList of elements to be added.
     */
    public void addAll(ArrayList<E> elements){
        for (E e : elements){
            add(e);
        }
    }
    
    /**
     * Removes all elements found in the supplied ArrayList.
     * @param elements  An ArrayList of elements to be removed.
     */
    public void removeAll(ArrayList<E> elements){
        for (E e : elements){
            remove(e);
        }
    }
    
    /**
     * @return  Returns the amount of elements in the set.
     */
    public int size(){
        return size;
    }
    
    /**
     * @return  True if the set contains no elements, false otherwise.
     */
    public boolean isEmpty(){
        return size == 0;
    }
    
    /**
     * @return ArrayList containing the elements of the HashSet
     */
    public ArrayList<E> toArrayList(){
        return map.keySet();
    }

    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator<>();
    }
    
    /**
     * An iterator for iterating over the set's elements.
     * @param <T>   Type of element
     */
    public class HashSetIterator<T> implements Iterator<T>{
        private ArrayList<T> keySet = (ArrayList<T>) map.keySet();
        private int expectedModCount = modCount;
        private int index = 0;
        
        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
            
            return index < (keySet.size());
        }

        @Override
        public T next() {
            if (index >= keySet.size()){
                throw new NoSuchElementException();
            }
            
            return (T) keySet.get(index++);
            
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }

            HashSet.this.remove((E) keySet.get(index++));
            expectedModCount++;
        }
    }
}
