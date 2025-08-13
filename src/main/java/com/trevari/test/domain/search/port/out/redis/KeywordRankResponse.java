package com.trevari.test.domain.search.port.out.redis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Schema(description = "검색 메타 정보")
@Builder
public record KeywordRankResponse(
        @Schema(description = "검색 메타 정보", example = "1")
        int rank,

        @Schema(description = "검색 메타 정보", example = "java")
        String keyword,

        @Schema(description = "검색 메타 정보", example = "11")
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
