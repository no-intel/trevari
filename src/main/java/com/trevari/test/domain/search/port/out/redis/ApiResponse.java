package com.trevari.test.domain.search.port.out.redis;

public record ApiResponse<T>(
        T data
) {
    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }
}
