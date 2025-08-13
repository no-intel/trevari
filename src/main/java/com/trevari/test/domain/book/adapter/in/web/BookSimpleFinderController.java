package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.application.BookSimpleFinderService;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
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
public class BookSimpleFinderController {

    private final BookSimpleFinderService service;

    @GetMapping
    public ResponseEntity<BookListResponse> getBooksKeyword(@RequestParam(required = false) String keyword,
                                                            @PageableDefault Pageable pageable) {
        BookListResponse response = service.getBooks(BookFinderDto.of(keyword, pageable));
        return ResponseEntity.ok(response);
    }
}
