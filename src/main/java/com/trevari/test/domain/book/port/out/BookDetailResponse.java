package com.trevari.test.domain.book.port.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trevari.test.domain.book.entity.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookDetailResponse(
        Long id,
        Long isbn,
        String title,
        String subtitle,
        String author,
        String publisher,
        String image,
        int pages,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        BigDecimal price,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate published,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
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
                book.getPublishDate(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
