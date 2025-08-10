package com.trevari.test.domain.book.controller;

import com.trevari.test.domain.book.dto.GetBooksDto;
import com.trevari.test.domain.book.response.GetBooksResponse;
import com.trevari.test.domain.book.service.GetBookService;
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
public class GetBookController {

    private final GetBookService service;

    @GetMapping
    public ResponseEntity<GetBooksResponse> getBooksKeyword(@RequestParam(required = false) String keyword,
                                                            @PageableDefault Pageable pageable) {
        GetBooksResponse response = service.getBooks(GetBooksDto.of(keyword, pageable));
        return ResponseEntity.ok(response);
    }
}
