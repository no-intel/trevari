package com.trevari.test.domain.book.adapter.out.persistence.querydsl;

import com.trevari.test.config.QuerydslTestConfig;
import com.trevari.test.domain.book.port.in.dto.BookFinderDto;
import com.trevari.test.domain.book.port.in.dto.BookMultiFinderDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookListResponseDto;
import com.trevari.test.domain.book.entity.Book;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookRepositoryCustomImpl.class, QuerydslTestConfig.class})
class BookRepositoryCustomImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    BookRepositoryCustomImpl repository;

    @BeforeEach
    void setUp() {
        em.persist(Book.builder()
                .isbn(9780000000001L)
                .title("Alpha")
                .subtitle("A-sub")
                .author("Alice")
                .publisher("Pub-A")
                .image("https://example.com/a.png")
                .pages(200)
                .price(new BigDecimal("10.00"))
                .publishDate(LocalDate.of(2024, 5, 10))
                .build());

        em.persist(Book.builder()
                .isbn(9780000000002L)
                .title("Bravo")
                .subtitle("B-sub")
                .author("Bob")
                .publisher("Pub-B")
                .image("https://example.com/b.png")
                .pages(150)
                .price(new BigDecimal("20.00"))
                .publishDate(LocalDate.of(2023, 12, 25))
                .build());

        em.persist(Book.builder()
                .isbn(9780000000003L)
                .title("Charlie")
                .subtitle("C-sub")
                .author("Carol")
                .publisher("Pub-C")
                .image("https://example.com/c.png")
                .pages(300)
                .price(new BigDecimal("30.00"))
                .publishDate(LocalDate.of(2020, 1, 15))
                .build());

        em.persist(Book.builder()
                .isbn(9780000000004L)
                .title("Delta")
                .subtitle("C-sub")
                .author("Dio")
                .publisher("Pub-D")
                .image("https://example.com/d.png")
                .pages(120)
                .price(new BigDecimal("31.00"))
                .publishDate(LocalDate.of(2020, 1, 15))
                .build());

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("심플 페이지네이션: 첫 페이지 2건, 총 4건")
    void simple_findBooks_pagination() {
        // given
        BookFinderDto dto = new BookFinderDto(null, PageRequest.of(0, 2));

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);

        // then
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(4);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("단일 정렬: title desc")
    void simple_sort_findBooks_title_desc() {
        // given
        BookFinderDto dto = new BookFinderDto(
                null,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "title"))
        );

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);
        List<BookListResponseDto> content = page.getContent();

        assertThat(extractTitles(content)).containsExactly("Delta", "Charlie", "Bravo", "Alpha");
    }

    @Test
    @DisplayName("복합 정렬: publishDate desc, title desc (A -> B -> D -> C)")
    void mult_sort_findBooks_publishDate_title() {
        // given
        BookFinderDto dto = new BookFinderDto(
                null,
                PageRequest.of(
                        0, 10,
                        Sort.by(
                                Sort.Order.desc("publishDate"),
                                Sort.Order.desc("title")
                        )
                )
        );

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Alpha", "Bravo", "Delta", "Charlie");
    }

    @Test
    @DisplayName("심플 키워드 검색 : l")
    void simple_keyword_findBooks() {
        // given
        BookFinderDto dto = new BookFinderDto("l", PageRequest.of(0, 10));

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Alpha", "Charlie", "Delta");
    }

    @Test
    @DisplayName("심플 키워드 검색 : l & 심플 정렬 : title desc")
    void simple_keyword_sort_findBooks_title_desc() {
        // given
        BookFinderDto dto = new BookFinderDto(
                "l",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "title"))
        );

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Delta", "Charlie", "Alpha");
    }

    @Test
    @DisplayName("심플 키워드 검색 : l & 복합 정렬 : publishDate desc, title desc")
    void simple_keyword_sort_findBooks_publishDate_title() {
        // given
        BookFinderDto dto = new BookFinderDto(
                "l",
                PageRequest.of(0, 10,
                        Sort.by(
                                Sort.Order.desc("publishDate"),
                                Sort.Order.desc("title")
                        )
                )
        );

        // when
        Page<BookListResponseDto> page = repository.findBooks(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Alpha", "Delta", "Charlie");
    }

    @Test
    @DisplayName("OR 복합 쿼리 검색 : A-sub, B-sub")
    void or_query_findBooks() {
        // given
        BookMultiFinderDto dto = BookMultiFinderDto.of("A-sub", "B-sub", PageRequest.of(0, 10));

        // when
        Page<BookListResponseDto> page = repository.findBooksWithOr(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Alpha", "Bravo");
    }

    @Test
    @DisplayName("NOT 복합 쿼리 검색 : a, B-sub")
    void not_query_findBooks() {
        // given
        BookMultiFinderDto dto = BookMultiFinderDto.of("a", "B-sub", PageRequest.of(0, 10));

        // when
        Page<BookListResponseDto> page = repository.findBooksWithNot(dto);
        List<BookListResponseDto> content = page.getContent();

        // then
        assertThat(extractTitles(content)).containsExactly("Alpha", "Charlie", "Delta");
    }

    private List<String> extractTitles(List<BookListResponseDto> list) {
        return list.stream().map(BookListResponseDto::title).toList();
    }
}