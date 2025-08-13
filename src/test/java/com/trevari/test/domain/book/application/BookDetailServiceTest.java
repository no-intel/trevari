package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.port.in.dto.BookDetailDto;
import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.exception.NotFoundBookException;
import com.trevari.test.domain.book.port.out.BookDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookDetailServiceTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookDetailService bookSearchService;

    @Test
    @DisplayName("ISBN으로 도서 상세 조회 성공 시, BookDetailResponse로 매핑된다")
    void get_book() {
        // given
        long isbn = 9780000000001L;

        BookDetailDto dto = mock(BookDetailDto.class);
        Book book = Book.builder()
                .isbn(isbn)
                .title("Alpha")
                .subtitle("A-sub")
                .author("Alice")
                .publisher("Pub-A")
                .image("https://example.com/a.png")
                .pages(200)
                .price(new BigDecimal("10.00"))
                .publishDate(LocalDate.of(2024, 5, 10))
                .build();

        when(bookService.getBook(anyLong())).thenReturn(book);

        // when
        BookDetailResponse response = bookSearchService.getBookByISBN(dto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(isbn);
        assertThat(response.isbn()).isEqualTo(isbn);
        assertThat(response.title()).isEqualTo("Alpha");
        assertThat(response.subtitle()).isEqualTo("A-sub");
        assertThat(response.author()).isEqualTo("Alice");
        assertThat(response.publisher()).isEqualTo("Pub-A");
        assertThat(response.image()).isEqualTo("https://example.com/a.png");
        assertThat(response.pages()).isEqualTo(200);
        assertThat(response.price()).isEqualTo(new BigDecimal("10.00"));
        assertThat(response.published()).isEqualTo("2024-05-10");
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 실패 시, NotFoundBookException 발생")
    void get_book_not_found() {
        // given
        BookDetailDto dto = mock(BookDetailDto.class);

        when(bookService.getBook(anyLong())).thenThrow(NotFoundBookException.class);

        // when & then
        assertThrows(NotFoundBookException.class, () -> {
            bookSearchService.getBookByISBN(dto);
        });
    }
}