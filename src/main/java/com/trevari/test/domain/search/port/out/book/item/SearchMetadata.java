package com.trevari.test.domain.search.port.out.book.item;

import com.trevari.test.domain.book.enums.SearchStrategyEnum;

public record SearchMetadata(
        long executionTime,
        SearchStrategyEnum strategy
) {
    public static SearchMetadata of(com.trevari.test.domain.book.port.out.item.SearchMetadata searchMetadata) {
        return new SearchMetadata(searchMetadata.executionTime(), searchMetadata.strategy());
    }
}