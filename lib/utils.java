package lib;

import java.util.ArrayList;
import java.util.Scanner;

public class utils {
    // set this to see detailed error messages on misinput
    static final boolean DEBUG = true;

    
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
     * @param expected_input ArrayList<String> that contains the acceptable inputs.
     * @param in The pre-initialized Scanner object to read from.
     * @return String The validated string that the user inputted.
     */
    public static String get_validated_input(String prompt, ArrayList<String> expected_input, Scanner in) {
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
            result = get_validated_input(prompt, expected_input, in);
        }
        if (expected_input.contains(result)) {
            return result;
        } else {
            result = get_validated_input(prompt, expected_input, in);
        }
        return result;
    }

    
    /**
     * This function validates returns a String that the user inputted.
     * It will re-run if the input cannot be parsed.
     * If the parameter "print" is set to true, it will print the accepted inputs.
     * Set this parameter to false or exclude it to avoid this.
     * This function should only be used for getting user input from a Scanner initialized from System.in.
     * @param prompt
     * @param expected_input
     * @param in
     * @param print
     * @return String
     */
    public static String get_validated_input(String prompt, ArrayList<String> expected_input, Scanner in, boolean print) {
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
            result = get_validated_input(prompt, expected_input, in, print);
        }
        if (expected_input.contains(result)) {
            return result;
        } else {
            result = get_validated_input(prompt, expected_input, in, print);
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