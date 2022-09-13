package lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class lab2 {
    public static <T> Boolean unique(List<T> list) {
        HashMap<T, Integer> map = new HashMap<T, Integer>();
        for (T entry : list) {
            // see LE GALL Benoît's answer to https://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java.
            // note that because we use hashmaps, any classes must overrride equals() and hashCode() for this to work,
            // as thats what hashmaps use to tell if two objects are equal.
            map.merge(entry, 1, Integer::sum);
        }
        boolean result = true;
        for (T entry : list) {
            if (map.get(entry) != 1) {
                result = false;
            }
        }
        return result;
    }

    public static List<Integer> allMultiples(int divBy, List<Integer> list) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i : list) {
            if (i % divBy == 0) {
                result.add(i);
            }
        }
        return result;
    }

    public static List<String> allStringsOfSize(int size, List<String> list) {
        List<String> result = new ArrayList<String>();
        for (String s : list) {
            if (s.length() == size) {
                result.add(s);
            }
        }
        return result;
    }

    public static <T> Boolean permutations(List<T> list1, List<T> list2) {
        HashMap<T, Integer> map1 = new HashMap<T, Integer>();
        HashMap<T, Integer> map2 = new HashMap<T, Integer>();
        // by definition, a permutation would have identical HashMap<Element, Count>

        // see LE GALL Benoît's answer to https://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java.
        // note that because we use hashmaps, any classes must overrride equals() and hashCode() for this to work,
        // as thats what hashmaps use to tell if two objects are equal.
        for (T entry : list1) {
            map1.merge(entry, 1, Integer::sum);
        }
        for (T entry : list2) {
            map2.merge(entry, 1, Integer::sum);
        }
        return map1.equals(map2);
    }

    public static List<String> tokenization(String s) {
        // this regex replace everything not a space, character, number, or apostrophe (assuming whatever we are using
        // this for can process possessives)
        s = s.replaceAll("[-/]", " ");
        s = s.replaceAll("[^a-zA-Z0-9 ']", "");
        List<String> res = new ArrayList<String>(Arrays.asList(s.split(" ")));
        System.out.println(res.toString());
        return res;
    }

    public static <T> List<T> removeAll(List<T> src, List<T> toRemove) {
        for (T entry : toRemove) {
            // gotta say, java has some clean lambda shorthand syntax
            src.removeIf(entry::equals);
        }
        return src;
    }






}
