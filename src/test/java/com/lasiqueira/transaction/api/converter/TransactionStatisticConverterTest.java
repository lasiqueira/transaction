package com.lasiqueira.transaction.api.converter;

import com.lasiqueira.transaction.api.converter.v1.TransactionStatisticConverter;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        statistic = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Statistic.class);
        transactionDTO = new TransactionDTO(BigDecimal.TEN, LocalDateTime.now());
    }

    @Test
    public void convertTransactionDTOToTransactionTest(){
        var expected = transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.getAmount(), transactionDTO.amount());
        Assertions.assertEquals(expected.getTimestamp(), transactionDTO.timestamp());
    }

    @Test
    public void convertStatisticToStatisticDTOTest(){
        var expected = transactionStatisticConverter.convertStatisticToStatisticDTO(statistic);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected.avg(), statistic.getAvg());
        Assertions.assertEquals(expected.max(), statistic.getMax());
        Assertions.assertEquals(expected.min(), statistic.getMin());
        Assertions.assertEquals(expected.sum(), statistic.getSum());
        Assertions.assertEquals(expected.count(), statistic.getCount());

    }
}
