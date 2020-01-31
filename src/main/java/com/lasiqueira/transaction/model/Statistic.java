package com.lasiqueira.transaction.model;

import java.math.BigDecimal;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var statistic = (Statistic) o;
        return count == statistic.count &&
                Objects.equals(sum, statistic.sum) &&
                Objects.equals(avg, statistic.avg) &&
                Objects.equals(max, statistic.max) &&
                Objects.equals(min, statistic.min);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }
}
