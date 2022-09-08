package lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import lab1.playerutilities.Coordinate;
import lab1.playerutilities.GameMap;
import lab1.playerutilities.LookData;

public class Person extends Creature {
  // note there's a lot happening so i've seperated the debug and info logs
  private static final Logger LOG = LogManager.getLogger(GameMap.class);
  public static HashMap<Integer, String> OBJECT_MAP = new HashMap<Integer, String>();
  public static HashMap<Integer, String> DIRECTIONS_MAP = new HashMap<Integer, String>();
  public static ArrayList<Integer> DIRECTIONS_LIST;
  public static ArrayList<Integer> SMALL_CIRCLE = new ArrayList<Integer>();
  private boolean RUNNING = false;
  private int STARTEDRUNNING = -1;
  private Coordinate bush = new Coordinate(-1, -1);
  private GameMap GM = null;
  private Integer moves_num = null;

  /** 
   * IDEAS FOR IMPROVEMENT:
   * create a seperate class to hold moves and move generation tools
   * add a DANGER_ZONE integer that is the minimum moves from the monster a move has to guarantee to be accepted.
   */ 

  public Person(Model model, int row, int column) {
    super(model, row, column);
    // the things initialized here are not persistent across games, so everything works when the grader is run.
    initializeMaps();
    GM = new GameMap();
    moves_num = 0;
  }

  
  /** 
   * This function returns our move.
   * @return int The direction to move.
   */
  int decideMove() {
    /* basic strat: move in a cricle towards the center collecting info until monster is sighted. once monster is 
     * sighted, run an algorithm that finds the largest path around a group of clustered objects (within 2 blocks of
     * each other) and then just repeat that. unless really unlucky the monster should never catch us, however once i
     * finish what i've described i will try to add a var to keep track of the monster and a check to make sure the
     * user is always x moves travel away from the monster.
     */
    // log some info
    LOG.info("Move number " + moves_num + " in this game.");
    // print the same
    // System.out.println("Total moves played: " + TOTAL_MOVES);
    // System.out.println("Move number " + moves_num + " in this game.");

    // initialize, collect, and submit our moves LookData
    LookData movesLookData = new LookData(this);
    // the +1 is for our edge offset, as described in the GameModel
    Coordinate pos = new Coordinate(this.row + 1, this.column + 1);
    GM.submitLookData(pos, movesLookData);


    // TODO: Find a better way to do this.
    Integer move = null;
    if (!RUNNING) {
      if (GM.monsterSighted == -1) {
        System.out.println("go to nearest marker");
        move = GM.determineDirection(pos, GM.determineShortest(pos, new ArrayList<Coordinate>(GM.QUADRANTS.values())));
      } else if (GM.monsterSighted == 0 ) {
        System.out.println("set bush and move towards it");
        bush = GM.determineShortest(pos, GM.BUSHES);
        move = GM.determineDirection(pos, bush.copy());
        GM.monsterSighted++;
      } else if (GM.monsterSighted > 0 && pos.distance(bush.copy()) > 2) {
        // why do coords keep flipping?
        System.out.println("Move towards bush " + bush.toString() +  pos.distance(bush.copy()));
        move = GM.determineDirection(pos, bush);
      } else if (GM.monsterSighted > 0 && pos.distance(bush) <= 2) {
        // TODO: troubleshoot this
        if (pos.distance(GM.getMonsterPos()) > 6) {
          // circle bush
          System.out.println("circling bush " + pos.distance(bush));
          int startDir = GM.determineDirection(pos, bush);
          for (int i = 0; i < 8; i++) {
            if (pos.distance(GM.coordinateManipulator(pos.copy(), i, 1)) < 2) {
              move = i;
            }
          }
        } else if (pos.distance(GM.getMonsterPos()) <= 6 && pos.distance(GM.getMonsterPos()) >= 3) {
          System.out.println("waiting for monster to get within 2: " + pos.distance(GM.getMonsterPos()));
          move = 8;
        } else if (pos.distance(GM.getMonsterPos()) <= 3) {
          System.out.println("We are now running");
          STARTEDRUNNING = moves_num;
          RUNNING = true;
        }
      } else {
        System.out.println("This happened");
      }
    }
    
    if (RUNNING) {
      // make sure this move is away from the monster
      if (STARTEDRUNNING == moves_num) {
        ArrayList<Coordinate> coords = GM.BUSHES;
        ArrayList<Coordinate> toRemove = new ArrayList<Coordinate>();
        for (Coordinate c : coords) {
          if (c.distance(pos) > c.distance(GM.getMonsterPos())) {
            toRemove.add(c);
          }
        }
        for (Coordinate c : toRemove) {
          coords.remove(c);
        }
        bush = GM.determineFarthest(GM.getMonsterPos(), coords);
      } 
      move = GM.determineDirection(pos, bush);
      if (pos.distance(GM.getMonsterPos()) >= 6) {
        System.out.println("since we are at " + pos.toString() + " and monst is at " + GM.getMonsterPos().toString() + " set running to false as distance is " + pos.distance(GM.getMonsterPos()));
        RUNNING = false;
      }
    }

    // log end of move data, and increment move counter
    // System.out.println(GM.toString());
    LOG.info(GM.toString());
    moves_num++;
    int res = alterImpossibleMove(move);
    System.out.println("move: " + move + " res: " + res);
    System.out.println();
		return res;
  }

  private int alterImpossibleMove(int move) {
    if (!canMove(move)) {
      if (canMove(move + 1)) {
        return move + 1;
      } else if (canMove(move - 1)) {
        return move - 1;
      } else if (canMove(move + 2)) {
        return move + 2;
      } else if (canMove(move - 2)) {
        return move - 2;
      }
    }
    return move;
  }


  /** 
   * This function returns a HashMap containing the direction, the object and the distance to the object.
   * @return HashMap<Integer, Integer[]> The HashMap in format <Integer:direction, Integer[]:[object, distance]>
   */
  public HashMap<Integer, Integer[]> lookAround() {
    HashMap<Integer, Integer[]> result = new HashMap<Integer, Integer[]>();
    for (int direction : DIRECTIONS_MAP.keySet()) {
      Integer[] data = new Integer[2];
      data[0] = look(direction);
      data[1] = distance(direction);
      result.put(direction, data);
    }
    return result;
  }

  /** 
   * This function initalizes the constant maps we use for reference.
   */
  private void initializeMaps() {
    // direction map initializers
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

    SMALL_CIRCLE.add(1);
    SMALL_CIRCLE.add(3);
    SMALL_CIRCLE.add(5);
    SMALL_CIRCLE.add(7);

    // object map initializers
    OBJECT_MAP.put(0, "EDGE");
    OBJECT_MAP.put(1, "MONSTER");
    OBJECT_MAP.put(2, "PERSON");
    OBJECT_MAP.put(3, "BUSH");
    OBJECT_MAP.put(4, "SPACE");
    OBJECT_MAP.put(5, "UNKNOWN");
  }
}
