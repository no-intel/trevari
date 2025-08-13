package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.search.applicateion.PopularKeywordQueryService;
import com.trevari.test.domain.search.port.out.redis.ApiResponse;
import com.trevari.test.domain.search.port.out.redis.PopularKeywordResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "인기 검색어 조회 API", description = "인기 검색어 TOP 10")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/popular-keywords")
public class PopularKeywordQueryController {
    private final PopularKeywordQueryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PopularKeywordResponse>>> getTopKeywords() {
        List<PopularKeywordResponse> responses = service.getTopKeywords();
        return ResponseEntity.ok(ApiResponse.of(responses));
    }
}
