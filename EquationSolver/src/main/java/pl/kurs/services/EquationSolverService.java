package pl.kurs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.dao.IEquationHistoryDao;
import pl.kurs.exceptions.InvalidEquationFormatException;
import pl.kurs.exceptions.UnknownOperatorException;
import pl.kurs.models.EquationHistory;

import java.sql.Timestamp;

@Service
public class EquationSolverService implements IEquationSolverService {
    private final IEquationHistoryDao equationHistoryDao;
    private final IEquationValidator equationValidator;
    private final IEquationEvaluator equationEvaluator;

    @Autowired
    public EquationSolverService(IEquationHistoryDao equationHistoryDao, IEquationValidator equationValidator, IEquationEvaluator equationEvaluator) {
        this.equationHistoryDao = equationHistoryDao;
        this.equationValidator = equationValidator;
        this.equationEvaluator = equationEvaluator;
    }

    @Override
    public double solve(String equation) throws InvalidEquationFormatException, UnknownOperatorException, ArithmeticException {
        equationValidator.validateEquation(equation);
        double result;
        try {
            result = equationEvaluator.evaluate(equation);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Error in equation evaluation: " + e.getMessage());
        }
        equationHistoryDao.insert(new EquationHistory(new Timestamp(System.currentTimeMillis()), equation, result));
        return result;
    }
}
