package com.epam.training.sportsbetting.domain.bet;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Bet {

    @JsonIgnore
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

    @Override
    public String toString() {
        if (betType.equals(BetType.SCORE))
            return betType + " " + description;
        else return betType.toString();
    }

    public enum BetType {
        GOAL,
        WINNER,
        SCORE;
    }
}
