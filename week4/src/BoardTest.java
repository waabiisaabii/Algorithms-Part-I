import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    @Before
    public void setUp() {
        int[][] blocks = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        board = new Board(blocks);
    }

    @Test
    public void testHamming() {
        assertEquals(5, board.hamming());
    }

    @Test
    public void testMahattan() {
        assertEquals(10, board.manhattan());
    }

    @Test
    public void testIsGoal() {
        assertFalse(board.isGoal());
    }

    @Test
    public void testIsGoal2() {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        board = new Board(blocks);
        assertTrue(board.isGoal());
    }

}