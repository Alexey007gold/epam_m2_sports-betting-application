package com.epam.trainning.sportsbetting;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.BetService;
import com.epam.trainning.sportsbetting.service.DataService;
import com.epam.trainning.sportsbetting.service.EventService;
import com.epam.trainning.sportsbetting.service.PlayerService;
import com.epam.trainning.sportsbetting.ui.ConsoleView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class App {

    private ConsoleView consoleView;
    private DataService dataService;
    private EventService eventService;
    private BetService betService;
    private PlayerService playerService;

    private DecimalFormat df = new DecimalFormat("###.##");

    public static void main(String[] args) throws IOException, URISyntaxException {
        new App().run();
    }

    public App() throws IOException, URISyntaxException {
        this.consoleView = new ConsoleView();
        this.dataService = new DataService();
        this.eventService = new EventService(dataService);
        this.playerService = new PlayerService(consoleView);
        this.betService = new BetService(consoleView, dataService);
    }

    private void run() {
        Player player = playerService.registerPlayer();
        sayHello(player);

        Map<Outcome, Wager> userBets = betService.gatherBets(player);

        eventService.playEvents();

        betService.processBets(userBets);

        displayUserPrize(userBets);
    }

    private void displayUserPrize(Map<Outcome, Wager> userBets) {
        if (userBets.isEmpty()) return;
        AtomicReference<Currency> currency = new AtomicReference<>();
        AtomicReference<Player> player = new AtomicReference<>();
        AtomicReference<Double> prize = new AtomicReference<>(.0);
        userBets.values().stream()
                .filter(Wager::isWin)
                .forEach(w -> {
                    if (currency.get() == null) {
                        currency.set(w.getCurrency());
                        player.set(w.getPlayer());
                    }
                    prize.updateAndGet(v -> (v + (w.getAmount() * w.getOutcomeOdd().getValue())));
                });

        if (prize.get() > 0) {
            consoleView.write(String.format("You have won %s %s%n", df.format(prize.get()), currency.get()));
            consoleView.write(String.format("Your new balance is %s %s%n", df.format(player.get().getBalance()), player.get().getCurrency()));
        } else {
            consoleView.write("Unfortunately you haven't won anything");
        }
    }

    private void sayHello(Player player) {
        consoleView.write(String.format("Hello %s!%n", player.getName()));
        consoleView.write(String.format("Your balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
    }
}
