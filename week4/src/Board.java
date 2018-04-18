import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Board class.
 * @author Yachen Lin
 */
public final class Board {
    private final int dimension;
    private int[][] blocks;
    private int numMoves;

    /**
     * Construct a board from an n-by-n array of blocks.
     *
     * @param blocks an array of blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = blocks;
        numMoves = 0;
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
    public boolean equals(Object y) {
        return false;

    }

    /**
     * All neighboring boards.
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        return null;

    }

    /**
     * String representation of this board (in the output format specified below).
     *
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        return null;

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
}
