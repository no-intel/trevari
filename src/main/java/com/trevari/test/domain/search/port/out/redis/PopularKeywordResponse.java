package com.trevari.test.domain.search.port.out.redis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Schema(description = "검색 메타 정보")
@Builder
public record PopularKeywordResponse(
        @Schema(description = "검색 메타 정보", example = "1")
        int rank,

        @Schema(description = "검색 메타 정보", example = "java")
        String keyword
) {
    public static List<PopularKeywordResponse> of(Set<TypedTuple<String>> tuple){
        List<PopularKeywordResponse> result = new ArrayList<>();
        int rank = 1;
        for (TypedTuple<String> t : tuple) {
            result.add(new PopularKeywordResponse(rank++, t.getValue()));
        }
        return result;
    }
}
