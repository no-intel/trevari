package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.port.out.BookListResponse;
import com.trevari.test.domain.search.adapter.out.book.BookSearchAdapter;
import com.trevari.test.domain.search.adapter.out.redis.event.PopularKeywordEvent;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import com.trevari.test.domain.search.port.in.BookSearchDto;
import com.trevari.test.domain.search.port.out.book.BookSearchResponse;
import com.trevari.test.domain.search.util.BookSearchParsingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSearchServiceTest {

    @Mock
    BookSearchAdapter adapter;

    @Mock
    ApplicationEventPublisher publisher;

    @InjectMocks
    BookSearchService service;

    @Test
    @DisplayName("단일 도서 검색")
    void search_book() {
        // given
        Pageable page = PageRequest.of(0, 2);
        String keyword = "a";
        BookSearchDto dto = BookSearchDto.of(keyword, page);

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
        BookListResponse bookSearchResponse = BookListResponse.of(keyword, stubPage, 10L);

        when(adapter.searchBook(any(String.class), any(Pageable.class), any(SearchStrategyEnum.class))).thenReturn(bookSearchResponse);
        doNothing().when(publisher).publishEvent(any(PopularKeywordEvent.class));

        // when
        BookSearchResponse res = service.searchBook(dto);

        assertThat(res.searchQuery()).isEqualTo(keyword);
        assertThat(res.books().getFirst().id()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().title()).isEqualTo("Alpha");
        assertThat(res.books().getFirst().subtitle()).isEqualTo("A-sub");
        assertThat(res.books().getFirst().author()).isEqualTo("Alice");
        assertThat(res.books().getFirst().image()).isEqualTo("https://example.com/a.png");
        assertThat(res.books().getFirst().isbn()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().published()).isEqualTo("2024-05-10");
    }

    @Test
    @DisplayName("OR 도서 검색")
    void search_book_or() {
        // given
        Pageable page = PageRequest.of(0, 2);
        String keyword = "Alpha|Bravo";
        BookSearchDto dto = BookSearchDto.of(keyword, page);

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
                        .isbn(9780000000002L)
                        .title("Bravo")
                        .subtitle("B-sub")
                        .author("Bob")
                        .image("https://example.com/b.png")
                        .publishDate(LocalDate.of(2023, 12, 25))
                        .build()
        );
        Page<BookListResponseDto> stubPage = new PageImpl<>(result, page, 100);
        BookListResponse bookSearchResponse = BookListResponse.of(keyword, stubPage, 10L);

        when(adapter.searchBook(any(String.class), any(Pageable.class), any(SearchStrategyEnum.class))).thenReturn(bookSearchResponse);
        doNothing().when(publisher).publishEvent(any(PopularKeywordEvent.class));

        // when
        BookSearchResponse res = service.searchBook(dto);

        assertThat(res.searchQuery()).isEqualTo(keyword);
        assertThat(res.books().getFirst().id()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().title()).isEqualTo("Alpha");
        assertThat(res.books().getFirst().subtitle()).isEqualTo("A-sub");
        assertThat(res.books().getFirst().author()).isEqualTo("Alice");
        assertThat(res.books().getFirst().image()).isEqualTo("https://example.com/a.png");
        assertThat(res.books().getFirst().isbn()).isEqualTo(9780000000001L);
        assertThat(res.books().getFirst().published()).isEqualTo("2024-05-10");
        assertThat(res.books().get(1).id()).isEqualTo(9780000000002L);
        assertThat(res.books().get(1).title()).isEqualTo("Bravo");
        assertThat(res.books().get(1).subtitle()).isEqualTo("B-sub");
        assertThat(res.books().get(1).author()).isEqualTo("Bob");
        assertThat(res.books().get(1).image()).isEqualTo("https://example.com/b.png");
        assertThat(res.books().get(1).isbn()).isEqualTo(9780000000002L);
        assertThat(res.books().get(1).published()).isEqualTo("2023-12-25");
    }

    @Test
    @DisplayName("NOT 도서 검색")
    void not_search_book() {
        // given
        Pageable page = PageRequest.of(0, 2);
        String keyword = "a";
        BookSearchDto dto = BookSearchDto.of(keyword, page);

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
        BookListResponse bookSearchResponse = BookListResponse.of(keyword, stubPage, 10L);

        when(adapter.searchBook(any(String.class), any(Pageable.class), any(SearchStrategyEnum.class))).thenReturn(bookSearchResponse);
        doNothing().when(publisher).publishEvent(any(PopularKeywordEvent.class));

        // when
        BookSearchResponse res = service.searchBook(dto);

        assertThat(res.searchQuery()).isEqualTo(keyword);
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