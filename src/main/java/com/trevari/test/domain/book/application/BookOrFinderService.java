package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.adapter.out.persistence.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.enums.SearchStrategyEnum;
import com.trevari.test.domain.book.port.in.BookFinderUseCase;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookOrFinderService implements BookFinderUseCase {
    private final BookRepositoryCustom bookRepositoryCustom;

    @Override
    public String getType() {
        return SearchStrategyEnum.OR_OPERATION.name();
    }

    public BookListResponse getBooks(BookFinderDto dto) {
        long start = System.currentTimeMillis();
        String[] query = dto.query().split("\\|");
        Page<BookListResponseDto> books = bookRepositoryCustom.findBooksWithOr(query[0].trim(), query[1].trim(), dto.pageable());
        long executionTime = System.currentTimeMillis() - start;
        return BookListResponse.of(dto.query(), books, executionTime);
    }
}
