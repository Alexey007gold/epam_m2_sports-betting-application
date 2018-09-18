package com.epam.trainning.sportsbetting.domain.bet;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;

import java.util.List;

public class Bet {

    private SportEvent event;
    private String description;
    private List<Outcome> outcomes;
    private BetType betType;

    public Bet() {
    }

    public Bet(SportEvent event, String description, List<Outcome> outcomes, BetType betType) {
        this.event = event;
        this.description = description;
        this.outcomes = outcomes;
        this.betType = betType;
    }

    public void setEvent(SportEvent event) {
        this.event = event;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public SportEvent getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public BetType getBetType() {
        return betType;
    }

    public enum BetType {
        GOAL,
        WINNER,
        SCORE;
    }
}
