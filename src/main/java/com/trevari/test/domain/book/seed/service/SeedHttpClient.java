package com.trevari.test.domain.book.seed.service;

import com.trevari.test.domain.book.seed.dto.SeedDetailResponse;
import com.trevari.test.domain.book.seed.dto.SeedSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SeedHttpClient {
    private final RestClient restClient;
    public SeedHttpClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.itbook.store/1.0").build();
    }

    public SeedSearchResponse search(String query, int page) {
        return restClient.get()
                .uri("/search/{q}/{p}", query, page)
                .retrieve()
                .body(SeedSearchResponse.class);
    }

    public SeedDetailResponse get(String isbn13) {
        return restClient.get()
                .uri("/books/{isbn13}", isbn13)
                .retrieve()
                .body(SeedDetailResponse.class);
    }
}
