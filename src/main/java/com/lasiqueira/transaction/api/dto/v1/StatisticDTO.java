package com.lasiqueira.transaction.api.dto.v1;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lasiqueira.transaction.api.serializer.v1.MoneySerializer;

import java.math.BigDecimal;
public record StatisticDTO (
    @JsonSerialize(using = MoneySerializer.class)
    BigDecimal sum,
    @JsonSerialize(using = MoneySerializer.class)
    BigDecimal avg,
    @JsonSerialize(using = MoneySerializer.class)
    BigDecimal max,
    @JsonSerialize(using = MoneySerializer.class)
    BigDecimal min,

    long count) {}

