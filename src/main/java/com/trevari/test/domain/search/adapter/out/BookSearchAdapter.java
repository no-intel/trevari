package com.trevari.test.domain.search.adapter.out;

import com.trevari.test.domain.book.port.in.BookSearchUseCase;
import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.port.out.BookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSearchAdapter {
    private final BookSearchUseCase bookSearchUseCase;

    public BookSearchResponse searchBook(String query, Pageable pageable){
        BookListResponse books = bookSearchUseCase.getBooks(BooksSearchDto.of(query, pageable));
        return BookSearchResponse.of(query, books);
    }
}
