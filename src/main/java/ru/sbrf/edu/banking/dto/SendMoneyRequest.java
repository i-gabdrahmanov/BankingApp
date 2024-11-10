package ru.sbrf.edu.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendMoneyRequest {
    private Long recipientId;
    private BigDecimal transferSum;
}
