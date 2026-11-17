package com.lasiqueira.transaction.api.controller.v1;

import tools.jackson.databind.ObjectMapper;
import com.lasiqueira.transaction.TestData;
import com.lasiqueira.transaction.api.converter.v1.TransactionStatisticConverter;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
import com.lasiqueira.transaction.api.validator.v1.DateValidator;
import com.lasiqueira.transaction.model.Statistic;
import com.lasiqueira.transaction.model.Transaction;
import com.lasiqueira.transaction.service.TransactionStatisticService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionStatisticController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionStatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TransactionStatisticService transactionStatisticService;
    @MockitoBean
    private TransactionStatisticConverter transactionStatisticConverter;
    @MockitoBean
    private DateValidator dateValidator;
    private TransactionDTO transactionDTO;
    private Statistic statistic;
    private Transaction transaction;
    private StatisticDTO statisticDTO;

    @BeforeAll
    public void setup() {
        transactionDTO = new TransactionDTO(BigDecimal.TEN, LocalDateTime.now());

        transaction = TestData.transaction();


        statistic = new Statistic();
        statisticDTO = new StatisticDTO(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, 1L);
    }

    @Test
    public void createTransactionSuccessTest() throws Exception {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        when(transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO)).thenReturn(transaction);
        mockMvc.perform(post("/v1/transactions")
                .content(objectMapper.writeValueAsString(transactionDTO))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createTransactionOldTest() throws Exception {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenReturn(Boolean.FALSE);
        when(transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO)).thenReturn(transaction);
        mockMvc.perform(post("/v1/transactions")
                .content(objectMapper.writeValueAsString(transactionDTO))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createTransactionInvalidTest() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .content("{hello}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransactionFutureTest() throws Exception {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenThrow(InvalidDateException.class);
        when(transactionStatisticConverter.convertTransactionDTOToTransaction(transactionDTO)).thenReturn(transaction);
        mockMvc.perform(post("/v1/transactions")
                .content(objectMapper.writeValueAsString(transactionDTO))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    public void createTransactionUnparseableAmountTest() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .content("{\"amount\":\"abc\",\"timestamp\":\"2021-06-01T10:00:00\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    public void createTransactionUnparseableTimestampTest() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .content("{\"amount\":\"10\",\"timestamp\":\"not-a-date\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    public void createTransactionMissingFieldsTest() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .content("{\"amount\":\"10\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTransactionsTest() throws Exception {
        mockMvc.perform(delete("/v1/transactions"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getStatisticsTest() throws Exception {
        when(transactionStatisticService.getStatistics()).thenReturn(statistic);
        when(transactionStatisticConverter.convertStatisticToStatisticDTO(statistic)).thenReturn(statisticDTO);
        mockMvc.perform(get("/v1/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}
