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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSimpleFinderServiceTest {

    @Mock
    BookRepositoryCustom bookRepositoryCustom;;

    @InjectMocks
    BookSimpleFinderService service;

    @Test
    @DisplayName("도서 리스트 조회 성공 - 검색어/페이지 정보/실행시간 매핑 확인")
    void find_books() {
        // given
        Pageable page = PageRequest.of(0, 2);
        String keyword = "a";
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
                        .build()
        );

        Page<BookListResponseDto> stubPage = new PageImpl<>(result, page, 100);
        when(bookRepositoryCustom.findBooks(any(BookFinderDto.class))).thenReturn(stubPage);

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
    }
}