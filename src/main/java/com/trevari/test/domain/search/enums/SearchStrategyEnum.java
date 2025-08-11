package com.trevari.test.domain.search.enums;

import com.trevari.test.domain.search.exception.NotMatchedSearchStrategyException;

import java.util.Arrays;

public enum SearchStrategyEnum {
    KEYWORD,
    OR_OPERATION,
    NOT_OPERATION
    ;

    public static SearchStrategyEnum fromString(String value) {
        return Arrays.stream(SearchStrategyEnum.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(NotMatchedSearchStrategyException::new); // 기본값
    }
}
