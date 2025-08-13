package com.trevari.test.domain.book.seed.service;

import com.trevari.test.domain.book.seed.dto.SeedDetailResponse;
import com.trevari.test.domain.book.seed.dto.SeedSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(SeedHttpClient.class)
class SeedHttpClientTest {

    @Autowired
    SeedHttpClient client;

    @Autowired
    MockRestServiceServer server;

    final String URL = "https://api.itbook.store/1.0";

    @Test
    @DisplayName("restClient 목록 조회")
    void get_seed_data() {
        // given
        server.expect(requestTo(URL + "/search/MongoDB/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                  {
                    "error":"0",
                    "total":"100",
                    "page":"1",
                    "books":[{},{},{},{},{},{},{},{},{},{}]
                  }
                  """, MediaType.APPLICATION_JSON));

        // when
        SeedSearchResponse response = client.search("MongoDB", 1);

        // then
        assertThat(response).isNotNull();
        assertThat(response.books()).hasSize(10);
    }

    @Test
    @DisplayName("restClient 상세 조회")
    void find_seed_data() {
        // given
        server.expect(requestTo(URL + "/books/9781617294136"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                  {
                    "title":"Securing DevOps",
                    "subtitle":"Security in the Cloud",
                    "authors":"Julien Vehent",
                    "publisher":"Manning",
                    "isbn13":"9781617294136",
                    "pages":"384",
                    "year":"2018",
                    "image":"https://cdn.example/9781617294136.png"
                  }
                  """, MediaType.APPLICATION_JSON));

        // when
        SeedDetailResponse response = client.get("9781617294136");

        // then
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