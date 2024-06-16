package pl.kurs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class EquationHistory implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp date;
    private String equation;
    private double result;

    public EquationHistory() {
    }

    public EquationHistory(Timestamp date, String equation, double result) {
        this.date = date;
        this.equation = equation;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getEquation() {
        return equation;
    }

    public double getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquationHistory that = (EquationHistory) o;
        return Double.compare(that.result, result) == 0 && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(equation, that.equation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, equation, result);
    }

    @Override
    public String toString() {
        return "EquationHistory{" +
                "id=" + id +
                ", date=" + date +
                ", equation='" + equation + '\'' +
                ", result=" + result +
                '}';
    }
}
