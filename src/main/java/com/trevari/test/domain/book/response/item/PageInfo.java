package com.trevari.test.domain.book.response.item;

public record PageInfo(
        int currentPage,
        int pageSize,
        int totalPages,
        long totalElements
) {
    public static PageInfo of(int currentPage, int pageSize, int totalPages, long totalElements) {
        return new PageInfo(currentPage, pageSize, totalPages, totalElements);
    }
}
