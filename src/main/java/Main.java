import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Main — Entry point for the Birth Year Calculator console application.
 *
 * <p>This class is responsible exclusively for user interaction (I/O): reading input from the
 * console, displaying formatted output, handling exceptions gracefully, and managing the
 * repeat-calculation loop. All business logic — birth year calculation and age validation —
 * is delegated to {@link BirthYearCalculator}.</p>
 *
 * <p>The application prompts the user to enter their age, computes the estimated birth year,
 * and displays the result. Users can perform multiple calculations in a single session and
 * type "exit" or "quit" to terminate the program.</p>
 *
 * <p><strong>Design Decision — Separation of Concerns:</strong> This class intentionally
 * contains zero calculation logic. It does not import {@code java.time.Year} or perform
 * any date arithmetic. This keeps the I/O layer cleanly separated from the business logic
 * layer, improving testability and maintainability.</p>
 */
public class Main {

    /**
     * Application entry point. Runs an interactive console loop that prompts the user
     * for their age, delegates calculation to {@link BirthYearCalculator}, and displays
     * the computed birth year.
     *
     * <p>The loop continues until the user types "exit" or "quit" (case-insensitive).
     * All exceptions are handled gracefully — the program never crashes with a stack trace.</p>
     *
     * @param args command-line arguments (not used by this application)
     */
    public static void main(String[] args) {
        // Use nextLine() instead of nextInt() so we can read both numeric input and
        // text commands like "exit" or "quit" from a single input stream without
        // leaving dangling newline characters in the buffer.
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Birth Year Calculator!");
        System.out.println("Enter your age to calculate your birth year.");
        System.out.println("Type \"exit\" or \"quit\" to stop.");
        System.out.println();

        // Repeat-calculation loop: allows the user to perform multiple calculations
        // in a single session without restarting the program. The loop runs
        // indefinitely until the user enters the exit sentinel ("exit" or "quit").
        while (true) {
            try {
                System.out.print("Enter your age: ");

                // Read the entire line as a String to support both numeric ages
                // and text-based exit commands in a unified input flow.
                String input = scanner.nextLine().trim();

                // Exit sentinel check: allow the user to gracefully terminate the
                // program by typing "exit" or "quit" (case-insensitive comparison
                // ensures "EXIT", "Quit", etc. are also accepted).
                if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                    System.out.println("Thank you for using the Birth Year Calculator. Goodbye!");
                    break;
                }

                // Attempt to parse the user's input as an integer. If the input is
                // not a valid whole number (e.g., "abc", "12.5", ""), parseInt throws
                // a NumberFormatException which is caught below.
                int age;
                try {
                    age = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid whole number for your age.");
                    System.out.println();
                    continue;
                }

                // Delegate input validation to BirthYearCalculator — this class does
                // not define what constitutes a valid age. The calculator rejects zero
                // and negative values, enforcing the business rule in one place.
                if (!BirthYearCalculator.isValidAge(age)) {
                    System.out.println("Invalid age. Please enter a positive number greater than zero.");
                    System.out.println();
                    continue;
                }

                // Delegate the birth year calculation to BirthYearCalculator.
                // Main.java never performs date arithmetic or accesses java.time APIs directly.
                int birthYear = BirthYearCalculator.calculateBirthYear(age);

                // Output the result in the exact format specified by the requirements.
                // CRITICAL: spacing, capitalization, and the trailing period must match exactly.
                System.out.println("If you are " + age + " years old, you were born in " + birthYear + ".");

                // Demonstrate the birthday edge case using the overloaded method.
                // If the user's birthday has not yet occurred this calendar year,
                // they were actually born one year earlier than the simple calculation suggests.
                int birthYearIfBirthdayNotOccurred = BirthYearCalculator.calculateBirthYear(age, false);
                System.out.println("If your birthday has not yet occurred this year, you were born in " + birthYearIfBirthdayNotOccurred + ".");

                System.out.println();

            } catch (Exception e) {
                // Safety net: catch any unexpected exceptions that were not anticipated
                // by the specific handlers above. This ensures the program never crashes
                // with an unhandled stack trace, regardless of what goes wrong.
                System.out.println("An unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again.");
                System.out.println();
            }
        }

        // Release the Scanner resource after the loop exits. Closing the Scanner
        // also closes the underlying System.in stream.
        scanner.close();
    }
}
