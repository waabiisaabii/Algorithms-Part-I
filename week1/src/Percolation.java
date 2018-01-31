/**
 * Algorithms, Assignment 1, Percolation.
 *
 * @author Yachen Lin
 * @date Jan 30, 2018
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 *
 * @author Yachen Lin
 */
public class Percolation {
    private final int size;
    private final int numberOfGrid;
    private int numberOfOpenSites;

    private boolean[] isOpen;

    private final WeightedQuickUnionUF unionUF;
    private final WeightedQuickUnionUF unionUF2;

    /**
     * Constructor of Percolation.
     * Create n-by-n grid, with all sites blocked.
     *
     * @param n the size of the grid
     */
    public Percolation(int n) {
        // O(n^2)
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal argument.");
        }
        size = n;
        numberOfGrid = n * n + 2;
        unionUF = new WeightedQuickUnionUF(numberOfGrid);
        unionUF2 = new WeightedQuickUnionUF(numberOfGrid);
        isOpen = new boolean[numberOfGrid];

        for (int i = 0; i < numberOfGrid; i++) {
            isOpen[i] = false;
        }
        isOpen[numberOfGrid - 2] = true;
        isOpen[numberOfGrid - 1] = true;
        numberOfOpenSites = 0;
    }

    /**
     * Convert 2D coordinates to 1D.
     * @return 1D index
     */
    private int xyTo1D(int x, int y) {
        // x, y starts at 0
        return size * x + y;
    }

    /**
     * Open site (row, col) if it is not open already.
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        // union(), O(1)
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Illegal argument.");
        }
        row--;
        col--;

        if (!isOpen[xyTo1D(row, col)]) {
            isOpen[xyTo1D(row, col)] = true;
            numberOfOpenSites++;

            if (row == 0) {
                unionUF.union(numberOfGrid - 2, xyTo1D(row, col));
                unionUF2.union(numberOfGrid - 2, xyTo1D(row, col));
            }

            int currentPosition = xyTo1D(row, col);

            // up
            if (row - 1 >= 0 && isOpen[xyTo1D(row - 1, col)] && xyTo1D(row - 1, col) < size * size) {
                unionUF.union(currentPosition, xyTo1D(row - 1, col));
                unionUF2.union(currentPosition, xyTo1D(row - 1, col));
            }

            // down
            if (row + 1 < size && isOpen[xyTo1D(row + 1, col)] && xyTo1D(row + 1, col) < size * size) {
                unionUF.union(currentPosition, xyTo1D(row + 1, col));
                unionUF2.union(currentPosition, xyTo1D(row + 1, col));
            }

            // right
            if (col + 1 < size && isOpen[xyTo1D(row, col + 1)] && xyTo1D(row, col + 1) < size * size) {
                unionUF.union(currentPosition, xyTo1D(row, col + 1));
                unionUF2.union(currentPosition, xyTo1D(row, col + 1));
            }

            // left
            if (col - 1 >= 0 && isOpen[xyTo1D(row, col - 1)] && xyTo1D(row, col - 1) < size * size) {
                unionUF.union(currentPosition, xyTo1D(row, col - 1));
                unionUF2.union(currentPosition, xyTo1D(row, col - 1));
            }

            if (row == size - 1) {
                unionUF.union(xyTo1D(row, col), numberOfGrid - 1);
            }


        }

    }

    /**
     * Is site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        // connected(), O(1)
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Illegal argument.");
        }
        row--;
        col--;
        return isOpen[xyTo1D(row, col)];

    }

    /**
     * Is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        // O(1)
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Illegal argument.");
        }
        row--;
        col--;

        return unionUF.connected(numberOfGrid - 2, xyTo1D(row, col)) &&
                unionUF2.connected(numberOfGrid - 2, xyTo1D(row, col));

    }

    /**
     * Number of open sites.
     *
     * @return
     */
    public int numberOfOpenSites() {
        // O(1)
        return numberOfOpenSites;

    }

    /**
     * Does the system percolate?
     *
     * @return
     */
    public boolean percolates() {

        // O(1)
        return unionUF.connected(numberOfGrid - 1, numberOfGrid - 2);

    }

    /**
     * Test client (optional).
     *
     * @param args
     */
    public static void main(String[] args) {
        int size = 2;
        Percolation p = new Percolation(size);

        p.open(1, 1);
        p.open(2, 2);
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());

    }
}