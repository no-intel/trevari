package com.trevari.test.domain.book.port.in.dto;

public record BookDetailDto(
        Long isbn
) {
    public static BookDetailDto of(String id) {
        return new BookDetailDto(Long.parseLong(id));
    }
}
