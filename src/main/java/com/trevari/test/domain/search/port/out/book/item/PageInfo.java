package com.trevari.test.domain.search.port.out.book.item;

public record PageInfo(
        int currentPage,
        int pageSize,
        int totalPages,
        long totalElements
) {
    public static PageInfo of(com.trevari.test.domain.book.port.out.item.PageInfo pageInfo) {
        return new PageInfo(pageInfo.currentPage(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements());
    }
}
