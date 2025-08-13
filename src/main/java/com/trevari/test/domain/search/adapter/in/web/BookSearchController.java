package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.search.applicateion.BookSearchService;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.book.BookSearchResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "03.도서 검색 API", description = "단순 키워드, OR, NOT 검색")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/books")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping
    public ResponseEntity<BookSearchResponse> searchBook(
            @Parameter(description = "검색 쿼리", example = "a, a|b, a-b 형식만 유효")
            @RequestParam(required = false) String q,
            @ParameterObject
            @PageableDefault Pageable pageable) {
        BookSearchResponse response = bookSearchService.searchBook(BookSearchDto.of(q, pageable));
        return ResponseEntity.ok(response);
    }
}
