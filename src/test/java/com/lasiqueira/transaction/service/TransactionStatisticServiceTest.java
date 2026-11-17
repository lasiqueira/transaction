package com.lasiqueira.transaction.service;

import com.lasiqueira.transaction.TestData;
import com.lasiqueira.transaction.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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
        transaction = TestData.transaction();
        transaction.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @BeforeEach
    public void clearTransactions() {
        transactionStatisticService.deleteTransactions();
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

    @Test
    public void getStatisticsWithZeroAmountTest() {
        transactionStatisticService.createTransaction(TestData.transaction(new BigDecimal("10")));
        transactionStatisticService.createTransaction(TestData.transaction(new BigDecimal("5")));
        transactionStatisticService.createTransaction(TestData.transaction(new BigDecimal("0")));
        var expected = transactionStatisticService.getStatistics();
        Assertions.assertEquals(3, expected.getCount());
        Assertions.assertEquals(0, expected.getSum().compareTo(new BigDecimal("15")));
        Assertions.assertEquals(0, expected.getAvg().compareTo(new BigDecimal("5")));
        Assertions.assertEquals(0, expected.getMax().compareTo(new BigDecimal("10")));
        Assertions.assertEquals(0, expected.getMin().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void getStatisticsWithNegativeAmountsTest() {
        transactionStatisticService.createTransaction(TestData.transaction(new BigDecimal("-3")));
        transactionStatisticService.createTransaction(TestData.transaction(new BigDecimal("-7")));
        var expected = transactionStatisticService.getStatistics();
        Assertions.assertEquals(2, expected.getCount());
        Assertions.assertEquals(0, expected.getSum().compareTo(new BigDecimal("-10")));
        Assertions.assertEquals(0, expected.getAvg().compareTo(new BigDecimal("-5")));
        Assertions.assertEquals(0, expected.getMax().compareTo(new BigDecimal("-3")));
        Assertions.assertEquals(0, expected.getMin().compareTo(new BigDecimal("-7")));
    }

    @Test
    public void identicalTransactionsAreCountedSeparatelyTest() {
        var timestamp = LocalDateTime.now(ZoneId.of("UTC"));
        transactionStatisticService.createTransaction(new Transaction(null, new BigDecimal("5"), timestamp));
        transactionStatisticService.createTransaction(new Transaction(null, new BigDecimal("5"), timestamp));
        var expected = transactionStatisticService.getStatistics();
        Assertions.assertEquals(2, expected.getCount());
        Assertions.assertEquals(0, expected.getSum().compareTo(new BigDecimal("10")));
    }

}
