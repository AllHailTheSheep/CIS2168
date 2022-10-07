package lab5.KnightsTour;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import lab5.Board;

public class knightsTourTests {
    @Test
    public void testValidate() {
        assertTrue(knightsTour.validate(0));
        assertTrue(knightsTour.validate(3));
        assertTrue(knightsTour.validate(7));
        assertFalse(knightsTour.validate(8));
        assertFalse(knightsTour.validate(-1));
        assertFalse(knightsTour.validate(10));
    }

    @Test
    public void testGetNextMoves() {
        Board b = new Board(8);
        ArrayList<int[]> res0 = new ArrayList<int[]>();
        res0.add(new int[] {1, 2});
        res0.add(new int[] {2, 1});
        assertArrayEquals(res0.toArray(), knightsTour.getNextMoves(b, 0, 0).toArray());
        ArrayList<int[]> res1 = new ArrayList<int[]>();
        b.setAtPos(0, 3, 0);
        res1.add(new int[] {2, 3});
        res1.add(new int[] {3, 2});
        res1.add(new int[] {0, 3});
        assertArrayEquals(res1.toArray(), knightsTour.getNextMoves(b, 1, 1).toArray());
        b.setAtPos(-1, 3, 0);
        b.setAtPos(0, 1, 2);
        ArrayList<int[]> res2 = new ArrayList<int[]>();
        res2.add(new int[] {1, 6});
        res2.add(new int[] {2, 5});
        res2.add(new int[] {2, 3});
        assertArrayEquals(res2.toArray(), knightsTour.getNextMoves(b, 0, 4).toArray());
    }

    
}
