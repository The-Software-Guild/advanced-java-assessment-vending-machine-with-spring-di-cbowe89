package VendingMachine.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {
    Scanner scanner;

    /**
     * Constructor for UserIOConsoleImpl
     */
    public UserIOConsoleImpl() {
        scanner = new Scanner(System.in);
    }

    /**
     * Takes in a message to display to the console
     * @param message String of info to display to the user
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Prints formatted string. Used for printing item number,
     * name, and price.
     * @param num Item number
     * @param str Item name
     * @param bigDecimal Item price
     */
    @Override
    public void printf(int num, String str, BigDecimal bigDecimal) {
        System.out.printf("%d. %-15s $%-15s\n", num, str, bigDecimal);
    }

    /**
     * Takes in a prompt to display to the console, waits
     * for an answer (String) from the user to return
     * @param prompt String, explains what info is wanted
     *               from the user
     * @return Answer to the prompt as a String
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter an integer to be returned
     * as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as an integer
     */
    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // Print prompt
                String stringValue= this.readString(prompt);
                // Get input, try to parse
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter an integer within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return An integer value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        int num;
        do {
            System.out.println(prompt);
            num = Integer.parseInt(scanner.nextLine());
        }
        while(num < min || num > max);

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a BigDecimal to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a BigDecimal
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean invalidInput = true;
        BigDecimal num = new BigDecimal("0.0");
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = new BigDecimal(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a BigDecimal within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A BigDecimal value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal num;
        do {
            System.out.println(prompt);
            num = new BigDecimal(scanner.nextLine());
        }
        while(num.doubleValue() < min.doubleValue()
                || num.doubleValue() > max.doubleValue()); // removed || num = null

        return num;
    }
}
