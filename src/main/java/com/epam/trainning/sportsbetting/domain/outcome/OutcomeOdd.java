package com.epam.trainning.sportsbetting.domain.outcome;

import com.epam.trainning.sportsbetting.json.serialize.LocalDateTimeDeserializer;
import com.epam.trainning.sportsbetting.json.serialize.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class OutcomeOdd {

    private double value;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime validFrom;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime validTo;

    public OutcomeOdd() {
    }

    public OutcomeOdd(double value, LocalDateTime validFrom, LocalDateTime validTo) {
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

    public double getValue() {
        return value;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }
}
