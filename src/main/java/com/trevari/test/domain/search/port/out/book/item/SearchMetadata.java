package com.trevari.test.domain.search.port.out.book.item;

import com.trevari.test.domain.book.enums.SearchStrategyEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "검색 메타 정보")
public record SearchMetadata(
        @Schema(description = "검색 시간", example = "23")
        long executionTime,

        @Schema(description = "검색 전략", example = "OR_OPERATION")
        SearchStrategyEnum strategy
) {
    public static SearchMetadata of(com.trevari.test.domain.book.port.out.item.SearchMetadata searchMetadata) {
        return new SearchMetadata(searchMetadata.executionTime(), searchMetadata.strategy());
    }
}