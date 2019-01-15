package com.epam.training.sportsbetting.entity;

import com.epam.training.sportsbetting.enums.Currency;
import com.epam.training.sportsbetting.enums.converter.CurrencyTypeConverter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "player")
public class PlayerEntity extends UserEntity {

    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;
    @Size(min = 3)
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @PositiveOrZero
    @Column(name = "balance", nullable = false)
    private double balance;
    @Convert(converter = CurrencyTypeConverter.class)
    @Column(name = "currency", nullable = false)
    private Currency currency;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Builder
    public PlayerEntity(String email, char[] password, boolean enabled, @Size(min = 3) String name,
                        @Size(min = 3) String accountNumber, @PositiveOrZero double balance,
                        Currency currency, LocalDate birthDate) {
        super(email, password, enabled);
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.birthDate = birthDate;
    }
}
