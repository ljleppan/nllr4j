package loez.nllr.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ljleppan
 */
public class ArrayListTest {
    private ArrayList<String> al;
    
    @Before
    public void setUp() {
        al = new ArrayList<>();
    }

    @Test
    public void empty() {
        assertEquals("A newly created arraylist should have size of zero",
                0, al.size());
        assertTrue("A newly created arraylist should return true for isEmpty()",
                al.isEmpty());
    }
    
    @Test
    public void add() {
        al.add("a");
        assertEquals("An arraylist with a single value should have a size of one",
                1, al.size());
        assertFalse("An arraylist with a single value should return false for isEmpty()",
                al.isEmpty());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutOfBoundsTooSmall(){
        al.get(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutOfBoundsTooLarge(){
        al.get(1);
    }
    
    @Test
    public void getSingle() {
        al.add("a");
        assertEquals("Getting a value with index 0 from an arraylist with a single value should return the only value",
                "a", al.get(0));
    }
    
    @Test
    public void getMultiple() {
        al.add("a");
        al.add("b");
        assertEquals("Getting a value with index 1 from an arraylist with two values should return the last added value",
                "b", al.get(1));
        assertEquals("Getting a value with index 0 from an arraylist with two values should return the first added value",
                "a", al.get(0));
    }
    
    @Test
    public void indexOf(){
        al.add("a");
        al.add("b");
        assertEquals("indexOf() should return 0 as the index of the first value",
                0, al.indexOf("a"));
        assertEquals("indexOf() should return 1 as the index of the second value",
                1, al.indexOf("b"));
        assertEquals("indexOf() should returne -1 as the index of a non-existant value",
                -1, al.indexOf("c"));
        assertEquals("indexOf() should return -1 as the index of null value in an arraylist with no null value",
                -1, al.indexOf(null));
        
        al.add(null);
        assertEquals("indexOf() should return 2 as the index of null value in an arraylist with null as third value",
                2, al.indexOf(null));
    }
    
    @Test
    public void contains(){
        al.add("a");
        assertFalse("contains() should return false for non-existant values",
                al.contains("b"));
        assertTrue("contains() should return true for existing values",
                al.contains("a"));
        assertFalse("contains() should return false for 'null', if null hasn't been added to the arraylist",
                al.contains(null));
        
        al.add(null);
        assertTrue("contains() should return true for 'null' if null has been added to the arraylist",
                al.contains(null));
    }
    
    @Test
    public void clearEmpties(){
        al.add("a");
        al.clear();
        assertTrue("An arraylist should be isEmpty() after clear()",
                al.isEmpty());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void clearReinitializes(){
        al.add("a");
        al.clear();
        al.get(0);
    }
    
    @Test
    public void backingArraySizeChanges(){
        assertEquals("The backing arrays should be 8 long at start",
                8, al.limit());
        
        addMultiple(5);
        assertEquals("Adding 6 items to an empty arraylist should keep the backing array at 8 long",
                8, al.limit());
        
        al.add("6");
        assertEquals("Adding 7th item to an arraylist with 6 items increases the backing array lenght to 16",
                16, al.limit());
        
        assertEquals("Increasing backing array size should keep current items at their indices",
                "1", al.get(1));
        assertEquals("Increasing backing array size should keep current items at their indices",
                "3", al.get(3));
        
        removeMultiple(3);
        
        assertEquals("Removing enough items causes backing array size to decrease",
                8, al.limit());
        
        assertEquals("Decreasing backing array size should keep current items at their indices",
                "4", al.get(0));
        assertEquals("Decreasing backing array size should keep current items at their indices",
                "6", al.get(2));
        
        removeMultiple(2);
        assertEquals("Backing array size will not reduce beyond 8",
                8, al.limit());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexOutOfBoundsTooLow(){
        al.add("a");
        al.remove(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexOutOfBoundsTooHigh(){
        al.add("a");
        al.remove(1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexSingleRemovesIndexFromUse(){
        al.add("a");
        al.remove(0);
        al.get(0);
    }
    
    @Test
    public void removeByIndexSingleEmpties(){
        al.add("a");
        al.remove(0);
        assertTrue("Removing the only item from the array list should cause isEmpty() to return true",
                al.isEmpty());
    }
    
    @Test
    public void removeByIndexMovesFurtherItemsBack(){
        addMultiple(3);
        al.remove(1);
        assertEquals("When removing items by index, items before the removed one should remain same",
                "0", al.get(0));
        assertEquals("When removing items by index, items after the removed one should have their indices reduced by one",
                "2", al.get(1));
        assertEquals("When removing items by index, items after the removed one should have their indices reduced by one",
                "3", al.get(2));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByValueSingleRemovesIndexFromUse(){
        al.add("a");
        al.remove("a");
        al.get(0);
    }
    
    @Test
    public void removeByValueSingleEmpties(){
        al.add("a");
        al.remove("a");
        assertTrue("Removing the only item from the array list should cause isEmpty() to return true",
                al.isEmpty());
    }
    
    @Test
    public void removeByValueMovesFurtherItemsBack(){
        addMultiple(3);
        al.remove("1");
        assertEquals("When removing items by index, items before the removed one should remain same",
                "0", al.get(0));
        assertEquals("When removing items by index, items after the removed one should have their indices reduced by one",
                "2", al.get(1));
        assertEquals("When removing items by index, items after the removed one should have their indices reduced by one",
                "3", al.get(2));
    }
    
    @Test
    public void canTryToRemoveNonExistantElements(){
        al.remove("1");
    }
    
    @Test
    public void canIterateWithFor(){
        addMultiple(3);
        ArrayList<String> metKeys = new ArrayList<>();
        for (String s : al){
            metKeys.add(s);
        }
        assertEquals("for keyword should loop thru all the elements in the arraylist",
                al.size(), metKeys.size());
    }
    
    @Test
    public void canRemoveWithIterator(){
        addMultiple(3);
        Iterator<String> iter = al.iterator();
        while (iter.hasNext()){
            iter.remove();
        }        
        assertTrue("removing items with iterator works",
                al.isEmpty());
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToRemoveWithFor(){
        addMultiple(3);
        for (String s : al){
            al.remove(s);
        }
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToContinueIterationAfterOutsideRemoval(){
        addMultiple(3);
        Iterator<String> iter = al.iterator();
        al.remove("1");
        iter.remove();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void unableToIterateBeyondLastElement(){
        addMultiple(3);
        Iterator<String> iter = al.iterator();
        while (iter.hasNext()){
            iter.next();
        }
        iter.next();
    }
    
    private void addMultiple(int amount){
        for (int i = 0; i <= amount; i++) {
            al.add(""+i);
        }
    }
    
    private void removeMultiple(int amount){
        for (int i = 0; i <= amount; i++) {
            al.remove(0);
        }
    }
}
