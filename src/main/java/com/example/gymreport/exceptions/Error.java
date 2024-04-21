package com.example.gymreport.exceptions;

import java.time.LocalDateTime;

public record Error(String message, ErrorType errorType, LocalDateTime timeStamp) {
}