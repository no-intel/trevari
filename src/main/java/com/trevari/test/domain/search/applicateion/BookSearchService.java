package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.adapter.out.BookSearchAdapter;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.BookSearchResponse;
import com.trevari.test.domain.search.util.BookSearchParsingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookSearchAdapter bookSearchAdapter;
    private final BookSearchParsingUtil parsingUtil;

    public BookSearchResponse searchBook(BookSearchDto dto) {
        SearchStrategyEnum strategyEnum = parsingUtil.parsingAndCheck(dto.query());
        BookListResponse response = bookSearchAdapter.searchBook(dto.query(), dto.pageable(), strategyEnum);
        return BookSearchResponse.of(dto.query(), response);
    }
}
