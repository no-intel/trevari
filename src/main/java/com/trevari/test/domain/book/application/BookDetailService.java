package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.port.in.dto.BookDetailDto;
import com.trevari.test.domain.book.port.out.BookDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDetailService {

    public final BookService bookService;

    public BookDetailResponse getBookByISBN(BookDetailDto dto) {
        Book book = bookService.getBook(dto.isbn());
        return BookDetailResponse.of(book);
    }
}
