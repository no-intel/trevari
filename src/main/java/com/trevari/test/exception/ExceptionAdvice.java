package com.trevari.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TrevariException.class})
    public ResponseEntity<Object> exceptionResponse(TrevariException e) {
        log.error("ExceptionHandler TrevariException - : ", e);
        HashMap<String, Object> body = new HashMap<>();
        body.put("code", e.getCode());
        body.put("field", e.getField());
        body.put("message", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(body);
    }
}