package com.epam.training.sportsbetting.domain.outcome;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutcomeOdd {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @JsonIgnore
    private Outcome outcome;
    private double value;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime validFrom;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime validTo;

    public OutcomeOdd() {
    }

    public OutcomeOdd(Outcome outcome, double value, LocalDateTime validFrom, LocalDateTime validTo) {
        this.outcome = outcome;
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return "[" +
                "value=" + outcome.getValue() +
                ", outcomeOdds=" + getValue() +
                " and valid from " + getValidFrom().format(dtf) +
                " to " + getValidTo().format(dtf) +
                ']';
    }
}
