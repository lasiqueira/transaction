package com.lasiqueira.transaction.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
import com.lasiqueira.transaction.api.validator.v1.DateValidator;
import com.lasiqueira.transaction.service.TransactionStatisticService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionStatisticController.class)
public class TransactionStatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionStatisticService transactionStatisticService;
    @MockBean
    private DateValidator dateValidator;
    private TransactionDTO transactionDTO;
    private StatisticDTO statisticDTO;


    @Before
    public void setup() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.valueOf(100.20));
        transactionDTO.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));

        statisticDTO = new StatisticDTO();
    }

    @Test
    public void createTransactionSuccessTest() throws InvalidDateException {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        try {
            mockMvc.perform(post("/transactions")
                    .content(objectMapper.writeValueAsString(transactionDTO))
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTransactionOldTest() throws InvalidDateException {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenReturn(Boolean.FALSE);
        try {
            mockMvc.perform(post("/transactions")
                    .content(objectMapper.writeValueAsString(transactionDTO))
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTransactionInvalidTest() {
        try {
            mockMvc.perform(post("/transactions")
                    .content("{hello}")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTransactionFutureTest() throws InvalidDateException {
        when(dateValidator.validateDate(Mockito.any(LocalDateTime.class))).thenThrow(InvalidDateException.class);
        try {
            mockMvc.perform(post("/transactions")
                    .content(objectMapper.writeValueAsString(transactionDTO))
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTransactionsTest() {
        try {
            mockMvc.perform(delete("/transactions"))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStatisticsTest() {
        when(transactionStatisticService.getStatistics()).thenReturn(statisticDTO);
        try {
            mockMvc.perform(get("/statistics"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
