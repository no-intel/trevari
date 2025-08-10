package com.trevari.test.domain.book.service;

import com.trevari.test.domain.book.dto.BooksSearchKeywordDto;
import com.trevari.test.domain.book.dto.Projection.BookSearchResponseDto;
import com.trevari.test.domain.book.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.response.BookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookSearchKeywordService {
    private final BookRepositoryCustom bookRepositoryCustom;

    public BookSearchResponse getBooks(BooksSearchKeywordDto dto) {
        long start = System.currentTimeMillis();
        Page<BookSearchResponseDto> books = bookRepositoryCustom.findBooks(dto);
        long executionTime = System.currentTimeMillis() - start;
        return BookSearchResponse.of(dto.keyword(), books, executionTime);
    }

}
