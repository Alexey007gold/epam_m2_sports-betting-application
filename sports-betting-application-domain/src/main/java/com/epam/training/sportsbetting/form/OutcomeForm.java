package com.epam.training.sportsbetting.form;

import com.epam.training.sportsbetting.domain.bet.Bet;
import lombok.Data;

import java.util.List;

@Data
public class OutcomeForm {

    private String value;
    private List<OutcomeOddForm> outcomeOdds;
    private Bet bet;
}
