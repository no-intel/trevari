package com.trevari.test.domain.search.port.out.book;

import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.port.out.book.item.BookItem;
import com.trevari.test.domain.search.port.out.book.item.PageInfo;
import com.trevari.test.domain.search.port.out.book.item.SearchMetadata;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "도서 검색 정보 응답 Response")
public record BookSearchResponse(
        @Schema(description = "검색 쿼리", example = "a, a|b, a-b")
        String searchQuery,

        PageInfo pageInfo,

        List<BookItem> books,

        SearchMetadata searchMetadata
) {
    public static BookSearchResponse of(String query, BookListResponse books) {
        return new BookSearchResponse(
                query,
                PageInfo.of(books.pageInfo()),
                BookItem.of(books.books()),
                SearchMetadata.of(books.searchMetadata())
        );
    };
}
