package com.trevari.test.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {TrevariException.class})
    public ResponseEntity<Object> exceptionResponse(TrevariException e) {
        log.error("ExceptionHandler TrevariException - : ", e);
        return ResponseEntity.status(e.getCode()).body(Map.of(
                "code", e.getCode(), "field", e.getField(), "message", e.getMessage()
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        log.error("ExceptionHandler ConstraintViolationException - : ", e);
        ConstraintViolation<?> v = e.getConstraintViolations().stream().findFirst().orElse(null);
        String path = v != null ? v.getPropertyPath().toString() : "parameter";
        String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
        String message = v != null ? v.getMessage() : "잘못된 요청 파라미터입니다.";
        return ResponseEntity.badRequest().body(Map.of(
                "code", 400,
                "field", field,
                "message", message
        ));
    }

    // JSON Body(@RequestBody @Valid) 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBodyValidation(MethodArgumentNotValidException e) {
        log.error("ExceptionHandler MethodArgumentNotValidException - : ", e);
        BindingResult binding = e.getBindingResult();
        FieldError fe = binding.getFieldErrors().stream().findFirst().orElse(null);
        ObjectError ge = binding.getGlobalErrors().stream().findFirst().orElse(null);

        String field = fe != null ? fe.getField() : binding.getObjectName();
        String message = fe != null ? fe.getDefaultMessage()
                : ge != null ? ge.getDefaultMessage()
                : "잘못된 요청 본문입니다.";

        return ResponseEntity.badRequest().body(
                Map.of(
                        "code", 400,
                        "field", field,
                        "message", message
                ));
    }

}