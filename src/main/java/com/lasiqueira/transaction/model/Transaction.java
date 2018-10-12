package com.lasiqueira.transaction.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
