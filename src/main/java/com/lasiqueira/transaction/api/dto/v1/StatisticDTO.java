package com.lasiqueira.transaction.api.dto.v1;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lasiqueira.transaction.api.serializer.v1.MoneySerializer;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class StatisticDTO {
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal sum;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal avg;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal max;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal min;
    private long count;

    public StatisticDTO() {
        sum = new BigDecimal("0.00");
        avg =  new BigDecimal("0.00");
        max =  new BigDecimal("0.00");
        min =  new BigDecimal("0.00");
    }

    public StatisticDTO(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

}
