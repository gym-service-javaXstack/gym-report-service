package com.example.gymreport.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorType errorType = ErrorType.FATAL_ERROR;

    public ServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ServiceException(String message) {
        super(message);
    }
}