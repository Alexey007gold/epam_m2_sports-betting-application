package com.epam.trainning.sportsbetting.domain.sportevent;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.json.serialize.LocalDateTimeDeserializer;
import com.epam.trainning.sportsbetting.json.serialize.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FootballSportEvent.class, name = "Football"),
        @JsonSubTypes.Type(value = TennisSportEvent.class, name = "Tennis")}
)
public abstract class SportEvent {

    private String title;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    private List<Bet> bets;
    private Result result;

    public SportEvent() {
    }

    public SportEvent(String title, LocalDateTime startDate, LocalDateTime endDate, List<Bet> bets) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bets = bets;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Result getResult() {
        return result;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportEvent event = (SportEvent) o;
        return Objects.equals(title, event.title) &&
                Objects.equals(startDate, event.startDate) &&
                Objects.equals(endDate, event.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate);
    }
}
