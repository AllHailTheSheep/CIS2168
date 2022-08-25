package lib;

import java.util.ArrayList;
import java.util.Scanner;

public class utils {
    // set this to see detailed error messages on misinput
    private static final boolean DEBUG = true;

    
    // input functions
    /**
     * This function returns a string that the user inputted.
     * It will re-run if the input cannot be parsed.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param in The pre-initialized Scanner object to read from.
     * @return String The string that the user inputted.
     */
    public static String get_input(String prompt, Scanner in) {
        System.out.print(prompt);
        String result;
        try {
            result = in.nextLine();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered a string?");
            }
            result = get_input(prompt, in);
        }
        return result;
    }

    
    /** 
     * This function returns a integer that the user inputted.
     * It will re-run if the input cannot be parsed.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param in The pre-initialized Scanner object to read from.
     * @return int The integer that the user inputted.
     */
    public static int get_int_input(String prompt, Scanner in) {
        System.out.print(prompt);
        int result;
        try {
            result = in.nextInt();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered an integer?");
            }
            result = get_int_input(prompt, in);
        }
        return result;
    }

    
    /** 
     * This function returns a double that the user inputted.
     * It will re-run if the input cannot be parsed.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param in The pre-initialized Scanner object to read from.
     * @return double The double that the user inputted.
     */
    public static double get_double_input(String prompt, Scanner in) {
        System.out.print(prompt);
        double result;
        try {
            result = in.nextDouble();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered a double?");
            }
            result = get_double_input(prompt, in);
        }
        return result;
    }

    
    // validating input functions
    /**
     * This function validates returns a String that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param expected_input An ArrayList<String> that contains the acceptable inputs.
     * @param in The pre-initialized Scanner object to read from.
     * @return String The validated string that the user inputted.
     */
    public static String get_input(String prompt, ArrayList<String> expected_input, Scanner in) {
        System.out.print(prompt);
        String result;
        try {
            result = in.nextLine();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered a string?");
            }
            result = get_input(prompt, expected_input, in);
        }
        if (expected_input.contains(result.toLowerCase())) {
            return result;
        } else {
            result = get_input(prompt, expected_input, in);
        }
        return result;
    }

    
    /**
     * This function validates returns a String that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param expected_input An ArrayList<String> that contains the acceptable inputs.
     * @param in The pre-initialized Scanner object to read from.
     * @param print Boolean to control the printing of expected inputs.
     * @return String
     */
    public static String get_input(String prompt, ArrayList<String> expected_input, Scanner in, boolean print) {
        System.out.print(prompt);
        if (print) {
            System.out.println("Expected inputs: " + expected_input.toString());
        }
        String result;
        try {
            result = in.nextLine();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered a string?");
            }
            result = get_input(prompt, expected_input, in, print);
        }
        if (expected_input.contains(result.toLowerCase())) {
            return result;
        } else {
            result = get_input(prompt, expected_input, in, print);
        }
        return result;
    }


    
    /** 
     * This function validates and returns a integer that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param expected_input An ArrayList<Integer> containing the acceptable inputs.
     * @param in The pre-initialized Scanner object to read from.
     * @return int The validated integer from the user.
     */
    public static int get_int_input(String prompt, ArrayList<Integer> expected_input, Scanner in) {
        System.out.print(prompt);
        int result;
        try {
            result = in.nextInt();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered an integer?");
            }
            result = get_int_input(prompt, expected_input, in);
        }
        if (expected_input.contains(result)) {
            return result;
        } else {
            get_int_input(prompt, expected_input, in);
        }
        return result;
    }

    
    /** 
     * This function validates and returns a integer that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param expected_input An ArrayList<Integer> containing the acceptable inputs.
     * @param in The pre-initialized Scanner object to read from.
     * @param print Boolean to control the printing of the expected values.
     * @return int
     */
    public static int get_int_input(String prompt, ArrayList<Integer> expected_input, Scanner in, boolean print) {
        System.out.print(prompt);
        if (print) {
            System.out.println("Expected inputs: " + expected_input.toString());
        }
        int result;
        try {
            result = in.nextInt();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered an integer?");
            }
            result = get_int_input(prompt, expected_input, in);
        }
        if (expected_input.contains(result)) {
            return result;
        } else {
            get_int_input(prompt, expected_input, in);
        }
        return result;
    }

    
    /**
     * This function validates and returns a integer that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param low The lower boundary (inclusive)
     * @param high The higher boundary (inclusive)
     * @param in The pre-initialized Scanner object to read from.
     * @return int The validated integer from the user.
     */
    public static int get_int_input(String prompt, int low, int high, Scanner in) {
        System.out.print(prompt);
        int result;
        try {
            result = in.nextInt();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered an integer?");
            }
            result = get_int_input(prompt, low, high, in);
        }
        if (low <= result && result <= high) {
            return result;
        } else {
            get_int_input(prompt, low, high, in);
        }
        return result;
    }

    
    /** 
     * This function validates and returns a integer that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt The prompt to be presented to the user.
     * @param low The lower bound (inclusive)
     * @param high The higher bound (inclusive)
     * @param in The pre-initialized Scanner object to read from.
     * @param print Boolean controlling the printing of the accepted inputs
     * @return int
     */
    public static int get_int_input(String prompt, int low, int high, Scanner in, boolean print) {
        if (print) {
            System.out.println("Expected input should be between " + low + " and " + high + ".");
        }
        System.out.print(prompt);
        int result;
        try {
            result = in.nextInt();
        } catch (Exception e) {
            if (DEBUG) {
                System.out.println(e);
            } else {
                System.out.println("That didn't work, are you sure you entered an integer?");
            }
            result = get_int_input(prompt, low, high, in, print);
        }
        if (result >= low && result <= high) {
            return result;
        } else {
            get_int_input(prompt, low, high, in, print);
        }
        return result;
    }


    // string manipulation functions
    /** 
     * This function capitalizes the first character of the input string.
     * @param str String to capitalize the first character of.
     * @return String The capitalized string.
     */
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}