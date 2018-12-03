package com.epam.training.sportsbetting.ui;

import com.cookingfox.guava_preconditions.Preconditions;
import com.epam.training.sportsbetting.service.I18NService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class ConsoleView {

    private final String TRY_AGAIN;

    private final ScannerWrapper in = new ScannerWrapper(new Scanner(System.in, StandardCharsets.UTF_8.name()));
    private final PrintStream out;

    private I18NService i18N;

    @Autowired
    public ConsoleView(I18NService i18N) {
        this.i18N = i18N;
        this.TRY_AGAIN = i18N.getMessage("code.tryagain");
        try {
            this.out = new PrintStream(System.out, true, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public void write(String message) {
        out.print(message);
    }

    public void line(String message) {
        out.println(message);
    }

    public String getStringInput() {
        return in.nextLine();
    }

    /**
     * Returns a String with non-zero length
     * @return
     */
    public String getNonEmptyStringInput() {
        while (true) {
            try {
                String input = in.nextLine();

                Preconditions.checkArgument(!input.isEmpty());

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

    /**
     * Returns an integer greater than 0
     * @return
     */
    public int getPositiveIntInput() {
        while (true) {
            try {
                int integer = in.nextInt();

                Preconditions.checkArgument(integer > 0);

                in.nextLine();
                return integer;
            } catch (Exception e) {
                out.println(TRY_AGAIN);
                in.nextLine();
            }
        }
    }

    /**
     * Returns an integer not less than 'from' param and not greater than 'to' param
     * @return
     */
    public int getIntInputWithinRange(int from, int to) {
        while (true) {
            try {
                int integer = in.nextInt();

                Preconditions.checkArgument(integer >= from && integer <= to);

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
                    out.println(i18N.getMessage("code.date.past") + " " + TRY_AGAIN);
                } else if (localDate.isAfter(LocalDate.now().minusYears(18))) {
                    out.println(i18N.getMessage("code.18plus") + " " + TRY_AGAIN);
                } else {
                    return localDate;
                }
            } catch (DateTimeParseException e) {
                out.println(i18N.getMessage("code.date.invalid"));
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
