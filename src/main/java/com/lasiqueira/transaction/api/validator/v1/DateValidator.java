package com.lasiqueira.transaction.api.validator.v1;

import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateValidator {
    private Logger logger = LoggerFactory.getLogger(DateValidator.class);

    public boolean validateDate(LocalDateTime localDateTime) throws InvalidDateException {
        logger.debug("validateDate: {}", localDateTime);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        if (localDateTime.compareTo(now) > 0) {
            logger.debug("DateTime is in the future.");
            throw new InvalidDateException("Invalid Date");
        } else if (localDateTime.compareTo(now.minusSeconds(60)) < 0) {
            logger.debug("DateTime is older than 60 seconds");
            return Boolean.FALSE;
        } else {
            logger.debug("DateTime is valid");
            return Boolean.TRUE;
        }

    }
}
