package com.trevari.test.domain.search.adapter.in.web;

import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.applicateion.BookSearchService;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.BookSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@WebMvcTest(controllers = BookSearchController.class)
class BookSearchControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookSearchService bookSearchService;

    @Test
    @DisplayName("단순 검색")
    void search_with_q_and_paging() throws Exception {
        // given
        Pageable page = PageRequest.of(0, 20);
        String keyword = "a";

        List<BookListResponseDto> result = List.of(
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
        Page<BookListResponseDto> stubPage = new PageImpl<>(result, page, 100);
        BookListResponse response = BookListResponse.of(keyword, stubPage, 10L);
        BookSearchResponse searchResponse = BookSearchResponse.of(keyword, response);

        when(bookSearchService.searchBook(any(BookSearchDto.class))).thenReturn(searchResponse);

        // when & then
        mockMvc.perform(
                get("/api/search/books")
                        .param("q", keyword)
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.searchQuery").value(keyword))
                .andExpect(jsonPath("$.books[0].id").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].title").value("Alpha"))
                .andExpect(jsonPath("$.books[0].subtitle").value("A-sub"))
                .andExpect(jsonPath("$.books[0].author").value("Alice"))
                .andExpect(jsonPath("$.books[0].image").value("https://example.com/a.png"))
                .andExpect(jsonPath("$.books[0].isbn").value(9780000000001L))
                .andExpect(jsonPath("$.books[0].published").value("2024-05-10"))
                .andExpect(jsonPath("$.searchMetadata.strategy").value(KEYWORD.name()));

    }
}