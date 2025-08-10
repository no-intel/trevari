package com.trevari.test.domain.book.infrastructure.repository;

import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookSearchResponseDto;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<BookSearchResponseDto> findBooks(BooksSearchDto dto);
}
