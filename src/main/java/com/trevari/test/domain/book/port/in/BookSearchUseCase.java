package com.trevari.test.domain.book.port.in;

import com.trevari.test.domain.book.port.in.dto.BooksSearchDto;
import com.trevari.test.domain.book.port.out.BookListResponse;

public interface BookSearchUseCase {
    BookListResponse getBooks(BooksSearchDto dto);
}
