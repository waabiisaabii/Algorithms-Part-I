import org.junit.Test;

import static org.junit.Assert.*;

public class RandomizedQueueTest {

    @Test
    public void testEnqueueAndSize() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        assertTrue(queue.size() == 0);

        queue.enqueue(1);
        assertTrue(queue.size() == 1);

        queue.enqueue(2);
        assertTrue(queue.size() == 2);

        queue.enqueue(3);
        assertTrue(queue.size() == 3);
    }

    @Test
    public void testEnqueueAndDequeue() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        assertTrue(queue.size() == 0);

        queue.enqueue(1);
        queue.dequeue();
        assertTrue(queue.size() == 0);

        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.size() == 2);

        queue.dequeue();
        assertTrue(queue.size() == 1);
        queue.dequeue();
        assertTrue(queue.size() == 0);
        assertTrue(queue.isEmpty());
    }
}