package com.epam.trainning.sportsbetting.ui;

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
}
