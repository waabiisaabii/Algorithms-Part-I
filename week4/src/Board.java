import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class.
 *
 * @author Yachen Lin
 */
public final class Board {
    private final int[][] blocks;
    private int numMoves;

    /**
     * Construct a board from an n-by-n array of blocks.
     *
     * @param blocks an array of blocks
     */
    public Board(int[][] blocks) {
        this.blocks = copyNestedArray(blocks);
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

    private static void swap(Board originalBoard, int[] x, int[] y) {
        int tmp = originalBoard.blocks[x[0]][x[1]];
        originalBoard.blocks[x[0]][x[1]] = originalBoard.blocks[y[0]][y[1]];
        originalBoard.blocks[y[0]][y[1]] = tmp;
    }

    private int[][] copyNestedArray(int[][] given) {
        int dimension = blocks.length;
        int[][] result = new int[dimension][dimension];
        for (int i = 0; i < given.length; i++) {
            System.arraycopy(given[i], 0, result[i], 0, given[0].length);
        }
        return result;
    }

    /**
     * Board dimension n.
     *
     * @return board dimension n
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * Number of blocks out of place.
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        int result = 0;
        int dimension = blocks.length;
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
//        return result + numMoves;
        return result;
    }

    /**
     * Sum of Manhattan distances between blocks and goal.
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        int dimension = blocks.length;
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
//        return result + numMoves;
        return result;
    }

    /**
     * Is this board the goal board?
     *
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        int dimension = blocks.length;
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

        int[][] blocksNew = copyNestedArray(blocks);


        Board newBoard = new Board(blocksNew);
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            swap(newBoard, new int[]{0, 0}, new int[]{0, 1});
        } else {
            swap(newBoard, new int[]{1, 0}, new int[]{1, 1});
        }

        return newBoard;
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
        int dimension = blocks.length;
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
     * All neighboring boards.
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int dimension = blocks.length;
        Board currentBoard = this;
        numMoves = currentBoard.numMoves;
        List<int[]> neighbors = new ArrayList<>();
        int[] hole = findHole();
        if (hole == null) {
            throw new IllegalArgumentException();
        }
        int[][] neis = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] neighbor : neis) {
            neighbor[0] += hole[0];
            neighbor[1] += hole[1];
            if (neighbor[0] < 0 || neighbor[0] >= dimension
                    || neighbor[1] < 0 || neighbor[1] >= dimension) {
                continue;
            }
            neighbors.add(new int[]{neighbor[0], neighbor[1]});
        }

        int[][] newBlocks = new int[dimension][dimension];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, newBlocks[i], 0, blocks[0].length);
        }

        List<Board> result = new ArrayList<>();
        for (int[] neighbor : neighbors) {
            Board b = new Board(blocks);
            b.numMoves = numMoves + 1;
            swap(b, neighbor, hole);
            result.add(b);
        }
        return result;
    }

    private int[] findHole() {
        int dimension = blocks.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * String representation of this board (in the output format specified below).
     *
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        int dimension = blocks.length;
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
}
