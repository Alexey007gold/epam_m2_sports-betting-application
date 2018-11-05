package com.epam.trainning.sportsbetting;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.Result;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.*;
import com.epam.trainning.sportsbetting.service.impl.DataServiceImpl;
import com.epam.trainning.sportsbetting.ui.ConsoleView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static test.TestUtils.invokePrivateMethod;
import static test.TestUtils.setPrivateField;

class AppTest {

    private App app;
    private ConsoleView consoleView;
    private DataService dataService;
    private EventService eventService;
    private BetService betService;
    private PlayerService playerService;
    private WagerService wagerService;

    @BeforeEach
    public void setUp() throws Exception {
        app = new App();

        consoleView = mock(ConsoleView.class);
        dataService = mock(DataService.class);
        eventService = mock(EventService.class);
        betService = mock(BetService.class);
        playerService = mock(PlayerService.class);
        wagerService = mock(WagerService.class);

        setPrivateField(app, "consoleView", consoleView);
        setPrivateField(app, "dataService", dataService);
        setPrivateField(app, "eventService", eventService);
        setPrivateField(app, "betService", betService);
        setPrivateField(app, "playerService", playerService);
        setPrivateField(app, "wagerService", wagerService);
    }

    @Test
    public void shouldReturnExpectedResultOnGetCurrencyPattern() throws Exception {
        String result = (String) invokePrivateMethod(app, "getCurrencyPattern");

        assertEquals("EUR|USD|HUF", result);
    }

