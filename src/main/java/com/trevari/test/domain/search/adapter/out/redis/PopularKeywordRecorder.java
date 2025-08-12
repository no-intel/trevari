package com.trevari.test.domain.search.adapter.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import static com.trevari.test.domain.search.util.PopularKeywordSupport.GLOBAL_KEY;


@Component
@RequiredArgsConstructor
public class PopularKeywordRecorder {

    private final StringRedisTemplate redisTemplate;

    public void record(String keyword) {
        if (ObjectUtils.isEmpty(keyword)) return;
        redisTemplate.opsForZSet().incrementScore(GLOBAL_KEY, keyword, 1d);
    }
}
