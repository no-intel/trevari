package com.trevari.test.domain.search.port.in;

import org.springframework.data.domain.Pageable;

public record BookSearchDto(
        String query,
        Pageable pageable
) {
    public static BookSearchDto of(String query, Pageable pageable) {
        return new BookSearchDto(query, pageable);
    }
}
