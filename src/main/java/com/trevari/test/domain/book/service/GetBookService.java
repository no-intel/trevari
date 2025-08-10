package com.trevari.test.domain.book.service;

import com.trevari.test.domain.book.dto.GetBooksDto;
import com.trevari.test.domain.book.dto.Projection.BooksResponseDto;
import com.trevari.test.domain.book.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.response.GetBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBookService {
    private final BookRepositoryCustom bookRepositoryCustom;

    public GetBooksResponse getBooks(GetBooksDto dto) {
        long start = System.currentTimeMillis();
        Page<BooksResponseDto> books = bookRepositoryCustom.findBooks(dto);
        long executionTime = System.currentTimeMillis() - start;
        return GetBooksResponse.of(dto.keyword(), books, executionTime);
    }
}
