package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.search.adapter.out.redis.PopularKeywordReader;
import com.trevari.test.domain.search.port.out.redis.PopularKeywordResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PopularKeywordQueryServiceTest {

    @Mock
    PopularKeywordReader popularKeywordReader;

    @InjectMocks
    PopularKeywordQueryService service;

    @Test
    @DisplayName("인기검색어가 null이면 빈 리스트 반환")
    void returns_empty_when_topN_is_null() {
        // given
        when(popularKeywordReader.topN(anyInt())).thenReturn(null);

        // when
        List<PopularKeywordResponse> responses = service.getTopKeywords();

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("topN이 빈 Set이면 빈 리스트 반환")
    void returns_empty_when_topN_is_empty() {
        // given
        when(popularKeywordReader.topN(anyInt())).thenReturn(Set.of());

        // when
        List<PopularKeywordResponse> responses = service.getTopKeywords();

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("인기 검색어 조회")
    void search_popular_keyword() {
        // given
        Set<TypedTuple<String>> set = new LinkedHashSet<>();

        TypedTuple<String> t1 = mock(TypedTuple.class);
        when(t1.getValue()).thenReturn("apple");

        TypedTuple<String> t2 = mock(TypedTuple.class);
        when(t2.getValue()).thenReturn("banana");

        set.add(t1);
        set.add(t2);

        when(popularKeywordReader.topN(anyInt())).thenReturn(set);

        // when
        List<PopularKeywordResponse> responses = service.getTopKeywords();

        // then
        assertThat(responses).hasSize(2);

        assertThat(responses.getFirst().rank()).isEqualTo(1);
        assertThat(responses.getFirst().keyword()).isEqualTo(t1.getValue());
        assertThat(responses.get(1).rank()).isEqualTo(2);
        assertThat(responses.get(1).keyword()).isEqualTo(t2.getValue());
    }
}