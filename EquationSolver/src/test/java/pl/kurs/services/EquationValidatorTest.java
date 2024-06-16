package pl.kurs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurs.exceptions.InvalidEquationFormatException;

import static org.junit.jupiter.api.Assertions.*;

public class EquationValidatorTest {
    private EquationValidator equationValidator;

    @BeforeEach
    public void setUp() {
        equationValidator = new EquationValidator();
    }

    @Test
    public void shouldThrowExceptionForNullEquation() {
        InvalidEquationFormatException exception = assertThrows(InvalidEquationFormatException.class, () -> {
            equationValidator.validateEquation(null);
        });
        assertEquals("Equation is empty or null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionForEmptyEquation() {
        InvalidEquationFormatException exception = assertThrows(InvalidEquationFormatException.class, () -> {
            equationValidator.validateEquation("");
        });
        assertEquals("Equation is empty or null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionForEquationWithInvalidCharacters() {
        InvalidEquationFormatException exception = assertThrows(InvalidEquationFormatException.class, () -> {
            equationValidator.validateEquation("3 + 5 & 2");
        });
        assertEquals("Equation contains invalid characters", exception.getMessage());
    }

    @Test
    public void shouldValidateCorrectEquation() {
        assertDoesNotThrow(() -> {
            equationValidator.validateEquation("3 + 5 - 2 * (4 / 2)");
        });
    }

    @Test
    public void shouldValidateEquationWithSpaces() {
        assertDoesNotThrow(() -> {
            equationValidator.validateEquation("3 + 5 - 2 * ( 4 / 2 )");
        });
    }

    @Test
    public void shouldValidateEquationWithNegativeNumbers() {
        assertDoesNotThrow(() -> {
            equationValidator.validateEquation("-3 + (-5) * 2");
        });
    }

    @Test
    public void shouldValidateEquationWithParentheses() {
        assertDoesNotThrow(() -> {
            equationValidator.validateEquation("(3 + 5) * 2");
        });
    }

    @Test
    public void shouldValidateEquationWithDecimalNumbers() {
        assertDoesNotThrow(() -> {
            equationValidator.validateEquation("3.5 + 2.1 * 4.2");
        });
    }
}
