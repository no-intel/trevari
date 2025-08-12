package com.trevari.test.domain.search.port.out.book;

import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.port.out.book.item.BookItem;
import com.trevari.test.domain.search.port.out.book.item.PageInfo;
import com.trevari.test.domain.search.port.out.book.item.SearchMetadata;

import java.util.List;

public record BookSearchResponse(
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
