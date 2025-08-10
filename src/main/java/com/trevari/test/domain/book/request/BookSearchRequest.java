package com.trevari.test.domain.book.request;

import org.hibernate.validator.constraints.ISBN;

import static org.hibernate.validator.constraints.ISBN.Type.ISBN_13;

public record BookSearchRequest(
        @ISBN(type = ISBN_13)
        Long isbn
) {
}
