package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.out.BookSearchResponse;
import com.trevari.test.domain.book.application.BookSearchKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookSearchKeywordController {

    private final BookSearchKeywordService service;

    @GetMapping
    public ResponseEntity<BookSearchResponse> getBooksKeyword(@RequestParam(required = false) String keyword,
                                                              @PageableDefault Pageable pageable) {
        BookSearchResponse response = service.getBooks(BooksSearchDto.of(keyword, pageable));
        return ResponseEntity.ok(response);
    }
}
