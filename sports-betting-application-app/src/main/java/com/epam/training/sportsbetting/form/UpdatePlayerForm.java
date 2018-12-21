package com.epam.training.sportsbetting.form;

import com.epam.training.sportsbetting.domain.user.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePlayerForm extends UpdateUserForm {
    @NotNull
    @Size(min = 3)
    private String name;
    @NotNull
    @Size(min = 3)
    private String accountNumber;
    @PositiveOrZero
    private double balance;
    @NotNull
    private Currency currency;
    @NotNull
    private LocalDate birthDate;
}
