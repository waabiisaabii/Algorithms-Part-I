/**
 * Algorithms, Part I
 * Assignment 2.
 *
 * @author Yachen Lin
 * @date Jan 31, 2018
 */

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

/**
 * RandomizedQueue class.
 *
 * @author Yachen Lin
 * @param <Item> type of item
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * initial size of the queue.
     */
    private int capacity = 1;

    /**
     * resizing array implementation of RandomizedQueue.
     */
    private Item[] items;

    /**
     * number of elements
     */
    private int numberOfElements;

    /**
     * Constructor.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[capacity];
        numberOfElements = 0;
    }

    /**
     * Returns whether the RandomizedQueue is empty.
     *
     * @return Returns whether the RandomizedQueue is empty.
     */
    public boolean isEmpty() {
        // Amortized time: O(1)
        return numberOfElements == 0;
    }

    /**
     * Return size of RandomizedQueue.
     *
     * @return Return size of RandomizedQueue.
     */
    public int size() {
        // Amortized time: O(1)
        return numberOfElements;
    }

    /**
     * Add the item.
     * @param item the item that needs to be added.
     */
    public void enqueue(Item item) {
        // Amortized time: O(1)
        if (item == null) {
            throw new IllegalArgumentException("Illegal argument: null item");
        }
        if (numberOfElements + 1 == capacity) {
            // double size of the array
            resize(capacity << 1);
        }

        items[numberOfElements++] = item;

        swap(StdRandom.uniform(numberOfElements), numberOfElements - 1);
    }

    /**
     * Resize the array.
     *
     * @param newCapacity new size of the array
     */
    private void resize(int newCapacity) {
        capacity = newCapacity;
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfElements; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    /**
     * Remove and return a random item.
     *
     * @return a random item
     */
    public Item dequeue() {
        // Amortized time: O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
        if (numberOfElements <= capacity / 4) {
            resize(capacity / 2);
        }

//        swap(StdRandom.uniform(numberOfElements), numberOfElements - 1);
        // Avoid loitering.
        Item item = items[--numberOfElements];
        items[numberOfElements] = null;
        return item;
    }

    /**
     * Return a random item (but do not remove it).
     *
     * @return Return a random item (but do not remove it)
     */
    public Item sample() {
        // Amortized time: O(1)
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
//        StdRandom.shuffle(items, 0, numberOfElements);
        swap(StdRandom.uniform(numberOfElements), numberOfElements - 1);
        return items[numberOfElements - 1];
    }

    /**
     * swap two elements.
     *
     * @param i index i
     * @param j index j
     */
    private void swap(int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    /**
     * Return an independent iterator over items in random order.
     *
     * @return Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        // O(n)
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        /**
         * array for iterator.
         */
        private final Item[] itemsIterator;

        /**
         * current index of the Iterator.
         */
        private int currentPosition;

        /**
         * Constructor.
         */
        RandomizedIterator() {
            StdRandom.shuffle(items, 0, numberOfElements);
            itemsIterator = (Item[]) new Object[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                itemsIterator[i] = items[i];
            }
            currentPosition = 0;
        }

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
            return currentPosition < itemsIterator.length;
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
                throw new NoSuchElementException("The queue has no more element.");
            }

            return itemsIterator[currentPosition++];
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
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }
}
