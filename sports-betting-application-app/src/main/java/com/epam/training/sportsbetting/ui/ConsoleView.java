package com.epam.training.sportsbetting.ui;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {

    private static final String TRY_AGAIN = "Try again: ";

    private ScannerWrapper in = new ScannerWrapper(new Scanner(System.in));
    private PrintStream out = new PrintStream(System.out);

    public void write(String message) {
        out.print(message);
    }

    public String getStringInput() {
        return in.nextLine();
    }

    public String getNonEmptyStringInput() {
        while (true) {
            try {
                String input = in.nextLine();
                if (input.isEmpty()) {
                    throw new InputMismatchException();
                }
                return input;
            } catch (Exception e) {
                out.println(TRY_AGAIN);
            }
        }
    }

    public String getStringInput(String pattern) {
        while (true) {
            try {
                String token = in.next(pattern);
                in.nextLine();
                return token;
            } catch (Exception e) {
                out.println(TRY_AGAIN);
                in.nextLine();
            }
        }
    }

    public int getPositiveIntInput() {
        while (true) {
            try {
                int integer = in.nextInt();
                if (integer <= 0) {
                    throw new InputMismatchException();
                }
                in.nextLine();
                return integer;
            } catch (Exception e) {
                out.println(TRY_AGAIN);
                in.nextLine();
            }
        }
    }

    public int getIntInputWithinRange(int from, int to) {
        while (true) {
            try {
                int integer = in.nextInt();
                if (integer < from || integer > to) {
                    throw new InputMismatchException();
                }
                in.nextLine();
                return integer;
            } catch (Exception e) {
                out.println(TRY_AGAIN);
                in.nextLine();
            }
        }
    }

    public LocalDate getDateInput() {
        while (true) {
            try {
                LocalDate localDate = LocalDate.parse(getStringInput("\\d{4}-\\d{2}-\\d{2}"));
                if (localDate.isAfter(LocalDate.now())) {
                    out.println("Date should be in the past. " + TRY_AGAIN);
                } else if (localDate.isAfter(LocalDate.now().minusYears(18))) {
                    out.println("Only people aged 18 or more are allowed. " + TRY_AGAIN);
                } else {
                    return localDate;
                }
            } catch (DateTimeParseException e) {
                out.println("Invalid date");
            }
        }
    }

    public static class ScannerWrapper {

        private Scanner scanner;

        public ScannerWrapper(Scanner scanner) {
            this.scanner = scanner;
        }

        public String nextLine() {
            return scanner.nextLine();
        }

        public String next() {
            return scanner.next();
        }

        public String next(String pattern) {
            return scanner.next(pattern);
        }

        public int nextInt() {
            return scanner.nextInt();
        }
    }
}
