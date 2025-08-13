package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.search.applicateion.PopularKeywordQueryService;
import com.trevari.test.domain.search.port.out.redis.PopularKeywordResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PopularKeywordQueryController.class)
class PopularKeywordQueryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PopularKeywordQueryService service;

    @Test
    @DisplayName("인기 검색어 Top10 조회 API")
    void get_top_keywords_returns_ok_response() throws Exception {
        // given
        List<PopularKeywordResponse> mockList = List.of(
                new PopularKeywordResponse(1, "apple"),
                new PopularKeywordResponse(2, "banana")
        );

        when(service.getTopKeywords()).thenReturn(mockList);

        // when & then
        mockMvc.perform(get("/api/search/popular-keywords")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].rank").value(1))
                .andExpect(jsonPath("$.data[0].keyword").value("apple"))
                .andExpect(jsonPath("$.data[1].rank").value(2))
                .andExpect(jsonPath("$.data[1].keyword").value("banana"));
    }
}