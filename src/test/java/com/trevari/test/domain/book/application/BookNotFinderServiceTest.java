package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.adapter.out.persistence.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookNotFinderServiceTest {

    @Mock
    BookRepositoryCustom bookRepositoryCustom;

    @InjectMocks
    BookNotFinderService service;

    @Test
    @DisplayName("도서 not 복합 조회 - a, B-sub")
    void find_book_not() {
        // given
        Pageable page = PageRequest.of(0, 2);
        String keyword = "a-B-sub";
        BookFinderDto dto = BookFinderDto.of(keyword, page);

        List<BookListResponseDto> result = List.of(
                BookListResponseDto.builder()
                        .id(9780000000001L)
                        .title("Alpha")
                        .subtitle("A-sub")
                        .author("Alice")
                        .image("https://example.com/a.png")
                        .isbn(9780000000001L)
                        .publishDate(LocalDate.of(2024, 5, 10))
                        .build(),
                BookListResponseDto.builder()
                        .isbn(9780000000003L)
                        .title("Charlie")
                        .subtitle("C-sub")
                        .author("Carol")
                        .image("https://example.com/c.png")
                        .publishDate(LocalDate.of(2020, 1, 15))
                        .build(),
                BookListResponseDto.builder()
                        .isbn(9780000000004L)
                        .title("Delta")
                        .subtitle("D-sub")
                        .author("Dio")
                        .image("https://example.com/d.png")
                        .publishDate(LocalDate.of(2020, 1, 15))
                        .build()
        );

        Page<BookListResponseDto> stubPage = new PageImpl<>(result, page, 100);
        when(bookRepositoryCustom.findBooksWithNot(anyString(), anyString(), any(Pageable.class))).thenReturn(stubPage);

        // when
        BookListResponse res = service.getBooks(dto);

        // then
        assertThat(res).isNotNull();

        assertThat(res.keyword()).isEqualTo(keyword);
        assertThat(res.books().getFirst().id()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().title()).isEqualTo("Alpha");
        assertThat(res.books().getFirst().subtitle()).isEqualTo("A-sub");
        assertThat(res.books().getFirst().author()).isEqualTo("Alice");
        assertThat(res.books().getFirst().image()).isEqualTo("https://example.com/a.png");
        assertThat(res.books().getFirst().isbn()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().published()).isEqualTo("2024-05-10");
        assertThat(res.books().get(1).id()).isEqualTo(9780000000003L);
        assertThat(res.books().get(1).title()).isEqualTo("Charlie");
        assertThat(res.books().get(1).subtitle()).isEqualTo("C-sub");
        assertThat(res.books().get(1).author()).isEqualTo("Carol");
        assertThat(res.books().get(1).image()).isEqualTo("https://example.com/c.png");
        assertThat(res.books().get(1).isbn()).isEqualTo(9780000000003L);
        assertThat(res.books().get(1).published()).isEqualTo("2020-01-15");
        assertThat(res.books().get(2).id()).isEqualTo(9780000000004L);
        assertThat(res.books().get(2).title()).isEqualTo("Delta");
        assertThat(res.books().get(2).subtitle()).isEqualTo("D-sub");
        assertThat(res.books().get(2).author()).isEqualTo("Dio");
        assertThat(res.books().get(2).image()).isEqualTo("https://example.com/d.png");
        assertThat(res.books().get(2).isbn()).isEqualTo(9780000000004L);
        assertThat(res.books().get(2).published()).isEqualTo("2020-01-15");
    }
}