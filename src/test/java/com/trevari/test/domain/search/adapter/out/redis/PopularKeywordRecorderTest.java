package com.trevari.test.domain.search.adapter.out.redis;

import com.trevari.test.domain.search.util.PopularKeywordSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PopularKeywordRecorderTest {

    @Mock
    StringRedisTemplate redisTemplate;
    @Mock
    ZSetOperations<String, String> zSetOps;

    @Test
    @DisplayName("정상 키워드면 ZINCRBY 1 호출")
    void record_increments_score() {
        // given
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        PopularKeywordRecorder recorder = new PopularKeywordRecorder(redisTemplate);

        // when
        recorder.record("alpha");

        // then
        verify(zSetOps).incrementScore(PopularKeywordSupport.GLOBAL_KEY, "alpha", 1d);
    }

    @Test
    @DisplayName("null/빈 문자열이면 아무 것도 하지 않음")
    void record_ignores_empty() {
        // given
        PopularKeywordRecorder recorder = new PopularKeywordRecorder(redisTemplate);

        // when
        recorder.record(null);
        recorder.record("");

        // then
        verify(redisTemplate, never()).opsForZSet();
    }
}