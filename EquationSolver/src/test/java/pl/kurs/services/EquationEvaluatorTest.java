package pl.kurs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurs.exceptions.UnknownOperatorException;
import pl.kurs.models.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EquationEvaluatorTest {
    private EquationEvaluator equationEvaluator;

    @BeforeEach
    public void init() {
        equationEvaluator = new EquationEvaluator(Arrays.asList(
                new AdditionOperator(),
                new SubtractionOperator(),
                new MultiplicationOperator(),
                new DivisionOperator()
        ));
    }

    @Test
    public void shouldConvertInfixToPostfixCorrectly() throws UnknownOperatorException {
        String equation = "3 + 4 * 2 / ( 1 - 5 )";
        List<String> expectedPostfix = Arrays.asList("3", "4", "2", "*", "1", "5", "-", "/", "+");
        List<String> result = equationEvaluator.convertToPostfix(equation);
        assertEquals(expectedPostfix, result);
    }

    @Test
    public void shouldCalculatePostfixCorrectly() throws UnknownOperatorException {
        List<String> postfix = Arrays.asList("3", "4", "2", "*", "1", "5", "-", "/", "+");
        double result = equationEvaluator.calculatePostfix(postfix);
        assertEquals(1.0, result);
    }

    @Test
    public void shouldThrowUnknownOperatorExceptionForInvalidOperatorInPostfix() {
        List<String> postfix = Arrays.asList("3", "4", "#");
        assertThrows(UnknownOperatorException.class, () -> equationEvaluator.calculatePostfix(postfix));
    }

    @Test
    public void shouldHandleNegativeNumbersInInfixToPostfix() throws UnknownOperatorException {
        String equation = "-3 + 4";
        List<String> expectedPostfix = Arrays.asList("-3", "4", "+");
        List<String> result = equationEvaluator.convertToPostfix(equation);
        assertEquals(expectedPostfix, result);
    }

    @Test
    public void shouldHandleNegativeNumbersInCalculation() throws UnknownOperatorException {
        String equation = "-3 + 4";
        double result = equationEvaluator.evaluate(equation);
        assertEquals(1.0, result);
    }


    @Test
    public void shouldThrowUnknownOperatorExceptionForInvalidOperatorInInfix() {
        String equation = "3 & 4";
        assertThrows(UnknownOperatorException.class, () -> equationEvaluator.convertToPostfix(equation));
    }

    @Test
    public void shouldThrowArithmeticExceptionForDivisionByZero() {
        String equation = "3 / 0";
        assertThrows(ArithmeticException.class, () -> equationEvaluator.evaluate(equation));
    }

    @Test
    public void shouldHandleMultipleNegativeNumbers() throws UnknownOperatorException {
        String equation = "-1-1";
        double result = equationEvaluator.evaluate(equation);
        assertEquals(-2.0, result);
    }

    @Test
    public void shouldHandleDoubleNegativeNumbers() throws UnknownOperatorException {
        String equation = "-1--1";
        double result = equationEvaluator.evaluate(equation);
        assertEquals(0.0, result);
    }

    @Test
    public void shouldThrowUnknownOperatorExceptionForMismatchedParentheses() {
        String equation = "(3 + 4 * 2";
        assertThrows(UnknownOperatorException.class, () -> equationEvaluator.convertToPostfix(equation));
    }
}
