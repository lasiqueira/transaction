package com.lasiqueira.transaction.service;

import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionStatisticServiceTest {
    @Autowired
    private TransactionStatisticService transactionStatisticService;
    private TransactionDTO transactionDTO;

    @Before
    public void setup() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(new BigDecimal("10.00"));
        transactionDTO.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    public void createTransactionTest() {
        boolean expected = transactionStatisticService.createTransaction(transactionDTO);
        assertTrue(expected);
    }

    @Test
    public void deleteTransactionsTest() {
        boolean expected = transactionStatisticService.deleteTransactions();
        assertTrue(expected);
    }

    @Test
    public void getStatisticsTest() {
        transactionStatisticService.createTransaction(transactionDTO);
        StatisticDTO expected = transactionStatisticService.getStatistics();
        assertNotNull(expected);
        assertTrue(expected.getCount() > 0);
        assertEquals(expected.getSum(), transactionDTO.getAmount());
        assertEquals(expected.getMax(), transactionDTO.getAmount());
        assertEquals(expected.getMin(), transactionDTO.getAmount());
        assertEquals(expected.getAvg(), transactionDTO.getAmount());

    }

}
