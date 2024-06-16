package pl.kurs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kurs.dao.IEquationHistoryDao;
import pl.kurs.exceptions.InvalidEquationFormatException;
import pl.kurs.exceptions.UnknownOperatorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquationSolverServiceTest {

    private IEquationHistoryDao equationHistoryDao;
    private IEquationValidator equationValidator;
    private IEquationEvaluator equationEvaluator;
    private EquationSolverService equationSolverService;

    @BeforeEach
    public void init() {
        equationHistoryDao = Mockito.mock(IEquationHistoryDao.class);
        equationValidator = Mockito.mock(IEquationValidator.class);
        equationEvaluator = Mockito.mock(IEquationEvaluator.class);
        equationSolverService = new EquationSolverService(equationHistoryDao, equationValidator, equationEvaluator);
    }

    @Test
    public void shouldReturn2WhenAdding1Plus1() throws InvalidEquationFormatException, UnknownOperatorException {
        String equation = "1 + 1";
        double expectedResult = 2.0;

        when(equationEvaluator.evaluate(equation)).thenReturn(expectedResult);

        double result = equationSolverService.solve(equation);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldThrowInvalidEquationFormatExceptionWhenEquationIsInvalid() throws InvalidEquationFormatException, UnknownOperatorException {
        String equation = "1 + a";

        doThrow(new InvalidEquationFormatException("Invalid equation")).when(equationValidator).validateEquation(equation);

        assertThrows(InvalidEquationFormatException.class, () -> {
            equationSolverService.solve(equation);
        });

    }

    @Test
    public void shouldReturn25WhenMultiplying5Times5() throws InvalidEquationFormatException, UnknownOperatorException {
        String equation = "5 * 5";
        double expectedResult = 25.0;

        when(equationEvaluator.evaluate(equation)).thenReturn(expectedResult);

        double result = equationSolverService.solve(equation);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturn4WhenDividing8By2() throws InvalidEquationFormatException, UnknownOperatorException {
        String equation = "8 / 2";
        double expectedResult = 4.0;

        when(equationEvaluator.evaluate(equation)).thenReturn(expectedResult);

        double result = equationSolverService.solve(equation);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturn6WhenAdding2Plus2AndMultiplyingBy2() throws UnknownOperatorException, InvalidEquationFormatException {
        String equation = "2 + 2 * 2";
        double expectedResult = 6;

        when(equationEvaluator.evaluate(equation)).thenReturn(expectedResult);

        double result = equationSolverService.solve(equation);

        assertEquals(expectedResult, result);

    }

    @Test
    void shouldThrowArithmeticExceptionWhenDividingByZero() throws UnknownOperatorException {
        Mockito.when(equationEvaluator.evaluate("2 / 0")).thenThrow(new ArithmeticException("Division by zero"));

        assertThrows(ArithmeticException.class, () -> equationSolverService.solve("2 / 0"));
    }
}
