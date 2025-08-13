package com.trevari.test.domain.book.adapter.in.web;

import com.trevari.test.domain.book.application.BookSimpleFinderService;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.trevari.test.domain.book.enums.SearchStrategyEnum.KEYWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BookSimpleFinderController.class)
class BookSimpleFinderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookSimpleFinderService service;

    @Test
    @DisplayName("도서 단순 검색 API - 키워드 X, page = 0, size = 20")
    void get_books_no_keyword() throws Exception {
        //given
        String keyword = null;
        int page = 0;
        int pageSize = 20;
        long total = 1L;
        long executionTime = 6L;
        List<BookListResponseDto> dtos = List.of(
                BookListResponseDto.builder()
                        .id(9780000000001L)
                        .title("Alpha")
                        .subtitle("A-sub")
                        .author("Alice")
                        .image("https://example.com/a.png")
                        .isbn(9780000000001L)
                        .publishDate(LocalDate.of(2024, 5, 10))
                        .build()
        );
        Page<BookListResponseDto> books = new PageImpl<>(dtos, PageRequest.of(page, pageSize), total);
        BookListResponse response = BookListResponse.of(keyword, books, executionTime);
        when(service.getBooks(any(BookFinderDto.class))).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/books")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keyword").value(keyword))
                .andExpect(jsonPath("$.pageInfo.currentPage").value(page + 1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(total))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(1))
                .andExpect(jsonPath("$.books[0].id").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].title").value("Alpha"))
                .andExpect(jsonPath("$.books[0].subtitle").value("A-sub"))
                .andExpect(jsonPath("$.books[0].author").value("Alice"))
                .andExpect(jsonPath("$.books[0].image").value("https://example.com/a.png"))
                .andExpect(jsonPath("$.books[0].isbn").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].published").value("2024-05-10"))
                .andExpect(jsonPath("$.searchMetadata.executionTime").value(executionTime))
                .andExpect(jsonPath("$.searchMetadata.strategy").value(KEYWORD.name()))
        ;

    }

    @Test
    @DisplayName("도서 단순 검색 API - 키워드 a, page = 0, size = 20")
    void get_books_keyword_a() throws Exception {
        //given
        String keyword = "A";
        int page = 0;
        int pageSize = 20;
        long total = 1L;
        long executionTime = 6L;
        List<BookListResponseDto> dtos = List.of(
                BookListResponseDto.builder()
                        .id(9780000000001L)
                        .title("Alpha")
                        .subtitle("A-sub")
                        .author("Alice")
                        .image("https://example.com/a.png")
                        .isbn(9780000000001L)
                        .publishDate(LocalDate.of(2024, 5, 10))
                        .build()
        );
        Page<BookListResponseDto> books = new PageImpl<>(dtos, PageRequest.of(page, pageSize), total);
        BookListResponse response = BookListResponse.of(keyword, books, executionTime);
        when(service.getBooks(any(BookFinderDto.class))).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/books")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keyword").value(keyword))
                .andExpect(jsonPath("$.pageInfo.currentPage").value(page + 1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(total))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(1))
                .andExpect(jsonPath("$.books[0].id").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].title").value("Alpha"))
                .andExpect(jsonPath("$.books[0].subtitle").value("A-sub"))
                .andExpect(jsonPath("$.books[0].author").value("Alice"))
                .andExpect(jsonPath("$.books[0].image").value("https://example.com/a.png"))
                .andExpect(jsonPath("$.books[0].isbn").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].published").value("2024-05-10"))
                .andExpect(jsonPath("$.searchMetadata.executionTime").value(executionTime))
                .andExpect(jsonPath("$.searchMetadata.strategy").value(KEYWORD.name()))
        ;
    }
}