package lab5.KnightsTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.lang.Math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lab5.Board;

class NoNextMoves extends Exception {
    public NoNextMoves(String errMsg, Throwable err) {
        super(errMsg, err);
    }
}

public class knightsTour {
    /*
     * sources:
     *      https://en.wikipedia.org/wiki/Knight%27s_tour
     *      https://github.com/douglassquirrel/warnsdorff/blob/master/SGKnightsTourPaper.pdf
     *      https://www.wolframcloud.com/objects/nbarch/2018/10/2018-10-10r6l3m/Knight.nb
     * this is an implemtation utilizing warnsdorff's heuristic, as well as roth's
     * tie-breaking rule. the premise is:
     * 
     * set the passed in position as initial position
     * for the number of sqaures on board:
     *      find positions that can be moved to and have not been visited
     *      move to position with minimum degree* (determine ties by choosing position
     *              with the farthest euclidean distance from the center of the board)
     *      set current position to move number
     * return board
     * 
     * * degree in this case is the amount of possible moves from that square
     * (remember not to count sqaures already visited)
     * 
     * some other implementation notes:
     * this algorithm should solve the problem on a board up to size 2000, 2000 with
     * a 1% fail rate. there is really no point in implmenting roth's rule in the case
     * of the assignment, as even without the rule this algorithm will work up to size 76,
     * 76, but it is simple enough to do and i enjoy making things more complicated.
     * i have tested this successfully out to board size 15000, 15000, though i would like to do some more tests
     * soon on a better system, and i don't think it is guaranteed to work every time for every starting position.
     * As far as I can tell, it should always work if the x and y coordinates are both even.
     */

    // these are changes in (x, y) coordinates relative to the current position.
    // note order does not matter since we will need to sort based on degrees
    // anyways
    public static int[][] MOVES = {{1, 2}, {2, 1}, {2, -1},  {1, -2}, {-1, -2}, {-2, -1}, {-2, 1},
            {-1, 2}};

    // careful with the log, it will quickly get VERY large with large board sizes (i've hit 7 gigabytes, not to
    // mention that logging WILL be the slowest part of the algorithm).
    // private static Logger log = LogManager.getLogger();

