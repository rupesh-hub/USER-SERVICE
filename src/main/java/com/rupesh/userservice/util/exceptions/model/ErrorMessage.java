package com.rupesh.userservice.util.exceptions.model;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Builder
public class ErrorMessage implements Serializable {

    private String message;
    private HttpStatus status;
    private int statusCode;
    private LocalDateTime timeStamp;
    private TimeZone timeZone;

}
