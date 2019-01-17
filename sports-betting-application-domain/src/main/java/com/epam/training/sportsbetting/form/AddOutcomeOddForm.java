package com.epam.training.sportsbetting.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddOutcomeOddForm {

    private Integer outcomeId;
    private double value;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
