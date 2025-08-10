package com.trevari.test.domain.book.controller;

import com.trevari.test.domain.book.dto.BookSearchDto;
import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.response.BookDetailResponse;
import com.trevari.test.domain.book.service.BookSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    BookSearchService service;

    @Test
    @DisplayName("도서 상세 조회 - 유효한 ISBN")
    void getBooks_valid_isbn_returns_200() throws Exception {

        BookDetailResponse response = BookDetailResponse.of(
                Book.builder()
                        .isbn(9780000000001L)
                        .title("Alpha")
                        .subtitle("A-sub")
                        .author("Alice")
                        .publisher("Pub-A")
                        .image("https://example.com/a.png")
                        .pages(200)
                        .price(new BigDecimal("10.00"))
                        .publishDate(LocalDate.of(2024, 5, 10))
                        .build()
        );
        when(service.getBookByISBN(any(BookSearchDto.class))).thenReturn(response);

        mockMvc.perform(get("/api/books/{id}", "9780000000001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9780000000001L))
                .andExpect(jsonPath("$.isbn").value(9780000000001L))
                .andExpect(jsonPath("$.title").value("Alpha"))
                .andExpect(jsonPath("$.subtitle").value("A-sub"))
                .andExpect(jsonPath("$.author").value("Alice"))
                .andExpect(jsonPath("$.publisher").value("Pub-A"))
                .andExpect(jsonPath("$.image").value("https://example.com/a.png"))
                .andExpect(jsonPath("$.pages").value(200))
                .andExpect(jsonPath("$.price").value(new BigDecimal("10.00")))
                .andExpect(jsonPath("$.published").value("2024-05-10"))
        ;

    }
}