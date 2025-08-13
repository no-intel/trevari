package com.trevari.test.domain.book.port.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trevari.test.domain.book.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "도서 상세 정보 응답 Response")
public record BookDetailResponse(
        @Schema(description = "ID(ISBN 번호)", example = "1001590311930")
        Long id,

        @Schema(description = "ISBN", example = "1001590311930")
        Long isbn,

        @Schema(description = "책 제목", example = "Containerized Docker Application Lifecycle with Microsoft Platform and Tools")
        String title,

        @Schema(description = "부제목", example = "부제목")
        String subtitle,

        @Schema(description = "저자명", example = "Cesar de la Torre")
        String author,

        @Schema(description = "출판사", example = "Microsoft Press")
        String publisher,

        @Schema(description = "책 섬네일 이미지 주소", example = "https://itbook.store/img/books/1001590311930.png")
        String image,

        @Schema(description = "총 페이지", example = "100")
        int pages,

        @Schema(description = "가격", example = "10.00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        BigDecimal price,

        @Schema(description = "출판일", example = "2008-08-01")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate published
) {
    public static BookDetailResponse of(Book book) {
        return new BookDetailResponse(
                book.getIsbn(),
                book.getIsbn(),
                book.getTitle(),
                book.getSubtitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getImage(),
                book.getPages(),
                book.getPrice(),
                book.getPublishDate()
        );
    }
}
