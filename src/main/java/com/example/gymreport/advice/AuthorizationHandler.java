package com.example.gymreport.advice;

public interface AuthorizationHandler {
    boolean isApplicable(String authHeader);

    void processAuthorization(String authHeader);
}
