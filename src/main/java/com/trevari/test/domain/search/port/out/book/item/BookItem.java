package com.trevari.test.domain.search.port.out.book.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "검색 도서 정보")
public record BookItem(
        @Schema(description = "ID(ISBN 번호)", example = "1001590311930")
        Long id,

        @Schema(description = "책 제목", example = "Containerized Docker Application Lifecycle with Microsoft Platform and Tools")
        String title,

        @Schema(description = "부제목", example = "부제목")
        String subtitle,

        @Schema(description = "책 섬네일 이미지 주소", example = "https://itbook.store/img/books/1001590311930.png")
        String image,

        @Schema(description = "저자명", example = "Cesar de la Torre")
        String author,

        @Schema(description = "ISBN", example = "1001590311930")
        Long isbn,

        @Schema(description = "출판일", example = "2020-01-01")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate published
) {
    public static List<BookItem> of(List<com.trevari.test.domain.book.port.out.item.BookItem> books) {
        List<BookItem> bookItems = new ArrayList<>();
        for (var book : books) {
            bookItems.add(
                    new BookItem(
                            book.isbn(),
                            book.title(),
                            book.subtitle(),
                            book.image(),
                            book.author(),
                            book.isbn(),
                            book.published()
                    )
            );
        }
        return bookItems;
    }
}