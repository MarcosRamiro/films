package com.ramiro.films.domain.exceptions;

public class LoginNotFoundException extends Exception {
    public LoginNotFoundException(String message) {
        super(message);
    }
}
