package com.ramiro.films.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MoveNotFoundException extends RuntimeException {

    public MoveNotFoundException() {
        super();
    }

    public MoveNotFoundException(String message) {
        super(message);
    }

}
