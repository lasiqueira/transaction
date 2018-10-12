package com.lasiqueira.transaction.api.converter;

import com.lasiqueira.transaction.api.converter.v1.TransactionStatisticConverter;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionStatisticConverterTest {

    @Autowired
    private TransactionStatisticConverter transactionStatisticConverter;

    private Statistic statistic;
    private TransactionDTO transactionDTO;


    @Before
    public void setup(){
        statistic = random(Statistic.class);
        transactionDTO = random(TransactionDTO.class);
    }

    @Test
    public void convertTransactionDTOToTransactionTest(){
        Transaction expected = transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO);

        assertNotNull(expected);
        assertEquals(expected.getAmount(), transactionDTO.getAmount());
        assertEquals(expected.getTimestamp(), transactionDTO.getTimestamp());
    }

    @Test
    public void convertStatisticToStatisticDTOTest(){
        StatisticDTO expected = transactionStatisticConverter.convertStatisticToStatisticDTO(statistic);

        assertNotNull(expected);
        assertEquals(expected.getAvg(), statistic.getAvg());
        assertEquals(expected.getMax(), statistic.getMax());
        assertEquals(expected.getMin(), statistic.getMin());
        assertEquals(expected.getSum(), statistic.getSum());
        assertEquals(expected.getCount(), statistic.getCount());

    }
}
