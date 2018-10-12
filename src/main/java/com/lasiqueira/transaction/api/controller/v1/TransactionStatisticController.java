package com.lasiqueira.transaction.api.controller.v1;

import com.lasiqueira.transaction.api.converter.v1.TransactionStatisticConverter;
import com.lasiqueira.transaction.api.dto.v1.StatisticDTO;
import com.lasiqueira.transaction.api.dto.v1.TransactionDTO;
import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
import com.lasiqueira.transaction.api.validator.v1.DateValidator;
import com.lasiqueira.transaction.service.TransactionStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class TransactionStatisticController {
    private Logger logger = LoggerFactory.getLogger(TransactionStatisticController.class);
    private TransactionStatisticService transactionStatisticService;
    private DateValidator dateValidator;
    private TransactionStatisticConverter transactionStatisticConverter;

    public TransactionStatisticController(TransactionStatisticService transactionStatisticService,
                                          DateValidator dateValidator,
                                          TransactionStatisticConverter transactionStatisticConverter) {
        this.transactionStatisticService = transactionStatisticService;
        this.dateValidator = dateValidator;
        this.transactionStatisticConverter = transactionStatisticConverter;
    }

    @PostMapping(value = "/transactions", produces = "application/json")
    public ResponseEntity<Void> createTransaction(@RequestBody @Valid TransactionDTO request) throws InvalidDateException {
        logger.debug("createTransaction: {}", request);
        boolean valid = dateValidator.validateDate(request.getTimestamp());
        HttpStatus status = HttpStatus.CREATED;
        if(valid){
            transactionStatisticService.createTransaction(transactionStatisticConverter.convertTransactionDTOToTransaction(request));
        } else{
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping(value = "/transactions", produces = "application/json")
    public ResponseEntity<Void> deleteTransactions(){
        logger.debug("deleteTransactions");
        transactionStatisticService.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/statistics", produces = "application/json")
    public ResponseEntity<StatisticDTO> getStatistics(){
        logger.debug("getStatistics");
        return new ResponseEntity<>(transactionStatisticConverter.convertStatisticToStatisticDTO(transactionStatisticService.getStatistics()), HttpStatus.OK);
    }
}
