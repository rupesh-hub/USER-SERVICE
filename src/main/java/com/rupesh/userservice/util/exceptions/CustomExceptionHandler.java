package com.rupesh.userservice.util.exceptions;

import com.rupesh.userservice.util.exceptions.domain.ResourceNotFoundException;
import com.rupesh.userservice.util.exceptions.domain.UserExistsException;
import com.rupesh.userservice.util.exceptions.model.ErrorMessage;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {UserExistsException.class})
    @ResponseStatus(value = CONFLICT)
    public ResponseEntity<ErrorMessage> userExistsException(final UserExistsException ex) {
        return this.errorResponse(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(final ResourceNotFoundException ex) {
        return this.errorResponse(ex.getMessage(), CONFLICT);
    }


    private ResponseEntity<ErrorMessage> errorResponse(final String message,
                                                       final HttpStatus status) {
        return new ResponseEntity(
                ErrorMessage
                        .builder()
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .timeStamp(LocalDateTime.now())
                        .timeZone(TimeZone.getTimeZone("Z"))
                        .build()
                ,
                status
        );
    }

}
