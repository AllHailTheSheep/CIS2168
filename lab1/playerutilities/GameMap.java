package lab1.playerutilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab1.Person;

public class GameMap {
    // TODO: finish commentating this file.
    private static final Logger LOG = LogManager.getLogger(GameMap.class);
    private int XSIZE = 21;
    private int YSIZE = 21;
    public int monsterSighted = -1;
    private static LinkedHashMap<Coordinate, Integer> grid = new LinkedHashMap<Coordinate, Integer>();
    private Coordinate monsterPos;
    public LinkedHashMap<String, Coordinate> QUADRANTS = new LinkedHashMap<String, Coordinate>();
    private LinkedHashMap<Double, Integer> ATAN2ANGLES = new LinkedHashMap<Double, Integer>();
    public ArrayList<Coordinate> BUSHES = new ArrayList<Coordinate>();

    public GameMap() {
        initializeMaps();
    }


    public int determineDirection(Coordinate pos, Coordinate dest) {
        pos.swap();
        dest.swap();
        List<Double> angles = new ArrayList<Double>(ATAN2ANGLES.keySet());
        Double atan2res = Math.atan2(dest.getY() - pos.getY(), dest.getX() - pos.getX());
        Double closest = closest(atan2res, angles);
        int res = ATAN2ANGLES.get(closest);
        return res;
    }

    public Double closest(Double of, List<Double> in) {
        // See dfa's answer to this question: https://stackoverflow.com/questions/1187352/find-closest-value-in-an-ordered-list
        Double min = Double.MAX_VALUE;
        Double closest = of;
        for (Double v : in) {
            final Double diff = Math.abs(v - of);
            if (diff < min) {
                min = diff;
                closest = v;
            }
        }
        return closest;
    }

    public Coordinate determineFarthest(Coordinate pos, ArrayList<Coordinate> coords) {
        LinkedHashMap<Double, Coordinate> map = new LinkedHashMap<Double, Coordinate>();
        for (Coordinate c : coords) {
            map.put(pos.distance(c), c);
        }
        return map.get(Collections.max(map.keySet()));
    }

    public Coordinate determineShortest(Coordinate pos, ArrayList<Coordinate> coords) {
        LinkedHashMap<Double, Coordinate> map = new LinkedHashMap<Double, Coordinate>();
        for (Coordinate c : coords) {
            map.put(pos.distance(c), c);
        }
        return map.get(Collections.min(map.keySet()));
    }

    /**
     * This function submits LookData for processing into the GameMap object. Note
     * that our GameMap sizes are 1 bigger
     * than the size of the map to deal with our 0-based indexing and edges in our
     * map. When passing in a position
     * coordinate, simply add 1 from row and column to account for this. See the
     * example in Person.decideMove().
     * 
     * @param pos  The players current position.
     * @param data The moves LookData.
     */
    public void submitLookData(Coordinate pos, LookData data) {
        // log info
        LOG.info("PLAYER POS: " + pos.toString());
        LOG.debug("LOOK DATA:\n" + data.toString());

        // iterate through the look data by direction
        for (int dir : Person.DIRECTIONS_LIST) {
            // get the directianal data from the LookData object
            Integer[] dir_data = data.getData(dir);
            // log debug info. hopefully this will never have to be looked at, but it came
            // in clutch earlier.
            LOG.debug("GOING DIR: " + Person.DIRECTIONS_MAP.get(dir));
            LOG.debug("DIRECTIONAL_DATA: [object: " + Person.OBJECT_MAP.get(dir_data[0]) + ", distance: " + dir_data[1]
                    + "]");

            // we move one step at a time towards the object, filling with space till we get
            // to the object.
            int distance = dir_data[1];
            int object = dir_data[0];
            // focus is the coord we are looking at
            Coordinate focus = pos.copy();
            for (int i = 1; i <= distance; i++) {
                focus = coordinateManipulator(focus, dir, 1);
                LOG.debug("FOCUS: " + focus.toString());
                if (i == distance) {
                    // place object
                    grid.put(focus, object);
                    if (object == 3) {
                        BUSHES.add(focus);
                    }
                    if (object == 1) {
                        monsterSighted++;
                        monsterPos = focus.copy();
                    }
                    if (focus.getX() > XSIZE || focus.getY() > YSIZE) {
                        LOG.error("Put object " + Person.OBJECT_MAP.get(object) + " at " + focus.toString());
                    }
                    LOG.debug("Put object " + Person.OBJECT_MAP.get(object) + " at " + focus.toString());
                    focus = pos.copy();
                } else {
                    // place space
                    grid.put(focus, 4);
                    LOG.debug("Put SPACE at " + focus.toString());
                }
            }
        }
    }

