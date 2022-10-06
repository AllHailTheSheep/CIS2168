package lab2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class lab2Tests {
    @Test
    public void testUnique() {
        List<String> stringtest1 = new ArrayList<String>(Arrays.asList("String1", "String2", "String3"));
        assertEquals(true, lab2.unique(stringtest1));

        List<String> stringtest2 = new ArrayList<String>(Arrays.asList("String1", "String2", "String1"));
        assertEquals(false, lab2.unique(stringtest2));

        List<Integer> inttest1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
        assertEquals(true, lab2.unique(inttest1));

        List<Integer> inttest2 = new ArrayList<Integer>(Arrays.asList(1, 2, 1, 2, 5));
        assertEquals(false, lab2.unique(inttest2));

        List<HashMap<Integer, Integer>> maptest1 = new ArrayList<HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> test1map1 = new HashMap<Integer, Integer>();
        test1map1.put(1, 7);
        test1map1.put(1, 9);
        test1map1.put(2, 2);
        maptest1.add(test1map1);
        HashMap<Integer, Integer> test1map2 = new HashMap<Integer, Integer>();
        test1map2.put(1, 7);
        test1map2.put(1, 9);
        test1map2.put(2, 2);
        maptest1.add(test1map2);
        assertEquals(false, lab2.unique(maptest1));

        List<HashMap<Integer, Integer>> maptest2 = new ArrayList<HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> test2map1 = new HashMap<Integer, Integer>();
        test2map1.put(1, 7);
        test2map1.put(1, 9);
        test2map1.put(2, 2);
        maptest2.add(test2map1);
        HashMap<Integer, Integer> test2map2 = new HashMap<Integer, Integer>();
        test2map2.put(1, 7);
        test2map2.put(1, 9);
        test2map2.put(2, 3);
        maptest2.add(test2map2);
        assertEquals(true, lab2.unique(maptest2));
    }

    @Test
    public void testAllMultiples() {
        List<Integer> test1 = new ArrayList<Integer>(Arrays.asList(1, 20, 50, 72, 70, 15, 25005));
        List<Integer> res1 = new ArrayList<Integer>(Arrays.asList(20, 50, 70, 15, 25005));
        assertEquals(res1, lab2.allMultiples(5, test1));

        List<Integer> test2 = new ArrayList<Integer>(Arrays.asList(1, 3, 6, 9, 10, 11, 12, 12, 27));
        List<Integer> res2 = new ArrayList<Integer>(Arrays.asList(3, 6, 9, 12, 12, 27));
        assertEquals(res2, lab2.allMultiples(3, test2));

        List<Integer> test3 = new ArrayList<Integer>(Arrays.asList(1, 25, 2, 5, 30, 19, 57, 2, 25));
        List<Integer> res3 = new ArrayList<Integer>(Arrays.asList(25, 5, 30, 25));
        assertEquals(res3, lab2.allMultiples(5, test3));
    }

    @Test
    public void testAllStringsOfSizes() {
        List<String> test = new ArrayList<String>(Arrays.asList("String1", "String2", "StringOne", "StringTwo"));
        List<String> res1 = new ArrayList<String>(Arrays.asList("String1", "String2"));
        assertEquals(res1, lab2.allStringsOfSize(7, test));

        List<String> res2 = new ArrayList<String>(Arrays.asList("StringOne", "StringTwo"));
        assertEquals(res2, lab2.allStringsOfSize(9, test));

    }

    @Test
    public void testPermutations() {
        List<String> list1 = new ArrayList<String>(Arrays.asList("StringOne", "StringTwo"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("StringTwo", "StringOne"));
        assertEquals(true, lab2.permutations(list1, list2));

        List<Integer> list3 = new ArrayList<Integer>(Arrays.asList(1, 4, 1, 10 , 10 , 5));
        List<Integer> list4 = new ArrayList<Integer>(Arrays.asList(4, 1, 1, 5, 10, 10));
        assertEquals(true, lab2.permutations(list3, list4));

        list4.add(3);
        assertEquals(false, lab2.permutations(list3, list4));
    }

    @Test
    public void testTokenization() {
        String test1 = "\"Don't expect any super-crazy and/or creative test strings here,\" I said.";
        List<String> res1 = new ArrayList<String>(Arrays.asList("Don't", "expect", "any", "super", "crazy", "and", "or",
                            "creative", "test", "strings", "here", "I", "said"));
        assertEquals(res1, lab2.tokenization(test1));

        String test2 = "Hello, world!";
        List<String> res2 = new ArrayList<String>(Arrays.asList("Hello", "world"));
        assertEquals(res2, lab2.tokenization(test2));
    }

    @Test
    public void testRemoveAll() {
    List<String> test1 = new ArrayList<String>(Arrays.asList("String1" , "String2", "String3", "String4"));
    List<String> toRemove1 = new ArrayList<String>(Arrays.asList("String2", "String4"));
    List<String> res1 = new ArrayList<String>(Arrays.asList("String1", "String3"));
    assertEquals(res1, lab2.removeAll(test1, toRemove1));

    List<Integer> test2 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7, 6));
    List<Integer> toRemove2 = new ArrayList<Integer>(Arrays.asList(1, 7, 8));
    List<Integer> res2 = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 6));
    assertEquals(res2, lab2.removeAll(test2, toRemove2));
    }

}
