package com.trevari.test.domain.book.exception;

import com.trevari.test.exception.TrevariException;

public class NotFoundBookException extends TrevariException {
    public NotFoundBookException(String field) {
        super(404, field, "존재 하지 않는 도서");
    }
}
