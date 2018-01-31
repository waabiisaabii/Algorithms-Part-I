/**
 * Algorithms, Part I
 * Assignment 2.
 *
 * @author Yachen Lin
 * @date Jan 31, 2018
 */

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

/**
 * Permutation class.
 *
 * @author Yachen Lin
 */
public class Permutation {
    /**
     * test client.
     *
     * @param args command line input
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            try {
                String input = StdIn.readString();
                randomizedQueue.enqueue(input);
            } catch (NoSuchElementException e) {
                break;
            }
        }

        Iterator<String> it = randomizedQueue.iterator();
        int index = 0;
        while (it.hasNext() && index < k) {
            StdOut.println(it.next());
            index++;
        }
    }
}
