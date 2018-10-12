package com.lasiqueira.transaction.service;

import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
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

    private Set<TransactionDTO> transactions;

    public TransactionStatisticService() {
        this.transactions = Collections.newSetFromMap(new ConcurrentHashMap<TransactionDTO, Boolean>());
    }

    public boolean createTransaction(TransactionDTO transactionDTO) {
        logger.debug("createTransaction: {}", transactionDTO);
        transactions.add(transactionDTO);
        logger.debug("createTransaction success");
        return Boolean.TRUE;
    }

    public boolean deleteTransactions() {
        logger.debug("deleteTransactions");
        transactions.clear();
        logger.debug("deleteTransactions success");
        return Boolean.TRUE;
    }

    public StatisticDTO getStatistics() {
        logger.debug("getStatistics");
        StatisticDTO statisticDTO = new StatisticDTO();
        for (TransactionDTO transactionDTO : transactions) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(60);
            if (transactionDTO.getTimestamp().compareTo(now) >= 0) {
                logger.debug("generating statistics from transaction: {}", transactionDTO);
                statisticDTO.setSum(sum(statisticDTO.getSum(), transactionDTO.getAmount()));
                statisticDTO.setMax(getMaximum(statisticDTO.getMax(), transactionDTO.getAmount()));
                statisticDTO.setMin(getMinimum(statisticDTO.getMin(), transactionDTO.getAmount()));
            } else {
                logger.debug("Removing old entry: {}", transactionDTO);
                transactions.remove(transactionDTO);
            }
        }
        statisticDTO.setCount(transactions.size());
        statisticDTO.setAvg(getAverage(statisticDTO.getSum(), statisticDTO.getCount()));

        logger.debug("Statistics generated successfully: {}", statisticDTO);

        return statisticDTO;
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
