package com.trevari.test.domain.search.adapter.out;

import com.trevari.test.domain.book.port.in.BookFinderUseCase;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSearchAdapter {
    private final BookSearchRouter router;

    public BookListResponse searchBook(String query, Pageable pageable, SearchStrategyEnum strategy) {
        BookFinderUseCase useCase = router.get(strategy);
        return useCase.getBooks(BookFinderDto.of(query, pageable));
    }
}
