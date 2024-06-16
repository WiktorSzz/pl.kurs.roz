package pl.kurs.services;

import org.springframework.stereotype.Component;
import pl.kurs.exceptions.InvalidEquationFormatException;

@Component
public class EquationValidator implements IEquationValidator {

    @Override
    public void validateEquation(String equation) throws InvalidEquationFormatException {
        if (equation == null || equation.isEmpty()) {
            throw new InvalidEquationFormatException("Equation is empty or null");
        }
        if (!equation.matches("[0-9+\\-*/(). ]+")) {
            throw new InvalidEquationFormatException("Equation contains invalid characters");
        }
    }
}
