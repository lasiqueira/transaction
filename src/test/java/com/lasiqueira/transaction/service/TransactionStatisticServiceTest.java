package com.lasiqueira.transaction.service;

import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionStatisticServiceTest {
    @Autowired
    private TransactionStatisticService transactionStatisticService;
    private Transaction transaction;

    @Before
    public void setup() {
        transaction = random(Transaction.class);
        transaction.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    public void createTransactionTest() {
        boolean expected = transactionStatisticService.createTransaction(transaction);
        assertTrue(expected);
    }

    @Test
    public void deleteTransactionsTest() {
        boolean expected = transactionStatisticService.deleteTransactions();
        assertTrue(expected);
    }

    @Test
    public void getStatisticsTest() {
        transactionStatisticService.createTransaction(transaction);
        Statistic expected = transactionStatisticService.getStatistics();
        assertNotNull(expected);
        assertTrue(expected.getCount() > 0);
        assertEquals(expected.getSum(), transaction.getAmount());
        assertEquals(expected.getMax(), transaction.getAmount());
        assertEquals(expected.getMin(), transaction.getAmount());
        assertEquals(expected.getAvg(), transaction.getAmount());

    }

}
