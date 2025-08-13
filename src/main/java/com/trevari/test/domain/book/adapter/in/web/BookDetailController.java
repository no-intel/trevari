package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.application.BookDetailService;
import com.trevari.test.domain.book.port.in.dto.BookDetailDto;
import com.trevari.test.domain.book.port.out.BookDetailResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "도서 상세 검색 API", description = "ISBN 기준 도서 상세 검색 API.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Validated
public class BookDetailController {

    private final BookDetailService service;

    @Operation(summary = "도서 상세 조회", description = "ISBN으로 도서 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponse> getBook(
            @Parameter(description = "도서 ISBN", example = "9781617291609")
            @PathVariable("id")
            @Pattern(regexp = "\\d{13}", message = "ISBN은 숫자 13자리여야 합니다.")
            String id) {
        BookDetailResponse response = service.getBookByISBN(BookDetailDto.of(id));
        return ResponseEntity.ok(response);
    }
}
