package lab5.KnightsTour;

import static org.junit.Assert.*;

import java.net.CacheRequest;
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
        b.setAtPos(-1, 3, 0);
    }

    @Test public void testCalculateDegree() {
        Board b = new Board(8);
        assertEquals(2, knightsTour.calculateDegree(b, 0, 0));
        assertEquals(2, knightsTour.calculateDegree(b, 0, 7));
        assertEquals(2, knightsTour.calculateDegree(b, 7, 0));
        assertEquals(2, knightsTour.calculateDegree(b, 7, 7));
        assertEquals(3, knightsTour.calculateDegree(b, 6, 7));
        assertEquals(8, knightsTour.calculateDegree(b, 4, 4));
        b.setAtPos(0, 7, 5);
        assertEquals(2, knightsTour.calculateDegree(b, 6, 7));
        b.setAtPos(-1, 7, 5);
        b.setAtPos(44, 6, 1);
        b.setAtPos(44, 2, 1);
        assertEquals(6, knightsTour.calculateDegree(b, 4, 2));
    }

    
}
