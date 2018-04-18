import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Board class.
 *
 * @author Yachen Lin
 */
public final class Board {
    private final int dimension;
    int numMoves;
    private final int[][] blocks;


    public Board getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Board predecessor) {
        this.predecessor = predecessor;
    }

    private Board predecessor;
    private int mDist;

    /**
     * Construct a board from an n-by-n array of blocks.
     *
     * @param blocks an array of blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = blocks;

        mDist = manhattan();
        numMoves = 0;
    }

    /**
     * Unit tests (not graded).
     *
     * @param args command line input
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }



    public int getmDist() {
        return mDist;
    }

    /**
     * Board dimension n.
     *
     * @return board dimension n
     */
    public int dimension() {
        return dimension;
    }

    /**
     * Number of blocks out of place.
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        int result = 0;
        outerloop:
        for (int i = 0, count = 1; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) {
                    // ignore last element in the board
                    break outerloop;
                }
                if (blocks[i][j] != count) {
                    result++;
                }
                count++;
            }
        }
        return result + numMoves;
    }

    /**
     * Sum of Manhattan distances between blocks and goal.
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int current = blocks[i][j];
                if (current == 0) {
                    continue;
                }
                int vertDist = Math.abs(i - (current - 1) / dimension);
                int horiDist = Math.abs(j - (current - 1) % dimension);
                result += vertDist + horiDist;
            }
        }
        return result + numMoves;
    }

    /**
     * Is this board the goal board?
     *
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == dimension - 1 && j == dimension - 1) {
                    break;
                }
                if (blocks[i][j] != i * dimension + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks.
     *
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        return null;
    }

    /**
     * Does this board equal y?
     *
     * @param y board y
     * @return true or false
     */
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        Board b = (Board) y;
        if (b.dimension() != dimension) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != b.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result += 31 * blocks[i][j];
            }
        }
        return result % (dimension * dimension);
    }

    /**
     * All neighboring boards.
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Board currentBoard = this;
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator(currentBoard);
            }
        };
    }

    /**
     * String representation of this board (in the output format specified below).
     *
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n%d\n", dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format(" %d ", blocks[i][j]));
            }
            sb.append(String.format("\n"));
        }
        sb.append("\n");
        return sb.toString();
    }

    private static class BoardIterator implements Iterator<Board> {
        private Board originalBoard;
        private List<int[]> neighbors;
        private int idx;
        private int[] hole;

        /**
         * Constructor.
         *
         * @param board
         */
        private BoardIterator(Board board) {
            originalBoard = board;
            neighbors = new ArrayList<>();
            hole = findHole();
            if (hole == null) {
                throw new IllegalArgumentException();
            }
            int[][] neis = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            int dimension = originalBoard.dimension();
            for (int[] neighbor : neis) {
                neighbor[0] += hole[0];
                neighbor[1] += hole[1];
                if (neighbor[0] < 0 || neighbor[0] >= dimension
                        || neighbor[1] < 0 || neighbor[1] >= dimension) {
                    continue;
                }
                neighbors.add(new int[]{neighbor[0], neighbor[1]});
            }

            idx = 0;

        }

        private static void swap(Board originalBoard, int[] x, int[] y) {
            int tmp = originalBoard.blocks[x[0]][x[1]];
            originalBoard.blocks[x[0]][x[1]] = originalBoard.blocks[y[0]][y[1]];
            originalBoard.blocks[y[0]][y[1]] = tmp;
        }

        private int[] findHole() {
            for (int i = 0; i < originalBoard.dimension(); i++) {
                for (int j = 0; j < originalBoard.dimension(); j++) {
                    if (originalBoard.blocks[i][j] == 0) {
                        return new int[]{i, j};
                    }
                }
            }
            return null;
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
            return idx < neighbors.size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }


            int[][] blocks = new int[originalBoard.dimension()][originalBoard.dimension()];
            for (int i = 0; i < blocks.length; i++) {
                System.arraycopy(originalBoard.blocks[i], 0, blocks[i], 0, blocks[0].length);
            }
            Board result = new Board(blocks);
            result.numMoves = originalBoard.numMoves + 1;
            swap(result, neighbors.get(idx), hole);
            idx++;
            return result;
        }
    }
}
