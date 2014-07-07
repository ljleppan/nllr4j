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
public class HashMapTest {
    private HashMap<String, Integer> hm;
    
    public class Collider{
        int value;
        
        public Collider(int value){
            this.value = value;
        }
        
        @Override
        public int hashCode(){
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Collider other = (Collider) obj;
            return this.value == other.value;
        }


    }
    
    @Before
    public void setUp() {
        hm = new HashMap<>();
    }

    @Test
    public void emptyHashMapHasNoKeys() {
        assertTrue("An empty hashmap should have no keys in its keyset",
                hm.keySet().isEmpty());
    }
    
    @Test
    public void addingKeysWords() {
        hm.put("a", 1);
        assertFalse("After adding a key, the keyset should be non-empty",
                hm.keySet().isEmpty());
        assertTrue("After adding a key, containsKey should return true for that key",
                hm.containsKey("a"));
        assertEquals("Adding two keys to an empty map should not change the size",
                16, hm.getSize());
        
    }
    
    @Test
    public void removingKeysWorks() {
        hm.put("a", 1);
        hm.put("b", 2);
        assertEquals("After adding two keys, two keys should be present in the keyset",
                2, hm.keySet().size());
        
        hm.remove("c");
        assertEquals("Removing a non-existant key should not do anything",
                2, hm.keySet().size());
        
        hm.remove("a");
        assertEquals("Keyset should not include a key after that key has been removed",
                1, hm.keySet().size());
        
        assertEquals("Removing one of two entries should not change size",
                16, hm.getSize());
    }
    
    @Test
    public void gettingKeysWorks() {
        assertEquals("Getting a non-existant key should return null",
                null, hm.get("NOPE"));
    }
    
    @Test
    public void collisionsWork() {
        HashMap<Collider, Integer> h = new HashMap<>();
        Collider c1 = new Collider(1);
        Collider c2 = new Collider(2);
        Collider c3 = new Collider(3);
        Collider c4 = new Collider(4);
        h.put(c1, 1);
        h.put(c2, 2);
        h.put(c3, 3);
        h.put(c4, 4);
        assertEquals("Adding colliding entries should keep all entries in keyset",
                4, h.keySet().size());
        assertTrue("Searching for colliding key should return corrent key",
                h.get(c1) == 1 && h.get(c2) == 2 && h.get(c3) == 3 && h.get(c4) == 4);

        h.remove(c3);
        assertTrue("Removing one of colliding keys should leave the others and remove the correct one",
                h.get(c1) == 1 && h.get(c2) == 2 && h.get(c3) == null && h.get(c4) == 4);
        h.remove(c4);
        assertTrue("Removing one of colliding keys should leave the others and remove the correct one",
                h.get(c1) == 1 && h.get(c2) == 2 && h.get(c3) == null && h.get(c4) == null);
        assertEquals("Adding 3 colliding keys to an empty hashmap should not increase the size",
                16, h.getSize());
    }
    
    @Test
    public void capacityChangesWork(){
        addMultiple(12);
        assertEquals("Adding 12 entries to an empty hashmap should not result in a size increase",
                16, hm.getSize());
        
        hm = new HashMap<>();
        addMultiple(13);
        assertEquals("Adding a 13th element to a hashmap with size 16 should increase the size",
                32, hm.getSize());        
        
        removeMultiple(9, 13);
        assertEquals("Leaving 8 keys in a hashmap with size 32 should not decrease the size",
                32, hm.getSize());
        
        hm.remove("8");
        assertEquals("Leaving 7 keys in a a hashmap with size 32 should decrease the size ",
                16, hm.getSize());
    }
    
    @Test
    public void canIterateWithFor(){
        addMultiple(3);
        ArrayList<String> metKeys = new ArrayList<>();
        for (String s : hm){
            metKeys.add(s);
        }
        assertEquals("for keyword should loop thru all the elements in the arraylist",
                hm.keySet().size(), metKeys.size());
    }
    
    @Test
    public void canRemoveWithIterator(){
        addMultiple(3);
        Iterator<String> iter = hm.iterator();
        while (iter.hasNext()){
            iter.remove();
        }        
        assertTrue("removing items with iterator works",
                hm.keySet().isEmpty());
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToRemoveWithFor(){
        addMultiple(3);
        for (String s : hm){
            hm.remove(s);
        }
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToContinueIterationAfterOutsideRemoval(){
        addMultiple(3);
        Iterator<String> iter = hm.iterator();
        hm.remove("1");
        iter.remove();
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void unableToContinueIterationAfterOutsideAdd(){
        addMultiple(3);
        Iterator<String> iter = hm.iterator();
        hm.put("5", 5);
        iter.remove();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void unableToIterateBeyondLastElement(){
        addMultiple(3);
        Iterator<String> iter = hm.iterator();
        while (iter.hasNext()){
            iter.next();
        }
        iter.next();
    }

    private void addMultiple(int amount) {
        for (int i = 1; i <= amount; i++) {
            hm.put(""+i, i);
        }
    }
    
    private void removeMultiple(int start, int end){
        for (int i = start; i <= end; i++) {
            hm.remove(""+i);
        }
    }
}
