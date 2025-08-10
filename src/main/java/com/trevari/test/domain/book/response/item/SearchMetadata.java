package com.trevari.test.domain.book.response.item;

import com.trevari.test.domain.book.enums.SearchStrategyEnum;

public record SearchMetadata(
        long executionTime,
        SearchStrategyEnum strategy
) {
    public static SearchMetadata of(long executionTime, SearchStrategyEnum strategy) {
        return new SearchMetadata(executionTime, strategy);
    }
}