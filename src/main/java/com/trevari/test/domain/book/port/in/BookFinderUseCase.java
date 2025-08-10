package com.trevari.test.domain.book.port.in;

import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.out.BookListResponse;

public interface BookFinderUseCase {
    BookListResponse getBooks(BookFinderDto dto);
}
