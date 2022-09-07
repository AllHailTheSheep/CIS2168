package lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import lab1.playerutilities.Coordinate;
import lab1.playerutilities.GameMap;
import lab1.playerutilities.LookData;

public class Person extends Creature {
  private static final Logger LOG = LogManager.getLogger(GameMap.class);
  public static HashMap<Integer, String> OBJECT_MAP = new HashMap<Integer, String>();
  public static HashMap<Integer, String> DIRECTIONS_MAP = new HashMap<Integer, String>();
  public static ArrayList<Integer> DIRECTIONS_LIST;
  private static GameMap GM = null;
  private static Integer moves = null;
  private static int TOTAL_MOVES = 0;

  public Person(Model model, int row, int column) {
    super(model, row, column);
    GM = new GameMap();
    moves = 0;
    mapInitializer();
  }

  int decideMove() {
    /* basic strat: move in a cricle towards the center collecting info until monster is sighted. once monster is sighted,
     * run an algorithm that finds the largest path around a group of clustered objects (within 2 blocks of each other) and
     * then just repeat that. unless really unlucky the monster should never catch us, however once i finish what i've described i
     * will try to add a var to keep track of the monster and a check to make sure the user is always x moves travel away from the monster.
     * 
    */
    // TODO: choose moves based off of game map
    LOG.info("Total moves played: " + TOTAL_MOVES);
    LOG.info("Move number " + moves + " in this game.");
    System.out.println("Total moves played: " + TOTAL_MOVES);
    System.out.println("Move number " + moves + " in this game.");
    LookData movesLookData = new LookData(this);
    GM.submitLookData(new Coordinate(this.column + 1, this.row + 1), movesLookData);
    int move = Model.random(0, 8);
    System.out.println(GM.toString());
    LOG.debug(GM.toString());
    moves++;
    TOTAL_MOVES++;
		return move;
  }
  
  public HashMap<Integer, Integer[]> lookAround(ArrayList<Integer> directions) {
    HashMap<Integer, Integer[]> result = new HashMap<Integer, Integer[]>();
    for (int direction : DIRECTIONS_MAP.keySet()) {
      Integer[] data = new Integer[2];
      data[0] = look(direction);
      data[1] = distance(direction);
      result.put(direction, data);
    }
    return result;
  }

  private void mapInitializer() {
    // directions initializers
    DIRECTIONS_MAP.put(0, "N");
    DIRECTIONS_MAP.put(1, "NE");
    DIRECTIONS_MAP.put(2, "E");
    DIRECTIONS_MAP.put(3, "SE");
    DIRECTIONS_MAP.put(4, "S");
    DIRECTIONS_MAP.put(5, "SW");
    DIRECTIONS_MAP.put(6, "W");
    DIRECTIONS_MAP.put(7, "NW");
    DIRECTIONS_MAP.put(8, "STAY");
    DIRECTIONS_LIST = new ArrayList<Integer>(DIRECTIONS_MAP.keySet());

    // object initializers
    OBJECT_MAP.put(0, "EDGE");
    OBJECT_MAP.put(1, "MONSTER");
    OBJECT_MAP.put(2, "PERSON");
    OBJECT_MAP.put(3, "BUSH");
    OBJECT_MAP.put(4, "SPACE");
    OBJECT_MAP.put(5, "UNKNOWN");
  }
}
