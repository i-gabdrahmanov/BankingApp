package ru.sbrf.edu.banking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CurrencyEnum {

    RUR("Руб", "RUR"),
    USD("Долл", "USD");
    @Getter
    private final String rusName;
    @Getter
    private final String engName;
}
