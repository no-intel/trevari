package com.trevari.test.domain.book.port.out.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record BookItem(
        Long id,
        String title,
        String subtitle,
        String image,
        String author,
        Long isbn,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate published
) {
    public static List<BookItem> of(List<BookListResponseDto> books) {
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
                            book.publishDate()
                    )
            );
        }
        return bookItems;
    }
}