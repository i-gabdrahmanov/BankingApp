package ru.sbrf.java.edu.sberbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sbrf.java.edu.sberbank.enums.CurrencyEnum;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull
    private BigDecimal overdraftLimit;
    @NotNull
    private CurrencyEnum currency;
}
