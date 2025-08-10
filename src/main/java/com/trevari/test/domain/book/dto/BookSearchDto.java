package com.trevari.test.domain.book.dto;

public record BookSearchDto(
        Long isbn
) {
    public static BookSearchDto of(Long isbn) {
        return new BookSearchDto(isbn);
    }
}
