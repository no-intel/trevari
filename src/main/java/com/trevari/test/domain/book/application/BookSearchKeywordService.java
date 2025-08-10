package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.infrastructure.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookSearchResponseDto;
import com.trevari.test.domain.book.port.out.BookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookSearchKeywordService {
    private final BookRepositoryCustom bookRepositoryCustom;

    public BookSearchResponse getBooks(BooksSearchDto dto) {
        long start = System.currentTimeMillis();
        Page<BookSearchResponseDto> books = bookRepositoryCustom.findBooks(dto);
        long executionTime = System.currentTimeMillis() - start;
        return BookSearchResponse.of(dto.query(), books, executionTime);
    }

}
