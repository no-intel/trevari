package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.entity.Book;
import com.trevari.test.domain.book.exception.NotFoundBookException;
import com.trevari.test.domain.book.infrastructure.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookService service;

    @Test
    @DisplayName("ISBN으로 도서 검색")
    void getBook_isbn() {
        // given
        Long isbn = 9780000000001L;
        Book book = Book.builder()
                .isbn(isbn)
                .title("Alpha")
                .subtitle("A-sub")
                .author("Alice")
                .publisher("Pub-A")
                .image("https://example.com/a.png")
                .pages(300)
                .price(new BigDecimal("19.99"))
                .publishDate(LocalDate.of(2024, 5, 10))
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));

        // when
        Book result = service.getBook(isbn);

        // then
        assertThat(result).isEqualTo(book);
    }

    @Test
    @DisplayName("getBook: isbn없음. NotExistBookException 발생")
    void getBook_isbn_not_found() {
        Long isbn = 9780000000002L;
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundBookException.class, () -> {
            service.getBook(isbn);
        });
    }
}