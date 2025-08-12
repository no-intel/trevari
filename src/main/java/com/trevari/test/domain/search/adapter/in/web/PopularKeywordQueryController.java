package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.search.applicateion.PopularKeywordQueryService;
import com.trevari.test.domain.search.port.out.redis.ApiResponse;
import com.trevari.test.domain.search.port.out.redis.KeywordRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/popular-keywords")
public class PopularKeywordQueryController {
    private final PopularKeywordQueryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KeywordRankResponse>>> getTopKeywords() {
        List<KeywordRankResponse> responses = service.getTopKeywords();
        return ResponseEntity.ok(ApiResponse.of(responses));
    }
}
