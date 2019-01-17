package com.epam.training.sportsbetting.form;

import com.epam.training.sportsbetting.domain.bet.Bet;
import lombok.Data;

import java.util.List;

@Data
public class BetForm {

    private String description;
    private List<OutcomeForm> outcomes;
    private Bet.BetType betType;
}
