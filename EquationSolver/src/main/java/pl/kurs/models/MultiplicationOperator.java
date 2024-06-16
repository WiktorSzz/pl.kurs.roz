package pl.kurs.models;

import org.springframework.stereotype.Component;

@Component
public class MultiplicationOperator implements Operator {
    @Override
    public double apply(double a, double b) {
        return a * b;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public int getPrecedence() {
        return 2;
    }
}