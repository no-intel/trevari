package com.trevari.test.domain.search.applicateion;

import com.trevari.test.domain.search.adapter.out.redis.PopularKeywordReader;
import com.trevari.test.domain.search.port.out.redis.KeywordRankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PopularKeywordQueryService {
    private final PopularKeywordReader popularKeywordReader;
    private final static int TOP = 10;

    public List<KeywordRankResponse> getTopKeywords() {
        Set<TypedTuple<String>> topN = popularKeywordReader.topN(TOP);
        if (topN == null || topN.isEmpty()) return List.of();
        return KeywordRankResponse.of(topN);
    }
}
