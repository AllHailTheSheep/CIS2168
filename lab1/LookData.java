package lab1;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LookData extends HashMap<Integer, Integer[]> {
    private LinkedHashMap<Integer, Integer[]> data;

    // ideally id define the data getting operation here but we would not be able to access look() or distance(), speedBar
    // passing the person in is the next best thing.
    public LookData(Person person) {
        data = person.lookAround();
    }

    
    /** 
     * Gets the stored Integer[] for a given direction.
     * @param dir The direction for which data to retrieve.
     * @return Integer[] The retrieved data in form Integer[]:[object, distance]
     */
    public Integer[] getData(int dir) {
        return data.get(dir);
    }

    
    /** 
     * Returns a String representation of the object.
     * @return String String representation of the LookData.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Integer direction : data.keySet()) {
            Integer[] info = data.get(direction);
            sb.append("Direction: " + Person.DIRECTIONS_MAP.get(direction) + ",\n");
            sb.append("Object: " + Person.OBJECT_MAP.get(info[0]) + ",\n");
            sb.append("Distance: " + info[1] + "\n");
        }
        return sb.toString();
    }
}
