package com.trevari.test.domain.book.seed.service;

import com.trevari.test.domain.book.seed.dto.SeedDetailResponse;
import com.trevari.test.domain.book.seed.dto.SeedSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SeedHttpClientTest {

    @Autowired
    SeedHttpClient client;

    @Test
    void seedHttpClientTest_목록_조회() {
        SeedSearchResponse search = client.search("MongoDB", 1);
        assertThat(search.books().size()).isEqualTo(10);
    }

    @Test
    void seedHttpClientTest_상세_조회() {
        SeedDetailResponse response = client.get("9781617294136");

        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("Securing DevOps");
        assertThat(response.subtitle()).isEqualTo("Security in the Cloud");
        assertThat(response.authors()).isEqualTo("Julien Vehent");
        assertThat(response.publisher()).isEqualTo("Manning");
        assertThat(response.isbn13()).isEqualTo("9781617294136");
        assertThat(response.pages()).isEqualTo("384");
        assertThat(response.year()).isEqualTo("2018");
        assertThat(response.image()).endsWith("/9781617294136.png");
    }
}