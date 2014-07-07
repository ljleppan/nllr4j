package loez.nllr.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author loezi
 */
public class HashSetTest {
    
    private HashSet<String> hs;
    
    @Before
    public void setUp() {
        hs = new HashSet<>();
    }
    

    @Test
    public void newHashSetIsEmpty(){
        assertEquals("A newly constructed HashSet should be empty",
                0, hs.size());
    }
    
    @Test
    public void canAddElements(){
        hs.add("a");
        assertEquals("Adding elements to the HashSet should increase its size",
                1, hs.size());
    }
    
    @Test
    public void canAddMultipleElements(){
        hs.add("a");
        hs.add("b");
        assertEquals("Adding multiple unique elements to the HashSet should increase its size",
                2, hs.size());
    }
    
    @Test
    public void canNotAddDuplicateElements(){
        hs.add("a");
        hs.add("a");
        assertEquals("Adding a duplicate element to the HashSet should not increase its size",
                1, hs.size());
    }
    
    @Test
    public void canRemoveElements(){
        hs.add("a");
        hs.remove("a");
        assertEquals("Removing an element actually removes it",
                0, hs.size());
    }
    
    @Test
    public void canTryToRemoveNonExistantElements(){
        hs.remove("a");
    }
    
    @Test
    public void containsReturnsTrueForPresentElements(){
        hs.add("a");
        assertTrue("contains() should return true if the queried element is present",
                hs.contains("a"));
    }
    
    @Test
    public void containsReturnsFalseForNonPresetElements(){
        hs.add("a");
        assertTrue("contains() should return false if the queried element is not present",
                hs.contains("a"));
    }
    
    @Test
    public void canAddElementsFromArrayList(){
        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        hs.addAll(a);
        assertTrue("addAll() should add all the elements from the list",
                hs.contains("a"));
        assertTrue("addAll() should add all the elements from the list",
                hs.contains("b"));
    }
    
    @Test
    public void canRemoveElementsFromArrayList(){
        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        hs.addAll(a);
        hs.removeAll(a);
        assertFalse("removeAll() should remove all the elements from the list",
                hs.contains("a"));
        assertFalse("removeAll() should remove all the elements from the lis",
                hs.contains("b"));
    }
    
    @Test
    public void setHandlesNullValues(){
        hs.add("a");
        assertFalse("contains() should return false for null",
                hs.contains(null));
        hs.add(null);
        assertFalse("adding null should not be possible",
                hs.contains(null));
        hs.remove(null);
        assertTrue("removing a null value makes no changes to the list",
                hs.contains("a"));
    }
    
    @Test
    public void canIterateWithFor(){
        hs.add("a");
        hs.add("b");
        hs.add("c");
        ArrayList<String> metKeys = new ArrayList<>();
        for (String s : hs){
            metKeys.add(s);
        }
        assertEquals("for keyword should loop thru all the elements in the arraylist",
                hs.size(), metKeys.size());
    }
    
    @Test
    public void canRemoveWithIterator(){
        hs.add("a");
        hs.add("b");
        hs.add("c");
        Iterator<String> iter = hs.iterator();
        while (iter.hasNext()){
            iter.remove();
        }
        
        for (String s : hs){
            System.out.print(s+" ");
        }
        
        assertTrue("removing items with iterator works",
                hs.isEmpty());
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToRemoveWithFor(){
        hs.add("a");
        hs.add("b");
        hs.add("c");
        for (String s : hs){
            hs.remove(s);
        }
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToContinueIterationAfterOutsideRemoval(){
        hs.add("a");
        hs.add("b");
        hs.add("c");
        Iterator<String> iter = hs.iterator();
        hs.remove("a");
        iter.remove();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void unableToIterateBeyondLastElement(){
        hs.add("a");
        hs.add("b");
        hs.add("c");
        Iterator<String> iter = hs.iterator();
        while (iter.hasNext()){
            iter.next();
        }
        iter.next();
    }
}
