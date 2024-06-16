package pl.kurs.services;

import pl.kurs.exceptions.UnknownOperatorException;

public interface IEquationEvaluator {
    double evaluate(String equation) throws UnknownOperatorException;
}
