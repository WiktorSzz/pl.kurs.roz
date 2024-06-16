package pl.kurs.models;

import org.springframework.stereotype.Component;

@Component
public class DivisionOperator implements Operator {
    @Override
    public double apply(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public int getPrecedence() {
        return 2;
    }
}
