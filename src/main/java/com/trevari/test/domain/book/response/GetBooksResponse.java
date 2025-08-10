package com.trevari.test.domain.book.response;

import com.trevari.test.domain.book.dto.Projection.BooksResponseDto;
import com.trevari.test.domain.book.response.item.BookItem;
import com.trevari.test.domain.book.response.item.PageInfo;
import com.trevari.test.domain.book.response.item.SearchMetadata;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.trevari.test.domain.book.enums.SearchStrategyEnum.KEYWORD;

public record GetBooksResponse(
        String keyword,
        PageInfo pageInfo,
        List<BookItem> books,
        SearchMetadata searchMetadata
) {
    public static GetBooksResponse of(String keyword, Page<BooksResponseDto> books, long executionTime) {
        return new GetBooksResponse(
                keyword,
                PageInfo.of(books.getPageable().getPageNumber() + 1, books.getPageable().getPageSize(), books.getTotalPages(), books.getTotalElements()),
                BookItem.of(books.getContent()),
                SearchMetadata.of(executionTime, KEYWORD)
        );
    };
}
