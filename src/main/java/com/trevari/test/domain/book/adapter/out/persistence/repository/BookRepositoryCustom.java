package com.trevari.test.domain.book.adapter.out.persistence.repository;

import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.BookMultiFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<BookListResponseDto> findBooks(BookFinderDto dto);
    Page<BookListResponseDto> findBooksWithOr(BookMultiFinderDto dto);
    Page<BookListResponseDto> findBooksWithNot(BookMultiFinderDto dto);
}
