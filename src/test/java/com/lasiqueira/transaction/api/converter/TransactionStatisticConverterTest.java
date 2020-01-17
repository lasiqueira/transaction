package com.lasiqueira.transaction.api.converter;

import com.lasiqueira.transaction.api.converter.v1.TransactionStatisticConverter;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionStatisticConverterTest {

    @Autowired
    private TransactionStatisticConverter transactionStatisticConverter;

    private Statistic statistic;
    private TransactionDTO transactionDTO;


    @BeforeAll
    public void setup(){
        statistic = random(Statistic.class);
        transactionDTO = random(TransactionDTO.class);
    }

    @Test
    public void convertTransactionDTOToTransactionTest(){
        Transaction expected = transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getAmount(), transactionDTO.getAmount());
        Assertions.assertEquals(expected.getTimestamp(), transactionDTO.getTimestamp());
    }

    @Test
    public void convertStatisticToStatisticDTOTest(){
        StatisticDTO expected = transactionStatisticConverter.convertStatisticToStatisticDTO(statistic);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getAvg(), statistic.getAvg());
        Assertions.assertEquals(expected.getMax(), statistic.getMax());
        Assertions.assertEquals(expected.getMin(), statistic.getMin());
        Assertions.assertEquals(expected.getSum(), statistic.getSum());
        Assertions.assertEquals(expected.getCount(), statistic.getCount());

    }
}