    /**
     * @param c
     * @param dir
     * @param steps
     * @return Coordinate
     */
    public Coordinate coordinateManipulator(Coordinate c, int dir, int steps) {
        // this function does not deal with boundaries and objects, it is not for
        // movement, just computing lookData
        switch (dir) {
            case 0:
                // move N
                c.addX(-steps);
                break;
            case 1:
                // move NE
                c.addX(-steps);
                c.addY(steps);
                break;
            case 2:
                // move E
                c.addY(steps);
                break;
            case 3:
                // move SE
                c.addY(steps);
                c.addX(steps);
                break;
            case 4:
                // move S
                c.addX(steps);
                break;
            case 5:
                // move SW
                c.addX(steps);
                c.addY(-steps);
                break;
            case 6:
                // move W
                c.addY(-steps);
                break;
            case 7:
                // move NW
                c.addY(-steps);
                c.addX(-steps);
                break;
            case 8:
                // do nothing
                break;
        }
        return c;
    }

    public Coordinate getMonsterPos() {
        return monsterPos;
    }

    public int getObjectAtLoc(Coordinate c) {
        return grid.get(c);
    }

    public int getXSize() {
        return XSIZE;
    }

    public int getYSize() {
        return YSIZE;
    }

    private void initializeMaps() {
        for (int x = 0; x <= XSIZE; x++) {
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
        QUADRANTS.put("N", new Coordinate((XSIZE / 2) + 1, (YSIZE / 4) + 1));
        QUADRANTS.put("NE", new Coordinate(3 * (XSIZE / 4) + 1, (YSIZE / 4) + 1));
        QUADRANTS.put("E", new Coordinate(3 * (XSIZE / 4) + 1, (YSIZE / 2) + 1));
        QUADRANTS.put("SE", new Coordinate(3 * (XSIZE / 4) + 1, 3 * (YSIZE / 4) + 1));
        QUADRANTS.put("S", new Coordinate((XSIZE / 2) + 1, 3 * (YSIZE / 4) + 1));
        QUADRANTS.put("SW", new Coordinate((XSIZE / 4) + 1, 3 * (YSIZE / 4) + 1));
        QUADRANTS.put("W", new Coordinate((XSIZE / 4) + 1, (YSIZE / 2) + 1));
        QUADRANTS.put("NW", new Coordinate((XSIZE / 4) + 1, (YSIZE / 4) + 1));
        for (String s : QUADRANTS.keySet()) {
            LOG.debug(s + QUADRANTS.get(s).toString());
        }
        
        Coordinate center = new Coordinate((XSIZE / 2) + 1, (YSIZE / 2) + 1);
        ArrayList<Integer> dirs = new ArrayList<Integer>(Person.DIRECTIONS_LIST);
        dirs.remove(8);
        for (int dir : dirs) {
            Coordinate coordInDir = QUADRANTS.get(Person.DIRECTIONS_MAP.get(dir));
            Double angle = Math.atan2(coordInDir.getY() - center.getY(), coordInDir.getX() - center.getX());
            ATAN2ANGLES.put(angle, dir);
            LOG.debug("Put " + angle + ", " + dir);
        }
    }

    /**
     * @param c
     * @return boolean
     */
    private boolean isEdge(Coordinate c) {
        if ((c.getX() == 0 || c.getX() == XSIZE) || (c.getY() == 0 || c.getY() == YSIZE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        int i = 0;
        for (Coordinate key : grid.keySet()) {
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
