import org.junit.Test;

import static org.junit.Assert.*;

public class DequeTest {
    @Test
    public void testAddFirstRemoveFirst() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());

        deque.addFirst(1);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.addFirst(2);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 2);

        deque.removeFirst();
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.removeFirst();
        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
    }

    @Test
    public void testAddFirstRemoveLast() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());

        deque.addFirst(1);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.addFirst(2);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 2);

        deque.removeLast();
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.removeLast();
        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
    }

    @Test
    public void testAddLastRemoveFirst() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());

        deque.addLast(1);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.addLast(2);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 2);

        deque.removeFirst();
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.removeFirst();
        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
    }

    @Test
    public void testAddLastRemoveLast() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());

        deque.addLast(1);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.addLast(2);
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 2);

        deque.removeLast();
        assertTrue(!deque.isEmpty());
        assertTrue(deque.size() == 1);

        deque.removeLast();
        assertTrue(deque.isEmpty());
        assertTrue(deque.size() == 0);
    }

}