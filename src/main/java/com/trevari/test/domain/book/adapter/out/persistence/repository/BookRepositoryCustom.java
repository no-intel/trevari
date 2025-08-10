package com.trevari.test.domain.book.adapter.out.persistence.repository;

import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<BookListResponseDto> findBooks(BooksSearchDto dto);
}
