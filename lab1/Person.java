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
  private Coordinate quadFarthestFromMonster = null;
  private Coordinate bestQuadrant = null;
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

    int move;
    if (bestQuadrant == null) {
      bestQuadrant = GM.determineShortest(pos, new ArrayList<Coordinate>(GM.QUADRANTS.values()));
    }
    if (GM.monsterSighted == -1 && !pos.within(bestQuadrant, 2)) {
      // monster has not been seen, move towards the closest quadrant
      LOG.info("Moving towards " + bestQuadrant.toString());
      System.out.println("Moving towards " + bestQuadrant.toString());
      move = GM.determineDirection(pos.copy(), bestQuadrant.copy());
    } else if (GM.monsterSighted == -1 && pos.within(bestQuadrant, 2)) {
      // TODO: make this bit do cirlces instead and see if it increases the grade
      LOG.info("Within 3 blocks of bestQuadrant and monster has not been seen, doing random shit");
      System.out.println("random move");
      move = Model.random(0, 7);
    } else {
      // monster has been seen
      LOG.info("Monster has been seen");
      System.out.println("monster has been seen");
      Coordinate monsterPos = GM.getMonsterPos();
      if (quadFarthestFromMonster == null || pos.within(quadFarthestFromMonster, 2)) {
        quadFarthestFromMonster = GM.determineFarthest(monsterPos, new ArrayList<Coordinate>(GM.QUADRANTS.values()));
        System.out.println("quadFarthestFromMonster set to " + quadFarthestFromMonster.toString());
        LOG.info("quadFarthestFromMonster set to " + quadFarthestFromMonster.toString());
        // find a bush to move around
      }
      // if move brings us closer to the monster, put a bush between us before moving to the best quadrant
      move = GM.determineDirection(pos.copy(), quadFarthestFromMonster.copy());
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
    int offset = 1;
    // deal with impossible moves
    while (!canMove(move)) {
      if (canMove((move + offset) % 8)) {
        move = (move + offset) % 8;
      } else if (canMove((move - offset) % 8)) {
        move = (move - offset) % 8;
      } else {
        offset++;
      }
    }
    LOG.info("Changed move to " + move);
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

    // object map initializers
    OBJECT_MAP.put(0, "EDGE");
    OBJECT_MAP.put(1, "MONSTER");
    OBJECT_MAP.put(2, "PERSON");
    OBJECT_MAP.put(3, "BUSH");
    OBJECT_MAP.put(4, "SPACE");
    OBJECT_MAP.put(5, "UNKNOWN");
  }
}
