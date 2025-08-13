package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.application.BookSimpleFinderService;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01.도서 단순 목록 조회 API", description = "Keyword 없으면 전체 목록 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookSimpleFinderController {

    private final BookSimpleFinderService service;

    @Operation(summary = "도서 단순 목록 조회", description = "Keyword 정보로 단순 조회합니다.")
    @GetMapping
    public ResponseEntity<BookListResponse> getBooksKeyword(
            @Parameter(description = "검색 keyword", example = "java")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "페이지 정보", example = "page = 0, size = 10")
            @PageableDefault Pageable pageable) {
        BookListResponse response = service.getBooks(BookFinderDto.of(keyword, pageable));
        return ResponseEntity.ok(response);
    }
}
