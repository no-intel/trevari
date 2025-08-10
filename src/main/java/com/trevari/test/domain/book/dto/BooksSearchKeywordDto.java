package com.trevari.test.domain.book.dto;

import org.springframework.data.domain.Pageable;

public record BooksSearchKeywordDto(
        String keyword,
        Pageable pageable
) {
    public static BooksSearchKeywordDto of(String keyword, Pageable pageable) {
        return new BooksSearchKeywordDto(keyword, pageable);
    }
}
