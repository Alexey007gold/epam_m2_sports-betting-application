package com.epam.trainning.sportsbetting.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {

    private static final String TRY_AGAIN = "Try again: ";

    private Scanner scanner = new Scanner(System.in);

    public void write(String message) {
        System.out.print(message);
    }

    public String getStringInput() {
        return scanner.nextLine();
    }

    public String getNonEmptyStringInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    throw new InputMismatchException();
                }
                return input;
            } catch (Exception e) {
                System.out.println(TRY_AGAIN);
            }
        }
    }

    public String getStringInput(String pattern) {
        while (true) {
            try {
                String token = scanner.next(pattern);
                scanner.nextLine();
                return token;
            } catch (Exception e) {
                System.out.println(TRY_AGAIN);
                scanner.nextLine();
            }
        }
    }

    public int getPositiveIntInput() {
        while (true) {
            try {
                int integer = scanner.nextInt();
                if (integer <= 0) {
                    throw new InputMismatchException();
                }
                scanner.nextLine();
                return integer;
            } catch (Exception e) {
                System.out.println(TRY_AGAIN);
                scanner.nextLine();
            }
        }
    }

    public int getIntInputWithinRange(int from, int to) {
        while (true) {
            try {
                int integer = scanner.nextInt();
                if (integer < from || integer > to) {
                    throw new InputMismatchException();
                }
                scanner.nextLine();
                return integer;
            } catch (Exception e) {
                System.out.println(TRY_AGAIN);
                scanner.nextLine();
            }
        }
    }

    public LocalDate getDateInput() {
        while (true) {
            try {
                LocalDate localDate = LocalDate.parse(getStringInput("\\d{4}-\\d{2}-\\d{2}"));
                if (localDate.isAfter(LocalDate.now())) {
                    System.out.println("Date should be in the past. " + TRY_AGAIN);
                } else if (localDate.isAfter(LocalDate.now().minusYears(18))) {
                    System.out.println("Only people aged 18 or more are allowed. " + TRY_AGAIN);
                } else {
                    return localDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date");
            }
        }
    }
}
