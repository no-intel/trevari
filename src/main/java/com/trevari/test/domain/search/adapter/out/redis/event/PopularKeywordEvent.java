package com.trevari.test.domain.search.adapter.out.redis.event;

public record PopularKeywordEvent(
        String Keyword
) {
    public static PopularKeywordEvent of(String Keyword) {
        return new PopularKeywordEvent(Keyword);
    }
}
