package com.trevari.test.domain.book.dto.Projection;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record BooksResponseDto(
    Long id,
    String title,
    String subtitle,
    String image,
    String author,
    Long isbn,
    LocalDate publishDate
) {
    @QueryProjection
    public BooksResponseDto {}
}