    public static final Integer SIZE = 8;
    public static final int[] CENTER = {SIZE/2, SIZE/2};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer x = getInput("What is the x starting coordinate? ", in);
        in.nextLine();
        Integer y = getInput("What is the y starting coordinate? ", in);
        in.close();
        System.out.println(runAlgorithm(x, y));
    }


    public static boolean runAlgorithm(int x, int y) {
        // Question for the professor: I know Java does not have any form of default parameters. Is there any workaround?
        // I would love to have a default parameter for printing output (path vs final board output or none) but I don't
        // know how Java accomplishes this other than overloading.
        LinkedList<int[]> path = new LinkedList<int[]>();
        Board b = new Board(SIZE);
        int move = 0;
        b.setAtPos(move, x, y);
        for (int i = 0; i < SIZE * SIZE - 1; i++) {
            // log.trace("i = " + i);
            // log.info("x: " + x + ", y: " + y);
            // log.info("\n" + b.toString());
            // determine movable sqaures
            ArrayList<int[]> nextMoves = getNextMoves(b, x, y);
            // log.debug("possible moves: " + nextMoves.size());
            // sort movable sqaures by degree
            Map<int[], Integer> movesToDegreeMap = sortMovesByDegree(b, x, y, nextMoves);
            // choose sqaure to move to, breaking ties by greatest euclidean distance to center
            ArrayList<int[]> bestMoveOrMoves = null;
            try {
                bestMoveOrMoves = chooseMove(movesToDegreeMap);
            } catch (NoNextMoves e) {
                // TODO: make this return false for testing
                System.err.println("Unable to find a next move. Ask the developer to implement a backtracking algorithm. " +
                "For now, try a different starting position. Hint: make sure the starting position is coordinates are " +
                "either both odd or both even.");
                System.exit(-1);
            }
            int[] nextMove = null;
            if (bestMoveOrMoves.size() == 1) {
                nextMove = bestMoveOrMoves.get(0);
            } else {
                // log.debug("Tie in move degrees: using Roth's rule");
                int[] best_key = null;
                double max_distance = Double.MIN_VALUE;
                for (int[] m : bestMoveOrMoves) {
                    double distance = calculateEuclideanDistance(m[0], m[1], CENTER[0], CENTER[1]);
                    // log.debug("[" + m[0] + "," + m[1] + "]: " + distance);
                    if (distance > max_distance) {
                        best_key = m;
                        max_distance = distance;
                    }
                }
                nextMove = best_key;
            }
            // log.debug("Next move: [" + nextMove[0] + ", " + nextMove[1] + "]");
            path.add(nextMove);
            // move x and y
            x = nextMove[0];
            y = nextMove[1];
            // set new postition to move number
            b.setAtPos(++move, x, y);
        }
        // check to see if program reached the correct solution
        boolean success = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (b.getAtPos(i, j) == -1) {
                    success = false;
                }
            }
        }
        // boards over size 31 usually don't print super well within the users terminal
        if (SIZE < 32) {
            System.out.println(b.toString());
        }
        StringBuilder sb = new StringBuilder();
        for (int[] p : path) {
            sb.append("[" + p[0] + ", " + p[1] + "], ");
        }
        System.out.println(sb.toString());
        return success;
    }

    

    private static ArrayList<int[]> chooseMove(Map<int[], Integer> movesMap ) throws NoNextMoves {
        // TODO: add backtracking so that if we get to this point we just move back till we can move to a different
        // position (this should guarantee that we always get a solution)
        Integer low = null;
        try {
            low = (int) movesMap.values().toArray()[0];
        } catch (IndexOutOfBoundsException e) {
            throw new NoNextMoves(null, e);
        }
        ArrayList<int[]> toRemove = new ArrayList<int[]>();
        for (Entry<int[], Integer> e : movesMap.entrySet()) {
            if (movesMap.get(e.getKey()) != low) {
                toRemove.add(e.getKey());
            }
        }
        for (int[] e : toRemove) {
            movesMap.remove(e);
        }
        ArrayList<int[]> bestMoveOrMoves = new ArrayList<int[]>();
        for (Entry<int[], Integer> e : movesMap.entrySet()) {
            bestMoveOrMoves.add(e.getKey());
        }
        return bestMoveOrMoves;
    }

    public static double calculateEuclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.hypot(x1-x2, y1-y2);
    }
    

    private static Map<int[], Integer> sortMovesByDegree(Board b, int x, int y, ArrayList<int[]> moves) {
        HashMap<int[], Integer> map = new HashMap<int[], Integer>();
        for (int[] m: moves) {
            Integer degree = calculateDegree(b, m[0], m[1]);
            map.put(m, degree);
            // log.debug("Degree of [" + m[0] + ", " + m[1] + "]: " + degree);
        }
        Map<int[], Integer> m = sortByValue(map);
        return m;
    }

    public static int calculateDegree(Board b, int x, int y) {
        return getNextMoves(b, x, y).size();
    }

    
    public static ArrayList<int[]> getNextMoves(Board b, int x, int y) {
        ArrayList<int[]> possible_moves = new ArrayList<int[]>();
        for (int i = 0; i < MOVES.length; i++) {
            int x_proj = x + MOVES[i][0];
            int y_proj = y + MOVES[i][1];
            // a move is valid if we have not been to that square, and both coords are within the board
            if (validate(x_proj) && validate(y_proj) && b.getAtPos(x_proj, y_proj) == -1) {
                possible_moves.add(new int[]{x_proj, y_proj});
            }
        }
        return possible_moves;
    }


    private static Integer getInput(String prompt, Scanner in) {
        if (prompt != null ){
            System.out.print(prompt);
        }
        Integer res = null;
        if (in.hasNextInt()){
            res = in.nextInt();
            if (!validate(res)) {
                System.out.println("Input must be from 0 to size - 1!");
                in.nextLine();
                res = getInput(prompt, in);
            }
        } else {
            System.out.println("Input must be an integer!");
            in.nextLine();
            res = getInput(prompt, in);
            if (!validate(res)) {
                in.nextLine();
                res = getInput(prompt, in);
            }
        }
        return res;
    }

    public static boolean validate(int i) {
        if (i < 0 || i >= SIZE) {
            return false;
        }
        return true;
    }

    public static HashMap<int[], Integer> sortByValue(HashMap<int[], Integer> hm) {
        // this code was shamelessly stolen from Saurav Jain's article https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
        // i'm certain i could write this myself but why bother reinventing the wheel when it's not integral to the assignment itself

        // Create a list from elements of HashMap
        List<Map.Entry<int[], Integer> > list = new LinkedList<Map.Entry<int[], Integer> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<int[], Integer>>() {
            public int compare(Map.Entry<int[], Integer> o1,  Map.Entry<int[], Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<int[], Integer> temp = new LinkedHashMap<int[], Integer>();
        for (Map.Entry<int[], Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    
    
}

