package com.lasiqueira.transaction.api.exception.v1;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({InvalidDateException.class, JsonParseException.class, DateTimeParseException.class, InvalidFormatException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        if (ex instanceof InvalidDateException || ex instanceof DateTimeParseException || ex instanceof InvalidFormatException) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        } else if (ex.getCause() instanceof JsonParseException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        }
    }

}
