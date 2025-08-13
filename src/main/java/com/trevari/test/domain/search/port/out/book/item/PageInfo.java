package com.trevari.test.domain.search.port.out.book.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "검색 페이지 정보")
public record PageInfo(
        @Schema(description = "검색 페이지", example = "1")
        int currentPage,

        @Schema(description = "페이지 목록 사이즈", example = "10")
        int pageSize,

        @Schema(description = "검색 도서 총 페이지", example = "5")
        int totalPages,

        @Schema(description = "총 검색 도서", example = "50")
        long totalElements
) {
    public static PageInfo of(com.trevari.test.domain.book.port.out.item.PageInfo pageInfo) {
        return new PageInfo(pageInfo.currentPage(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements());
    }
}
