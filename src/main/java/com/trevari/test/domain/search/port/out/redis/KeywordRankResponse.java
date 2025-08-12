package com.trevari.test.domain.search.port.out.redis;

import lombok.Builder;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
public record KeywordRankResponse(
        int rank,
        String keyword,
        long score
) {
    public static List<KeywordRankResponse> of(Set<TypedTuple<String>> tuple){
        List<KeywordRankResponse> result = new ArrayList<>();
        int rank = 1;
        for (TypedTuple<String> t : tuple) {
            long score = t.getScore() == null ? 0L : t.getScore().longValue();
            result.add(new KeywordRankResponse(rank++, t.getValue(), score));
        }
        return result;
    }
}
