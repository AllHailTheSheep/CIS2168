package lab1.playerutilities;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab1.Person;

public class GameMap {
    // TODO: create hashmap to hold move patterns, then implement the comment in Person.decideMove()
    private static final Logger LOG = LogManager.getLogger(GameMap.class);
    // note that our sizes are 1 bigger than the size of the map to deal with our 0-based indexing and edges in our map.
    // when passing in a position coordinate, simply remove 1 from row and column. see the example in Person.decideMove()
    private static int XSIZE = 21;
    private static int YSIZE = 21;
    private static boolean monsterSighted = false;
    private static LinkedHashMap<Coordinate, Integer> grid = new LinkedHashMap<Coordinate, Integer>();

    public GameMap() {
        initializeGrid();
    }

    public void submitLookData(Coordinate pos, LookData data) {
        LOG.info("PLAYER POS: " + pos.toString());
        LOG.debug("LOOK DATA:\n" + data.toString());
        for (int dir : Person.DIRECTIONS_LIST) {
            LOG.debug("GOING DIR: " + Person.DIRECTIONS_MAP.get(dir));
            Integer[] dir_data = data.getData(dir);
            LOG.debug("DIRECTIONAL_DATA: [object: " + Person.OBJECT_MAP.get(dir_data[0]) + ", distance: " + dir_data[1] + "]");
            Coordinate focus = pos.copy();
            int object = dir_data[0];
            if (object == 1) {
                monsterSighted = true;
            }
            int distance = dir_data[1];
            // for each step in a direction, fill with space till n-1 and end with the object
            for (int i = 1; i <= distance; i++) {
                focus = coordinateManipulator(focus, dir, 1);
                LOG.debug("FOCUS: " + focus.toString());
                if (i == distance) {
                    grid.put(focus, object);
                    LOG.debug("Put object " + Person.OBJECT_MAP.get(object) + " at " + focus.toString());
                    focus = pos.copy();
                } else {
                    grid.put(focus, 4);
                    LOG.debug("Put SPACE at " + focus.toString());
                }

            }
        }
    }

    public Coordinate coordinateManipulator(Coordinate c, int dir, int steps) {
        // this function does not deal with boundaries and objects, it is not for movement, just computing lookData
        switch (dir) {
            case 0:
                // move N
                // neg y
                c.addY(-steps);
                break;
            case 1:
                // move NE
                // neg y, pos x
                c.addY(-steps);
                c.addX(steps);
                break;
            case 2:
                // move E
                // pos x
                c.addX(steps);
                break;
            case 3:
                // move SE
                // pos x, pos y
                c.addX(steps);
                c.addY(steps);
                break;
            case 4:
                // move S
                // pos Y
                c.addY(steps);
                break;
            case 5:
                // move SW
                // pos y, neg x
                c.addX(-steps);
                c.addY(steps);
                break;
            case 6:
                // move W
                // neg x
                c.addX(-steps);
                break;
            case 7:
                // move NW
                // neg x, neg y
                c.addX(-steps);
                c.addY(-steps);
                break;
            case 8:
                // do nothing
                break;
        }
        return c;
    }

    private void initializeGrid() {
        for (int x = 0; x  <= XSIZE; x++) {
            for (int y = 0; y <= YSIZE; y++) {
                // 5 is the number that represents unknown, 0 is an edge
                Coordinate c = new Coordinate(x, y);
                if (isEdge(c) == true) {
                    grid.put(c, 0);
                } else {
                    grid.put(c, 5);
                }
            }
        }        
    }

    private boolean isEdge(Coordinate c) {
        if ((c.getX() == 0 || c.getX() == XSIZE) || (c.getY() == 0 || c.getY() == YSIZE)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        int i = 0;
        for (Coordinate key: grid.keySet()) {
            if (i == XSIZE) {
                sb.append(grid.get(key) + "\n");
                i = 0;
            } else {
                sb.append(grid.get(key) + " ");
                i++;
            }
        }
        return sb.toString();
    }
}
