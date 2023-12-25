package com.techsavvy.security.exception;

public class ApiAccessDeniedException extends Exception {
    private String errorMessage;
    public ApiAccessDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
