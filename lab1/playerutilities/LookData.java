package lab1.playerutilities;

import java.util.Collection;
import java.util.HashMap;

import lab1.Person;

public class LookData {
    // TODO: create better getters
    private HashMap<Integer, Integer[]> data;

    // ideally id define the data getting operation here but we would not be able to access look() or distance()
    public LookData(Person person) {
        data = person.lookAround(Person.DIRECTIONS_LIST);
    }

    public Collection<Integer[]> getInfo() {
        return data.values();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Integer direction : data.keySet()) {
            Integer[] info = data.get(direction);
            sb.append("Direction: " + Person.DIRECTIONS_MAP.get(direction) + ", ");
            sb.append("Object: " + Person.OBJECT_MAP.get(info[0]) + ", ");
            sb.append("Distance: " + info[1] + "\n");
        }
        return sb.toString();
    }
    

}
