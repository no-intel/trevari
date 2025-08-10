package com.trevari.test.domain.book.port.in.dto;

import org.springframework.data.domain.Pageable;

public record BooksSearchDto(
        String query,
        Pageable pageable
) {
    public static BooksSearchDto of(String query, Pageable pageable) {
        return new BooksSearchDto(query, pageable);
    }
}
