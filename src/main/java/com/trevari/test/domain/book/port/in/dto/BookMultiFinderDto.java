package com.trevari.test.domain.book.port.in.dto;

import org.springframework.data.domain.Pageable;

public record BookMultiFinderDto(
        String first,
        String second,
        Pageable pageable
) {
    public static BookMultiFinderDto of(String first, String second, Pageable pageable) {
        return new BookMultiFinderDto(first, second, pageable);
    }
}
