package ru.sbrf.edu.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sbrf.edu.banking.enums.CurrencyEnum;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {

    private BigDecimal currentSum;
    private BigDecimal overdraftLimit;
    private CurrencyEnum currency;
}
