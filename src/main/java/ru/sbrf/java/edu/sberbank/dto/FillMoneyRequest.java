package ru.sbrf.java.edu.sberbank.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FillMoneyRequest {
    private BigDecimal appendSum;
}
