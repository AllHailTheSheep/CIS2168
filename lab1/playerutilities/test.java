package lab1.playerutilities;

import lab1.Person;

public class test {
    public static void main(String[] args) {
        Person p1 = new Person(null, 20, 20);
        GameMap gm = new GameMap();
        for (Coordinate c : gm.QUADRANTS.values()) {
            for (Coordinate k : gm.QUADRANTS.values()) {
                StringBuffer sb = new StringBuffer();
                sb.append("FROM " + c .toString() + " TO " + k.toString());
                sb.append(" DIR: " + Person.DIRECTIONS_MAP.get(gm.determineDirection(c, k)) + "\n");
                System.out.println(sb.toString());
            }
        }
    }
    
}
