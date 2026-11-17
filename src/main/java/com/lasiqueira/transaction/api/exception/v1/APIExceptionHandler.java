package com.lasiqueira.transaction.api.exception.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({InvalidDateException.class, StreamReadException.class, DateTimeParseException.class, InvalidFormatException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        // Jackson errors reach this handler wrapped in an HttpMessageNotReadableException, so check the cause too.
        if (isUnprocessable(ex) || isUnprocessable(ex.getCause())) {
            var status = HttpStatus.UNPROCESSABLE_CONTENT;

            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        } else if (ex.getCause() instanceof StreamReadException) {
            var status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        } else {
            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new ApiError(ex.getMessage()), status);
        }
    }

    private static boolean isUnprocessable(Throwable ex) {
        return ex instanceof InvalidDateException || ex instanceof DateTimeParseException || ex instanceof InvalidFormatException;
    }

}
