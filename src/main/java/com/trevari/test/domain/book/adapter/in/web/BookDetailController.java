package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.port.in.dto.BookSearchDto;
import com.trevari.test.domain.book.port.out.BookDetailResponse;
import com.trevari.test.domain.book.application.BookDetailService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Validated
public class BookDetailController {

    private final BookDetailService service;

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponse> getBooks(@PathVariable("id")
                                                           @Pattern(regexp = "\\d{13}", message = "ISBN은 숫자 13자리여야 합니다.")
                                                           String id) {
        BookDetailResponse response = service.getBookByISBN(BookSearchDto.of(id));
        return ResponseEntity.ok(response);
    }
}
