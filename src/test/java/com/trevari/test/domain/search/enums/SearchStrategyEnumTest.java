package com.trevari.test.domain.search.enums;


import com.trevari.test.domain.search.exception.NotMatchedSearchStrategyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.trevari.test.domain.search.enums.SearchStrategyEnum.KEYWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchStrategyEnumTest {

    @Test
    @DisplayName("SearchStrategyEnum 매칭 성공")
    void string_to_enum() {
        // given
        String value = "KEYWORD";

        // when
        SearchStrategyEnum searchStrategyEnum = SearchStrategyEnum.fromString(value);

        // then
        assertThat(searchStrategyEnum).isEqualTo(KEYWORD);
    }

    @Test
    @DisplayName("SearchStrategyEnum 매칭 실패")
    void string_to_enum_fail() {
        // given
        String value = "FAIL";

        // when & then
        assertThrows(NotMatchedSearchStrategyException.class, () ->
                        SearchStrategyEnum.fromString(value)
        );
    }
}