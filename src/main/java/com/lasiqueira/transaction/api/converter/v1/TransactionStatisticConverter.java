package com.lasiqueira.transaction.api.converter.v1;

import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatisticConverter {


    public Transaction convertTransactionDTOToTransaction(TransactionDTO transactionDTO){
        return new Transaction(
                null,
                transactionDTO.amount(),
                transactionDTO.timestamp()
        );
    }
    public StatisticDTO convertStatisticToStatisticDTO(Statistic statistic){
        return new StatisticDTO(
                statistic.getSum(),
                statistic.getAvg(),
                statistic.getMax(),
                statistic.getMin(),
                statistic.getCount()
        );
    }
}
