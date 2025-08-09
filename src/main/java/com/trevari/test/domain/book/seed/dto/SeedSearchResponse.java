package com.trevari.test.domain.book.seed.dto;

import java.util.List;

public record SeedSearchResponse(
        String error,
        String total,
        String page,
        List<SeedListItem> books
) {
    public record SeedListItem(
            String title,
            String subtitle,
            String isbn13,
            String price,
            String image,
            String url
    ) {
    }

}
