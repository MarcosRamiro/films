package com.ramiro.films.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MoveNotFoundException extends Exception {

    public MoveNotFoundException(String message) {
        super(message);
    }

}
