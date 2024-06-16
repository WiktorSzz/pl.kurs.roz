package pl.kurs.services;

import org.springframework.stereotype.Service;
import pl.kurs.exceptions.UnknownOperatorException;
import pl.kurs.models.Operator;

import java.util.*;

@Service
public class EquationEvaluator implements IEquationEvaluator {

    private final List<Operator> operators;
    private final Map<String, Integer> precedence = new HashMap<>();

    public EquationEvaluator(List<Operator> operators) {
        this.operators = operators;
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    @Override
    public double evaluate(String equation) throws UnknownOperatorException {
        equation = equation.replaceAll("\\s", ""); // Remove all spaces
        List<String> postfix = convertToPostfix(equation);
        return calculatePostfix(postfix);
    }

    public List<String> convertToPostfix(String equation) throws UnknownOperatorException {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        StringBuilder number = new StringBuilder();

        boolean expectNumber = true;

        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                expectNumber = false;
            } else {
                if (number.length() > 0) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (c == ' ') {
                    continue;
                }

                String token = String.valueOf(c);

                if (precedence.containsKey(token)) {

                    if (token.equals("-") && (i == 0 || precedence.containsKey(String.valueOf(equation.charAt(i - 1))) || equation.charAt(i - 1) == '(')) {
                        number.append(c);
                        expectNumber = true;
                    } else {
                        while (!operators.isEmpty() && precedence.containsKey(operators.peek()) &&
                                precedence.get(token) <= precedence.get(operators.peek())) {
                            output.add(operators.pop());
                        }
                        operators.push(token);
                    }
                } else if (token.equals("(")) {
                    operators.push(token);
                    expectNumber = true;
                } else if (token.equals(")")) {
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        output.add(operators.pop());
                    }
                    if (!operators.isEmpty() && operators.peek().equals("(")) {
                        operators.pop();
                    } else {
                        throw new UnknownOperatorException("Mismatched parentheses");
                    }
                } else {
                    throw new UnknownOperatorException("Unknown operator: " + token);
                }
            }
        }

        if (number.length() > 0) {
            output.add(number.toString());
        }

        while (!operators.isEmpty()) {
            String top = operators.pop();
            if (top.equals("(") || top.equals(")")) {
                throw new UnknownOperatorException("Mismatched parentheses");
            }
            output.add(top);
        }

        return output;
    }

    public double calculatePostfix(List<String> postfix) throws UnknownOperatorException {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (stack.size() < 2) {
                    throw new UnknownOperatorException("Invalid postfix expression: " + postfix);
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        stack.push(a / b);
                        break;
                    default:
                        throw new UnknownOperatorException("Unknown operator: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new UnknownOperatorException("Invalid postfix expression: " + postfix);
        }

        return stack.pop();
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
