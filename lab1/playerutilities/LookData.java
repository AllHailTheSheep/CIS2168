package lab1.playerutilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import lab1.Person;

public class LookData extends HashMap<Integer, Integer[]> {
    // TODO: create better getters
    private HashMap<Integer, Integer[]> data;

    // ideally id define the data getting operation here but we would not be able to access look() or distance()
    public LookData(Person person) {
        data = person.lookAround(Person.DIRECTIONS_LIST);
    }

    // Question for the Professor: why do I have to Override this here for LookDataObject.keySet() to work?
    // I would have thought that since I'm extending HashMap<Integer, Integer[]> I would be able to call .keySet() on
    // without this boilerplate code, but without this it only returns an empty set.
    @Override
    public Set<Integer> keySet() {
        return data.keySet();
    }

    @Override
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
