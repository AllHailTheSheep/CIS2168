package lab1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import lab1.playerutilities.Coordinate;
import lab1.playerutilities.GameMap;
import lab1.playerutilities.LookData;
import lib.utils;

public class Person extends Creature {
  public static HashMap<Integer, String> OBJECT_MAP = new HashMap<Integer, String>();
  public static HashMap<Integer, String> DIRECTIONS_MAP = new HashMap<Integer, String>();
  public static ArrayList<Integer> DIRECTIONS_LIST;
  public static Coordinate POSITION;
  private static GameMap GM = new GameMap();

  public Person(Model model, int row, int column) {
    super(model, row, column);
    mapInitializer();
    POSITION = new Coordinate(column, row);
  }

  int decideMove() {
    // TODO: choose moves based off of game map
    LookData movesLookData = new LookData(this);
    System.out.println(movesLookData.toString());
    GM.submitLookData(POSITION, movesLookData);
    int move = utils.get_int_input("Choose your direction: ", 0, 8, new Scanner(System.in));
    // System.out.println(GM.toString());
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
