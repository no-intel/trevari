package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.adapter.out.BookSearchAdapter;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.BookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookSearchAdapter bookSearchAdapter;

    public BookSearchResponse searchBook(BookSearchDto dto) {
        BookListResponse response = bookSearchAdapter.searchBook(dto.query(), dto.pageable());
        return BookSearchResponse.of(dto.query(), response);
    }
}
