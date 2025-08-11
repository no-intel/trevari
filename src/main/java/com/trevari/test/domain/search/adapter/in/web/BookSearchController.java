package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.search.applicateion.BookSearchService;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.BookSearchResponse;
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
@RequestMapping("/api/search/books")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping
    public ResponseEntity<BookSearchResponse> searchBook(@RequestParam(required = false) String q,
                                                         @PageableDefault Pageable pageable) {
        BookSearchResponse response = bookSearchService.searchBook(BookSearchDto.of(q, pageable));
        return ResponseEntity.ok(response);
    }
}
