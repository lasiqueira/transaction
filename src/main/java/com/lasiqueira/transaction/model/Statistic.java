package com.lasiqueira.transaction.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Statistic {
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public Statistic() {
        sum = new BigDecimal("0.00");
        avg =  new BigDecimal("0.00");
        max =  new BigDecimal("0.00");
        min =  new BigDecimal("0.00");
    }
}