    @Test
    public void shouldCallServiceOnSayHello() throws Exception {
        Player player = new Player.Builder()
                .withName("John")
                .withBalance(50)
                .withCurrency(Currency.EUR)
                .build();

        invokePrivateMethod(app, "sayHello", player);

        DecimalFormat df = new DecimalFormat("###.##");
        verify(consoleView).write(String.format("Hello %s!%n", player.getName()));
        verify(consoleView).write(String.format("Your balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
    }

    @Test
    public void shouldCallServiceOnDisplayUserPrize() throws Exception {
        DataService dataService = DataServiceImpl.getInstance();

        Player player = new Player.Builder()
                .withName("John")
                .withBalance(100)
                .withAccountNumber("1111")
                .withCurrency(Currency.EUR)
                .build();

        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        Outcome outcome2 = dataService.getPossibleOutcomes().get(1);
        SportEvent event1 = outcome1.getBet().getEvent();
        SportEvent event2 = outcome2.getBet().getEvent();
        event1.setResult(new Result(Arrays.asList(outcome1)));
        event2.setResult(new Result(Arrays.asList(outcome2)));
        List<Wager> wagers = Arrays.asList(
                new Wager.Builder()
                        .withEvent(event1)
                        .withPlayer(player)
                        .withOutcomeOdd(outcome1.getOutcomeOdds().get(0))
                        .withAmount(5)
                        .withCurrency(player.getCurrency())
                        .withTimestamp(System.currentTimeMillis())
                        .withWinner(true)
                        .build(),
                new Wager.Builder()
                        .withEvent(event2)
                        .withPlayer(player)
                        .withOutcomeOdd(outcome2.getOutcomeOdds().get(0))
                        .withAmount(5)
                        .withCurrency(player.getCurrency())
                        .withTimestamp(System.currentTimeMillis())
                        .build());

        int prize = 10;
        invokePrivateMethod(app, "displayUserPrize", new Class<?>[]{List.class, double.class}, wagers, prize);

        DecimalFormat df = new DecimalFormat("###.##");
        verify(consoleView).write("Your winner bet is Outcome 0 [value=Southampton, outcomeOdds=4.0 and valid from 2018-09-23 07:00:00 to 2018-11-06 12:00:00]; wage: 5; prize for that: 0\r\n");
        verify(consoleView).write(String.format("You have won %s %s%n", df.format(prize), player.getCurrency()));
        verify(consoleView).write(String.format("Your new balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
    }

    @Test
    public void shouldReturnCorrectResultOnChooseOutcome() throws Exception {
        when(consoleView.getStringInput()).thenReturn("0", "6", "4");
        Outcome expected = mock(Outcome.class);
        List<Outcome> outcomes = Arrays.asList(mock(Outcome.class), mock(Outcome.class),
                mock(Outcome.class), expected, mock(Outcome.class));

        Outcome result = (Outcome) invokePrivateMethod(app, "chooseOutcome", new Class[]{List.class}, outcomes);

        assertEquals(expected, result);
        verify(consoleView, times(2)).write("Try again:\n");
    }

    @Test
    public void shouldReturnNullOnChooseOutcome() throws Exception {
        when(consoleView.getStringInput()).thenReturn("q");
        List<Outcome> outcomes = Arrays.asList(mock(Outcome.class), mock(Outcome.class),
                mock(Outcome.class), mock(Outcome.class), mock(Outcome.class));

        Outcome result = (Outcome) invokePrivateMethod(app, "chooseOutcome", new Class[]{List.class}, outcomes);

        assertNull(result);
    }

    @Test
    public void shouldReturnNegativeValueOnGetWage() throws Exception {
        Player player = new Player.Builder()
                .withName("John")
                .withBalance(100)
                .withCurrency(Currency.EUR)
                .build();
        when(consoleView.getStringInput()).thenReturn("q");

        double result = (double) invokePrivateMethod(app, "getWage", player);

        assertEquals(-1, result);
    }

    @Test
    public void shouldReturnCorrectResultOnGetWage() throws Exception {
        DecimalFormat df = new DecimalFormat("###.##");
        Player player = new Player.Builder()
                .withName("John")
                .withBalance(100)
                .withCurrency(Currency.EUR)
                .build();
        when(consoleView.getStringInput()).thenReturn("-1", "10", "10");
        when(playerService.canMakeWager(any(), anyDouble())).thenReturn(false, true);

        double result = (double) invokePrivateMethod(app, "getWage", player);

        assertEquals(10, result);
        verify(consoleView).write("Try again:\n");
        verify(consoleView).write(String.format("You don't have enough money, your balance is %s %s%n",
                df.format(player.getBalance()), player.getCurrency()));
    }

    @Test
    public void shouldProduceExpectedOutputOnPrintBetMenu() throws Exception {
        DataService dataService = DataServiceImpl.getInstance();

        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        Outcome outcome2 = dataService.getPossibleOutcomes().get(1);
        outcome1.getOutcomeOdds().get(0).setValidTo(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1));
        outcome2.getOutcomeOdds().get(0).setValidTo(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1));

        invokePrivateMethod(app, "printBetMenu", new Class[]{List.class}, Arrays.asList(outcome1, outcome2));

        verify(consoleView).write("Please choose an outcome to bet on! (choose a number or press q for quit)\n");
        verify(consoleView).write("1: Bet on the Southampton v Bournemoth sport event, the winner will be Southampton. The odd on this is 4.0, valid from 2018-09-23 07:00:00 to 2018-11-06 12:00:00\r\n");
        verify(consoleView).write("2: Bet on the Southampton v Bournemoth sport event, the winner will be Bournemoth. The odd on this is 1.7, valid from 2018-09-23 07:00:00 to 2018-11-06 12:00:00\r\n");
    }

    @Test
    public void shouldReturnCorrectResultOnGatherBets() throws Exception {
        DataService dataService = DataServiceImpl.getInstance();
        Player player = new Player.Builder()
                .withName("John")
                .withBalance(100)
                .withCurrency(Currency.EUR)
                .build();
        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        Outcome outcome2 = dataService.getPossibleOutcomes().get(1);
        outcome1.getOutcomeOdds().get(0).setValidTo(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1));
        outcome2.getOutcomeOdds().get(0).setValidTo(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(1));
        when(this.dataService.getPossibleOutcomes()).thenReturn(Arrays.asList(outcome1, outcome2));
        when(consoleView.getStringInput()).thenReturn("1", "10", "2", "15", "q");
        when(playerService.canMakeWager(any(), anyDouble())).thenReturn(true);
        doAnswer(invocationOnMock -> {
            Player argument = invocationOnMock.getArgument(0);
            argument.setBalance(argument.getBalance() - 10);
            return null;
        }).when(playerService).decreasePlayerBalance(any(), anyDouble());

        List<Wager> result = (List<Wager>) invokePrivateMethod(app, "gatherBets", player);

        assertEquals(player, result.get(0).getPlayer());
        assertEquals(10, result.get(0).getAmount());
        assertEquals(outcome1.getBet().getEvent(), result.get(0).getEvent());
        assertEquals(outcome1.getActiveOdd(), result.get(0).getOutcomeOdd());
        assertEquals(player.getCurrency(), result.get(0).getCurrency());
        assertEquals(player, result.get(1).getPlayer());
        assertEquals(15, result.get(1).getAmount());
        assertEquals(outcome2.getBet().getEvent(), result.get(1).getEvent());
        assertEquals(outcome2.getActiveOdd(), result.get(1).getOutcomeOdd());
        assertEquals(player.getCurrency(), result.get(1).getCurrency());

        verify(consoleView, times(2)).write("How much do you want to bet on it? (q for quit)\n");
        verify(consoleView).write(String.format("Your new balance is %s %s%n", 90, player.getCurrency()));
        verify(consoleView).write(String.format("Your new balance is %s %s%n", 80, player.getCurrency()));
        verify(playerService).decreasePlayerBalance(player, 10);
        verify(playerService).decreasePlayerBalance(player, 15);
    }

    @Test
    public void shouldProduceExpectedOutputOnDisplayResults() throws Exception {
        DataService dataService = DataServiceImpl.getInstance();

        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        Outcome outcome2 = dataService.getPossibleOutcomes().get(1);
        SportEvent event1 = outcome1.getBet().getEvent();
        SportEvent event2 = outcome2.getBet().getEvent();
        event1.setResult(new Result(Arrays.asList(outcome1)));
        event2.setResult(new Result(Arrays.asList(outcome2)));

        invokePrivateMethod(app, "displayResults", new Class[]{List.class}, Arrays.asList(event1, event2));

        verify(consoleView).write("Events results:\n");
        verify(consoleView, times(2)).write(" - Event: Southampton v Bournemoth The winner is Outcome 0, WINNER: Bournemoth\r\n");
        verify(consoleView).write("\n");
    }

    @Test
    public void shouldReturnCorrectResultOnGetPlayer() throws Exception {
        String name = "John";
        String acc = "1234";
        String balance = "50";
        String currency = "EUR";
        LocalDate birthDate = LocalDate.now();
        when(consoleView.getNonEmptyStringInput()).thenReturn(name, acc);
        when(consoleView.getPositiveIntInput()).thenReturn(Integer.parseInt(balance));
        when(consoleView.getStringInput(anyString())).thenReturn(currency);
        when(consoleView.getDateInput()).thenReturn(birthDate);
        when(playerService.registerPlayer(anyString(), anyString(), anyDouble(), any(), any()))
                .thenAnswer((i) -> new Player.Builder()
                .withName(i.getArgument(0))
                .withAccountNumber(i.getArgument(1))
                .withBalance(i.getArgument(2))
                .withCurrency(i.getArgument(3))
                .withBirthDate(i.getArgument(4))
                .build());

        Player result = (Player) invokePrivateMethod(app, "getPlayer");

        assertEquals(name, result.getName());
        assertEquals(acc, result.getAccountNumber());
        assertEquals(Double.parseDouble(balance), result.getBalance());
        assertEquals(Currency.valueOf(currency), result.getCurrency());
        assertEquals(birthDate, result.getBirthDate());
    }

    @Test
    public void ss() throws Exception {
        String name = "John";
        String acc = "1234";
        String balance = "50";
        String currency = "EUR";
        LocalDate birthDate = LocalDate.now();
        when(consoleView.getNonEmptyStringInput()).thenReturn(name, acc);
        when(consoleView.getPositiveIntInput()).thenReturn(Integer.parseInt(balance));
        when(consoleView.getStringInput(anyString())).thenReturn(currency);
        when(consoleView.getDateInput()).thenReturn(birthDate);
        when(playerService.registerPlayer(anyString(), anyString(), anyDouble(), any(), any()))
                .thenAnswer((i) -> new Player.Builder()
                        .withName(i.getArgument(0))
                        .withAccountNumber(i.getArgument(1))
                        .withBalance(i.getArgument(2))
                        .withCurrency(i.getArgument(3))
                        .withBirthDate(i.getArgument(4))
                        .build());
        Map<Player, Double> map = mock(Map.class);
        when(map.get(any(Player.class))).thenReturn(1.);
        when(betService.processBets(anyList(), anyList())).thenReturn(map);

        invokePrivateMethod(app, "run");

        verify(dataService).getEvents();
        verify(eventService).playEvents(any());
        verify(betService).processBets(anyList(), anyList());
    }

}