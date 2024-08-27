package ru.sbrf.java.edu.sberbank.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendMoneyRequest {
    private Long recipientId;
    private BigDecimal transferSum;
}
