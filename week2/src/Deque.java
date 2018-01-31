/**
 * Algorithms, Part I
 * Assignment 2.
 *
 * @author Yachen Lin
 * @date Jan 31, 2018
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Deque class.
 *
 * A deque containing n items must use at most 48n + 192 bytes
 * of memory and use space proportional to the number of items
 * currently in the deque.
 *
 * @author Yachen Lin
 * @param <Item> Generic type for Deque
 */
public class Deque<Item> implements Iterable<Item> {
    /**
     * first and last node of Deque.
     */
    private Node first, last;

    /**
     * number of elements.
     */
    private int size;

    /**
     * Node class for linkedlist.
     */
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    /**
     * Construct an empty deque.
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Returns whether the deque is empty.
     * @return Returns whether the deque is empty
     */
    public boolean isEmpty() {
        // worst case: O(1)
        return first == null;
    }

    /**
     * Return the number of items on the deque.
     *
     * @return Return the number of items on the deque
     */
    public int size() {
        // worst case: O(1)
        return size;

    }

    /**
     * Add the item to the front.
     *
     * @param item the item that should be added to the deque.
     */
    public void addFirst(Item item) {
        // worst case: O(1)
        if (item == null) {
            throw new IllegalArgumentException("Illegal argument: null");
        }
        size++;
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.previous = null;

        if (first != null) {
            first.previous = newFirst;
        }
        first = newFirst;

        if (last == null) {
            last = first;
        }
    }

    /**
     * Add the item to the end.
     *
     * @param item the item that should be added to the deque.
     */
    public void addLast(Item item) {
        // worst case: O(1)
        if (item == null) {
            throw new IllegalArgumentException("Illegal argument: null");
        }
        size++;
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.previous = last;

        if (last == null) {
            last = newLast;
        } else {
            last.next = newLast;
            last = last.next;
        }

        if (first == null) {
            first = newLast;
        }
    }

    /**
     * Remove and return the item from the front.
     *
     * @return the removed item
     */
    public Item removeFirst() {
        // worst case: O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove element in an empty Deque.");
        }
        size--;
        Node removed = first;
        if (first.next != null) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
        }

        removed.next = null;

        if (isEmpty()) last = null;
        return removed.item;
    }

    /**
     * Remove and return the item from the end.
     * @return the removed item
     */
    public Item removeLast() {
        // worst case: O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove element in an empty Deque.");
        }
        size--;
        Node removed = last;
        if (removed != null) {
            last = removed.previous;
        }
        if (last != null) {
            last.next = null;
        }
        if (removed != null) {
            removed.previous = null;
        }

        if (last == null) {
            first = null;
        }
        return removed.item;
    }

    /**
     * Return an iterator over items in order from front to end.
     *
     * @return Return an iterator over items in order from front to end
     */
    @Override
    public Iterator<Item> iterator() {
        // worst case: O(1)
        return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            // worst case: O(1)
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {
            // worst case: O(1)
            if (!hasNext()) {
                throw new NoSuchElementException("No more element.");
            }
            Node nextItem = new Node();
            nextItem.item = current.item;
            nextItem.next = null;
            nextItem.previous = null;

            current = current.next;
            return nextItem.item;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            // worst case: O(1)
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }
}