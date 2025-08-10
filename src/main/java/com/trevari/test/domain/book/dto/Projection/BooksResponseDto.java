package com.trevari.test.domain.book.dto.Projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDate;

@Builder
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
