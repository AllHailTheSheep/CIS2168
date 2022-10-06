package lab5.KnightsTour;

import java.util.Scanner;

import lab5.Board;

public class knightsTour {
    /*
     * sources:
     *      https://en.wikipedia.org/wiki/Knight%27s_tour
     *      https://github.com/douglassquirrel/warnsdorff/blob/master/SGKnightsTourPaper.pdf
     *      https://www.wolframcloud.com/objects/nbarch/2018/10/2018-10-10r6l3m/Knight.nb
     * this is an implemtation utilizing warnsdoff's heuristic, as well as roth's
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
     *  of the assignment, as even without the rule this algorithm will work up to size 76,
     * 76
     */

    // these are changes in (x, y) coordinates relative to the current position.
    // note order does not matter since we will need to sort based on degrees
    // anyways
    public static int[][] MOVES = { { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 }, { -2, 1 },
            { -1, 2 } };

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer x = getInput("What is the x and y starting coordinate?", in);
        Integer y = getInput(null, in);

        runAlgorithm(x, y, 8, 8);

    }

    private static Integer getInput(String prompt, Scanner in) {
        System.out.print(prompt);
        
        Integer res = null;
        if (in.hasNextInt()){
            res = in.nextInt();
        } else {
            System.out.println("Input must be an integer!");
            res = getInput(prompt, in);
        }
        in.close();
        return res;
    }

    public static void runAlgorithm(int x, int y, int r, int c) {
        Board b = new Board(r, c);
        System.out.println(b.toString());

    }
}