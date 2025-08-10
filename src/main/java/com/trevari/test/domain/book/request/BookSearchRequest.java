package com.trevari.test.domain.book.request;

import org.hibernate.validator.constraints.ISBN;

import static org.hibernate.validator.constraints.ISBN.Type.ISBN_13;

public record BookSearchRequest(
        @ISBN(type = ISBN_13, message = "ISBN은 13자리 입니다.")
        String id
) {
}
