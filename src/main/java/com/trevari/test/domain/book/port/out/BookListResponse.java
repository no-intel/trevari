package com.trevari.test.domain.book.port.out;

import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.item.BookItem;
import com.trevari.test.domain.book.port.out.item.PageInfo;
import com.trevari.test.domain.book.port.out.item.SearchMetadata;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.trevari.test.domain.book.enums.SearchStrategyEnum.KEYWORD;

@Schema(description = "도서 목록 조회 응답 Response")
public record BookListResponse(
        @Schema(description = "검색 키워드", example = "a")
        String keyword,

        PageInfo pageInfo,

        List<BookItem> books,

        SearchMetadata searchMetadata
) {
    public static BookListResponse of(String keyword, Page<BookListResponseDto> books, long executionTime) {
        return new BookListResponse(
                keyword,
                PageInfo.of(books.getPageable().getPageNumber() + 1, books.getPageable().getPageSize(), books.getTotalPages(), books.getTotalElements()),
                BookItem.of(books.getContent()),
                SearchMetadata.of(executionTime, KEYWORD)
        );
    };
}
