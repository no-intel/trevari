package com.trevari.test.domain.search.adapter.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.trevari.test.domain.search.util.PopularKeywordSupport.GLOBAL_KEY;

@Service
@RequiredArgsConstructor
public class PopularKeywordReader {
    private final StringRedisTemplate redis;

    public Set<TypedTuple<String>> topN(int n) {
        if (n <= 0) return Set.of();
        Set<TypedTuple<String>> set = redis.opsForZSet().reverseRangeWithScores(GLOBAL_KEY, 0, n - 1);
        if (set == null || set.isEmpty()) return Set.of();

        return set;
    }
}
