package lab4;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {
    @Test
    public void testIsInMaze() {
        assertEquals(false, MazeGridPanel.isInMaze(-1, 0));
        assertEquals(true, MazeGridPanel.isInMaze(1, 0));
    }
}
