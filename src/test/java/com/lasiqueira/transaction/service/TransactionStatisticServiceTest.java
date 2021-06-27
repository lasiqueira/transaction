package com.lasiqueira.transaction.service;

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

import java.time.LocalDateTime;
import java.time.ZoneId;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionStatisticServiceTest {
    @Autowired
    private TransactionStatisticService transactionStatisticService;
    private Transaction transaction;

    @BeforeAll
    public void setup() {
        transaction = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Transaction.class);
        transaction.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @Test
    public void createTransactionTest() {
        var expected = transactionStatisticService.createTransaction(transaction);
        Assertions.assertTrue(expected);
    }

    @Test
    public void deleteTransactionsTest() {
        var expected = transactionStatisticService.deleteTransactions();
        Assertions.assertTrue(expected);
    }

    @Test
    public void getStatisticsTest() {
        transactionStatisticService.createTransaction(transaction);
        var expected = transactionStatisticService.getStatistics();
        Assertions.assertNotNull(expected);
        Assertions.assertTrue(expected.getCount() > 0);
        Assertions.assertEquals(expected.getSum(), transaction.getAmount());
        Assertions.assertEquals(expected.getMax(), transaction.getAmount());
        Assertions.assertEquals(expected.getMin(), transaction.getAmount());
        Assertions.assertEquals(expected.getAvg(), transaction.getAmount());

    }

}
