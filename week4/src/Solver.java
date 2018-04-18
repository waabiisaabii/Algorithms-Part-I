import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Solver class.
 *
 * @author Yachen
 */
public class Solver {
    private MinPQ<Tuple> minPQ;
    private int moves;

    /**
     * Find a solution to the initial board (using the A* algorithm).
     *
     * @param initial Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        minPQ = new MinPQ<>(new MyComparator());
        initial.setPredecessor(null);
        minPQ.insert(new Tuple(initial, new ArrayList<>()));
        solution = solve();
    }

    /**
     * Solve a slider puzzle (given below).
     *
     * @param args Solve a slider puzzle (given below)
     */
    public static void main(String[] args) {

    }

    /**
     * Is the initial board solvable?
     *
     * @return Is the initial board solvable?
     */
    public boolean isSolvable() {
        return true;

    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable.
     *
     * @return Min number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        return moves;
    }

    private Iterable<Board> solution;
    /**
     * Sequence of boards in a shortest solution; null if unsolvable.
     *
     * @return Sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        return solution;
    }

    private Iterable<Board> solve() {
        while (!minPQ.isEmpty()) {
            Tuple t = minPQ.delMin();
            Board current = t.board;
            List<Board> path = t.path;
            Board prev = current.getPredecessor();
            path.add(current);
            if (current.isGoal()) {
                moves = current.numMoves;
                return t.path;
            }
            for (Board neighbor : current.neighbors()) {
                if (!(neighbor.equals(prev))) {
                    neighbor.setPredecessor(current);
                    minPQ.insert(new Tuple(neighbor, path));
                }
            }
        }
        return null;
    }

    private static class Tuple {
        Board board;

        List<Board> path;

        public Tuple(Board board, List<Board> path) {
            this.board = board;
            this.path = path;
        }
    }

    private class MyComparator implements Comparator<Tuple> {
        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * In the foregoing description, the notation
         * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
         * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
         * <tt>0</tt>, or <tt>1</tt> according to whether the value of
         * <i>expression</i> is negative, zero or positive.<p>
         * <p>
         * The implementor must ensure that <tt>sgn(compare(x, y)) ==
         * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>compare(x, y)</tt> must throw an exception if and only
         * if <tt>compare(y, x)</tt> throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
         * <tt>compare(x, z)&gt;0</tt>.<p>
         * <p>
         * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
         * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
         * <tt>z</tt>.<p>
         * <p>
         * It is generally the case, but <i>not</i> strictly required that
         * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         */
        @Override
        public int compare(Tuple o1, Tuple o2) {
            return Integer.compare(o1.board.getmDist(), o2.board.getmDist());
        }
    }
}