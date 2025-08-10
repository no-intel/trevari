package com.trevari.test.exception;

import lombok.Getter;

@Getter
public class TrevariException extends RuntimeException {
    private int code;
    private String field;
    private String message;

    public TrevariException(int code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }
}