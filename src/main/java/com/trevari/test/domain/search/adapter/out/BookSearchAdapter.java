package com.trevari.test.domain.search.adapter.out;

import com.trevari.test.domain.book.port.in.BookSearchUseCase;
import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSearchAdapter {
    private final BookSearchUseCase bookSearchUseCase;

    public BookListResponse searchBook(String query, Pageable pageable){
        return bookSearchUseCase.getBooks(BooksSearchDto.of(query, pageable));
    }
}
