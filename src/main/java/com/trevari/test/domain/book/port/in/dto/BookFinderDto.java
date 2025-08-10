package com.trevari.test.domain.book.port.in.dto;

import org.springframework.data.domain.Pageable;

public record BookFinderDto(
        String query,
        Pageable pageable
) {
    public static BookFinderDto of(String query, Pageable pageable) {
        return new BookFinderDto(query, pageable);
    }
}
