package com.lasiqueira.transaction.api.validator.v1;

import com.lasiqueira.transaction.api.exception.v1.InvalidDateException;
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
public class DateValidatorTest {
    @Autowired
    private DateValidator dateValidator;

    private LocalDateTime futureDateTime;
    private LocalDateTime currentDateTime;
    private LocalDateTime oldDateTime;

    @BeforeAll
    public void setup() {
        futureDateTime = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1);
        oldDateTime = LocalDateTime.now(ZoneId.of("UTC")).minusDays(1);
        currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @Test
    public void validateCurrentDateTest() throws InvalidDateException {
        var expected = dateValidator.validateDate(currentDateTime);
        Assertions.assertTrue(expected);
    }

    @Test
    public void validateOldDateTest() throws InvalidDateException {
        var expected = dateValidator.validateDate(oldDateTime);
        Assertions.assertFalse(expected);

    }

    @Test
    public void validateFutureDateTest() throws InvalidDateException {
        Assertions.assertThrows(InvalidDateException.class, () -> dateValidator.validateDate(futureDateTime));

    }
}
