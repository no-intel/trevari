package com.trevari.test.domain.book.service;

import com.trevari.test.domain.book.dto.BookSearchDto;
import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.response.BookDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    public final BookService bookService;

    public BookDetailResponse getBookByISBN(BookSearchDto dto) {
        Book book = bookService.getBook(dto.isbn());
        return BookDetailResponse.of(book);
    }
}
