package pl.kurs.services;

import pl.kurs.exceptions.InvalidEquationFormatException;
import pl.kurs.exceptions.UnknownOperatorException;

public interface IEquationSolverService {
    double solve(String equation) throws InvalidEquationFormatException, UnknownOperatorException;
}
