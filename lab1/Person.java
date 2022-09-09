package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Person extends Creature {
  public static HashMap<Integer, String> OBJECT_MAP = new HashMap<Integer, String>();
  public static HashMap<Integer, String> DIRECTIONS_MAP = new HashMap<Integer, String>();
  public static ArrayList<Integer> DIRECTIONS_LIST;
  public ArrayList<Coordinate> BUSHES = new ArrayList<Coordinate>();
  public Coordinate MONSTER = new Coordinate(-1, -1);
  public Coordinate POS = new Coordinate(-1, -1);
  public boolean canSeeMonster = false;
  public int MONSTERDIR = -1;
  
  public Person(Model model, int row, int column) {
    super(model, row, column);
    initializeMaps();
  }
    
  int decideMove() {
    POS.setY(this.row);
    POS.setX(this.column);

    LookData movesLookData = new LookData(this);
    // System.out.println(movesLookData.toString());
    // parse move data
    for (Integer dir : DIRECTIONS_LIST) {
      Integer[] data = movesLookData.getData(dir);
      if (data[0] == 1) {
        // add to monster pos
        Coordinate mons_pos = calculateNewCoord(dir, data[1]);
        // System.out.println("SAW MONSTER AT: " + mons_pos.toString());
        MONSTER.setX(mons_pos.getX());
        MONSTER.setY(mons_pos.getY());
        MONSTERDIR = dir;
        canSeeMonster = true;
      } else if (data[0] == 3) {
        // add bush pos
        Coordinate bush_pos = calculateNewCoord(dir, data[1]);
        BUSHES.add(bush_pos);
        // System.out.println("BUSH at " + bush_pos.toString());
      }
    }

    Integer move = null;
    if (canSeeMonster) {
      // determine which direction the monster is and move out of sight
      // TODO: make this so we dont have to worry about moving towards the monster
      // System.out.println(DIRECTIONS_MAP.get(MONSTERDIR));
      ArrayList<Integer> acceptableDirs = getAcceptableDirs(); // dirs that are -1 and 1 from monster
      int offset = 1;
      while(!canMove(turn(MONSTERDIR, offset)) || acceptableDirs.contains(turn(MONSTERDIR, offset))) {
        offset++;
      } // grades to 70%, escapes 50% of the time

      move = turn(MONSTERDIR, offset);
      // System.out.println("MOVE: " + DIRECTIONS_MAP.get(move));
      canSeeMonster = false;
    } else {
      move = 8;
    }
		return move;
  }

  private ArrayList<Integer> getAcceptableDirs() {
    ArrayList<Integer> acceptableDirs = new ArrayList<Integer>();
    acceptableDirs.add(turn(MONSTERDIR, -1));
    acceptableDirs.add(turn(MONSTERDIR, 1));
    // acceptableDirs.add(turn(MONSTERDIR, 4));
    // acceptableDirs.add(turn(MONSTERDIR, 3));
    // acceptableDirs.add(turn(MONSTERDIR, 5));
    //System.out.println("MONSTER DIR: " + DIRECTIONS_MAP.get(MONSTERDIR));
    // for (int dir : acceptableDirs) { 
    //   System.out.println(DIRECTIONS_MAP.get(dir));
    // }
    return acceptableDirs;  
  }


  private int turn(int direction, int number) {
      int mod = (direction + number) % (7 - 0 + 1);
      if (mod >= 0) return mod;
      else return 8 + mod;
  }

  private Coordinate calculateNewCoord(int dir, int distance) {
    Coordinate loc = new Coordinate(row, column);
    switch (dir) {
      case 0:
        loc.setX(this.row - distance);
        break;
      case 1:
        loc.setX(this.row - distance);
        loc.setY(this.column + distance);
        break;
      case 2:
        loc.setY(this.column + distance);
        break;
      case 3:
        loc.setX(this.row + distance);
        loc.setY(this.column + distance);
        break;
      case 4:
        loc.setX(this.row + distance);
        break;
      case 5:
        loc.setX(this.row + distance);
        loc.setY(this.column - distance);
        break;
      case 6:
        loc.setY(this.column - distance);
        break;
      case 7:
        loc.setX(this.row - distance);
        loc.setY(this.column - distance);
        break;
      case 8:
        break;
    }
    // i realized i did these backwards but thats why i implemented this.
    loc.swap();
    return loc;
  }


  /** 
   * This function returns a HashMap containing the direction, the object and the distance to the object.
   * @return HashMap<Integer, Integer[]> The HashMap in format <Integer:direction, Integer[]:[object, distance]>
   */
  public LinkedHashMap<Integer, Integer[]> lookAround() {
    LinkedHashMap<Integer, Integer[]> result = new LinkedHashMap<Integer, Integer[]>();
    for (int direction : DIRECTIONS_MAP.keySet()) {
      Integer[] data = new Integer[2];
      data[0] = look(direction);
      data[1] = distance(direction);
      result.put(direction, data);
    }
    return result;
  }

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
