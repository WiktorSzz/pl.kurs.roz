package pl.kurs.services;

import pl.kurs.exceptions.InvalidEquationFormatException;

public interface IEquationValidator {
    void validateEquation(String equation) throws InvalidEquationFormatException;
}
