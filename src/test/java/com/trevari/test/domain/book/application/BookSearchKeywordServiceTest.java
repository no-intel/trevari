package com.trevari.test.domain.book.application;

import com.trevari.test.domain.book.port.in.dto.BooksSearchKeywordDto;
import com.trevari.test.domain.book.port.in.dto.Projection.BookSearchResponseDto;
import com.trevari.test.domain.book.infrastructure.repository.BookRepositoryCustom;
import com.trevari.test.domain.book.port.out.BookSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSearchKeywordServiceTest {

    @Mock
    BookRepositoryCustom bookRepositoryCustom;;

    @InjectMocks
    BookSearchKeywordService service;

    @Test
    @DisplayName("도서 리스트 조회 성공 - 검색어/페이지 정보/실행시간 매핑 확인")
    void getBooks_success() {
        // given
        BooksSearchKeywordDto dto = mock(BooksSearchKeywordDto.class);
        String keyword = "javascript";
        when(dto.keyword()).thenReturn(keyword);

        BookSearchResponseDto item1 = mock(BookSearchResponseDto.class);
        BookSearchResponseDto item2 = mock(BookSearchResponseDto.class);
        when(item1.title()).thenReturn("javascript Good");
        when(item2.title()).thenReturn("javascript Bad");

        Page<BookSearchResponseDto> stubPage =
                new PageImpl<>(List.of(item1, item2), PageRequest.of(0, 2), 100);

        when(bookRepositoryCustom.findBooks(dto)).thenReturn(stubPage);

        // when
        BookSearchResponse res = service.getBooks(dto);

        // then
        assertThat(res).isNotNull();

        assertThat(res.keyword()).isEqualTo(keyword);
        assertThat(res.pageInfo().pageSize()).isEqualTo(2);
        assertThat(res.books().get(0).title()).isEqualTo("javascript Good");
        assertThat(res.books().get(1).title()).isEqualTo("javascript Bad");
        assertThat(res.searchMetadata().executionTime()).isGreaterThanOrEqualTo(0);
    }

}