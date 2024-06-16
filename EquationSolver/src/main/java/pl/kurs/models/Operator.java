package pl.kurs.models;

public interface Operator {
    double apply(double a, double b);
    String getSymbol();
    int getPrecedence();
}
