package ru.sbrf.java.edu.sberbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SendMoneyResponse {
    private BigDecimal currentSum;
}
