package com.epam.training.sportsbetting.ui;

import com.epam.training.sportsbetting.service.I18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleViewTest {

    @Mock
    private I18NService i18N;

    private ConsoleView consoleView;

    private ConsoleView.ScannerWrapper scanner;
    private PrintStream printStreamMock;

    @BeforeEach
    void setUp() throws Exception {
        when(i18N.getMessage(anyString())).thenReturn("msg");
        consoleView = new ConsoleView(i18N);

        scanner = mock(ConsoleView.ScannerWrapper.class);
        printStreamMock = mock(PrintStream.class);
        Field inField = ConsoleView.class.getDeclaredField("in");
        inField.setAccessible(true);
        inField.set(consoleView, scanner);
        Field outField = ConsoleView.class.getDeclaredField("out");
        outField.setAccessible(true);
        outField.set(consoleView, printStreamMock);
    }

    @Test
    void shouldCallPrintOnWrite() {
        consoleView.write("msg");

        verify(printStreamMock).print("msg");
    }

    @Test
    void shouldReturnCorrectResultOnGetStringInput() {
        when(scanner.nextLine()).thenReturn("msg");

        String result = consoleView.getStringInput();

        verify(scanner).nextLine();
        assertEquals("msg", result);
    }

    @Test
    void shouldReturnCorrectResultOnGetNonEmptyStringInput() {
        when(scanner.nextLine()).thenReturn("", "str");

        String result = consoleView.getNonEmptyStringInput();

        verify(scanner, times(2)).nextLine();
        verify(printStreamMock).println("msg");
        assertEquals("str", result);
    }

    @Test
    void shouldReturnCorrectResultOnGetStringInputWithPattern() {
        when(scanner.nextLine()).thenReturn("");
        when(scanner.next("pattern")).thenThrow(InputMismatchException.class).thenReturn("msg");

        String result = consoleView.getStringInput("pattern");

        verify(scanner, times(2)).next("pattern");
        verify(scanner, times(2)).nextLine();
        verify(printStreamMock).println("msg");
        assertEquals("msg", result);
    }

    @Test
    void shouldReturnCorrectResultOnGetPositiveIntInput() {
        when(scanner.nextLine()).thenReturn("");
        when(scanner.nextInt()).thenThrow(InputMismatchException.class).thenReturn(0).thenReturn(2);

        int result = consoleView.getPositiveIntInput();

        verify(scanner, times(3)).nextInt();
        verify(scanner, times(3)).nextLine();
        verify(printStreamMock, times(2)).println("msg");
        assertEquals(2, result);
    }

    @Test
    void shouldReturnCorrectResultOnGetIntInputWithinRange() {
        when(scanner.nextLine()).thenReturn("");
        when(scanner.nextInt()).thenReturn(1).thenReturn(2);

        int result = consoleView.getIntInputWithinRange(2, 4);

        verify(scanner, times(2)).nextInt();
        verify(scanner, times(2)).nextLine();
        verify(printStreamMock).println("msg");
        assertEquals(2, result);
    }

    @Test
    void shouldReturnCorrectResultOnGetDateInput() {
        when(scanner.next(any())).thenReturn("", "1111",
                LocalDate.now().minusYears(17).toString(),
                LocalDate.now().plusDays(1).toString(),
                "1111-11-11");

        when(i18N.getMessage(anyString())).thenReturn("msg1", "msg1", "msg2", "msg3");

        LocalDate result = consoleView.getDateInput();

        verify(scanner, times(5)).nextLine();
        verify(printStreamMock, times(2)).println("msg1");
        verify(printStreamMock, times(1)).println("msg2 msg");
        verify(printStreamMock, times(1)).println("msg3 msg");
        assertEquals(LocalDate.parse("1111-11-11"), result);
    }
}