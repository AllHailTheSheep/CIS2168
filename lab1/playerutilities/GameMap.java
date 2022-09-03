package lab1.playerutilities;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {
    private static int XSIZE = 20;
    private static int YSIZE = 20;
    private static HashMap<Coordinate, Integer> grid = new HashMap<Coordinate, Integer>();
    private static ArrayList<Coordinate> keySet = new ArrayList<Coordinate>();
    public GameMap() {
        initializeGrid();
    }

    public void submitLookData(Coordinate pos, LookData data) {
        // TODO: make this fucntion load data into the map
        System.out.println(data.keySet());
        // System.out.println(pos.toString());
        // System.out.println(data.toString());
    }

    private void initializeGrid() {
        for (int x = 1; x  <= XSIZE; x++) {
            for (int y = 1; y <= YSIZE; y++) {
                // 5 is the number that represents unknown
                Coordinate c = new Coordinate(x, y);
                grid.put(c, 5);
                keySet.add(c);
            }
        }        
    }

    @Override
    public String toString() {
        ArrayList<Coordinate> revKeySet = new ArrayList<Coordinate>();
        for (int i = keySet.size() - 1; i >= 0; i--) {
            revKeySet.add(keySet.get(i));
        }
        StringBuffer sb = new StringBuffer();
        int i = 1;
        for (Coordinate key: revKeySet) {
            if (i == XSIZE) {
                sb.append(grid.get(key) + "\n");
                i = 1;
            } else {
                sb.append(grid.get(key) + " ");
                i++;
            }
        }
        return sb.toString();
    }
}
