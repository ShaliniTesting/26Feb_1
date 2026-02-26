import java.time.Year;

/**
 * BirthYearCalculator — Core business logic class for the Birth Year Calculator application.
 *
 * <p>Provides reusable static methods for calculating a user's birth year based on their
 * current age and validating age input. All methods use the modern {@link java.time.Year}
 * API to dynamically retrieve the current calendar year at runtime.</p>
 *
 * <p>This class is intentionally decoupled from all I/O operations (Scanner, System.in,
 * System.out) to ensure maximum testability and clean separation of concerns. It contains
 * only pure calculation and validation logic with no side effects.</p>
 */
public class BirthYearCalculator {

    /**
     * Calculates the birth year by subtracting the given age from the current calendar year.
     *
     * <p>This method assumes the caller has already validated the age using
     * {@link #isValidAge(int)}. It performs no input validation itself.</p>
     *
     * @param age the user's current age in whole years (must be a positive integer)
     * @return the estimated birth year (currentYear - age)
     */
    public static int calculateBirthYear(int age) {
        // Use java.time.Year.now() to dynamically retrieve the current year at runtime.
        // This avoids hardcoded year values and replaces deprecated Date/Calendar APIs.
        return Year.now().getValue() - age;
    }

    /**
     * Validates that the given age is a positive integer greater than zero.
     *
     * <p>Returns {@code true} for any positive integer (1, 2, 30, 150, etc.)
     * and {@code false} for zero (0) or any negative integer (-1, -5, etc.).</p>
     *
     * @param age the age value to validate
     * @return {@code true} if the age is greater than zero; {@code false} otherwise
     */
    public static boolean isValidAge(int age) {
        return age > 0;
    }

    /**
     * Calculates the birth year with an adjustment for the birthday edge case.
     *
     * <p>This overloaded method handles the scenario where the user's birthday
     * has not yet occurred in the current calendar year.</p>
     *
     * <ul>
     *   <li>If {@code birthdayOccurred} is {@code true}, the result is the same as
     *       {@link #calculateBirthYear(int)}: {@code currentYear - age}.</li>
     *   <li>If {@code birthdayOccurred} is {@code false}, the result is adjusted
     *       by subtracting 1 additionally: {@code currentYear - age - 1}. This is
     *       because the user has not yet turned {@code age} years old in the current
     *       year, meaning they were actually born one year earlier than the simple
     *       subtraction would suggest.</li>
     * </ul>
     *
     * <p><strong>Example:</strong> If the current year is 2026 and age is 30:</p>
     * <ul>
     *   <li>Birthday has occurred → born in 1996 (2026 - 30)</li>
     *   <li>Birthday has NOT occurred → born in 1995 (2026 - 30 - 1)</li>
     * </ul>
     *
     * @param age              the user's current age in whole years (must be a positive integer)
     * @param birthdayOccurred {@code true} if the user's birthday has already occurred this year;
     *                         {@code false} if it has not yet occurred
     * @return the estimated birth year, adjusted for the birthday edge case
     */
    public static int calculateBirthYear(int age, boolean birthdayOccurred) {
        // Retrieve the current calendar year dynamically using the modern java.time API.
        // Year.now().getValue() is used instead of deprecated Date or Calendar classes.
        int birthYear = Year.now().getValue() - age;

        // If the user's birthday has not yet occurred this year, they were actually
        // born one year earlier than the simple subtraction suggests. For example,
        // a person who says they are 30 but whose birthday is next month was
        // actually born 31 years ago (currentYear - age - 1).
        if (!birthdayOccurred) {
            birthYear = birthYear - 1;
        }

        return birthYear;
    }
}
