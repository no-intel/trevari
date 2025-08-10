package com.trevari.test.domain.book.dto;

import org.springframework.data.domain.Pageable;

public record GetBooksDto(
        String keyword,
        Pageable pageable
) {
}
