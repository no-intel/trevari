package com.trevari.test.domain.book.repository;

import com.trevari.test.domain.book.dto.Projection.BooksResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BooksResponseDto> findBooks(Pageable pageable);
}
