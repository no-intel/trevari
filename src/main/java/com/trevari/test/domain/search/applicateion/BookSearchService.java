package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.adapter.out.book.BookSearchAdapter;
import com.trevari.test.domain.search.adapter.out.redis.event.PopularKeywordEvent;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.book.BookSearchResponse;
import com.trevari.test.domain.search.util.BookSearchParsingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookSearchAdapter bookSearchAdapter;
    private final BookSearchParsingUtil parsingUtil;
    private final ApplicationEventPublisher eventPublisher;

    public BookSearchResponse searchBook(BookSearchDto dto) {
        SearchStrategyEnum strategyEnum = parsingUtil.parsingAndCheck(dto.query());
        BookListResponse response = bookSearchAdapter.searchBook(dto.query(), dto.pageable(), strategyEnum);
        eventPublisher.publishEvent(PopularKeywordEvent.of(dto.query()));
        return BookSearchResponse.of(dto.query(), response);
    }
}
