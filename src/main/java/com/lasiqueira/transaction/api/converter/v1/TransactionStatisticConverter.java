package com.lasiqueira.transaction.api.converter.v1;

import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatisticConverter {


    public Transaction convertTransactionDTOToTransaction(TransactionDTO transactionDTO){
        var transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        return transaction;
    }
    public StatisticDTO convertStatisticToStatisticDTO(Statistic statistic){
        var statisticDTO = new StatisticDTO();
        statisticDTO.setAvg(statistic.getAvg());
        statisticDTO.setCount(statistic.getCount());
        statisticDTO.setMax(statistic.getMax());
        statisticDTO.setMin(statistic.getMin());
        statisticDTO.setSum(statistic.getSum());
        return statisticDTO;
    }
}
