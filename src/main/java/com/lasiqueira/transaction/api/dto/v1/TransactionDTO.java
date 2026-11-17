package com.lasiqueira.transaction.api.dto.v1;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO (
    @NotNull
    BigDecimal amount,
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalDateTime timestamp
){}
