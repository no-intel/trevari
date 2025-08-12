package com.trevari.test.domain.search.adapter.out.book;


import com.trevari.test.domain.book.port.in.BookFinderUseCase;
import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSearchRouterTest {

    @Mock
    BookFinderUseCase simpleUseCase;

    @Mock
    BookFinderUseCase orUseCase;

    @Mock
    BookFinderUseCase notUseCase;

    @Test
    @DisplayName("enum으로 UseCase 반환")
    void route_type() {
        // given
        when(simpleUseCase.getType()).thenReturn("KEYWORD");
        when(orUseCase.getType()).thenReturn("OR_OPERATION");
        when(notUseCase.getType()).thenReturn("NOT_OPERATION");

        BookSearchRouter router = new BookSearchRouter(List.of(simpleUseCase, orUseCase, notUseCase));

        // when & then
        assertThat(simpleUseCase).isEqualTo(router.get(SearchStrategyEnum.KEYWORD));
        assertThat(orUseCase).isEqualTo(router.get(SearchStrategyEnum.OR_OPERATION));
        assertThat(notUseCase).isEqualTo(router.get(SearchStrategyEnum.NOT_OPERATION));
    }
}