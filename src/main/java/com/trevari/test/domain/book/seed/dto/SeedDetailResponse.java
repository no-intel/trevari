package com.trevari.test.domain.book.seed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeedDetailResponse(
        String title,
        String subtitle,
        String authors,
        String publisher,
        String isbn13,
        String pages,
        String year,
        String price,
        String image
) {
}
