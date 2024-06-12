package com.example.gymreport.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthorizedException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorType errorType = ErrorType.AUTHORIZATION_ERROR;

    private LocalDateTime timeStamp;

    public UnauthorizedException(String message, ErrorType errorType, LocalDateTime timeStamp) {
        super(message, errorType);
        this.timeStamp = timeStamp;
    }

    public UnauthorizedException(String message, LocalDateTime timeStamp) {
        super(message);
        this.timeStamp = timeStamp;
    }
}
