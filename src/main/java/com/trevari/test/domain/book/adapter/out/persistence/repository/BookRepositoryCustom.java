package com.trevari.test.domain.book.adapter.out.persistence.repository;

import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListResponseDto> findBooks(BookFinderDto dto);
    Page<BookListResponseDto> findBooksWithOr(String first, String second, Pageable pageable);
    Page<BookListResponseDto> findBooksWithNot(String first, String second, Pageable pageable);
}
