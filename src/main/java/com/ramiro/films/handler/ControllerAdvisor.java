package com.ramiro.films.handler;

import com.ramiro.films.handler.exceptions.MatchNotFoundException;
import com.ramiro.films.handler.exceptions.MoveNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MoveNotFoundException.class, MatchNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(
            Exception ex, WebRequest request) {

        return prepareErrorResponse(ex);
    }

    private ResponseEntity<Object> prepareErrorResponse(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
