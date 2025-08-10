package com.trevari.test.domain.book.repository;

import com.trevari.test.domain.book.dto.GetBooksDto;
import com.trevari.test.domain.book.dto.Projection.BooksResponseDto;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<BooksResponseDto> findBooks(GetBooksDto dto);
}
