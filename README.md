# Birth Year Calculator

A Java console application that calculates a user's birth year based on the age they enter at the console. Built with the modern `java.time` API for accurate date handling — no deprecated `Date` or `Calendar` classes are used. The application features robust input validation, graceful exception handling, a repeat-calculation loop for performing multiple calculations in a single session, and birthday edge-case support for users whose birthday has not yet occurred this year.

## Prerequisites

- **Java 21 LTS** (OpenJDK 21 or later)
- **Apache Maven 3.9+** (3.9.12 recommended)

Verify your installations:

```bash
java -version
mvn -version
```

## Project Structure

```
birth-year-calculator/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       └── BirthYearCalculator.java
│   └── test/
│       └── java/
│           └── BirthYearCalculatorTest.java
```

| File | Description |
|------|-------------|
| `pom.xml` | Maven project descriptor with Java 21 compiler configuration, JUnit 5 dependency, and build plugins |
| `Main.java` | Application entry point with Scanner-based input loop, output formatting, and exception handling |
| `BirthYearCalculator.java` | Core business logic for birth year calculation and input validation using `java.time.Year` |
| `BirthYearCalculatorTest.java` | JUnit 5 unit tests covering calculation correctness, validation logic, and edge cases |

## Build Instructions

All commands should be run from the project root directory.

**Compile the project:**

```bash
mvn compile
```

**Run the unit tests:**

```bash
mvn test
```

**Package the application into an executable JAR:**

```bash
mvn package
```

**Run the application:**

```bash
java -jar target/birth-year-calculator-1.0-SNAPSHOT.jar
```

## Usage Examples

### Valid Input

```
Welcome to the Birth Year Calculator!
Enter your age to calculate your birth year.
Type "exit" or "quit" to stop.

Enter your age: 30
If you are 30 years old, you were born in 1996.
If your birthday has not yet occurred this year, you were born in 1995.

Enter your age: 5
If you are 5 years old, you were born in 2021.
If your birthday has not yet occurred this year, you were born in 2020.
```

### Invalid Input — Negative Number

```
Enter your age: -5

Invalid age. Please enter a positive number greater than zero.
```

### Invalid Input — Zero

```
Enter your age: 0

Invalid age. Please enter a positive number greater than zero.
```

### Invalid Input — Non-Numeric

```
Enter your age: abc

Invalid input. Please enter a valid whole number for your age.
```

### Exiting the Program

```
Enter your age: exit

Thank you for using the Birth Year Calculator. Goodbye!
```

## Features

- **Birth year calculation** using `java.time.Year.now()` — dynamically retrieves the current year at runtime, never hardcoded
- **Input validation** — rejects negative numbers, zero, and non-numeric input with clear error messages
- **Exception handling** — gracefully catches `NumberFormatException` when non-numeric input is entered, preventing application crashes
- **Repeated calculations** — allows the user to perform multiple calculations without restarting the program via a loop-based interaction model
- **Birthday edge-case handling** — supports an overloaded calculation method that accounts for whether the user's birthday has already occurred this year (birth year could be `currentYear - age` or `currentYear - age - 1`)
- **JUnit 5 unit test coverage** — comprehensive test suite validating calculation correctness, input validation, and edge cases

## Testing

Run the full test suite with:

```bash
mvn test
```

The project uses **JUnit 5.14.2** (Jupiter API) for unit testing. The test suite (`BirthYearCalculatorTest.java`) covers the following scenarios:

| Test Category | Description |
|---------------|-------------|
| Valid age calculation | Verifies correct birth year for typical age inputs |
| Zero rejection | Confirms that an age of `0` is rejected by validation |
| Negative number rejection | Confirms that negative ages (e.g., `-5`) are rejected by validation |
| Boundary values | Tests edge-case ages such as `1` and `150` |
| Birthday edge case | Validates the overloaded method that adjusts the birth year when the user's birthday has not yet occurred |
| Current-year correctness | Ensures the calculation dynamically uses the actual system year |

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 LTS | Programming language and runtime |
| Apache Maven | 3.9.12 | Build automation and dependency management |
| JUnit | 5.14.2 | Unit testing framework (Jupiter API) |
