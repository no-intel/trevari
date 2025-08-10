package com.trevari.test.domain.book.port.out;

import com.trevari.test.domain.book.port.in.dto.Projection.BookSearchResponseDto;
import com.trevari.test.domain.book.port.out.item.BookItem;
import com.trevari.test.domain.book.port.out.item.PageInfo;
import com.trevari.test.domain.book.port.out.item.SearchMetadata;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.trevari.test.domain.book.enums.SearchStrategyEnum.KEYWORD;

public record BookSearchResponse(
        String keyword,
        PageInfo pageInfo,
        List<BookItem> books,
        SearchMetadata searchMetadata
) {
    public static BookSearchResponse of(String keyword, Page<BookSearchResponseDto> books, long executionTime) {
        return new BookSearchResponse(
                keyword,
                PageInfo.of(books.getPageable().getPageNumber() + 1, books.getPageable().getPageSize(), books.getTotalPages(), books.getTotalElements()),
                BookItem.of(books.getContent()),
                SearchMetadata.of(executionTime, KEYWORD)
        );
    };
}
