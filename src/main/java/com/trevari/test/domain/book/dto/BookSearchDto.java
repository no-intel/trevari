package com.trevari.test.domain.book.dto;

public record BookSearchDto(
        Long isbn
) {
    public static BookSearchDto of(String id) {
        return new BookSearchDto(Long.parseLong(id));
    }
}
