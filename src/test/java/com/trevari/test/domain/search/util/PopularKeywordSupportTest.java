package com.trevari.test.domain.search.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.trevari.test.domain.search.util.PopularKeywordSupport.normalize;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PopularKeywordSupportTest {

    @Test
    @DisplayName("키워드 normalize 테스트")
    void normalize_test() {
        // given
        String keyword = "Alpha";

        // when
        String normalized = normalize(keyword);

        // then
        assertThat(normalized).isEqualTo("alpha");
    }
}