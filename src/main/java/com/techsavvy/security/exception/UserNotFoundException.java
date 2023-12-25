package com.techsavvy.security.exception;

import lombok.AllArgsConstructor;

public class UserNotFoundException extends Exception {
    private String errorMessage;

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
