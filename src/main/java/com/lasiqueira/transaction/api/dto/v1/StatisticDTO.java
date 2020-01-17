package com.lasiqueira.transaction.api.dto.v1;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lasiqueira.transaction.api.serializer.v1.MoneySerializer;

import java.math.BigDecimal;
import java.util.Objects;

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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticDTO that = (StatisticDTO) o;
        return count == that.count &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(avg, that.avg) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }
}
