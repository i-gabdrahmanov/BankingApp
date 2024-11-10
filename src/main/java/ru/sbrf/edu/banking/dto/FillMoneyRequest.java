package ru.sbrf.edu.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FillMoneyRequest {
    private BigDecimal appendSum;
}
