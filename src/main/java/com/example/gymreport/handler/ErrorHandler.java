package com.example.gymreport.handler;

import com.example.gymreport.exceptions.Error;
import com.example.gymreport.exceptions.ErrorType;
import com.example.gymreport.exceptions.UnauthorizedException;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException ex) {
        log.error("handleBadRequestException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("handleMissingRequestHeaderException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.VALIDATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("handleUnauthorizedException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ex.getErrorType(), ex.getTimeStamp());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleException(Exception ex) {
        log.error("handleException: message {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }
}
