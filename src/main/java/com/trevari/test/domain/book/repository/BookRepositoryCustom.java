package com.trevari.test.domain.book.repository;

import com.trevari.test.domain.book.dto.BooksSearchKeywordDto;
import com.trevari.test.domain.book.dto.Projection.BookSearchResponseDto;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<BookSearchResponseDto> findBooks(BooksSearchKeywordDto dto);
}
