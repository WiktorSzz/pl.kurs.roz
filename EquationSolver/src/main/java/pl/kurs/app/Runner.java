package pl.kurs.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.kurs.exceptions.InvalidEquationFormatException;
import pl.kurs.exceptions.UnknownOperatorException;
import pl.kurs.services.EquationSolverService;

import java.util.InputMismatchException;
import java.util.Scanner;

@ComponentScan(basePackages = "pl.kurs")
public class Runner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Runner.class);
        EquationSolverService equationSolverService = ctx.getBean(EquationSolverService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Equation Solver!");
        printOptions();
        int option = -1;

        do {
            try {
                String input = scanner.nextLine().trim();
                option = Integer.parseInt(input);
                switch (option) {
                    case 1:
                        System.out.println("Enter the equation:");
                        String equation = scanner.nextLine();
                        double result = equationSolverService.solve(equation);
                        System.out.println("The result is: " + result);
                        printOptions();
                        break;
                    case 0:
                        System.out.println("Exit");
                        break;
                    default:
                        System.err.println("Unrecognized option!");
                        printOptions();
                }
            } catch (InvalidEquationFormatException | UnknownOperatorException | ArithmeticException e) {
                handleException(e);
            } catch (InputMismatchException | NumberFormatException e) {
                System.err.println("Choose correct option!");
                printOptions();
            }
        } while (option != 0);
        scanner.close();
    }

    private static void printOptions() {
        System.out.println("Choose an option:");
        System.out.println("1 - Solve an equation");
        System.out.println("0 - Exit");
    }

    private static void handleException(Throwable e) {
        if (e instanceof UnknownOperatorException) {
            System.err.println("Unknown operator in equation. Please enter a valid equation.");
        } else if (e.getMessage() != null) {
            System.err.println(e.getMessage());
        } else {
            System.err.println("Input error!");
        }
        printOptions();
    }
}
