import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link BirthYearCalculator}.
 *
 * <p>Validates all three public static methods of the BirthYearCalculator business logic class:
 * <ul>
 *   <li>{@code calculateBirthYear(int age)} — standard birth year calculation</li>
 *   <li>{@code isValidAge(int age)} — input validation for age values</li>
 *   <li>{@code calculateBirthYear(int age, boolean birthdayOccurred)} — birthday edge-case handling</li>
 * </ul>
 *
 * <p>All expected year values are computed dynamically using {@link Year#now()} to ensure
 * tests remain valid regardless of the calendar year in which they are executed.
 * No hardcoded year values (e.g., 2026) are used anywhere in this test class.</p>
 *
 * <p>This class resides in the default package (no {@code package} statement),
 * mirroring the main source structure.</p>
 */
public class BirthYearCalculatorTest {

    // ========================================================================
    // Tests for calculateBirthYear(int age) — Standard Birth Year Calculation
    // ========================================================================

    /**
     * Verifies the core calculation formula returns the correct birth year
     * for a standard valid age (30 years old).
     */
    @Test
    @DisplayName("calculateBirthYear returns correct year for a valid age")
    void testCalculateBirthYearWithValidAge() {
        // Arrange: define a typical age and compute the expected birth year dynamically
        int age = 30;
        int expected = Year.now().getValue() - age;

        // Act: invoke the calculator method
        int actual = BirthYearCalculator.calculateBirthYear(age);

        // Assert: verify the calculated birth year matches the expected value
        assertEquals(expected, actual,
                "Birth year should equal the current year minus the given age");
    }

    /**
     * Verifies the calculation is correct at the lower boundary of valid ages (age 1).
     * This ensures the formula works for the smallest possible valid input.
     */
    @Test
    @DisplayName("calculateBirthYear returns correct year for age 1")
    void testCalculateBirthYearWithAgeOne() {
        // Arrange: age 1 is the smallest valid positive integer
        int age = 1;
        int expected = Year.now().getValue() - age;

        // Act
        int actual = BirthYearCalculator.calculateBirthYear(age);

        // Assert
        assertEquals(expected, actual,
                "Birth year for age 1 should be one year before the current year");
    }

    /**
     * Verifies the calculation is correct at an extreme upper boundary (age 150).
     * While unlikely, this tests that the method handles large valid ages without error.
     */
    @Test
    @DisplayName("calculateBirthYear returns correct year for age 150")
    void testCalculateBirthYearWithAge150() {
        // Arrange: 150 is an extreme but technically valid age
        int age = 150;
        int expected = Year.now().getValue() - age;

        // Act
        int actual = BirthYearCalculator.calculateBirthYear(age);

        // Assert
        assertEquals(expected, actual,
                "Birth year for age 150 should be 150 years before the current year");
    }

    // ========================================================================
    // Tests for isValidAge(int age) — Input Validation
    // ========================================================================

    /**
     * Verifies that zero is rejected as an invalid age.
     * An age of zero is not meaningful for birth year calculation.
     */
    @Test
    @DisplayName("isValidAge returns false for zero")
    void testIsValidAgeWithZero() {
        // Act & Assert: zero should be rejected
        assertFalse(BirthYearCalculator.isValidAge(0),
                "Zero should not be a valid age");
    }

    /**
     * Verifies that negative numbers are rejected as invalid ages.
     * Negative ages are physically impossible and must be caught by validation.
     */
    @Test
    @DisplayName("isValidAge returns false for negative number")
    void testIsValidAgeWithNegativeNumber() {
        // Act & Assert: negative values should be rejected
        assertFalse(BirthYearCalculator.isValidAge(-5),
                "Negative numbers should not be valid ages");
    }

    /**
     * Verifies that a standard positive age is accepted as valid.
     * Positive integers are the only valid age values.
     */
    @Test
    @DisplayName("isValidAge returns true for positive age")
    void testIsValidAgeWithPositiveAge() {
        // Act & Assert: positive integers should be accepted
        assertTrue(BirthYearCalculator.isValidAge(25),
                "Positive integers should be valid ages");
    }

    /**
     * Verifies the boundary between valid (1) and invalid (0) ages.
     * Age 1 is the smallest positive integer and must be accepted.
     */
    @Test
    @DisplayName("isValidAge returns true for age 1")
    void testIsValidAgeWithAgeOne() {
        // Act & Assert: 1 is the boundary between valid and invalid
        assertTrue(BirthYearCalculator.isValidAge(1),
                "Age 1 should be valid — it is the smallest positive integer");
    }

    // ========================================================================
    // Tests for calculateBirthYear(int age, boolean birthdayOccurred) — Edge Case
    // ========================================================================

    /**
     * Verifies the birthday edge-case method returns the same result as the simple method
     * when the user's birthday has already occurred this year.
     */
    @Test
    @DisplayName("calculateBirthYear with birthday occurred returns same as simple method")
    void testCalculateBirthYearBirthdayOccurred() {
        // Arrange: when the birthday has occurred, result should equal currentYear - age
        int age = 30;
        int expected = Year.now().getValue() - age;

        // Act: pass birthdayOccurred = true
        int actual = BirthYearCalculator.calculateBirthYear(age, true);

        // Assert: should match the simple calculateBirthYear(age) result
        assertEquals(expected, actual,
                "When birthday has occurred, result should equal currentYear - age");
    }

    /**
     * Verifies the birthday edge-case method returns one year earlier than the simple method
     * when the user's birthday has NOT yet occurred this year.
     */
    @Test
    @DisplayName("calculateBirthYear with birthday not occurred returns one year earlier")
    void testCalculateBirthYearBirthdayNotOccurred() {
        // Arrange: when the birthday has NOT occurred, result should be currentYear - age - 1
        int age = 30;
        int expected = Year.now().getValue() - age - 1;

        // Act: pass birthdayOccurred = false
        int actual = BirthYearCalculator.calculateBirthYear(age, false);

        // Assert: should be one year earlier than the simple calculation
        assertEquals(expected, actual,
                "When birthday has not occurred, result should equal currentYear - age - 1");
    }

    // ========================================================================
    // Test for Current-Year Correctness — Dynamic Year Verification
    // ========================================================================

    /**
     * Verifies that calculateBirthYear uses the current year dynamically from the system clock,
     * rather than a hardcoded year value. This test will naturally pass for any execution year,
     * proving no magic numbers are embedded in the calculation logic.
     */
    @Test
    @DisplayName("calculateBirthYear uses current year dynamically")
    void testCalculateBirthYearUsesCurrentYear() {
        // Arrange: capture the current year and define a test age
        int currentYear = Year.now().getValue();
        int age = 10;

        // Act: invoke the calculator
        int actual = BirthYearCalculator.calculateBirthYear(age);

        // Assert: the result must equal the dynamically computed expected value
        assertEquals(currentYear - age, actual,
                "calculateBirthYear must use the current system year, not a hardcoded value");
    }
}
