package com.lasiqueira.transaction;

import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** Test fixtures (replaces the abandoned random-beans library). */
public final class TestData {

    private TestData() {
    }

    public static Transaction transaction() {
        return transaction(new BigDecimal("12.34"));
    }

    public static Transaction transaction(BigDecimal amount) {
        return new Transaction(1L, amount, LocalDateTime.now(ZoneId.of("UTC")));
    }

    public static Statistic statistic() {
        var statistic = new Statistic();
        statistic.setSum(new BigDecimal("30.00"));
        statistic.setAvg(new BigDecimal("15.00"));
        statistic.setMax(new BigDecimal("20.00"));
        statistic.setMin(new BigDecimal("10.00"));
        statistic.setCount(2);
        return statistic;
    }
}
