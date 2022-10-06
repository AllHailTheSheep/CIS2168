package lab3;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class CircularLinkedListTests {
    @Test
    public void testCircularLinkedListAdd() {
        CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>(true);
        // testing our exceptions
        Exception exception0 = assertThrows(IndexOutOfBoundsException.class, () -> cll.add(1, 1));
        Exception exception1 = assertThrows(IndexOutOfBoundsException.class, () -> cll.add(-1, -1));
        
        // test adding the first element of list by index (redirects to add(item) method)
        cll.add(0, 1);
        String res0 = "1 ==> ";
        assertEquals(res0, cll.toString());
        // test addding non first element to beginning
        cll.add(0, 0);
        String res1 = "0 ==> 1 ==> ";
        assertEquals(res1, cll.toString());
        // test adding to end by index (redirects to add(item) method)
        cll.add(2, 2);
        String res2 = "0 ==> 1 ==> 2 ==> ";
        assertEquals(res2, cll.toString());
        // test addding to middle
        cll.add(1, 999);
        String res3 = "0 ==> 999 ==> 1 ==> 2 ==> ";
        assertEquals(res3, cll.toString());
        // test final exception
        Exception exception2 = assertThrows(IndexOutOfBoundsException.class, () -> cll.add(5, 5));
    }

    @Test
    public void testCircularLinkedListRemove() {
        CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>(true);
        // test exception when list is empty
        assertThrows(IndexOutOfBoundsException.class, () -> cll.remove(0));
        for (int i = 0; i < 5; i++) {
            cll.add(i);
        }
        // test exceptions
        assertThrows(IndexOutOfBoundsException.class, () -> cll.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> cll.remove(5));
        // test remove end
        cll.remove(4);
        String res0 = "0 ==> 1 ==> 2 ==> 3 ==> ";
        assertEquals(res0, cll.toString());
        // test remove beginning
        cll.remove(0);
        String  res1 = "1 ==> 2 ==> 3 ==> ";
        assertEquals(res1, cll.toString());
        cll.add(0, 0);
        // test remove middle
        cll.remove(2);
        String res2 = "0 ==> 1 ==> 3 ==> ";
        assertEquals(res2, cll.toString());
        cll.add(2, 2);
        // test remove end
        cll.remove(3);
        String res3 = "0 ==> 1 ==> 2 ==> ";
        assertEquals(res3, cll.toString());
    }

    @Test
    public void testIteratorNext() {
        CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>(true);
        Iterator<Integer> it = cll.iterator();
        assertEquals(null, it.next());
        cll.add(0);
        cll.add(1);
        it = cll.iterator();
        assertEquals((Integer)0, (Integer)it.next());
        assertEquals((Integer)1, (Integer)it.next());
        assertEquals((Integer)0, (Integer)it.next());
    }

    @Test
    public void testIteratorRemove() {
        CircularLinkedList<Integer> cll = new CircularLinkedList<Integer>(true);
        for (int i = 0; i < 5; i++) {
            cll.add(i);
        }
        Iterator<Integer> it = cll.iterator();
        it.remove();
        String res0 = "1 ==> 2 ==> 3 ==> 4 ==> ";
        assertEquals(res0, cll.toString());
        // example test case
        it.next();
        it.next();
        it.remove();
        String res1 = "1 ==> 3 ==> 4 ==> ";
        assertEquals(res1, cll.toString());
    }
    
}
