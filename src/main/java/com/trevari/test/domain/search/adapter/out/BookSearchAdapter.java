package com.trevari.test.domain.search.adapter.out;

import com.trevari.test.domain.book.port.in.BookFinderUseCase;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSearchAdapter {
    private final BookFinderUseCase bookSearchUseCase;

    public BookListResponse searchBook(String query, Pageable pageable){
        return bookSearchUseCase.getBooks(BookFinderDto.of(query, pageable));
    }
}
