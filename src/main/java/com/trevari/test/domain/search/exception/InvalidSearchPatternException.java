
package com.trevari.test.domain.search.exception;

import com.trevari.test.exception.TrevariException;

public class InvalidSearchPatternException extends TrevariException {
    public InvalidSearchPatternException() {
        super(404, "query", "검색 쿼리 오류. 'a|b' or 'a-b'만 허용합니다.");
    }
}
