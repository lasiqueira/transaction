package com.lasiqueira.transaction.api.validator.v1;

import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateValidatorTest {
    @Autowired
    private DateValidator dateValidator;

    private LocalDateTime futureDateTime;
    private LocalDateTime currentDateTime;
    private LocalDateTime oldDateTime;

    @Before
    public void setup() {
        futureDateTime = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1);
        oldDateTime = LocalDateTime.now(ZoneId.of("UTC")).minusDays(1);
        currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @Test
    public void validateCurrentDateTest() throws InvalidDateException {
        boolean expected = dateValidator.validateDate(currentDateTime);
        assertTrue(expected);
    }

    @Test
    public void validateOldDateTest() throws InvalidDateException {
        boolean expected = dateValidator.validateDate(oldDateTime);
        assertFalse(expected);

    }

    @Test(expected = InvalidDateException.class)
    public void validateFutureDateTest() throws InvalidDateException {
        dateValidator.validateDate(futureDateTime);

    }
}
