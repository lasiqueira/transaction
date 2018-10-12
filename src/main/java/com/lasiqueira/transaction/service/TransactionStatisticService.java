package com.lasiqueira.transaction.service;

import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionStatisticService {
    private Logger logger = LoggerFactory.getLogger(TransactionStatisticService.class);

    private Set<Transaction> transactions;

    public TransactionStatisticService() {
        this.transactions = Collections.newSetFromMap(new ConcurrentHashMap<Transaction, Boolean>());
    }

    public boolean createTransaction(Transaction transaction) {
        transaction.setId(Long.valueOf(LocalDateTime.now(ZoneId.of("UTC")).hashCode()));
        logger.debug("createTransaction: {}", transaction);
        transactions.add(transaction);
        logger.debug("createTransaction success");
        return Boolean.TRUE;
    }

    public boolean deleteTransactions() {
        logger.debug("deleteTransactions");
        transactions.clear();
        logger.debug("deleteTransactions success");
        return Boolean.TRUE;
    }

    public Statistic getStatistics() {
        logger.debug("getStatistics");
        Statistic statistic = new Statistic();
        for (Transaction transaction : transactions) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(60);
            if (transaction.getTimestamp().compareTo(now) >= 0) {
                logger.debug("generating statistics from transaction: {}", transaction);
                statistic.setSum(sum(statistic.getSum(), transaction.getAmount()));
                statistic.setMax(getMaximum(statistic.getMax(), transaction.getAmount()));
                statistic.setMin(getMinimum(statistic.getMin(), transaction.getAmount()));
            } else {
                logger.debug("Removing old entry: {}", transaction);
                transactions.remove(transaction);
            }
        }
        statistic.setCount(transactions.size());
        statistic.setAvg(getAverage(statistic.getSum(), statistic.getCount()));

        logger.debug("Statistics generated successfully: {}", statistic);

        return statistic;
    }

    private BigDecimal getMaximum(BigDecimal oldVal, BigDecimal newVal) {
        if (oldVal.compareTo(newVal) < 0) {
            return newVal;
        }
        return oldVal;
    }

    private BigDecimal getMinimum(BigDecimal oldVal, BigDecimal newVal) {
        if (oldVal.compareTo(BigDecimal.ZERO) == 0 || oldVal.compareTo(newVal) > 0) {
            return newVal;
        }
        return oldVal;
    }

    private BigDecimal getAverage(BigDecimal val, long count) {
        if (count > 0) {
            return val.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP);
        }
        return val;
    }

    private BigDecimal sum(BigDecimal oldVal, BigDecimal newVal) {
        return oldVal.add(newVal);
    }


}
