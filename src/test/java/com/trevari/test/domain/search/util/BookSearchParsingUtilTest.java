package com.trevari.test.domain.search.util;

import com.trevari.test.domain.search.enums.SearchStrategyEnum;
import com.trevari.test.domain.search.exception.InvalidSearchPatternException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookSearchParsingUtilTest {

    @Test
    @DisplayName("쿼리 파싱 - 단일키워드 : a")
    void parsing_keyword() throws Exception {
        // given
        String query = "a";

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.KEYWORD);
    }

    @Test
    @DisplayName("쿼리 파싱 - null")
    void parsing_keyword_null() throws Exception {
        // given
        String query = null;

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.KEYWORD);
    }

    @Test
    @DisplayName("쿼리 파싱 - empty")
    void parsing_keyword_empty() throws Exception {
        // given
        String query = "";

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.KEYWORD);
    }

    @Test
    @DisplayName("쿼리 파싱 - blank")
    void parsing_keyword_blank() throws Exception {
        // given
        String query = " ";

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.KEYWORD);
    }

    @Test
    @DisplayName("쿼리 파싱 - OR키워드 : a|b")
    void parsing_or_query() throws Exception {
        // given
        String query = "a|b";

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.OR_OPERATION);
    }

    @Test
    @DisplayName("쿼리 파싱 실패 - OR 키워드 : a|b|c")
    void parsing_or_query_too_long() throws Exception {
        // given
        String query = "a|b|c";

        // when & then
        assertThrows(InvalidSearchPatternException.class, () -> BookSearchParsingUtil.parsingAndCheck(query));
    }

    @Test
    @DisplayName("쿼리 파싱 실패 - OR 키워드 : |b|c")
    void parsing_or_query_invalid_pattern() throws Exception {
        // given
        String query = "|b|c";

        // when & then
        assertThrows(InvalidSearchPatternException.class, () -> BookSearchParsingUtil.parsingAndCheck(query));
    }

    @Test
    @DisplayName("쿼리 파싱 - NOT키워드 : a-b")
    void parsing_not_query() throws Exception {
        // given
        String query = "a-b";

        // when
        SearchStrategyEnum strategyEnum = BookSearchParsingUtil.parsingAndCheck(query);

        // then
        assertThat(strategyEnum).isEqualTo(SearchStrategyEnum.NOT_OPERATION);
    }

    @Test
    @DisplayName("쿼리 파싱 실패 - OR 키워드 : a-b-c")
    void parsing_not_query_too_long() throws Exception {
        // given
        String query = "a-b-c";

        // when & then
        assertThrows(InvalidSearchPatternException.class, () -> BookSearchParsingUtil.parsingAndCheck(query));
    }

    @Test
    @DisplayName("쿼리 파싱 실패 - OR 키워드 : -b-c")
    void parsing_not_query_invalid_pattern() throws Exception {
        // given
        String query = "-b-c";

        // when & then
        assertThrows(InvalidSearchPatternException.class, () -> BookSearchParsingUtil.parsingAndCheck(query));
    }

    @Test
    @DisplayName("쿼리 파싱 실패 - 복합 키워드 : a|b-c")
    void parsing_not_query_multi_pattern() throws Exception {
        // given
        String query = "a|b-c";

        // when & then
        assertThrows(InvalidSearchPatternException.class, () -> BookSearchParsingUtil.parsingAndCheck(query));
    }

}