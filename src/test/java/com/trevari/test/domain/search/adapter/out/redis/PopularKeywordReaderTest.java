package com.trevari.test.domain.search.adapter.out.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.trevari.test.domain.search.util.PopularKeywordSupport.GLOBAL_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PopularKeywordReaderTest {
    @Mock
    StringRedisTemplate redis;

    @Mock
    ZSetOperations<String, String> zSetOps;

    @InjectMocks
    PopularKeywordReader reader;

    @Test
    @DisplayName("Redis에서 null/empty Set을 받으면 빈 리스트를 반환한다")
    void topN_whenNullOrEmptySet_returnsEmpty() {
        // given
        when(redis.opsForZSet()).thenReturn(zSetOps);
        when(zSetOps.reverseRangeWithScores(GLOBAL_KEY, 0, 9)).thenReturn(Set.of());

        // when
        Set<TypedTuple<String>> responses = reader.topN(10);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("정상 Set이면 그 Set을 그대로 반환")
    void topN_whenValidSet_mapsToKeywordRankResponse() {
        // given
        when(redis.opsForZSet()).thenReturn(zSetOps);

        Set<TypedTuple<String>> expected = new LinkedHashSet<>();
        expected.add(mock(TypedTuple.class));
        expected.add(mock(TypedTuple.class));

        when(zSetOps.reverseRangeWithScores(any(String.class), anyLong(), anyLong())).thenReturn(expected);

        // when
        Set<TypedTuple<String>> actual = reader.topN(10);

        // then
        assertThat(actual).isSameAs(expected);
        assertThat(actual).hasSize(2);
    }

}